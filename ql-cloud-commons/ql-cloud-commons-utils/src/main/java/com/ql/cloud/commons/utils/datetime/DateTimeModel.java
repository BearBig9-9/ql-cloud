/**
 * DateTimeModel.java 2016年11月22日 13:24
 * <p/>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.utils.datetime;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author 张凯停
 * @date 2016年11月22日 13:24
 */
public class DateTimeModel implements Serializable {

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
