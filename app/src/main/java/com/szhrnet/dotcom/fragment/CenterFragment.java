package com.szhrnet.dotcom.fragment;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.logger.LogUtils;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;
import com.szhrnet.dotcom.view.ExpandGridView;

import java.util.HashMap;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/19.
 * 我的
 */

public class CenterFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.iv_head)
    ImageView iv_head;
    @Bind(R.id.tv_nick)
    TextView tv_nick;
    @Bind(R.id.iv_vip)
    ImageView iv_vip;
    @Bind(R.id.tv_location)
    TextView tv_location;
    @Bind(R.id.iv_erweima)
    ImageView iv_erweima;
    @Bind(R.id.rl_zichan)
    RelativeLayout rl_zichan;
    @Bind(R.id.rl_order)
    RelativeLayout rl_order;
    @Bind(R.id.tv_qrz)
    TextView tv_qrz;
    @Bind(R.id.tv_yrz)
    TextView tv_yrz;
    @Bind(R.id.gridview_top)
    ExpandGridView gridview_top;
    @Bind(R.id.gridview_center)
    ExpandGridView gridview_center;
    @Bind(R.id.gridview_bottom)
    ExpandGridView gridview_bottom;


    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_center;
    }

    @Override
    protected String initTitle() {
        return "我的";
    }

    @Override
    protected void initEvent() {
        etitle.setLeftImage(R.mipmap.ic_mine_sz);
        etitle.setRightBtn1Icon(R.mipmap.ic_mine_xx);
        etitle.setLeftClickListener(this);
    }

    @Override
    protected void initData() {
        String accout = SPUtil.getString(StringConstant.ACCOUNT);
        String psw = SPUtil.getString(StringConstant.PSW);
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_name", accout);
        params.put("user_pwd", psw);
        HttpUtils.httpPostForm(getActivity(), TAG_FRAGMENT, NetConstant.GETUSERBASEINFO, params, new NetCallback<BaseResponseBean<CarGoods>>() {
            @Override
            public BaseResponseBean<CarGoods> parseNetworkResponse(String response) throws Exception {
//                LogUtils.e(response);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<CarGoods> response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                break;
        }
    }
}
