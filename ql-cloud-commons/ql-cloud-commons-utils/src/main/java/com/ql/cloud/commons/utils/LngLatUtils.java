/**
 * LngLatUtils.java 2015年12月29日 下午12:52:08
 *
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 *
 */
package com.ql.cloud.commons.utils;

import java.math.BigDecimal;

/**
 * 经纬度工具类
 * 
 * @author donghq
 * @version 3.0 2015年12月29日 下午12:52:09
 * 
 */
public class LngLatUtils {

    private static final BigDecimal MATH_PI = new BigDecimal(Math.PI);
    private static final BigDecimal NUM_180 = new BigDecimal(180);
    private static final BigDecimal NUM_2 = new BigDecimal(2);

    /**
     * 根据经纬度，获取两点间的距离
     * 
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return 两点之间距离 单位为m
     * */
    public static BigDecimal distanceByLngLat(BigDecimal lng1, BigDecimal lat1, BigDecimal lng2, BigDecimal lat2) {
        BigDecimal radLat1 = (lat1.multiply(MATH_PI)).divide(NUM_180, 6);
        BigDecimal radLat2 = (lat2.multiply(MATH_PI)).divide(NUM_180, 6);
        BigDecimal radLng1 = (lng1.multiply(MATH_PI)).divide(NUM_180, 6);
        BigDecimal radLng2 = (lng2.multiply(MATH_PI)).divide(NUM_180, 6);
        BigDecimal a = radLat1.subtract(radLat2);
        BigDecimal b = radLng1.subtract(radLng2);
        double s =
                2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a.divide(NUM_2).doubleValue()), NUM_2.doubleValue())
                        + Math.cos(radLat1.doubleValue()) * Math.cos(radLat2.doubleValue())
                        * Math.pow(Math.sin(b.divide(NUM_2).doubleValue()), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        BigDecimal result = new BigDecimal(s);
        return result;
    }

    /**
     * 根据经纬度，获取两点间的距离
     * 
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return 两点之间距离 单位为m
     * */
    public static BigDecimal distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        return LngLatUtils.distanceByLngLat(new BigDecimal(lng1), new BigDecimal(lat1), new BigDecimal(lng2), new BigDecimal(lat2));
    }

    public static void main(String[] args) throws Exception {
        System.out.println(LngLatUtils.distanceByLngLat(116.435386, 39.898819, 116.417528, 39.912919));
    }
}
