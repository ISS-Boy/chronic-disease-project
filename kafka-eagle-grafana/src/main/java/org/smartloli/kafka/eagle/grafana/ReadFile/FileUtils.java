package org.smartloli.kafka.eagle.grafana.ReadFile;


import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * 文件操作代码
 *
 * @author cn.outofmemory
 * @date 2013-1-7
 */
public class FileUtils {
    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    public static void writeToFile(String strFilename, String strBuffer) {
        try {
            // 创建文件对象
            File file = ResourceUtils.getFile(strFilename);
//            File file = new File(strFilename);
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(file);
            //将""转化为null,否则无法显示图像
            strBuffer = strBuffer.replace("\"\"", "null");
            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, filePath);
        return sb.toString();
    }
}
