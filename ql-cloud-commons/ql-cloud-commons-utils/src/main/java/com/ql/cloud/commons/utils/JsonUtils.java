/**
 * JsonUtils.java 2016年7月25日 上午10:05:16
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json工具类
 *
 * @author liusn
 * @date 2016年7月25日 上午10:06:17
 *
 */
public class JsonUtils {
    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        }
    }

    private JsonUtils() {}

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());
        }
        return map;
    }

    /**
     * json转map无级联关系
     * 
     * @param jsonStr
     * @return
     */
    public static Map<String, String> jsonToMap(String jsonStr) {
        Map<String, String> jsonMap = new HashMap<>();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        // 递归遍历
        decodeJSONObject(jsonObject, jsonMap);
        return jsonMap;
    }

    /**
     * 递归解析JSONOBJECT
     * 
     * @param json
     * @param jsonMap
     */
    private static void decodeJSONObject(JSONObject json, Map<String, String> jsonMap) {
        Iterator<String> keys = json.keys();
        JSONObject jo = null;
        JSONArray jsonArray = null;
        Object o;
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            o = json.get(key);
            if (o instanceof JSONObject) {
                jo = (JSONObject) o;
                if (jo.keySet().size() > 0) {
                    decodeJSONObject(jo, jsonMap);
                } else {
                    continue;
                }
            } else if (o instanceof JSONArray) {
                jsonArray = (JSONArray) o;
                decodeJSONArray(jsonArray, jsonMap);
            } else {
                jsonMap.put(key, String.valueOf(o));
                // System.out.println(o);
            }
        }
    }

    /**
     * 递归解析JSONARRAY
     * 
     * @param jsonArray
     * @param jsonMap
     */
    private static void decodeJSONArray(JSONArray jsonArray, Map<String, String> jsonMap) {
        if (jsonArray.size() > 0) {
            Object o = null;
            JSONObject jsonObject = null;
            JSONArray jsonArray1 = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                o = jsonArray.get(i);
                if (o instanceof JSONObject) {
                    jsonObject = (JSONObject) o;
                    decodeJSONObject(jsonObject, jsonMap);
                } else if (o instanceof JSONArray) {
                    jsonArray1 = (JSONArray) o;
                    decodeJSONArray(jsonArray1, jsonMap);
                }
            }
        }
    }
}
