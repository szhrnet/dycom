package com.szhrnet.dotcom.fragment;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.SearchActivity;
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
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SP_System_Util;

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
    ListView recyclerView;
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
            String location = null;
            if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(district)) {
                location = province + "." + city + "." + district;
                SP_System_Util.put(StringConstant.LOCATION, location);
                locationTextView.setText(location);
                mLocationClient.stopLocation();
            }
            if (!TextUtils.isEmpty(adCode)) {
                SP_System_Util.put(StringConstant.ADCODE, adCode);
            }
//            LogUtils.e(amapLocation.toString());
        }
    };
    private String adCode;

    private boolean last;

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
//        String sha1 = StringUtils.sHA1(getApplicationContext());
//        LogUtils.e(sha1);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        String location = SP_System_Util.getString(StringConstant.LOCATION);
        if (!TextUtils.isEmpty(location)) {
            locationTextView.setText(location);
        }
        if (!TextUtils.isEmpty(adCode)) {

        }
        initPosition();

        datas = new ArrayList<>();
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new HomeAdapter(datas);
        recyclerView.setAdapter(adapter);


//        viewPager.setOffscreenPageLimit(5);
        bannerComponent = new BannerComponent(indicator, viewPager, false);
        adAdapter = new BannerAdapter();
        bannerComponent.setAdapter(adAdapter);
        bannerComponent.setAutoPlayTime(3000);
    }

    private void initPosition() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(adCode)) {
            getBannerData();
            getTuijianGoods();
        }
    }

    private void getTuijianGoods() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("region_id", adCode);
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
                    datas.addAll(response.getData().getList());
                    last = response.getData().is_last();

                }
            }
        });
    }

    private void getBannerData() {
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
//            PicassoUtils.LoadImage(container.getContext(), datas.get(position).getPic(), imageView);
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
