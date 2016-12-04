/**   
* MessageUtils.java  2016年11月11日 下午1:34:07 
*
* Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
*
* @Description 
* @version 1.0   
*
*/ 
package com.ql.cloud.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* 消息工具类
*
* @author hll
* @date 2016年11月11日 下午1:34:07 
*/
public class MessageUtils {
    private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
    
    public static String createKey(String messageTag){
        if(messageTag == null || "".equals(messageTag)){
            messageTag = "default";
        }
        String dateStr = dateFormat.format(new Date());
        int random = (int) ((Math.random()*9+1)*100000);
        
        String key = messageTag + dateStr + random;
        
        logger.info("{}消息生成key={}", messageTag, key);
        return key;
    }
    
    public static void main(String[] args){
        System.out.println(MessageUtils.createKey(null));
        System.out.println(MessageUtils.createKey("hall"));
    }
}


