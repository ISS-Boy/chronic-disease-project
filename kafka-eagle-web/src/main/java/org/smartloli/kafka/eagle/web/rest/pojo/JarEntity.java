package org.smartloli.kafka.eagle.web.rest.pojo;

import java.util.List;

/**
 * Created by dujijun on 2018/3/28.
 */
public class JarEntity {
    private List<byte[]> jar;
    private String path;

    public JarEntity(List<byte[]> jar, String path) {
        this.jar = jar;
        this.path = path;
    }

    @Override
    public String toString() {
        return "JarEntity{" +
                "jar=" + jar +
                ", path='" + path + '\'' +
                '}';
    }

    public List<byte[]> getJar() {
        return jar;
    }

    public void setJar(List<byte[]> jar) {
        this.jar = jar;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
