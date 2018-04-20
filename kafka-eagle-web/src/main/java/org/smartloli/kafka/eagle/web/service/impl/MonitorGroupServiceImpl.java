package org.smartloli.kafka.eagle.web.service.impl;

import com.sun.mail.iap.ConnectionException;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.dao.MonitorGroupDao;
import org.smartloli.kafka.eagle.web.json.pojo.Block;
import org.smartloli.kafka.eagle.web.json.pojo.Source;
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
    @Transactional(rollbackFor = RuntimeException.class)
    public ValidateResult createImage(String creator, List<Block> blocksEntity) throws IOException {

        logger.info("==========开始创建镜像===========");

        // 封装monitorGroup
        String monitorGroupId = creator + "-" + System.currentTimeMillis();
        Date createTime = Date.from(Instant.now());
        String state = "uncreated";
        String imageId = monitorGroupId + "-image";
        String serviceId = monitorGroupId + "-service";

        MonitorGroup monitorGroup = new MonitorGroup(monitorGroupId, createTime, creator, state, imageId, serviceId);

        // 封装monitor
        List<Monitor> monitors = new ArrayList<>();

        // 这里会轮询传递进来的monitor做封装
        List<String> monitorIdList = new ArrayList<>();

        // monitor命名规范: monitorGroupId-*
        for (int i = 0; i < blocksEntity.size(); i++)
            monitorIdList.add(String.format("%s-%d", monitorGroupId, i));

        String monitorIds = String.join(",", monitorIdList);

        logger.info("==========开始获取jar包===========");
        // 调用黄章昊接口，获取jar包和对应的路径
        JarEntity jars = streamService.getJarFromStreamingPeer(monitorIds, imageId, monitorIdList.size());

        for (int i = 0; i < blocksEntity.size(); i++) {
            // 这里需要重新规划一下
            String metricName = blocksEntity.get(i)
                    .getSource()
                    .stream()
                    .map(Source::getSourceName)
                    .reduce((x, y) -> x + "," + y).orElse("");

            Monitor monitor = new Monitor(monitorIdList.get(i),
                    monitorGroupId,
                    blocksEntity.get(i).toString(),
                    metricName,
                    jars.getJar().get(i));

            monitors.add(monitor);
        }

        logger.info("==========开始获取进行数据存储===========");
        // 封装对象，存数据库
        boolean result = addMonitorGroupAndMonitor(monitorGroup, monitors);
        if (!result)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "数据存储异常");

        logger.info("==========开始Docker调用===========");
        Map<String, String> resMes = DockerRestResolver.resolveResult(dockerRestService.createImage(jars.getPath(), imageId));

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
    @Transactional
    public ValidateResult deleteMonitorGroup(String monitorGroupId) {
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);
        // 由于设定外键级联删除，所以这里不需要手动删除monitor
        int n = deleteMonitorGroupById(monitorGroupId);
        if(n != 1)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "数据删除失败");
        logger.info("========数据删除完成========");

        String res = dockerRestService.deleteMonitorImage(monitorGroup.getImageId());
        logger.info(res);
        Map<String, String> resMes = DockerRestResolver.resolveResult(res);
        if("200".equals(resMes.get("root.code")))
            return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "镜像删除成功");
        else
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "镜像删除失败");
    }

    @Override
    @Transactional(rollbackFor = ConnectionException.class)
    public ValidateResult stopMonitorGroupService(String monitorGroupId) throws ConnectionException{
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);

        int n = monitorGroupDao.updateMonitorGroup("stopped", monitorGroupId);
        if(n != 1)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "状态更新失败");

        logger.info("==========容器状态更新=========");
        String res = dockerRestService.stopMonitorServiceOnDocker(monitorGroup.getServiceId());
        Map<String, String> resMes = DockerRestResolver.resolveResult(res);
        if("200".equals(resMes.get("root.code")))
            return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "服务停止成功");
        else
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "服务停止失败");
    }

    @Override
    public ValidateResult showMonitorDashBoard(String monitorGroupId) {

        return null;
    }
}
