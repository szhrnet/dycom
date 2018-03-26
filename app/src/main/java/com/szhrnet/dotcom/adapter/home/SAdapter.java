package com.szhrnet.dotcom.adapter.home;

import android.content.Context;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.home.StyleValue;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class SAdapter extends CommonAdapter<StyleValue> {

    public SAdapter(Context context, int layoutId, List<StyleValue> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, StyleValue item, int position) {
        viewHolder.setText(R.id.tv_text, item.getSv_value());
    }
}
