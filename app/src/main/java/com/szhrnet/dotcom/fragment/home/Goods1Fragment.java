package com.szhrnet.dotcom.fragment.home;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.logger.LogUtils;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.GoodsDetailActivity;
import com.szhrnet.dotcom.bean.home.GoodsDetailBean;
import com.szhrnet.dotcom.bean.home.HomeBanner;
import com.szhrnet.dotcom.fragment.BaseFragment;
import com.szhrnet.dotcom.fragment.HomeFragment;
import com.szhrnet.dotcom.utils.GlideUtils;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.view.BaseDialog;
import com.szhrnet.dotcom.view.ExpandGridView;
import com.szhrnet.dotcom.view.XRatingBar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/23.
 */

public class Goods1Fragment extends BaseFragment {

    @Bind(R.id.banner_viewPager)
    ViewPager viewPager;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_price2)
    TextView price_vip;
    @Bind(R.id.iv_viptop)
    ImageView topVip;
    @Bind(R.id.iv_vipbottom)
    ImageView bottomVoip;
    @Bind(R.id.iv_share)
    ImageView shareImage;
    @Bind(R.id.tv_sum)
    TextView tv_sum;
    @Bind(R.id.tv_user)
    TextView tv_user;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.ratingbar)
    XRatingBar ratingbar;
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.gridview)
    ExpandGridView gridview;
    @Bind(R.id.tv_seeAll)
    TextView tv_seeAll;
    @Bind(R.id.banner_indicator)
    FixedIndicatorView indicator;


    private GoodsDetailBean goods;
    private BannerComponent bannerComponent;
    private BannerAdapter adAdapter;
    private BaseDialog dialog;
    private View dialog_view;

    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_goods1;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected void initEvent() {
        goods = (GoodsDetailBean) getArguments().getSerializable("goods");
        setData();

        viewPager.setOffscreenPageLimit(4);
        bannerComponent = new BannerComponent(indicator, viewPager, false);
        adAdapter = new BannerAdapter();
        bannerComponent.setAdapter(adAdapter);
    }

    private void setData() {
        String goods_price = goods.getGoodsarr().getGoods_price();//原价
        String goods_vip_price = goods.getGoodsarr().getGoods_vip_price();//vip价格
        int is_vip = goods.getGoodsarr().getIs_vip();//是否vip
        String product_name = goods.getGoodsarr().getProduct_name();//商品名
        int goods_is_collected = goods.getGoodsarr().getGoods_is_collected();//是否收藏

        adAdapter.setDatas(goods.getGoodsarr().getProduct_picarr());
        tv_name.setText(product_name);
        if (is_vip == 1) {
            topVip.setVisibility(View.VISIBLE);
            bottomVoip.setVisibility(View.GONE);
            price_vip.setText("¥" + goods_vip_price);
            price_vip.setTextColor(getResources().getColor(R.color.red));
            tv_price.setTextColor(getResources().getColor(R.color.gray));
            tv_price.setText("原价 ¥" + goods_price);
        } else {
            topVip.setVisibility(View.GONE);
            bottomVoip.setVisibility(View.VISIBLE);
            price_vip.setText("¥" + goods_price);
            price_vip.setTextColor(getResources().getColor(R.color.orange));
            tv_price.setTextColor(getResources().getColor(R.color.red));
            tv_price.setText("原价 ¥" + goods_vip_price);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_share, R.id.tv_seeAll, R.id.tv_kefu, R.id.tv_collect, R.id.tv_addCar, R.id.tv_buy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                MyToast.showToast(mContext, "share");
                break;
            case R.id.tv_seeAll:
                ((GoodsDetailActivity) getActivity()).changeFragment(3);
                break;
            case R.id.tv_kefu:
                MyToast.showToast(mContext, "客服");
                break;
            case R.id.tv_collect:
                MyToast.showToast(mContext, "收藏");
                break;
            case R.id.tv_addCar:
//                MyToast.showToast(mContext, "加入购物车");
                showDialog();
                break;
            case R.id.tv_buy:
                MyToast.showToast(mContext, "立即购买");
                break;
        }
    }

    private void showDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dialog_view = LayoutInflater.from(mContext).inflate(R.layout.option_layout, null);
            View ivClose = dialog_view.findViewById(R.id.iv_close);
            View iv_erweima = dialog_view.findViewById(R.id.iv_erweima);
        }
        dialog = new BaseDialog(getActivity());
        dialog.setContentView(dialog_view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.showBottom();
    }

    private class BannerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        private List<String> datas;

        public void setDatas(List<String> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(container.getContext())
                        .inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new ImageView(container.getContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUtils.loadViewHolder(container.getContext(), datas.get(position), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    toOtherActivity(DiscountActivity.class);
                }
            });
            return convertView;
        }
    }
}
