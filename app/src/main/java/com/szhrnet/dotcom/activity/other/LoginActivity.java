package com.szhrnet.dotcom.activity.other;

import android.view.View;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;

/**
 * Created by ${CL} on 2018/3/19.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected String initTitle() {
        return "登录";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEvent() {
        etitle.setRightText("注册");
        etitle.setRightTextClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                toOtherActivity(RegisterActivity.class);
                break;
        }
    }
}
