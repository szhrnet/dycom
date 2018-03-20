package com.szhrnet.dotcom.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.SearchActivity;
import com.szhrnet.dotcom.adapter.home.HomeAdapter;
import com.szhrnet.dotcom.bean.home.HomeGoodsBean;
import com.szhrnet.dotcom.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/19.
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.ll_location)
    LinearLayout locationLinearLayout;
    @Bind(R.id.ll_search)
    LinearLayout searchLinearLayout;
    @Bind(R.id.tv_location)
    TextView locationTextView;
    @Bind(R.id.iv_xiala)
    ImageView xialaImageView;
    @Bind(R.id.banner_viewPager)
    ViewPager viewPager;
    @Bind(R.id.banner_indicator)
    Indicator indicator;
    @Bind(R.id.iv_image1)
    ImageView image1ImageView;
    @Bind(R.id.iv_image2)
    ImageView image2ImageView;
    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Bind(R.id.et_search)
    TextView searchEditText;
    private HomeAdapter adapter;
    private List<HomeGoodsBean> datas;
    private BannerComponent bannerComponent;
    private BannerAdapter adAdapter;

    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected void initEvent() {
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new HomeAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(5);
        bannerComponent = new BannerComponent(indicator, viewPager, false);
        adAdapter = new BannerAdapter();
        bannerComponent.setAdapter(adAdapter);
        bannerComponent.setAutoPlayTime(3000);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_location, R.id.ll_search})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                MyToast.showToast(getActivity(), "定位");
                break;
            case R.id.ll_search:
//                MyToast.showToast(getActivity(), "SearchActivity.class");
                toOtherActivity(SearchActivity.class);
                break;
        }

    }

    private class BannerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

//        List<BannerBean> datas;
//
//        public void setDatas(List<BannerBean> datas) {
//            this.datas = datas;
//        }

        @Override public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override public View getViewForTab(int position, View convertView, ViewGroup container) {
//            if (convertView == null) {
//                convertView = LayoutInflater.from(container.getContext())
//                        .inflate(R.layout.tab_guide, container, false);
//            }
            return convertView;
        }

        @Override public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new ImageView(container.getContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

//            PicassoUtils.LoadImage(container.getContext(), datas.get(position).getPic(), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
//                    toOtherActivity(DiscountActivity.class);
                }
            });
            return convertView;
        }
    }
}
