package org.smartloli.kafka.eagle.grafana.utils;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by dujijun on 2018/4/16.
 */
public class GrafanaConfigUtil {
    private static Properties grafanaConfig;

    static {
        grafanaConfig = new Properties();
        getReources("server/grafana-rest.properties");
    }

    /** Load profiles from different operate systems. */
    private static void getReources(String name) {
        try {
            try {
                String osName = System.getProperties().getProperty("os.name");
                if (osName.contains("Mac") || osName.contains("Win")) {
                    grafanaConfig.load(GrafanaConfigUtil.class.getClassLoader().getResourceAsStream(name));
                } else {
                    grafanaConfig.load(new FileInputStream(System.getProperty("user.dir") + "/conf/" + name));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByKey(String key){
        return grafanaConfig.getProperty(key);
    }
}
