package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.json.pojo.BlockGroup;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by dujijun on 2018/4/9.
 */
public interface MonitorGroupService {

    List<MonitorGroup> getAllMonitorGroups();

    boolean addMonitorGroupAndMonitor(MonitorGroup monitorGroup, List<Monitor> monitor);

    int addMonitorGroup(MonitorGroup monitorGroup);

    int addMonitors(List<Monitor> monitors);

    MonitorGroup getMonitorGroupById(String monitorGroupId);

    int updateMonitorGroupState(String state, String monitorGroupId);

    int deleteMonitorGroupById(String MonitorGroupId);


    /**
     * 创建MonitorGroup
     * 1、初始化Id，封装对象
     * 2、从KStream端获取jar包
     * 3、将Jar包一起写入封装对象中
     * 4、写入数据库
     * 5、调用DockerService生成Image
     * @param creator 创建者
     * @param blockGroup 监视器块组
     * @return 创建结果
     * @throws IOException
     */
    ValidateResult createImage(String creator, BlockGroup blockGroup) throws IOException;


    /**
     * 运行Service
     * @param monitorGroupId
     * @return 运行结果
     */
    ValidateResult runService(String monitorGroupId);

    /**
     * 删除MonitorGroup
     * 1、首先判断是否Monitor是否处于开启状态，如果是开启状态，则关闭，否则直接跳过
     * 2、删除数据库内容
     * 3、删除DockerImage
     * 如中途发生异常，则回滚数据库
     * @param monitorGroupId
     * @return 校验结果
     */
    ValidateResult deleteMonitorGroup(String monitorGroupId) throws IOException;

    /**
     * 停止MonitorGroup服务，如果本身Monitor就是关闭状态，则不发生改变
     * @param monitorGroupId
     * @return 校验结果
     * @throws Exception
     */
    ValidateResult stopMonitorGroupService(String monitorGroupId) throws Exception;

    /**
     * 创建仪表板然后获取仪表板url
     * @param monitorGroupId
     * @return 校验结果，attach中含有UrlList
     */
    ValidateResult createMonitorDashBoardAndGetUrl(String monitorGroupId);

    /**
     * 清理无用的Images
     */
    void cleanUselessResources();
}
