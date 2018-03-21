package com.szhrnet.dotcom.bean.home;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class HomeBanner {

    //轮播编号
    private Integer banner_id;
    //地区编号
    private Integer region_id;
    //类型
    private Integer banner_type;
    //文章标题
    private String banner_title;
    //图片/视频封面地址
    private String banner_pic;
    //文章内容
    private String banner_content;
    //排序
    private Integer banner_orders;
    //添加时间
    private String banner_addtime;


    public Integer getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(Integer banner_id) {
        this.banner_id = banner_id;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public Integer getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(Integer banner_type) {
        this.banner_type = banner_type;
    }

    public String getBanner_title() {
        return banner_title;
    }

    public void setBanner_title(String banner_title) {
        this.banner_title = banner_title;
    }

    public String getBanner_pic() {
        return banner_pic;
    }

    public void setBanner_pic(String banner_pic) {
        this.banner_pic = banner_pic;
    }

    public String getBanner_content() {
        return banner_content;
    }

    public void setBanner_content(String banner_content) {
        this.banner_content = banner_content;
    }

    public Integer getBanner_orders() {
        return banner_orders;
    }

    public void setBanner_orders(Integer banner_orders) {
        this.banner_orders = banner_orders;
    }

    public String getBanner_addtime() {
        return banner_addtime;
    }

    public void setBanner_addtime(String banner_addtime) {
        this.banner_addtime = banner_addtime;
    }
}
