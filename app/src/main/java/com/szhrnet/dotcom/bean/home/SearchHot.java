package com.szhrnet.dotcom.bean.home;

/**
 * Created by Administrator on 2018/3/23.
 */

public class SearchHot {

    //热搜编号
    private int search_hot_id;
    //热门搜索类型 1-线上商品
    private int search_hot_type;
    //热搜排序
    private int search_hot_orders;
    //热搜关键字
    private String search_hot_title;
    //添加时间
    private String search_hot_addtime;

    public int getSearch_hot_id() {
        return search_hot_id;
    }

    public void setSearch_hot_id(int search_hot_id) {
        this.search_hot_id = search_hot_id;
    }

    public int getSearch_hot_type() {
        return search_hot_type;
    }

    public void setSearch_hot_type(int search_hot_type) {
        this.search_hot_type = search_hot_type;
    }

    public int getSearch_hot_orders() {
        return search_hot_orders;
    }

    public void setSearch_hot_orders(int search_hot_orders) {
        this.search_hot_orders = search_hot_orders;
    }

    public String getSearch_hot_title() {
        return search_hot_title;
    }

    public void setSearch_hot_title(String search_hot_title) {
        this.search_hot_title = search_hot_title;
    }

    public String getSearch_hot_addtime() {
        return search_hot_addtime;
    }

    public void setSearch_hot_addtime(String search_hot_addtime) {
        this.search_hot_addtime = search_hot_addtime;
    }
}
