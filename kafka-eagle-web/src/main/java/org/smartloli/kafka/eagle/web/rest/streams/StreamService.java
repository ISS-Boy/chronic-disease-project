package org.smartloli.kafka.eagle.web.rest.streams;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.web.json.pojo.BlockGroup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StreamService {

    private static final Logger logger = Logger.getLogger(StreamService.class);

    private static final String DEFAULT_URL = SystemConfigUtils.getProperty("kstream.url.prefix");

    private static final String PATH_PREFIX = SystemConfigUtils.getProperty("nfs.path.prefix");

    public String getJarFromStreamingPeer(BlockGroup blockGroup,
                                          String monitorGroupId,
                                          String creator) throws IOException {

        // 从请求当中获取nfs的path, 然后从nfs path中读取文件
        String path = getUrlAndRunKStream(blockGroup, monitorGroupId, creator);

        logger.info(path);
        return path;
    }

    public String getUrlAndRunKStream(BlockGroup monitorGroup, String monitorGroupId, String creator){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        // 封装对象JSON
        String params = JSON.toJSONString(new RequestJsonClass(monitorGroup, monitorGroupId, creator));
        HttpEntity<String> entity = new HttpEntity<>(params, headers);
        logger.info("========" + params + "========");
        ResponseEntity<String> response = restTemplate.postForEntity(DEFAULT_URL, entity, String.class);
        return response.getBody();
    }

    private static class RequestJsonClass{
        BlockGroup blockGroup;
        String monitorGroupId;
        String userId;

        public RequestJsonClass(BlockGroup blockGroup, String monitorGroupId, String userId) {
            this.blockGroup = blockGroup;
            this.monitorGroupId = monitorGroupId;
            this.userId = userId;
        }

        public BlockGroup getBlockGroup() {
            return blockGroup;
        }

        public void setBlockGroup(BlockGroup blockGroup) {
            this.blockGroup = blockGroup;
        }

        public String getMonitorGroupId() {
            return monitorGroupId;
        }

        public void setMonitorGroupId(String monitorGroupId) {
            this.monitorGroupId = monitorGroupId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public void cleanUselessStreamFiles(List<String> userIdAndMonitorGroupId){
        logger.info("清理中...");

        //Paths.get(PATH_PREFIX)
    }

}
