package org.smartloli.kafka.eagle.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.mail.iap.ConnectionException;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.dao.MonitorGroupDao;
import org.smartloli.kafka.eagle.web.exception.entity.InternalException;
import org.smartloli.kafka.eagle.web.grafana.service.GrafanaDashboardService;
import org.smartloli.kafka.eagle.web.json.pojo.BlockGroup;
import org.smartloli.kafka.eagle.web.json.pojo.BlockValues;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.rest.docker.DockerRestService;
import org.smartloli.kafka.eagle.web.rest.streams.StreamService;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.DataConsistencySyncronizer;
import org.smartloli.kafka.eagle.web.utils.DockerRestResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
    public void createImage(String creator, BlockGroup blockGroup) throws IOException {
        try {
            DataConsistencySyncronizer.lockForCreatingMonitor();

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
                throw new InternalException("数据存储异常");

            logger.info("==========开始Docker调用===========");
            Map<String, String> resMes = DockerRestResolver.resolveResult(dockerRestService.createImage(path, imageId));

            // 如果调用失败
            if (!"200".equals(resMes.get("root.code")))
                throw new InternalException(resMes.get("root.msg"));

            // 如果调用成功
            int res = updateMonitorGroupState("stopped", monitorGroupId);
            if(res != 1)
                throw new InternalException("状态更新失败!");
        } finally {
            DataConsistencySyncronizer.releaseLockOfCreatingMonitor();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runService(String monitorGroupId){
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);

        // 如果已经开启, 则直接返回
        if("started".equals(monitorGroup.getState())) {
            logger.info("服务已经开启, 无需重复启动!");
            return;
        }
        int res = monitorGroupDao.updateMonitorGroup("started", monitorGroupId);

        // 数据更新异常
        if(res != 1)
            throw new InternalException("数据库更新状态失败!");

        logger.info("==========数据库写入成功==========");

        String response = dockerRestService.runMonitorServiceOnDocker(
                monitorGroup.getMonitorGroupId(),
                monitorGroup.getImageId(),
                monitorGroup.getServiceId());
        Map<String, String> resMes = DockerRestResolver.resolveResult(response);
        if(!"200".equals(resMes.get("root.code")))
            throw new InternalException("服务运行失败: " + resMes.get("root.msg"));

        logger.info(monitorGroupId + ": 服务运行成功");
    }

    // 注意: 删除MonitorGroup只删除数据库信息, 保证MonitorService关闭
    // 至于MonitorGroup镜像的删除, Grafana的清理以及文件系统的清理, 应该由后台清理线程去完成
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMonitorGroup(String monitorGroupId) throws IOException {

        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);

        // 由于设定外键级联删除，所以这里不需要手动删除monitor
        int n = deleteMonitorGroupById(monitorGroupId);
        if(n != 1)
            throw new InternalException("数据删除失败！");

        Map<String, String> resMap = DockerRestResolver.resolveResult(dockerRestService.deleteMonitorImage(monitorGroup.getImageId()));
        String resultCode = resMap.get("root.code");
        if(!"200".equals(resultCode)) {
            if (!"203".equals(resultCode))
                throw new InternalException("Image清除: " + resMap.get("root.msg"));
            else
                logger.info("ImageId不存在, Image已清除! ");
        }
        logger.info("========数据删除完成========");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopMonitorGroupService(String monitorGroupId) throws ConnectionException {
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);

        // 如果状态没有开启，则直接回复校验成功结果
        if(!"started".equals(monitorGroup.getState()))
            return;

        int n = monitorGroupDao.updateMonitorGroup("stopped", monitorGroupId);
        if(n != 1)
            throw new InternalException("状态更新失败");


        logger.info("==========容器状态更新=========");
        String res = dockerRestService.stopMonitorServiceOnDocker(monitorGroup.getServiceId());
        Map<String, String> resMes = DockerRestResolver.resolveResult(res);
        if(!"200".equals(resMes.get("root.code")))
            throw new InternalException(monitorGroupId + ": 服务停止失败! " + resMes.get("root.msg"));
    }

    @Override
    public List<String> createMonitorDashBoardAndGetUrl(String monitorGroupId) {
        // 判断如果服务没有开启，则抛出异常
        MonitorGroup monitorGroup = getMonitorGroupById(monitorGroupId);
        if(!"started".equals(monitorGroup.getState()))
            throw new InternalException("服务未开启，请先开启服务");

        List<Monitor> monitors = monitorService.getAllMonitorByGroupId(monitorGroupId);

        return grafanaDashboardService.createDashboardAndGetUrl(monitorGroupId, monitorGroup, monitors);
    }

    @Override
    public void cleanUselessResources() {
        try {
            // 开启数据一致锁
            DataConsistencySyncronizer.lockForDeletingResources();

            // 清理无用的Docker镜像
            List<MonitorGroup> monitorGroups = getAllMonitorGroups();
            List<String> imageIds = monitorGroups.stream()
                    .map(MonitorGroup::getImageId)
                    .collect(Collectors.toList());
//            dockerRestService.cleanUselessImages(imageIds);

            // 清理无用的Grafana仪表板
            List<String> monitorGroupIds = monitorGroups.stream()
                    .map(MonitorGroup::getMonitorGroupId)
                    .collect(Collectors.toList());
            grafanaDashboardService.cleanUselessDashboard(monitorGroupIds);

            // 清理无用的文件镜像
            List<String> userIdAndMonitorGroupIds = monitorGroups.stream()
                    .map(m -> m.getCreator() + "-" + m.getMonitorGroupId())
                    .collect(Collectors.toList());
            streamService.cleanUselessStreamFiles(userIdAndMonitorGroupIds);

        } catch(Exception e) {
            // 防止异常抛出到外层被ExceptionHandler接受并进行处理
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            DataConsistencySyncronizer.releaseLockOfDeletingResources();
        }
    }
}
