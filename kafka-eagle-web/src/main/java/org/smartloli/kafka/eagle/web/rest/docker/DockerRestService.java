package org.smartloli.kafka.eagle.web.rest.docker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.web.utils.DockerRestResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dujijun on 2018/3/28.
 */
@Service
public class DockerRestService {
    private static final Logger logger = Logger.getLogger(DockerRestService.class);

    private static final String PREFIX = SystemConfigUtils.getProperty("docker.url.prefix");

    public String createImage(String path, String imageName){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("dfpath", "X:\\" + path.replaceAll("/", "\\\\"));
        params.put("imageName", imageName);
        String url = String.format("%s/image/createImage?dfpath={dfpath}&imageName={imageName}", PREFIX);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        return response.getBody();
    }

    public String runMonitorServiceOnDocker(String monitorGroupId,
                                            String imageName,
                                            String serviceName){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/service/createService?" +
                "monitorId={monitorId}&" +
                "imageName={imageName}&" +
                "serviceName={serviceName}", PREFIX);
        Map<String, Object> params = new HashMap<>();
        params.put("monitorId", monitorGroupId);
        params.put("imageName", imageName);
        params.put("serviceName", serviceName);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        return response.getBody();
    }

    public String stopMonitorServiceOnDocker(String serviceName){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/service/deleteServiceByName?serviceName={serviceName}", PREFIX);
        Map<String, String> params = new HashMap<>();
        params.put("serviceName", serviceName);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        return response.getBody();
    }

    public String deleteMonitorImage(String imageName){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/image/removeImage?imageName={imageName}", PREFIX);
        Map<String, String> params = new HashMap<>();
        params.put("imageName", imageName);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        return response.getBody();
    }

    public String getAllService(){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/service/getAllServices", PREFIX);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String getAllTasks(String serviceName, String state){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/service/getAllTasks?serviceName={serviceName}", PREFIX);
        Map<String, String> params = new HashMap<>();
        if (!StringUtils.isEmpty(state)) {
            url += "&state={state}";
            params.put("state", state);
        }
        params.put("serviceName", serviceName);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        return response.getBody();
    }

    public List<String> getAllImages(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://172.16.17.141:5000/v2/_catalog", String.class);
        List<String> images = JSON.parseObject(response.getBody())
                .getJSONArray("repositories")
                .toJavaList(String.class);
        return images;
    }

    public void cleanUselessImages(List<String> imageIds){
        List<String> imageIdsInDocker = getAllImages();

        // 去除仍在数据库中的id
        imageIdsInDocker.removeAll(imageIds);

        // 去除不以-image结尾的id(不是由monitor产生的)
        imageIdsInDocker.removeIf(id -> !id.endsWith("-image"));

        int i = 1;
        for(String imageId: imageIdsInDocker){
            Map<String, String> resMap = DockerRestResolver.resolveResult(deleteMonitorImage(imageId));
            if("200".equals(resMap.get("root.code")))
                logger.info(String.format("============成功删除第%d个image, imageId为%s==============", i, imageId));
            else
                logger.error(String.format("============删除第%d个image失败, 结果为: %s", i, resMap.get("data")));
            i++;
        }
    }

}
