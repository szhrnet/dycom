package com.szhrnet.dotcom.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.adapter.center.BottomAdapter;
import com.szhrnet.dotcom.adapter.center.CenterAdapter;
import com.szhrnet.dotcom.adapter.center.TopAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.center.CenterBean;
import com.szhrnet.dotcom.bean.center.LocalCenterBean;
import com.szhrnet.dotcom.bean.other.UserInfo;
import com.szhrnet.dotcom.bean.shoppingcar.CarGoods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.ClickUtils;
import com.szhrnet.dotcom.utils.GlideUtils;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;
import com.szhrnet.dotcom.view.BaseDialog;
import com.szhrnet.dotcom.view.ExpandGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
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
    @Bind(R.id.iv_setting)
    ImageView leftImage;
    @Bind(R.id.iv_message)
    ImageView rightImage;
    @Bind(R.id.rl_titlebar)
    RelativeLayout rl_titlebar;

    private TopAdapter adapterTop;
    private CenterAdapter adaptercenter;
    private BottomAdapter adapterbottom;
    private List<LocalCenterBean> topDatas;
    private List<LocalCenterBean> centerDatas;
    private List<LocalCenterBean> bottomDatas;
    private View dialog_view;
    private BaseDialog dialog;


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
        leftImage.setImageResource(R.mipmap.ic_mine_sz);
        rightImage.setImageResource(R.mipmap.ic_mine_xx);

        topDatas = new ArrayList<>();
        adapterTop = new TopAdapter(mContext, R.layout.item_center, topDatas);
        gridview_top.setAdapter(adapterTop);

        centerDatas = new ArrayList<>();
        adaptercenter = new CenterAdapter(mContext, R.layout.item_center, centerDatas);
        gridview_center.setAdapter(adaptercenter);

        bottomDatas = new ArrayList<>();
        adapterbottom = new BottomAdapter(mContext, R.layout.item_center, bottomDatas);
        gridview_bottom.setAdapter(adapterbottom);

        gridview_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        gridview_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        gridview_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void initData() {
        //是否验证
        int anInt = SPUtil.getInt(StringConstant.AUTH);
        getData();
        setLocalData();
        getLocalData(anInt);
    }

    private void getLocalData(int auth) {
        bottomDatas.clear();
        if (auth == 0) {
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hyxxb, "会员信息"));
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hyjfb, "积分中心"));
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hytqb, "特权周边"));
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hysyb, "年度收益"));
        } else {
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hyxxa, "会员信息"));
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hyjfa, "积分中心"));
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hytqa, "特权周边"));
            bottomDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hysya, "年度收益"));
        }
        adapterbottom.notifyDataSetChanged();
    }

    private void setLocalData() {
        topDatas.clear();
        topDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hysc, "收藏"));
        topDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hyzj, "足迹"));
        topDatas.add(new LocalCenterBean(R.mipmap.ic_mine_hydz, "地址"));
        adapterTop.notifyDataSetChanged();

        centerDatas.clear();
        centerDatas.add(new LocalCenterBean(R.mipmap.ic_mine_bdwf, "待付款"));
        centerDatas.add(new LocalCenterBean(R.mipmap.ic_mine_bddf, "待发货"));
        centerDatas.add(new LocalCenterBean(R.mipmap.ic_mine_bdds, "待收货"));
        centerDatas.add(new LocalCenterBean(R.mipmap.ic_mine_bdwc, "已完成"));
        centerDatas.add(new LocalCenterBean(R.mipmap.ic_mine_bdth, "退货"));
        adaptercenter.notifyDataSetChanged();
    }

    private void getData() {
        String accout = SPUtil.getString(StringConstant.ACCOUNT);
        String psw = SPUtil.getString(StringConstant.PSW);
        int userID = SPUtil.getInt(StringConstant.USERID);
        String token = SPUtil.getString(StringConstant.TOKEN);
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_name", accout);
        params.put("user_pwd", psw);
        params.put("user_id", userID);
        params.put("user_token", token);
        HttpUtils.httpPostForm(getActivity(), TAG_FRAGMENT, NetConstant.GETUSERBASEINFO, params, new NetCallback<BaseResponseBean<CenterBean>>() {
            @Override
            public BaseResponseBean<CenterBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, CenterBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<CenterBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    UserInfo userarr = response.getData().getUserarr();
                    int user_is_auth = userarr.getUser_is_auth();
                    getLocalData(user_is_auth);
                    tv_nick.setText(userarr.getUser_nick());
                    tv_location.setText(userarr.getRegion_desc());
                    GlideUtils.loadCustomerViewHolder(getActivity(), userarr.getUser_pic(), iv_head);
                    SPUtil.put(StringConstant.AUTH, user_is_auth);
                    int user_level = userarr.getUser_level();
                    if (user_level == 2) {
                        iv_vip.setVisibility(View.VISIBLE);
                    }

                    if (user_is_auth == 1) {
                        tv_yrz.setClickable(false);
                        tv_qrz.setVisibility(View.GONE);
                        tv_yrz.setVisibility(View.VISIBLE);
                    } else {
                        tv_yrz.setVisibility(View.GONE);
                        tv_qrz.setVisibility(View.VISIBLE);
                    }
                    SPUtil.put(StringConstant.LEVEL, user_level);
                }
            }
        });
    }

    @OnClick({R.id.tv_qrz, R.id.rl_zichan, R.id.rl_order, R.id.iv_erweima, R.id.iv_setting, R.id.iv_message})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_qrz:
                MyToast.showToast(mContext, "认证");
                break;
            case R.id.rl_zichan:

                break;
            case R.id.rl_order:

                break;
            case R.id.iv_erweima:
                //二维码
                showDialog();
                break;
            case R.id.iv_setting:
                MyToast.showToast(mContext, "setting");
                break;
            case R.id.iv_message:
                MyToast.showToast(mContext, "message");
                break;
        }
    }

    private void showDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dialog_view = LayoutInflater.from(mContext).inflate(R.layout.erweima_layout, null);
            View ivClose = dialog_view.findViewById(R.id.iv_close);
            View iv_erweima = dialog_view.findViewById(R.id.iv_erweima);
        }
        dialog = new BaseDialog(getActivity());
        dialog.setContentView(dialog_view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.showViewBottom(rl_titlebar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                break;
        }
    }
}
