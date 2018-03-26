package com.szhrnet.dotcom.bean.home;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/26.
 */

public class GoodsStyle {

    private int g_id;

    private String product_name;

    private String product_logo;

    private Map<String, GStyle> style_valueList;

    private List<GoodsValue> sv_priceList;

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
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

    public Map<String, GStyle> getStyle_valueList() {
        return style_valueList;
    }

    public void setStyle_valueList(Map<String, GStyle> style_valueList) {
        this.style_valueList = style_valueList;
    }

    public List<GoodsValue> getSv_priceList() {
        return sv_priceList;
    }

    public void setSv_priceList(List<GoodsValue> sv_priceList) {
        this.sv_priceList = sv_priceList;
    }
}
