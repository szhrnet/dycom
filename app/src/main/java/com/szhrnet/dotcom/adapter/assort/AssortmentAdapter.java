package com.szhrnet.dotcom.adapter.assort;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.assort.AssortGoods;
import com.szhrnet.dotcom.bean.assort.AssortGoodsTwo;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortmentAdapter extends CommonAdapter<AssortGoodsTwo> {

    public boolean showText = true;

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public AssortmentAdapter(Context context, int layoutId, List<AssortGoodsTwo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, AssortGoodsTwo item, int position) {
        if (showText) {
            viewHolder.getView(R.id.tv_sort_item).setVisibility(View.VISIBLE);
        }
        GlideUtils.loadViewHolder(mContext, item.getGt_pic(), (ImageView) viewHolder.getView(R.id.iv_sort_item));
        viewHolder.setText(R.id.tv_sort_item, item.getGt_name());
    }
}
