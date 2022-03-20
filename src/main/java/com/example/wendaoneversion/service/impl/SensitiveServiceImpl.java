package com.example.wendaoneversion.service.impl;

import com.example.wendaoneversion.controller.LoginController;
import org.apache.tomcat.util.buf.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.commons.lang3.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@Service
public class SensitiveServiceImpl implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveServiceImpl.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt=bufferedReader.readLine())!=null){
                addWord(lineTxt.trim());
            }
            reader.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }
    //增加词
    private void addWord(String lineTxt ){
        TrieNode tempNode = rootNode;
        for (int i=0;i<lineTxt.length();i++){
            Character c = lineTxt.charAt(i);
            if (isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);

            if (node==null){
                node = new TrieNode();
                tempNode.addSubNode(c,node);
            }
            tempNode = node;

            if (i == lineTxt.length() - 1) {
                // 关键词结束， 设置结束标志
                tempNode.setKeywordEnd(true);
            }
        }
    }
    private class TrieNode{
            //是不是关键词的结尾
            private boolean end = false;

            private Map<Character,TrieNode> subNodes = new HashMap<Character,TrieNode>();

            public void addSubNode(Character key,TrieNode node){
                        subNodes.put(key,node);
            }

            TrieNode getSubNode(Character key){
                return  subNodes.get(key);
            }

            boolean isKeywordEnd(){
                return  end;
            }
            void  setKeywordEnd(boolean end){
                this.end = end;
            }
        }
    private TrieNode rootNode = new TrieNode();

    private  boolean isSymbol(char c){
        int ic = (int)c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic> 0x9FFF);
    }
    public  String filter(String text){
        if (StringUtils.isEmpty(text)){
            return  text;
        }
        StringBuilder result= new StringBuilder();
        String replacement = "***";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position<text.length()){
            char c = text.charAt(position);
            if (isSymbol(c)){
                if (tempNode == rootNode){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);

            if (tempNode==null){
                result.append(text.charAt(begin));
                position=begin+1;
                begin = position;
                tempNode = rootNode;
            }else if(tempNode.isKeywordEnd()){
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            }else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    public static void main(String[] args) {
        SensitiveServiceImpl s = new SensitiveServiceImpl();
        s.addWord("尼玛");
        s.addWord("色情");
        s.addWord("你好社区色情");
        System.out.println(s.filter("尼 玛 的我祝你身体健康"));
    }
}
