package com.szhrnet.dotcom.bean.home;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class SearchBean {

    private List<SearchHot> list;

    private List<SearchHis> user_searchList;

    public List<SearchHot> getList() {
        return list;
    }

    public void setList(List<SearchHot> list) {
        this.list = list;
    }

    public List<SearchHis> getUser_searchList() {
        return user_searchList;
    }

    public void setUser_searchList(List<SearchHis> user_searchList) {
        this.user_searchList = user_searchList;
    }
}
