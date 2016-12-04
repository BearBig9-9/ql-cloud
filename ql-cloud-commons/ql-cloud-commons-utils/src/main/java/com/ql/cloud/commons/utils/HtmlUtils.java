/**
 * HtmlUtils.java 2016年7月21日 下午5:53:45
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * html工具类
 *
 * @author hezhifang
 * @date 2016年7月21日 下午5:53:45
 */
public class HtmlUtils {

    /**
     * 删除Html标签
     * 
     * @param inputString
     * @return
     */
    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern pScript;
        Matcher mScript;
        Pattern pStyle;
        Matcher mStyle;
        Pattern pHtml;
        Matcher mHtml;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            pScript = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            mScript = pScript.matcher(htmlStr);
            htmlStr = mScript.replaceAll(""); // 过滤script标签
            pStyle = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            mStyle = pStyle.matcher(htmlStr);
            htmlStr = mStyle.replaceAll(""); // 过滤style标签
            pHtml = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            mHtml = pHtml.matcher(htmlStr);
            htmlStr = mHtml.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

}
