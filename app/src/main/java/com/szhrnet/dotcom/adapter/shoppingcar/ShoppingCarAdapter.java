package com.szhrnet.dotcom.adapter.shoppingcar;

import android.content.Context;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoods;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class ShoppingCarAdapter extends CommonAdapter<CarGoods> {

    public ShoppingCarAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CarGoods item, int position) {

    }
}
