package com.szhrnet.dotcom.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.activity.GoodsFragmentCollect;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.home.GoodsDetailBean;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.fragment.BaseFragment;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.view.BaseDialog;
import com.szhrnet.dotcom.view.ETitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/23.
 */

public class GoodsDetailActivity extends BaseActivity {


    @Bind(R.id.view_pager)
    ViewPager viewpager;

    private View view;
    private ScrollIndicatorView scrollIndicatorView;
    private IndicatorViewPager indicatorViewPager;
    private int g_id;
    private GoodsDetailBean data;
    private BaseDialog dialog;
    private View dialog_view;
    @Bind(R.id.e_title)
    protected ETitleBar etitle;

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initEvent() {
        g_id = getIntent().getIntExtra("g_id", -1);

        RelativeLayout titleContainer = etitle.getTitleContainer();
        initTitleView(titleContainer);

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.3f;
        scrollIndicatorView.setScrollBar(new ColorBar(this, getResources().getColor(R.color.white), 2));
        viewpager.setOffscreenPageLimit(10);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewpager);
        indicatorViewPager.setAdapter(adapter);
    }

    protected void initTitleView(RelativeLayout titleContainer) {
        titleContainer.removeAllViews();
        if (view == null) {
            view = LayoutInflater.from(this).inflate(R.layout.title_goods, null);
        }
        scrollIndicatorView = (ScrollIndicatorView) view.findViewById(R.id.tpi);
        titleContainer.setHorizontalGravity(Gravity.CENTER);
        titleContainer.addView(view);

        //右边按纽
        etitle.setRightBtn1Icon(R.mipmap.ic_home_gwc);
        etitle.setRightViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                //获取商品款式
//                getGoodStyle();
            }
        });
    }

    private void getGoodStyle() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("g_id",g_id);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.GETGOODSSTYLE, params, new NetCallback() {
            @Override
            public Object parseNetworkResponse(String response) throws Exception {
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    private void showDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dialog_view = LayoutInflater.from(this).inflate(R.layout.option_layout, null);
            View ivClose = dialog_view.findViewById(R.id.iv_close);
            View iv_erweima = dialog_view.findViewById(R.id.iv_erweima);
        }
        dialog = new BaseDialog(this);
        dialog.setContentView(dialog_view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.showBottom();
    }

    @Override
    protected void initData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("g_id", g_id);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.GETGOODSDETAIL, params, new NetCallback<BaseResponseBean<GoodsDetailBean>>() {
            @Override
            public BaseResponseBean<GoodsDetailBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, GoodsDetailBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<GoodsDetailBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    data = response.getData();
                }
            }
        });
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private List<String> rows = new ArrayList<>();
        private GoodsFragmentCollect goodsFragmentCollect;

        {
            rows.add("商品");
            rows.add("详情");
            rows.add("评价");
        }

        MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            goodsFragmentCollect = new GoodsFragmentCollect();
        }

        @Override
        public int getCount() {
            return rows == null ? 0 : rows.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView =
                        LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.tab_top2, container, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            String label = rows.get(position);
            textView.setText(label);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            BaseFragment fragment = null;
            if (goodsFragmentCollect != null) {
                fragment =
                        goodsFragmentCollect.getCurrentRechargeFragment(position);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("goods", data);
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    /**
     * 根据tag 切换到对应页面
     */
    public void changeFragment(int tag) {
        PagerAdapter pagerAdapter = indicatorViewPager.getAdapter().getPagerAdapter();
        int count = pagerAdapter.getCount();
        if (tag < 0) {
            return;
        }

        if (pagerAdapter instanceof FragmentListPageAdapter) {
            int itemPosition =
                    ((FragmentListPageAdapter) pagerAdapter).findFragmentByTag(String.valueOf(tag));
            if (itemPosition < 0 || itemPosition >= count) {
                return;
            }
            indicatorViewPager.setCurrentItem(itemPosition, true);
        }
    }
}
