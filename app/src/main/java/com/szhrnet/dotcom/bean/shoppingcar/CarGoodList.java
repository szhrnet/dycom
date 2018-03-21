package com.szhrnet.dotcom.bean.shoppingcar;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class CarGoodList {

    private int cart_id;

    private int g_id;

    private String product_logo;

    private String product_name;

    private float gs_price;

    private int cart_sum;

    private String sv_id;

    private String stylestr;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getGs_price() {
        return gs_price;
    }

    public void setGs_price(float gs_price) {
        this.gs_price = gs_price;
    }

    public int getCart_sum() {
        return cart_sum;
    }

    public void setCart_sum(int cart_sum) {
        this.cart_sum = cart_sum;
    }

    public String getSv_id() {
        return sv_id;
    }

    public void setSv_id(String sv_id) {
        this.sv_id = sv_id;
    }

    public String getStylestr() {
        return stylestr;
    }

    public void setStylestr(String stylestr) {
        this.stylestr = stylestr;
    }
}
