package com.szhrnet.dotcom.adapter.center;

import android.content.Context;
import android.widget.ImageView;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.center.LocalCenterBean;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/22.
 */

public class CenterAdapter extends CommonAdapter<LocalCenterBean> {

    public CenterAdapter(Context context, int layoutId, List<LocalCenterBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, LocalCenterBean item, int position) {
        viewHolder.setText(R.id.tv_sort_item, item.getLabel());
        GlideUtils.loadViewHolderLocal(mContext, item.getIdres(),
                (ImageView) viewHolder.getView(R.id.iv_sort_item));

    }
}
