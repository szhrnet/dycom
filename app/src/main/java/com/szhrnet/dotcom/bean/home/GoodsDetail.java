package com.szhrnet.dotcom.bean.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class GoodsDetail implements Serializable {

    private int g_id;

    private String product_name;

    private String product_pic_details;

    private String product_guige_details;

    private String product_logo;

    private String product_pic;

    private String goods_price;

    private String goods_vip_price;

    private int goods_sales;

    private int goods_inventory;

    private List<String> product_picarr;

    private int goods_is_collected;

    private int is_vip;

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

    public String getProduct_pic_details() {
        return product_pic_details;
    }

    public void setProduct_pic_details(String product_pic_details) {
        this.product_pic_details = product_pic_details;
    }

    public String getProduct_guige_details() {
        return product_guige_details;
    }

    public void setProduct_guige_details(String product_guige_details) {
        this.product_guige_details = product_guige_details;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_vip_price() {
        return goods_vip_price;
    }

    public void setGoods_vip_price(String goods_vip_price) {
        this.goods_vip_price = goods_vip_price;
    }

    public int getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goods_sales = goods_sales;
    }

    public int getGoods_inventory() {
        return goods_inventory;
    }

    public void setGoods_inventory(int goods_inventory) {
        this.goods_inventory = goods_inventory;
    }

    public List<String> getProduct_picarr() {
        return product_picarr;
    }

    public void setProduct_picarr(List<String> product_picarr) {
        this.product_picarr = product_picarr;
    }

    public int getGoods_is_collected() {
        return goods_is_collected;
    }

    public void setGoods_is_collected(int goods_is_collected) {
        this.goods_is_collected = goods_is_collected;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }
}
