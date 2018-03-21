package com.szhrnet.dotcom.fragment;

import android.widget.ListView;

import com.shizhefei.logger.LogUtils;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.adapter.shoppingcar.ShoppingCarAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoodList;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/19.
 * 购物车
 */

public class ShoppingCarFragment extends BaseFragment {

    @Bind(R.id.listView)
    ListView listView;
    private ShoppingCarAdapter adapter;

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
        adapter = new ShoppingCarAdapter(getActivity(), R.layout.item_shoppingcar);
        listView.setAdapter(adapter);
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
        HttpUtils.httpPostForm(getActivity(), TAG_FRAGMENT, NetConstant.GETCARTLIST, params, new NetCallback<BaseResponseBean<CarGoods>>() {
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
                    List<CarGoodList> list = response.getData().getList();
                    if (ListUtils.isEmpty(list)) {

                    }
                }
            }
        });
    }


}
