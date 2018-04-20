package org.smartloli.kafka.eagle.web.pojo;

import java.util.List;

public class PageBean<T>{
    private int totalPage;    //总页数

    private int pageSize;       //每页显示的数据条数
    private int currentPage;        //当前页

    private int totalCount;         //总记录数
    private int startIndex;         //开始索引，从第几行开始查
    private List<T> datalist;           //每页要显示的数据放在list中
    private int start;              //分页显示的页数  start=1
    private int end;                //end=5或者其他

    public PageBean(int currentPage,int pageSize,int totalCount){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        this.totalPage=(int)Math.ceil(totalCount*1.0/pageSize);
        this.startIndex = (currentPage-1)*pageSize;
        this.start = 1;
        this.end = 5;
        if(totalPage<=5){
            this.end = totalPage;  //如果总页数小于5，那么end就为总页数的值
        }else{
            // 如果大于5，那么根据当前页是第几页，来判断start和end是多少了
            this.start = currentPage-2;    // !!!!!!!!!!为什么减2
            this.end = currentPage+2;
            if(start<0){
                this.start = 1;
                this.end = 5;
            }
            if(end>this.totalPage){
                this.end = totalPage;
                this.start= end-5;
            }
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<T> datalist) {
        this.datalist = datalist;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
