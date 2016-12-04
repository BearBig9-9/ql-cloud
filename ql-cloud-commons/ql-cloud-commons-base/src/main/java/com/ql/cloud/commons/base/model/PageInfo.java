/**
 * PageInfo.java 2016年11月19日 16:44
 * <p>
 * Copyright (c) 2016-2021 qilong All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud.commons.base.model;

import java.util.List;

/**
 * pageInfo
 *
 * @author 齐龙
 * @date 2016年11月19日 16:44
 */
public class PageInfo<T> {

    private int pageNo = 1; // 目标页号

    private int startIndex = 0;

    private long totalNum = 0; // 记录总数

    private int perPageNum = 20; // 每页显示记录数

    private int allPage = 1; // 总页数

    private List<T> resultList;// 结果集合

    public PageInfo() {
    }

    public PageInfo(Integer pageNo) {
        if (pageNo == null || pageNo.intValue() == 0) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    public PageInfo(Integer pageNo, Integer perPageNum) {
        if (pageNo == null || pageNo.intValue() == 0) {
            pageNo = 1;
        }
        if (perPageNum == null || perPageNum.intValue() == 0) {
            perPageNum = 15;
        }
        this.pageNo = pageNo;
        this.perPageNum = perPageNum;
    }

    /**
     * @param pageNo     目标页号
     * @param totalNum   总记录数
     * @param perPageNum 每页记录数
     */
    public PageInfo(int pageNo, long totalNum, int perPageNum) {
        this.pageNo = pageNo;
        this.totalNum = totalNum;
        this.perPageNum = perPageNum;
        this.calculateAllPage();
    }

    public int getAllPage() {
        return allPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    /**
     * 取得目标页的开始记录位置索引
     *
     * @return
     */
    public int getStartIndex() {
        startIndex = (this.pageNo - 1) * this.perPageNum;
        return startIndex;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;

    }

    public void setPerPageNum(int perPageNum) {
        this.perPageNum = perPageNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
        this.calculateAllPage();
    }

    /**
     * 计算全部页数
     */
    public void calculateAllPage() {
        /*
         * this.allPage = (int) Math.ceil((this.totalNum + this.perPageNum - 1)
         * / this.perPageNum);
         */
        if (this.totalNum == 0) {
            this.allPage = 1;
        } else if (this.totalNum % this.perPageNum == 0) {
            this.allPage = (int) this.totalNum / this.perPageNum;
        } else {
            this.allPage = (int) this.totalNum / this.perPageNum + 1;
        }
    }
}
