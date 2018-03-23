package com.szhrnet.dotcom.adapter.shoppingcar;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.abslistviewadapter.CommonAdapter;
import com.shizhefei.abslistviewadapter.ViewHolder;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoods;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoodses;
import com.szhrnet.dotcom.utils.ClickUtils;
import com.szhrnet.dotcom.utils.GlideUtils;

import java.util.List;

/**
 * Created by ${CL} on 2018/3/21.
 */

public class ShoppingCarAdapter extends CommonAdapter<CarGoodses> {

    public boolean isDelete = false;
    public boolean chooseAll = false;
    public boolean isEdit = false;
    private ClickUtils.OnClickListener listener;

    public ShoppingCarAdapter(Context context, int layoutId, List<CarGoodses> datas, ClickUtils.OnClickListener listener) {
        super(context, layoutId, datas);
        this.listener = listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, CarGoodses item, int position) {
        CheckBox checkBox1 = viewHolder.getView(R.id.checkbox);
        ImageView iv_goods = viewHolder.getView(R.id.iv_goods);
        LinearLayout ll_description = viewHolder.getView(R.id.ll_description);
        TextView tv_name = viewHolder.getView(R.id.tv_name);
        TextView tv_price = viewHolder.getView(R.id.tv_price);
        LinearLayout ll_edit = viewHolder.getView(R.id.ll_edit);
        ImageView image_view_remove_bei_shu = viewHolder.getView(R.id.image_view_remove_bei_shu);
        TextView edit_text_beishu = viewHolder.getView(R.id.edit_text_beishu);
        ImageView image_view_add_bei_shu = viewHolder.getView(R.id.image_view_add_bei_shu);
        TextView tv_delete = viewHolder.getView(R.id.tv_delete);
        //是否删除
        if (!isEdit) {
            ll_description.setVisibility(View.VISIBLE);
            ll_edit.setVisibility(View.GONE);
        } else {
            ll_description.setVisibility(View.GONE);
            ll_edit.setVisibility(View.VISIBLE);
        }
        //是否全选
        if (chooseAll) {
            checkBox1.setChecked(true);
        } else {
            checkBox1.setChecked(false);
        }

        GlideUtils.loadViewHolder(mContext, item.getProduct_logo(), iv_goods);
        tv_name.setText(item.getProduct_name());
        tv_price.setText(String.valueOf(item.getGs_price()));

        edit_text_beishu.setText(item.getCart_sum());

        ClickUtils.addClickTo(image_view_remove_bei_shu, listener);
        ClickUtils.setPos(image_view_remove_bei_shu, position);

        ClickUtils.addClickTo(image_view_add_bei_shu, listener);
        ClickUtils.setPos(image_view_add_bei_shu, position);

        ClickUtils.addClickTo(tv_delete, listener);
        ClickUtils.setPos(tv_delete, position);

        ClickUtils.addClickTo(checkBox1, listener);
        ClickUtils.setPos(checkBox1, position);

    }
}
