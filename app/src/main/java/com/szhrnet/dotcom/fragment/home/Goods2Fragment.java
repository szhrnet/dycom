package com.szhrnet.dotcom.fragment.home;

import android.view.View;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/23.
 */

public class Goods2Fragment extends BaseFragment {

    @Bind(R.id.tv_pic)
    TextView tv_pic;
    @Bind(R.id.tv_guige)
    TextView tv_guige;

    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_goods2;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected void initEvent() {
        tv_pic.setTextColor(mContext.getResources().getColor(R.color.red));

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_pic, R.id.tv_guige})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_pic:
                tv_pic.setTextColor(mContext.getResources().getColor(R.color.red));
                tv_guige.setTextColor(mContext.getResources().getColor(R.color.black));
                break;
            case R.id.tv_guige:
                tv_pic.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_guige.setTextColor(mContext.getResources().getColor(R.color.red));
                break;
        }
    }
}
