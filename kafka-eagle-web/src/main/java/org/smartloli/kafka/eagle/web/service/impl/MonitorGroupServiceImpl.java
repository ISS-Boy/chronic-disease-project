package org.smartloli.kafka.eagle.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.mail.iap.ConnectionException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.dao.MonitorGroupDao;
import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.smartloli.kafka.eagle.web.grafana.service.GrafanaDashboardService;
import org.smartloli.kafka.eagle.web.json.pojo.BlockGroup;
import org.smartloli.kafka.eagle.web.json.pojo.BlockValues;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.rest.docker.DockerRestService;
import org.smartloli.kafka.eagle.web.rest.pojo.JarEntity;
import org.smartloli.kafka.eagle.web.rest.streams.StreamService;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.DockerRestResolver;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

/**
 * Created by dujijun on 2018/4/9.
 */
@Service
public class MonitorGroupServiceImpl implements MonitorGroupService {

    private static Logger logger = Logger.getLogger(MonitorGroupServiceImpl.class);

    @Autowired
    private MonitorGroupDao monitorGroupDao;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private StreamService streamService;

    @Autowired
    private DockerRestService dockerRestService;

    @Autowired
    private GrafanaDashboardService grafanaDashboardService;

    @Override
    public List<MonitorGroup> getAllMonitorGroups() {
        return monitorGroupDao.getAllMonitorGroups();
    }

    @Override
    @Transactional
    public boolean addMonitorGroupAndMonitor(MonitorGroup monitorGroup, List<Monitor> monitors) {
        int n = addMonitorGroup(monitorGroup);
        if (n != 1)
            return false;
        n = addMonitors(monitors);
        if (n != monitors.size())
            return false;
        return true;
    }

    @Override
    public int addMonitorGroup(MonitorGroup monitorGroup) {
        return monitorGroupDao.insertMonitorGroup(monitorGroup);
    }

    @Override
    public int addMonitors(List<Monitor> monitors) {
        int count = 0;
        for (Monitor monitor : monitors) {
            count += monitorService.addMonitor(monitor);
        }
        return count;
    }

    @Override
    public MonitorGroup getMonitorGroupById(String monitorGroupId) {
        return monitorGroupDao.getMonitorGroupById(monitorGroupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMonitorGroupState(String state, String monitorGroupId) {
        return monitorGroupDao.updateMonitorGroup(state, monitorGroupId);
    }

    @Override
    public int deleteMonitorGroupById(String monitorGroupId) {
        return monitorGroupDao.deleteMonitorGroupById(monitorGroupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ValidateResult createImage(String creator, BlockGroup blockGroup) throws IOException {

        logger.info("==========开始创建镜像===========");

        List<BlockValues> blocksEntity = blockGroup.getBlockValues();

        // 封装monitorGroup
        String monitorGroupId = creator + "-" + System.currentTimeMillis();
        String monitorGroupName = blockGroup.getBlockGroupName();
        Date createTime = Date.from(Instant.now());
        String state = "uncreated";
        String imageId = monitorGroupId + "-image";
        String serviceId = monitorGroupId + "-service";

        MonitorGroup monitorGroup = new MonitorGroup(monitorGroupId,
                                                    monitorGroupName,
                                                    createTime,
                                                    creator,
                                                    state,
                                                    imageId,
                                                    serviceId);

        // 封装monitor
        List<Monitor> monitors = new ArrayList<>();

        // 这里会轮询传递进来的monitor做封装
        List<String> monitorIdList = new ArrayList<>();

        // monitor命名规范: monitorGroupId-*
        for (int i = 0; i < blocksEntity.size(); i++)
            monitorIdList.add(String.format("%s-%d", monitorGroupId, i));

        String monitorIds = String.join(",", monitorIdList);

        logger.info("==========开始获取jar包===========");
        // 调用黄章昊接口，获取jar包对应路径
        String path = streamService.getJarFromStreamingPeer(blockGroup, monitorGroupId, creator);
//        String path = "the-user-1/the-user-1-1525337364460";
        monitorGroup.setPath(path);

        for (int i = 0; i < blocksEntity.size(); i++) {
            BlockValues block = blocksEntity.get(i);

            Monitor monitor = new Monitor(monitorIdList.get(i),
                                        block.getMonitorName(),
                                        monitorGroupId,
                                        JSON.toJSON(block).toString());

            monitors.add(monitor);
        }

        logger.info("==========开始获取进行数据存储===========");
        // 封装对象，存数据库
        boolean result = addMonitorGroupAndMonitor(monitorGroup, monitors);
        if (!result)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "数据存储异常");

        logger.info("==========开始Docker调用===========");
        Map<String, String> resMes = DockerRestResolver.resolveResult(dockerRestService.createImage(path, imageId));

        // 如果调用失败
        if (!"200".equals(resMes.get("root.code")))
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, resMes.get("root.msg"));

        // 如果调用成功
        int res = updateMonitorGroupState("stopped", monitorGroupId);
        if (res == 1)
            return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "创建镜像成功");
        else
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "状态更新失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ValidateResult runService(String monitorGroupId){
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);
        // 如果已经开启, 则直接返回
        if("started".equals(monitorGroup.getState()))
            return new ValidateResult(ValidateResult.ResultCode.WARNING, "服务已经开启");

        int res = monitorGroupDao.updateMonitorGroup("started", monitorGroupId);
        logger.info("==========数据库写入成功==========");

        // 数据更新异常
        if(res != 1)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "数据更新失败");

