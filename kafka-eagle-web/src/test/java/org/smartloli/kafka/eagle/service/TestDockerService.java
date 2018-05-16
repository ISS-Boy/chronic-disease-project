package org.smartloli.kafka.eagle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smartloli.kafka.eagle.web.rest.docker.DockerRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dujijun on 2018/4/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 对于多module项目来说需要classpath*
@ContextConfiguration("classpath*:spring-*.xml")
public class TestDockerService {

    @Autowired
    private DockerRestService dockerRestService;


    @Test
    public void testGetAllImages(){
        System.out.println(dockerRestService.getAllImages());
    }
}
