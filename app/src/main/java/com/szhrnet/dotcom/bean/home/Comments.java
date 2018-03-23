package com.szhrnet.dotcom.bean.home;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class Comments {

    //总评分
    private float gc_pingfen;
    //评论总数
    private int gc_comment_cnt;
    //是否最后一页
    private boolean is_last;
    //
    private List<CommentBean> list;

    public float getGc_pingfen() {
        return gc_pingfen;
    }

    public void setGc_pingfen(float gc_pingfen) {
        this.gc_pingfen = gc_pingfen;
    }

    public int getGc_comment_cnt() {
        return gc_comment_cnt;
    }

    public void setGc_comment_cnt(int gc_comment_cnt) {
        this.gc_comment_cnt = gc_comment_cnt;
    }

    public boolean isIs_last() {
        return is_last;
    }

    public void setIs_last(boolean is_last) {
        this.is_last = is_last;
    }

    public List<CommentBean> getList() {
        return list;
    }

    public void setList(List<CommentBean> list) {
        this.list = list;
    }
}
