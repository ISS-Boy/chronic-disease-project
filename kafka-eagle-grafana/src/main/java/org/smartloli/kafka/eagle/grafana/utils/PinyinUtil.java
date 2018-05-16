package org.smartloli.kafka.eagle.grafana.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * Created by dujijun on 2018/5/7.
 */
public class PinyinUtil {
    public static String chineseToPinyin(String source) {
        char[] cs = source.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean hanyu = false;
        for (char c : cs) {
            if (c <= 128) {
                sb.append(c);
                hanyu = false;
            } else {
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
                String pinyin = pinyins[0].replaceAll("[^a-z]", "");
                System.out.println(pinyin);
                if (hanyu)
                    sb.append('-');
                sb.append(pinyin);
                hanyu = true;
            }
        }
        return sb.toString();
    }
}
