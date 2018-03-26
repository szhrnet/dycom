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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.szhrnet.dotcom.adapter.home.StyleAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.home.GStyle;
import com.szhrnet.dotcom.bean.home.GoodsDetailBean;
import com.szhrnet.dotcom.bean.home.GoodsStyeleBean;
import com.szhrnet.dotcom.bean.home.GoodsStyle;
import com.szhrnet.dotcom.bean.home.GoodsValue;
import com.szhrnet.dotcom.bean.home.StyleValue;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/23.
 */

public class GoodsDetailActivity extends BaseActivity {


    @Bind(R.id.view_pager)
    ViewPager viewpager;
    @Bind(R.id.ll_back)
    LinearLayout ll_back;
    @Bind(R.id.ll_shop)
    LinearLayout ll_shop;
    @Bind(R.id.tpi)
    ScrollIndicatorView scrollIndicatorView;

    private IndicatorViewPager indicatorViewPager;
    private int g_id;
    private GoodsDetailBean data;
    private BaseDialog dialog;
    private View dialog_view;
    private StyleAdapter styleAdapter;
    private List<Map<String, List<StyleValue>>> strings;

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

        scrollIndicatorView.setScrollBar(new ColorBar(this, getResources().getColor(R.color.white), 4));
        viewpager.setOffscreenPageLimit(5);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewpager);
        indicatorViewPager.setAdapter(adapter);
    }

    private void getGoodStyle() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("g_id", g_id);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.GETGOODSSTYLE, params, new NetCallback<BaseResponseBean<GoodsStyeleBean>>() {

            private Map<String, List<StyleValue>> map;
            private List<StyleValue> styleValues;

            @Override
            public BaseResponseBean<GoodsStyeleBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, GoodsStyeleBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<GoodsStyeleBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {

                    List<Map<String, List<StyleValue>>> listout = new ArrayList<>();

                    GoodsStyle style_info = response.getData().getStyle_info();
                    Iterator<Map.Entry<String, GStyle>> it = style_info.getStyle_valueList().entrySet().iterator();

                    while (it.hasNext()) {

                        map = new HashMap<>();

                        Map.Entry<String, GStyle> entry = it.next();

                        String key = entry.getKey();

                        Map<String, StyleValue> value1 = entry.getValue().getValue();

                        Iterator<Map.Entry<String, StyleValue>> iterator1 = value1.entrySet().iterator();

                        while (iterator1.hasNext()) {
                            styleValues = new ArrayList<>();

                            Map.Entry<String, StyleValue> next = iterator1.next();

                            styleValues.add(next.getValue());
                        }
                        map.put(key, styleValues);
                    }
                    listout.add(map);
                    strings.addAll(listout);
                    styleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void showDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dialog_view = LayoutInflater.from(this).inflate(R.layout.option_layout, null);
            ImageView ivClose = (ImageView) dialog_view.findViewById(R.id.iv_cancel);
            ImageView iv_goods = (ImageView) dialog_view.findViewById(R.id.iv_goods);
            ImageView image_jian = (ImageView) dialog_view.findViewById(R.id.image_jian);
            ImageView image_add = (ImageView) dialog_view.findViewById(R.id.image_add);
            TextView tv_name = (TextView) dialog_view.findViewById(R.id.tv_name);
            TextView tv_price = (TextView) dialog_view.findViewById(R.id.tv_price);
            TextView tv_buy = (TextView) dialog_view.findViewById(R.id.tv_buy);
            ListView listView = (ListView) dialog_view.findViewById(R.id.listView);
            EditText edit_beishu = (EditText) dialog_view.findViewById(R.id.edit_beishu);

            strings = new ArrayList<>();
            styleAdapter = new StyleAdapter(this, R.layout.item_goods_style, strings);
            listView.setAdapter(styleAdapter);
            dialog = new BaseDialog(this);
            dialog.setContentView(dialog_view);
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.showBottom();
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
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
//            if (data != null) {
//                bundle.putSerializable("goods", data);
//            }
            bundle.putInt("g_id", g_id);
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

    @OnClick({R.id.ll_back, R.id.ll_shop})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                closeActivity();
                break;
            case R.id.ll_shop:
//                showDialog();
                //获取商品款式
//                getGoodStyle();
                break;
        }
    }
}
