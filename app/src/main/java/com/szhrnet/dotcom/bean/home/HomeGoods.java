package com.szhrnet.dotcom.bean.home;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class HomeGoods {

    private boolean is_last;

    private List<Goods> list;

    public boolean is_last() {
        return is_last;
    }

    public void setIs_last(boolean is_last) {
        this.is_last = is_last;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }
}
