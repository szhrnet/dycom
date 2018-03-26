package com.szhrnet.dotcom.adapter.home;

import android.content.Context;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.home.StyleValue;
import com.szhrnet.dotcom.view.ExpandGridView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/26.
 */

public class StyleAdapter extends CommonAdapter<Map<String, List<StyleValue>>> {


    public StyleAdapter(Context context, int layoutId, List<Map<String, List<StyleValue>>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Map<String, List<StyleValue>> item, int position) {
        Iterator<Map.Entry<String, List<StyleValue>>> iterator = item.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<StyleValue>> next = iterator.next();
            viewHolder.setText(R.id.text, next.getKey());
            ExpandGridView gridview = viewHolder.getView(R.id.gridview);

            SAdapter adapter = new SAdapter(mContext, R.layout.textview_flow, next.getValue());
            gridview.setAdapter(adapter);
        }
    }
}
