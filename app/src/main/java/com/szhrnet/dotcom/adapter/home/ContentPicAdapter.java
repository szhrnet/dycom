package com.szhrnet.dotcom.adapter.home;

import android.content.Context;
import android.widget.ImageView;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class ContentPicAdapter extends CommonAdapter<String> {

    public ContentPicAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        ImageView view = viewHolder.getView(R.id.iv_sort_item);
        GlideUtils.loadViewHolder(mContext, item, view);
    }

}
