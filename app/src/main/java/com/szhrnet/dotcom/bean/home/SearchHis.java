package com.szhrnet.dotcom.bean.home;

/**
 * Created by Administrator on 2018/3/23.
 */

public class SearchHis {

    //搜索编号
    private int search_id;
    //搜索类型 1-商品
    private int search_type;
    //搜索次数
    private int search_sum;
    //搜索关键字
    private String search_keyword;
    //搜索时间
    private String search_addtime;

    public int getSearch_id() {
        return search_id;
    }

    public void setSearch_id(int search_id) {
        this.search_id = search_id;
    }

    public int getSearch_type() {
        return search_type;
    }

    public void setSearch_type(int search_type) {
        this.search_type = search_type;
    }

    public int getSearch_sum() {
        return search_sum;
    }

    public void setSearch_sum(int search_sum) {
        this.search_sum = search_sum;
    }

    public String getSearch_keyword() {
        return search_keyword;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }

    public String getSearch_addtime() {
        return search_addtime;
    }

    public void setSearch_addtime(String search_addtime) {
        this.search_addtime = search_addtime;
    }
}
