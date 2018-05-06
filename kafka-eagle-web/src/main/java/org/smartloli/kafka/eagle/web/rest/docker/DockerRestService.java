package org.smartloli.kafka.eagle.web.rest.docker;

import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dujijun on 2018/3/28.
 */
@Service
public class DockerRestService {
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


}
