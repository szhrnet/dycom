package com.szhrnet.dotcom.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cundong.recyclerview.RecyclerViewUtils;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.GoodsDetailActivity;
import com.szhrnet.dotcom.bean.home.Goods;
import com.szhrnet.dotcom.bean.home.HomeGoods;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${CL} on 2018/3/22.
 */

public class DataAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<Goods> mDataList = new ArrayList<>();
    private Context context;
    private RecyclerView view;

    public DataAdapter(Context context, RecyclerView view) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.view = view;
    }

    public void addAll(List<Goods> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        GlideUtils.loadViewHolder(context, mDataList.get(position).getProduct_logo(), viewHolder.iv_goods);
        viewHolder.tv_name.setText(mDataList.get(position).getProduct_name());
        viewHolder.tv_price.setText("¥" + String.valueOf(mDataList.get(position).getGoods_price()));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_goods;
        private TextView tv_name;
        private TextView tv_price;
        private LinearLayout ll_goods;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goods = (ImageView) itemView.findViewById(R.id.iv_goods);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            ll_goods = (LinearLayout) itemView.findViewById(R.id.ll_goods);

            ll_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goods goods = mDataList.get(RecyclerViewUtils.getAdapterPosition(view, ViewHolder.this));
                    int g_id = goods.getG_id();
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("g_id", g_id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
