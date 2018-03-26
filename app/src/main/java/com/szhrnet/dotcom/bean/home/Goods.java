package com.szhrnet.dotcom.bean.home;

import java.io.Serializable;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class Goods implements Serializable{

    private int g_id;

    private float goods_price;

    private String product_name;

    private String product_logo;

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }
}
