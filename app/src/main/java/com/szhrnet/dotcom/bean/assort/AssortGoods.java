package com.szhrnet.dotcom.bean.assort;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortGoods {

    private Integer gt_id;

    private String gt_name;

    private List<AssortGoodsTwo> goods_type_child_1;

    public Integer getGt_id() {
        return gt_id;
    }

    public void setGt_id(Integer gt_id) {
        this.gt_id = gt_id;
    }

    public String getGt_name() {
        return gt_name;
    }

    public void setGt_name(String gt_name) {
        this.gt_name = gt_name;
    }

    public List<AssortGoodsTwo> getGoods_type_child_1() {
        return goods_type_child_1;
    }

    public void setGoods_type_child_1(List<AssortGoodsTwo> goods_type_child_1) {
        this.goods_type_child_1 = goods_type_child_1;
    }
}
