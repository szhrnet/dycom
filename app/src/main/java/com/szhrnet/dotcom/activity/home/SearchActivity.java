package com.szhrnet.dotcom.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.constant.StringConstant;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

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
    private List<String> strings;

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

        //设置流式布局 item点击
        hotFlowLayout.setOnSelectListener(this);
        historyFlowLayout.setOnSelectListener(this);
    }

    @Override
    protected void initData() {
        strings = new ArrayList<>();

        hotFlowLayout.setAdapter(new TagAdapter<String>(strings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.textview_flow, hotFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        historyFlowLayout.setAdapter(new TagAdapter<String>(strings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.textview_flow, historyFlowLayout, false);
                tv.setText(s);
                return tv;
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
            intent.putExtra(StringConstant.ARG1, search_key);
            toOtherActivity(intent);
        }
    }

    //流式布局点击回调
    @Override
    public void onSelected(Set<Integer> selectPosSet) {
        doSearch(selectPosSet.toString());
    }
}