        String response = dockerRestService.runMonitorServiceOnDocker(
                monitorGroup.getMonitorGroupId(),
                monitorGroup.getImageId(),
                monitorGroup.getServiceId());
        Map<String, String> resMes = DockerRestResolver.resolveResult(response);
        if("200".equals(resMes.get("root.code")))
            return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "服务运行成功");
        else
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "服务运行失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ValidateResult deleteMonitorGroup(String monitorGroupId) throws IOException {
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);
        // 由于设定外键级联删除，所以这里不需要手动删除monitor
        int n = deleteMonitorGroupById(monitorGroupId);
        if(n != 1)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "数据删除失败");
        logger.info("========数据删除完成========");

        // 删除grafana dashboard
//        ValidateResult deleteResult = grafanaDashboardService.deleteDashboard(monitorGroupId);
//        if(deleteResult.getResultCode() != ValidateResult.ResultCode.SUCCESS)
//            throw new NormalException(deleteResult.getMes()) ;
//        logger.info("========dashboard删除完成========");

        // 删除image文件
        //String path = monitorGroup.getPath();
        //if(StringUtils.isBlank(path) ||c
        //        Files.deleteIfExists(Paths.get(monitorGroup.getPath())))
        //    return new ValidateResult(ValidateResult.ResultCode.FAILURE, "删除镜像文件失败!");
        // logger.info("========image文件删除完成========")

//        String res = dockerRestService.deleteMonitorImage(monitorGroup.getImageId());
//        logger.info(res);
//        Map<String, String> resMes = DockerRestResolver.resolveResult(res);
//        if(!"200".equals(resMes.get("root.code")))
//            throw new NormalException("远程镜像删除失败");

        return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "image删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ValidateResult stopMonitorGroupService(String monitorGroupId) throws ConnectionException {
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);

        ValidateResult success = new ValidateResult(ValidateResult.ResultCode.SUCCESS, "服务停止成功");
        // 如果状态没有开启，则直接回复校验成功结果
        if(!"started".equals(monitorGroup.getState()))
            return success;

        int n = monitorGroupDao.updateMonitorGroup("stopped", monitorGroupId);
        if(n != 1)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "状态更新失败");

        logger.info("==========容器状态更新=========");
        String res = dockerRestService.stopMonitorServiceOnDocker(monitorGroup.getServiceId());
        Map<String, String> resMes = DockerRestResolver.resolveResult(res);
        if("200".equals(resMes.get("root.code")))
            return success;
        else
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "服务停止失败");
    }

    @Override
    public ValidateResult createMonitorDashBoardAndGetUrl(String monitorGroupId) {
        // 判断如果服务没有开启，则抛出异常
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);
        if(!"started".equals(monitorGroup.getState()))
            throw new NormalException("服务未开启，请先开启服务");

        List<Monitor> monitors = monitorService.getAllMonitorByGroupId(monitorGroupId);

        return grafanaDashboardService.createDashboardAndGetUrl(monitorGroupId, monitorGroup, monitors);
    }
}
