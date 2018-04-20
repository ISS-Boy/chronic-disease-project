package org.smartloli.kafka.eagle.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dujijun on 2018/3/28.
 */
public class DockerRestResolver {

    public static Map<String, String> resolveResult(String restResult){
        JSONObject jsonObject = JSON.parseObject(restResult);
        Map<String, String> resMes = new HashMap<>();
        recursiveFlat(jsonObject, resMes, "root");
        return resMes;
    }

    private static void recursiveFlat(JSONObject json, Map<String, String> resMes, String prefix){
        json.forEach((k, v) -> {
            String pre = prefix + "." + k;
            if(v instanceof JSONObject)
                recursiveFlat((JSONObject) v, resMes, pre);
            else if (v instanceof JSONArray){
                // 对数组数据进行处理
            }else
                resMes.put(pre, v == null? "null" : v.toString());
        });
    }
}
