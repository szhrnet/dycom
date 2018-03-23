package com.szhrnet.dotcom.activity.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.adapter.home.DataAdapter;
import com.szhrnet.dotcom.adapter.home.DataAdapter2;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.home.Goods;
import com.szhrnet.dotcom.bean.home.HomeGoods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;
import com.szhrnet.dotcom.view.ETitleBar;
import com.szhrnet.dotcom.view.sample.utils.RecyclerViewStateUtils;
import com.szhrnet.dotcom.view.sample.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ${CL} on 2018/3/20.
 */

public class SearchResultActivity extends BaseActivity {

    @Bind(R.id.et_search)
    EditText searchEditText;
    @Bind(R.id.tv_search)
    TextView searchTextView;
    @Bind(R.id.iv_back)
    ImageView backImageView;
    @Bind(R.id.iv_sort)
    ImageView sortImageView;
    @Bind(R.id.recycler1)
    RecyclerView recyclerView1;
    @Bind(R.id.recycler2)
    RecyclerView recyclerView2;
    private String from;
    private String search_key;
    private int gt_id;
    private int userID;
    private String adCode;

    private int page = 1;
    private List<Goods> datas;
    private List<Goods> datas2;
    private DataAdapter mDataAdapter;
    private DataAdapter2 mDataAdapter2;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    @Bind(R.id.e_title)
    protected ETitleBar etitle;
    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;
    //是否到了最后
    private boolean last;
    //    private SearchResultAdapter adapter;

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initEvent() {
        adCode = SPUtil.getString(StringConstant.ADCODE);
        from = getIntent().getStringExtra(StringConstant.ARG1);
        search_key = getIntent().getStringExtra("search_key");
        gt_id = getIntent().getIntExtra("gt_id", 0);
        userID = SPUtil.getInt(StringConstant.USERID);

        //双列显示
        datas = new ArrayList<>();
        mDataAdapter = new DataAdapter(this, recyclerView1);
        mDataAdapter.addAll(datas);

        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
        recyclerView1.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        //setLayoutManager
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) recyclerView1.getAdapter(), manager.getSpanCount()));
        recyclerView1.setLayoutManager(manager);
        recyclerView1.addOnScrollListener(mOnScrollListener);

        //单例显示
        datas2 = new ArrayList<>();
        mDataAdapter2 = new DataAdapter2(this, recyclerView2);
        mDataAdapter.addAll(datas2);

        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
        recyclerView2.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        //setLayoutManager
//        GridLayoutManager manager2 = new GridLayoutManager(this, 1);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) recyclerView2.getAdapter(), manager.getSpanCount()));
        recyclerView2.setLayoutManager(manager2);
        recyclerView2.addOnScrollListener(mOnScrollListener);

    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView1);
            if (state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }
            if (!last) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(SearchResultActivity.this, recyclerView1, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                page++;
                doSearch();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(SearchResultActivity.this, recyclerView1, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    @Override
    protected void initData() {
        doSearch();
    }

    @OnClick({R.id.ll_back, R.id.tv_search, R.id.iv_sort})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_search:
                String search_key = searchEditText.getText().toString();
                if (!TextUtils.isEmpty(search_key)) {
                    doSearch();
                }
                break;
            case R.id.iv_sort:

                break;
        }
    }

    private void doSearch() {
        HashMap<String, Object> params = new HashMap<>();
        if (from.endsWith("search")) {
            params.put("product_name", search_key);
            params.put("user_id", userID);
        } else {
            params.put("gt_id", gt_id);
        }
        params.put("region_id", adCode);
        params.put("page_size", 10);
        params.put("page", page);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.GETGOODSLIST, params, new NetCallback<BaseResponseBean<HomeGoods>>() {
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
                        mDataAdapter.addAll(response.getData().getList());
                        mDataAdapter2.addAll(response.getData().getList());
                    }
                    last = response.getData().is_last();
                }
            }
        });
    }

}
