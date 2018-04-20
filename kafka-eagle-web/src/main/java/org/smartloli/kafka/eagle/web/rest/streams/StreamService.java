package org.smartloli.kafka.eagle.web.rest.streams;

import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.smartloli.kafka.eagle.web.rest.pojo.JarEntity;
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

    public JarEntity getJarFromStreamingPeer(String monitorGroupId,
                                             String imageId,
                                             int size) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        // 从请求当中获取nfs的path, 然后从nfs path中读取文件
        String path = "/Users/dujijun/Documents/tmp/nfsTest/nfs/KSTREAM_TEST";

        List<byte[]> jarBytes = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles(f -> f.getName().endsWith(".jar"));
        if(size != files.length)
            throw new NormalException("执行计划生成异常：监视器与流计算程序数量不一致");

        for(File f: files){
            InputStream in = new FileInputStream(f);
            byte[] jar = new byte[in.available()];
            in.read(jar);
            jarBytes.add(jar);
        }
        return new JarEntity(jarBytes, path);
    }

}
