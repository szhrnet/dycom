package com.szhrnet.dotcom.activity.home;

import android.content.Context;
import android.view.View;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.home.CommentBean;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.view.ExpandGridView;
import com.szhrnet.dotcom.view.XRatingBar;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CommentAdapter extends CommonAdapter<CommentBean> {

    private ContentPicAdapter adapter;

    public CommentAdapter(Context context, int layoutId, List<CommentBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CommentBean item, int position) {
        ExpandGridView gridview = viewHolder.getView(R.id.gridview);
        viewHolder.setText(R.id.tv_user, item.getUser_nick());
        viewHolder.setText(R.id.tv_time, item.getGc_addtime());
        XRatingBar ratingbar = viewHolder.getView(R.id.ratingbar);
        viewHolder.setText(R.id.tv_content, item.getGc_content());
        ratingbar.setCountSelected(Math.round(item.getGc_star()));
        if (ListUtils.isEmpty(item.getGc_picarr())) {
            gridview.setVisibility(View.GONE);
        } else {
            gridview.setVisibility(View.VISIBLE);
        }

        adapter = new ContentPicAdapter(mContext, R.layout.item_assort, item.getGc_picarr());
        gridview.setAdapter(adapter);
    }
}
