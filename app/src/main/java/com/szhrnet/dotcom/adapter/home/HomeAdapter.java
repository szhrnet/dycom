package com.szhrnet.dotcom.adapter.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.szhrnet.dotcom.bean.home.Goods;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/20.
 */

public class HomeAdapter extends BaseAdapter {

    private List<Goods> mList;
    private int sumCount;

    public HomeAdapter(List<Goods> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        int count = mList.size();
        if (count % 2 == 0) {
            sumCount = count / 2; // 如果是双数直接减半
        } else {
            sumCount = (int) Math.floor((double) count / 2) + 1;
        }
        return sumCount;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
