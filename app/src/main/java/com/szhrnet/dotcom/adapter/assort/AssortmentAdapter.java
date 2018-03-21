package com.szhrnet.dotcom.adapter.assort;

import android.content.Context;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.bean.assort.AssortGoods;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortmentAdapter extends CommonAdapter<AssortGoods> {

    public AssortmentAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void convert(ViewHolder viewHolder, AssortGoods item, int position) {

    }
}
