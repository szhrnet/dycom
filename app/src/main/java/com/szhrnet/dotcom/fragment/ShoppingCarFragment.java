package com.szhrnet.dotcom.fragment;

import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shizhefei.logger.LogUtils;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.adapter.shoppingcar.ShoppingCarAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoodses;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.ClickUtils;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/19.
 * 购物车
 */

public class ShoppingCarFragment extends BaseFragment {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.tv_edit)
    TextView editTextView;
    @Bind(R.id.checkbox2)
    CheckBox checkbox2;
    @Bind(R.id.tv_price)
    TextView priceTextView;
    @Bind(R.id.tv_buy)
    TextView buyTextView;
    @Bind(R.id.iv_empty)
    ImageView emptyImageView;
    @Bind(R.id.ll_list)
    LinearLayout ll_list;
    private ShoppingCarAdapter adapter;
    private List<CarGoodses> goodses;

    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_shoppingcar;
    }

    @Override
    protected String initTitle() {
        return "购物车";
    }

    @Override
    protected void initEvent() {
        goodses = new ArrayList<>();
        adapter = new ShoppingCarAdapter(getActivity(), R.layout.item_shoppingcar, goodses, listener);
        listView.setAdapter(adapter);
    }

    private ClickUtils.OnClickListener listener = new ClickUtils.OnClickListener() {
        @Override
        public void onClick(View v, int type, int pos, int child) {
            switch (v.getId()) {
                case R.id.checkbox:
                    //设置选择状态
                    setCheckState((CheckBox) v, pos);
                    //计算每个商品的总价
                    float totalMoney = goodsTotalMoney();
                    setMoney(totalMoney);
                    break;
                case R.id.image_view_remove_bei_shu:
                    CarGoodses carGoodses = goodses.get(pos);
                    int sum = carGoodses.getCart_sum();
                    if (sum == 0) {
                        return;
                    } else {
                        sum--;
                        carGoodses.setCart_sum(sum);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.tv_delete:
                    goodses.remove(pos);
                    adapter.notifyDataSetChanged();
                    getData();
                    break;
                case R.id.image_view_add_bei_shu:
                    CarGoodses carGoodses2 = goodses.get(pos);
                    int sum2 = carGoodses2.getCart_sum();
                    sum2++;
                    carGoodses2.setCart_sum(sum2);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void setMoney(float totalMoney) {
        String s = "合计¥" + "<font color = \"#FF0000\">" + String.valueOf(totalMoney) + "</font>";
        priceTextView.setText(Html.fromHtml(s));
    }

    private void setCheckState(CheckBox v, int pos) {
        CheckBox checkBox = v;
        if (checkBox.isChecked()) {
            goodses.get(pos).setChecked(true);
        } else {
            goodses.get(pos).setChecked(false);
        }
    }

    private float goodsTotalMoney() {
        float money = 0;
        for (int i = 0; i < goodses.size(); i++) {
            if (goodses.get(i).isCheck()) {
                money += goodses.get(i).getCart_sum() * goodses.get(i).getGs_price();
            }
        }
        return money;
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        String token = SPUtil.getString(StringConstant.TOKEN);
        int userID = SPUtil.getInt(StringConstant.USERID);
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", userID);
        params.put("user_token", token);
        HttpUtils.httpPostForm(mContext, TAG_FRAGMENT, NetConstant.GETCARTLIST, params, new NetCallback<BaseResponseBean<CarGoods>>() {
            @Override
            public BaseResponseBean<CarGoods> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, CarGoods.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<CarGoods> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    List<CarGoodses> list = response.getData().getList();
                    if (!ListUtils.isEmpty(list)) {
                        ll_list.setVisibility(View.VISIBLE);
                        emptyImageView.setVisibility(View.GONE);
                        goodses.clear();
                        goodses.addAll(response.getData().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        emptyImageView.setVisibility(View.VISIBLE);
                        ll_list.setVisibility(View.GONE);
                    }
                } else {
                    emptyImageView.setVisibility(View.VISIBLE);
                    ll_list.setVisibility(View.GONE);
                    MyToast.showSingleToast(mContext, response.getMsg());
                }
            }
        });
    }

    @OnClick({R.id.checkbox, R.id.tv_edit, R.id.checkbox2, R.id.tv_buy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.checkbox:
                //全选
                chooseAll();
                setMoney(goodsTotalMoney());
                break;
            case R.id.tv_edit:
                //编辑
                edit();
                break;
            case R.id.checkbox2:
                //全选
                chooseAll();
                setMoney(goodsTotalMoney());
                break;
            case R.id.tv_buy:
                MyToast.showToast(mContext, "buy");
                break;
        }
    }

    private void edit() {
        if (!adapter.isEdit) {
            adapter.isEdit = true;
            editTextView.setText("完成");
        } else {
            adapter.isEdit = false;
            editTextView.setText("编辑");
            adapter.chooseAll = false;
        }
    }

    private void chooseAll() {
        if (!adapter.chooseAll) {
            adapter.chooseAll = true;
            for (int i = 0; i < goodses.size(); i++) {
                goodses.get(i).setChecked(true);
            }
        } else {
            adapter.chooseAll = false;
            for (int i = 0; i < goodses.size(); i++) {
                goodses.get(i).setChecked(false);
            }
        }
    }

}
