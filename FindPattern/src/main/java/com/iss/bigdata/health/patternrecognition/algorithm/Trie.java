package com.iss.bigdata.health.patternrecognition.algorithm;

import com.iss.bigdata.health.patternrecognition.util.CalcUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/4/24
 */
public class Trie {
    private Node root;

    public Trie() {
        root = new Node();
    }

    /**
     * 插入模式到树里
     * @param patternName 需要插入的模式
     */
    public void insert(String patternName) {
        Integer[] pn = CalcUtil.string2bs(patternName);
        insert(this.root, pn);
    }

    private void insert(Node root, Integer[] patternName){

        for(int i = 0, length = patternName.length; i < length; i++){

            int index = patternName[i];
            //如果不存在，则添加到map里
            if(!root.children.containsKey(index)) {
                root.children.put(index, new Node());
            }

            ///如果到了字串结尾，则做标记
            if(i == length - 1){
                root.isWord = true;
            }

            ///root指向子节点，继续处理
            root = root.children.get(index);
        }
    }

    /**
     * 判断模式是否包含在Trie树里了。包含：指不管存不存在，只要能找到，是一部分子树也算
     * 比如有aba，查ab时即使节点isWord为false也算包含
     * @param patternName 需要查询的模式
     */
    public boolean containsPattern(String patternName) {
        Integer[] pn = CalcUtil.string2bs(patternName);
        return search(this.root, pn);
    }

    private boolean search(Node root, Integer[] patternName) {

        for(int index : patternName){
            if(!root.children.containsKey(index)){
                ///如果不存在，则查找失败
                return false;
            }
            root = root.children.get(index);
        }

        return true;
    }

}

class Node {
    boolean isWord;
    Map<Integer, Node> children;

    Node() {
        this.isWord = false;
        this.children = new HashMap<Integer, Node>();
    }
}