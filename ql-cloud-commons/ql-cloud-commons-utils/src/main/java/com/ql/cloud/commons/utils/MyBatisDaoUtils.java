/**
 * MyBatisDaoUtils.java 2016年7月25日 上午10:05:16
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;

/**
 * MyBatisDao工具类
 *
 * @author liusn
 * @date 2016年7月25日 上午10:06:17
 *
 */
public final class MyBatisDaoUtils {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected static final Log log = LogFactory.getLog(MyBatisDaoUtils.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private MyBatisDaoUtils() {}

    /**
     * 根据对象找到主键名称
     * 
     * @param 对象
     * @return id名称
     */
    protected static String getPrimaryKeyFieldName(Object o) {
        Field[] fieldlist = o.getClass().getDeclaredFields();
        String fieldName = null;
        for (Field fld : fieldlist) {
            if (fld.getName().equals("id") || fld.getName().indexOf("Id") > -1) {
                fieldName = fld.getName();
                break;
            }
        }
        return fieldName;
    }

    /**
     * Get the object type of the primary key
     * 
     * @param o the object to examine
     * @return the class type
     */
    protected static Class<?> getPrimaryKeyFieldType(Object o) {
        Field[] fieldlist = o.getClass().getDeclaredFields();
        Class<?> fieldType = null;
        for (Field fld : fieldlist) {
            if (fld.getName().equals("id") || fld.getName().indexOf("Id") > -1 || fld.getName().equals("version")
                    || fld.getName().equals("fact_id")) {
                fieldType = fld.getType();
                break;
            }
        }
        return fieldType;
    }

    /**
     * 根据泛型对象，反射getId()方法
     * 
     * @param 泛型对象
     * @return getId()对象
     */
    public static Object getPrimaryKeyValue(Object o) {
        String fieldName = getPrimaryKeyFieldName(o);
        String getterMethod = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            Method getMethod = o.getClass().getMethod(getterMethod, (Class[]) null);
            return getMethod.invoke(o, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not invoke method '" + getterMethod + "' on " + ClassUtils.getShortName(o.getClass()));
        }
        return null;
    }

    /**
     * 添加修改预处理操作
     */
    public static void prepareObjectForSaveOrUpdate(Object o) {
        // try {
        // Field[] fieldlist = o.getClass().getDeclaredFields();
        // for (Field fld : fieldlist) {
        // String fieldName = fld.getName();
        // if (fieldName.equals("version")) {
        // Method setMethod = o.getClass().getMethod("setVersion", Integer.class);
        // Object value = o.getClass().getMethod("getVersion", (Class[]) null).invoke(o, (Object[])
        // null);
        // if (value == null) {
        // setMethod.invoke(o, 1);
        // } else {
        // setMethod.invoke(o, (Integer) value + 1);
        // }
        // }
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // log.error("Could not prepare '" + ClassUtils.getShortName(o.getClass()) +
        // "' for insert/update");
        // }
    }

    /**
     * Sets the primary key's value
     * 
     * @param o the object to examine
     * @param clazz the class type of the primary key
     * @param value the value of the new primary key
     */
    protected static void setPrimaryKey(Object o, Class<?> clazz, Object value) {
        String fieldName = getPrimaryKeyFieldName(o);
        String setMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        try {
            Method setMethod = o.getClass().getMethod(setMethodName, clazz);
            if (value != null) {
                setMethod.invoke(o, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(MessageFormat.format("Could not set ''{0}.{1} with value {2}", ClassUtils.getShortName(o.getClass()), fieldName,
                    value));
        }
    }

    /**
     * 根据泛型实体名称，返回查询列表方法名称
     * 
     * @param 泛型实体类名
     * @return "get" + className + "list" ,列如：getUserList
     */
    public static String getSelectQuery(String className) {
        return "get" + className + "List";
    }

    /**
     * 根据泛型实体名称，返回分页查询方法名称
     * 
     * @param 泛型实体类名
     * @return "page" + className ,列如：pageUser
     */
    public static String getPageQuery(String className) {
        return "page" + className + "List";
    }

    public static String getCountListQuery(String className) {
        return "count" + className + "List";
    }

    public static String getCountsQuery(String className) {
        return "count" + className;
    }

    /**
     * 根据泛型实体名称，返回查询方法名称
     * 
     * @param 泛型实体类名
     * @return "getOne" + className ,列如：getOneUser
     */
    public static String getFindQuery(String className) {
        return "getOne" + className;
    }

    /**
     * 根据泛型实体名称，返回查询方法名称
     * 
     * @param 泛型实体类名
     * @return "get" + className ,列如：getUser
     */
    public static String getFindQueryByPK(String className) {
        return "getOne" + className + "ByPK";
    }


    /**
     * 根据泛型实体类，返回添加方法名称
     * 
     * @param 泛型实体类名
     * @return "save" + className ,列如：saveUser
     */
    public static String getInsertQuery(String className) {
        return "save" + className;
    }


    /**
     * 根据泛型实体类，返回修改方法名称
     * 
     * @param 泛型实体类名
     * @return "update" + className ,列如：updateUser
     */
    public static String getUpdateQuery(String className) {
        return "update" + className;
    }

    /**
     * 根据泛型实体类，返回删除方法名称
     * 
     * @param 泛型实体类名
     * @return "delete" + className ,列如：deleteUser
     */
    public static String getDeleteQuery(String className) {
        return "delete" + className;
    }


}
