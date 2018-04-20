package org.smartloli.kafka.eagle.grafana.DashboardAPI;

import org.smartloli.kafka.eagle.grafana.utils.GrafanaConfigUtil;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DashboardAPI {
    //发送JSON字符串 如果成功则返回成功标识。
    public static int createdashboard(String urlPath, String filepath) throws IOException {
        int result = 0;

        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置文件类型:
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Authorization", "Bearer eyJrIjoiNkxLZ0E5dnoxaUZuajk1bzJtZlU3MHZzemJIMVRlWVciLCJuIjoidGVzdCIsImlkIjoxfQ==");
        conn.setRequestProperty("Authorization", GrafanaConfigUtil.getPropertyByKey("grafana.token"));

        // 往服务器里面发送数据
        OutputStream out = null;
        out = conn.getOutputStream();
        //json.json文件
        File file = ResourceUtils.getFile(filepath);
//        File file = new File(filepath);
        FileInputStream fileIn = new FileInputStream(file);
        byte[] bys = new byte[1024];
        Integer len = 0;
        while ((len = fileIn.read(bys)) != -1) {
            out.write(bys);
            //System.out.println(new String(bys,0,len));
        }
        //  System.out.println(conn.getResponseCode());
        result = conn.getResponseCode();
        conn.disconnect();
        out.close();
        return result;
    }

    public static int deletedashboard(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Authorization", "Bearer eyJrIjoiNkxLZ0E5dnoxaUZuajk1bzJtZlU3MHZzemJIMVRlWVciLCJuIjoidGVzdCIsImlkIjoxfQ==");
        conn.setRequestProperty("Authorization", GrafanaConfigUtil.getPropertyByKey("grafana.token"));
        //System.out.println(conn.getResponseCode());
        conn.disconnect();
        return conn.getResponseCode();

    }

    public static void getdashboard(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", GrafanaConfigUtil.getPropertyByKey("grafana.token"));
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String lines;
        while ((lines = reader.readLine()) != null) {
            System.out.println(lines);
        }
        reader.close();
        conn.disconnect();

    }


}