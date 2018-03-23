package com.szhrnet.dotcom.bean.other;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class UserInfo {

    //用户编号
    private int user_id;
    private String user_token;
    //用户性别 1-男 2-女 3-保密
    private int user_gender;
    //用户角色1-普通用户2-配送员
    private String user_role;
    private String user_pic;
    private String user_nick;
    //所在地编号
    private String region_id;
    private String user_name;
    private String user_balance;
    //客户端id
    private String user_client_id;
    //详细地址
    private String user_address;
    private String user_mobile;
    //用户级别 1-普通 2-会员
    private int user_level;
    //用户是否认证 0-否 1-是
    private int user_is_auth;
    private String user_email;
    //所在地名称
    private String region_desc;
    //是否设置支付密码 0-否 1-是
    private String is_set_pay_pwd;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public int getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(int user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(String user_balance) {
        this.user_balance = user_balance;
    }

    public String getUser_client_id() {
        return user_client_id;
    }

    public void setUser_client_id(String user_client_id) {
        this.user_client_id = user_client_id;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getUser_is_auth() {
        return user_is_auth;
    }

    public void setUser_is_auth(int user_is_auth) {
        this.user_is_auth = user_is_auth;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getRegion_desc() {
        return region_desc;
    }

    public void setRegion_desc(String region_desc) {
        this.region_desc = region_desc;
    }

    public String getIs_set_pay_pwd() {
        return is_set_pay_pwd;
    }

    public void setIs_set_pay_pwd(String is_set_pay_pwd) {
        this.is_set_pay_pwd = is_set_pay_pwd;
    }
}
