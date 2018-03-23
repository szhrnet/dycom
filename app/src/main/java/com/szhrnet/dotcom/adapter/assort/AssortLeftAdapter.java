package com.szhrnet.dotcom.adapter.assort;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortLeftAdapter extends CommonAdapter<String> {

    private int selectItem = 0;

    public AssortLeftAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        TextView textView = viewHolder.getView(R.id.text);
        View view = viewHolder.getView(R.id.view);
        LinearLayout item_parent = viewHolder.getView(R.id.item_parent);
        if (selectItem == position) {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            item_parent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.main_bg));
            item_parent.setBackgroundColor(mContext.getResources().getColor(R.color.main_bg));
        }
        textView.setGravity(Gravity.CENTER);
        viewHolder.setText(R.id.text, item);
    }
}
