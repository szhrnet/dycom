package com.szhrnet.dotcom.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.home.HomeGoodsBean;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${CL} on 2018/3/20.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeGoodsViewHolder> {

    private Context context;
    private List<HomeGoodsBean> datas;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<HomeGoodsBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public HomeGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeGoodsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeGoodsViewHolder holder, int position) {
        GlideUtils.loadViewHolder(context, "", holder.goodsImageView);
        holder.nameTextView.setText("");
        holder.priceTextView.setText("");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    static class HomeGoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_goods)
        ImageView goodsImageView;
        @Bind(R.id.tv_name)
        TextView nameTextView;
        @Bind(R.id.tv_price)
        TextView priceTextView;

        public HomeGoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
