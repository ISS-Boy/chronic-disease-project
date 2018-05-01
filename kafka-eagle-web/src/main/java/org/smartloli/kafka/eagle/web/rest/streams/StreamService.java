package org.smartloli.kafka.eagle.web.rest.streams;

import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.smartloli.kafka.eagle.web.rest.pojo.JarEntity;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class StreamService {

    private static final String DEFAULT_URL = SystemConfigUtils.getProperty("kstream.url.prefix");

    private static final String PATH_PREFIX = SystemConfigUtils.getProperty("nfs.path.prefix");

    public String getJarFromStreamingPeer(String monitorGroupId,
                                                  String imageId,
                                                  String creator) throws IOException {

        // 从请求当中获取nfs的path, 然后从nfs path中读取文件
        //String path = "/Users/dujijun/Documents/tmp/nfsTest/nfs/" + getUrlAndRunKStream(monitorGroupId, creator);
        String path = PATH_PREFIX + "KSTREAM_TEST2";

        return path;
    }

    public String getUrlAndRunKStream(String monitorGroupId, String creator){
        RestTemplate restTemplate = new RestTemplate();
        String params = String.format("monitorGroupId=%s\nuserId=%s", monitorGroupId, creator);
        ResponseEntity<String> response = restTemplate.postForEntity(DEFAULT_URL, params, String.class);
        return response.getBody();
    }

}
