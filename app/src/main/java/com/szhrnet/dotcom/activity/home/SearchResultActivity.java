package com.szhrnet.dotcom.activity.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

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

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_back, R.id.tv_search, R.id.iv_sort})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_search:
                String search_key = searchEditText.getText().toString();
                doSearch(search_key);
                break;
            case R.id.iv_sort:

                break;
        }
    }

    private void doSearch(String search_key) {
        if (!TextUtils.isEmpty(search_key)) {

        }
    }

}
