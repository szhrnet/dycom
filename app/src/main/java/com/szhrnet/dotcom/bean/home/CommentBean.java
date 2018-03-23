package com.szhrnet.dotcom.bean.home;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CommentBean {

    //评论编号
    private int gc_id;
    //用户编号
    private int user_id;
    //子订单编号
    private int goc_id;
    //商品编号
    private int g_id;
    //便利店编号
    private int s_id;
    //商品描述评价分数
    private int gc_g_star;
    //服务态度评价分数
    private int gc_s_star;
    //发货速度评价分数
    private int gc_t_star;
    //评价内容
    private String gc_content;
    //
    private String gc_pic;
    //是否匿名
    private int gc_is_name;
    //评价时间
    private String gc_addtime;
    //
    private List<String> gc_picarr;
    //该条评论平均评分
    private float gc_star;
    //用户昵称
    private String user_nick;
    //用户头像
    private String user_pic;

    public int getGc_id() {
        return gc_id;
    }

    public void setGc_id(int gc_id) {
        this.gc_id = gc_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGoc_id() {
        return goc_id;
    }

    public void setGoc_id(int goc_id) {
        this.goc_id = goc_id;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getGc_g_star() {
        return gc_g_star;
    }

    public void setGc_g_star(int gc_g_star) {
        this.gc_g_star = gc_g_star;
    }

    public int getGc_s_star() {
        return gc_s_star;
    }

    public void setGc_s_star(int gc_s_star) {
        this.gc_s_star = gc_s_star;
    }

    public int getGc_t_star() {
        return gc_t_star;
    }

    public void setGc_t_star(int gc_t_star) {
        this.gc_t_star = gc_t_star;
    }

    public String getGc_content() {
        return gc_content;
    }

    public void setGc_content(String gc_content) {
        this.gc_content = gc_content;
    }

    public String getGc_pic() {
        return gc_pic;
    }

    public void setGc_pic(String gc_pic) {
        this.gc_pic = gc_pic;
    }

    public int getGc_is_name() {
        return gc_is_name;
    }

    public void setGc_is_name(int gc_is_name) {
        this.gc_is_name = gc_is_name;
    }

    public String getGc_addtime() {
        return gc_addtime;
    }

    public void setGc_addtime(String gc_addtime) {
        this.gc_addtime = gc_addtime;
    }

    public List<String> getGc_picarr() {
        return gc_picarr;
    }

    public void setGc_picarr(List<String> gc_picarr) {
        this.gc_picarr = gc_picarr;
    }

    public float getGc_star() {
        return gc_star;
    }

    public void setGc_star(float gc_star) {
        this.gc_star = gc_star;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }
}
