package com.szhrnet.dotcom.adapter.assort;

import android.content.Context;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class AssortLeftAdapter extends CommonAdapter<String> {

    private List<String> stringList;

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    public AssortLeftAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.text, stringList.get(position));
    }
}
