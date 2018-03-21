package com.szhrnet.dotcom.bean.assort;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortGoodsTwo {

    private Integer gt_id;

    private String gt_name;

    private List<AssortGoodsThree> goods_type_child_2;

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

    public List<AssortGoodsThree> getGoods_type_child_2() {
        return goods_type_child_2;
    }

    public void setGoods_type_child_2(List<AssortGoodsThree> goods_type_child_2) {
        this.goods_type_child_2 = goods_type_child_2;
    }
}
