package com.szhrnet.dotcom.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.shizhefei.logger.LogUtils;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.SearchActivity;
import com.szhrnet.dotcom.adapter.home.DataAdapter;
import com.szhrnet.dotcom.adapter.home.HomeAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.home.Goods;
import com.szhrnet.dotcom.bean.home.HomeBanner;
import com.szhrnet.dotcom.bean.home.HomeBannerBean;
import com.szhrnet.dotcom.bean.home.HomeGoods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GlideUtils;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;
import com.szhrnet.dotcom.utils.SP_System_Util;
import com.szhrnet.dotcom.view.sample.utils.RecyclerViewStateUtils;
import com.szhrnet.dotcom.view.sample.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

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
    RecyclerView mRecyclerView;
    @Bind(R.id.et_search)
    TextView searchEditText;
    private HomeAdapter adapter;
    private List<Goods> datas;
    private BannerComponent bannerComponent;
    private BannerAdapter adAdapter;
    private int page = 1;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private String adCode;

    private boolean last;
    private DataAdapter mDataAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

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
        String location = SP_System_Util.getString(StringConstant.LOCATION);
        if (!TextUtils.isEmpty(location)) {
            locationTextView.setText(location);
        }
        //定位
        initPosition();
        //推荐商品设置
        datas = new ArrayList<>();
        mDataAdapter = new DataAdapter(mContext, mRecyclerView);
        mDataAdapter.addAll(datas);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        //setLayoutManager
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        //轮播图设置
        viewPager.setOffscreenPageLimit(4);
        bannerComponent = new BannerComponent(indicator, viewPager, false);
        adAdapter = new BannerAdapter();
        bannerComponent.setAdapter(adAdapter);
        bannerComponent.setAutoPlayTime(3000);
        bannerComponent.startAutoPlay();
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if (state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }
            if (!last) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
//                requestData();
                page++;
                getTuijianGoods();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void initPosition() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @Override
    protected void initData() {
        String adcode = SPUtil.getString(StringConstant.ADCODE);
        if (!TextUtils.isEmpty(adcode)) {
            getBannerData(adcode);
            getTuijianGoods();
        }
    }

    private void getTuijianGoods() {
        String adcode = SP_System_Util.getString(StringConstant.ADCODE);
        HashMap<String, Object> params = new HashMap<>();
        params.put("region_id", adcode);
        params.put("page_size", 10);
        params.put("page", page);
        HttpUtils.httpPostForm(getActivity(), TAG_FRAGMENT, NetConstant.GETINDEXRECOMMENTLIST, params, new NetCallback<BaseResponseBean<HomeGoods>>() {
            @Override
            public BaseResponseBean<HomeGoods> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, HomeGoods.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<HomeGoods> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    if (!ListUtils.isEmpty(response.getData().getList())) {
//                        datas.addAll(response.getData().getList());
                        mDataAdapter.addAll(response.getData().getList());
//                        mDataAdapter.notifyDataSetChanged();
                    }
                    last = response.getData().is_last();
                    if (last) {
                        MyToast.showToast(mContext, "到底啦");
                    }
                }
            }
        });
    }

    private void getBannerData(String adCode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("region_id", adCode);
        params.put("banner_type", 1);
        HttpUtils.httpPostForm(getActivity(), TAG_FRAGMENT, NetConstant.GETINDEXBANNERLIST, params, new NetCallback<BaseResponseBean<HomeBannerBean>>() {

            @Override
            public BaseResponseBean<HomeBannerBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, HomeBannerBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<HomeBannerBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    List<HomeBanner> list = response.getData().getList();
                    adAdapter.setDatas(list);
                    adAdapter.notifyDataSetChanged();
                }

            }
        });
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

        List<HomeBanner> datas;

        public void setDatas(List<HomeBanner> datas) {
            this.datas = datas;
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
            GlideUtils.loadViewHolder(container.getContext(), datas.get(position).getBanner_pic(), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    toOtherActivity(DiscountActivity.class);
                }
            });
            return convertView;
        }
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            String province = amapLocation.getProvince();//省信息
            String city = amapLocation.getCity();//城市信息
            String district = amapLocation.getDistrict();//城区信息
            double latitude = amapLocation.getLatitude();//获取纬度
            double longitude = amapLocation.getLongitude();//获取经度
            //地区编码
            adCode = amapLocation.getAdCode();
            SP_System_Util.put(StringConstant.ADCODE, adCode);
            if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(district)) {
                SP_System_Util.put(StringConstant.LOCATION, district);
                locationTextView.setText(district);
                mLocationClient.stopLocation();
            }
            LogUtils.e(amapLocation.toString());
            if (!TextUtils.isEmpty(adCode)) {

                //获取轮播图
                getBannerData(adCode);
                //推荐
                getTuijianGoods();
            }
        }
    };

}
