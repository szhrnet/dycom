package com.szhrnet.dotcom.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.home.SearchBean;
import com.szhrnet.dotcom.bean.home.SearchHis;
import com.szhrnet.dotcom.bean.home.SearchHot;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;
import com.szhrnet.dotcom.view.ETitleBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ${CL} on 2018/3/20.
 */

public class SearchActivity extends BaseActivity implements TagFlowLayout.OnSelectListener {

    @Bind(R.id.et_search)
    EditText searchEditText;
    @Bind(R.id.tv_search)
    TextView searchTextView;
    @Bind(R.id.iv_back)
    ImageView backImageView;
    @Bind(R.id.flowlayout_history)
    TagFlowLayout historyFlowLayout;
    @Bind(R.id.flowlayout_hot)
    TagFlowLayout hotFlowLayout;
    private List<SearchHot> stringsHot;
    private List<SearchHis> stringsHis;
    private TagAdapter<SearchHot> adapterHot;
    private TagAdapter<SearchHis> adapterHis;
    @Bind(R.id.e_title)
    protected ETitleBar etitle;


    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initEvent() {
        stringsHot = new ArrayList<>();
        stringsHis = new ArrayList<>();
        etitle.setTitle(null);

        adapterHot = new TagAdapter<SearchHot>(stringsHot) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHot s) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.textview_flow, hotFlowLayout, false);
                tv.setText(s.getSearch_hot_title());
                return tv;
            }
        };
        hotFlowLayout.setAdapter(adapterHot);

        adapterHis = new TagAdapter<SearchHis>(stringsHis) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHis s) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.textview_flow, hotFlowLayout, false);
                tv.setText(s.getSearch_keyword());
                return tv;
            }
        };
        historyFlowLayout.setAdapter(adapterHis);
        //设置流式布局 item点击
        hotFlowLayout.setOnSelectListener(this);
        historyFlowLayout.setOnSelectListener(this);
    }

    @Override

    protected void initData() {
        //获取热门和历史记录
        getData();
    }

    private void getData() {
        int userID = SPUtil.getInt(StringConstant.USERID);
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", userID);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.GETSEARCHHOTLIST, params, new NetCallback<BaseResponseBean<SearchBean>>() {
            @Override
            public BaseResponseBean<SearchBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, SearchBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<SearchBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    List<SearchHot> listHot = response.getData().getList();
                    List<SearchHis> user_searchList = response.getData().getUser_searchList();
                    if (!ListUtils.isEmpty(listHot)) {
                        stringsHot.addAll(listHot);
                        adapterHot.notifyDataChanged();
                    }
                    if (!ListUtils.isEmpty(user_searchList)) {
                        stringsHis.addAll(user_searchList);
                        adapterHis.notifyDataChanged();
                    }
                }

            }
        });
    }

    @OnClick({R.id.ll_back, R.id.tv_search})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_search:
                String search_key = searchEditText.getText().toString();
                doSearch(search_key);
                break;
        }
    }

    private void doSearch(String search_key) {
        if (!TextUtils.isEmpty(search_key)) {
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("search_key", search_key);
            intent.putExtra(StringConstant.ARG1, "search");
            toOtherActivity(intent);
        }
    }

    //流式布局点击回调
    @Override
    public void onSelected(Set<Integer> selectPosSet) {
        doSearch(selectPosSet.toString());
    }
}
