package com.szhrnet.dotcom.adapter.assort;

import android.content.Context;
import android.widget.ImageView;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.assort.AssortGoods;
import com.szhrnet.dotcom.bean.assort.AssortGoodsThree;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortTopAdapter extends CommonAdapter<AssortGoodsThree> {

    private List<AssortGoodsThree> datas;

    public boolean showText = false;

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public void setDatas(List<AssortGoodsThree> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public AssortTopAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public AssortTopAdapter(Context context, int layoutId, List<AssortGoodsThree> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, AssortGoodsThree item, int position) {
        GlideUtils.loadViewHolder(mContext, item.getGt_pic(), (ImageView) viewHolder.getView(R.id.iv_sort_item));
    }
}
