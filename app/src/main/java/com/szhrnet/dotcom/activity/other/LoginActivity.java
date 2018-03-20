package com.szhrnet.dotcom.activity.other;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.utils.MyToast;

import butterknife.Bind;

/**
 * Created by ${CL} on 2018/3/19.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_account)
    EditText accountEditText;
    @Bind(R.id.et_psw)
    EditText pswEditText;
    @Bind(R.id.tv_forgetpsw)
    TextView forgetPswTextView;
    @Bind(R.id.tv_login)
    TextView loginTextView;

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
        loginTextView.setOnClickListener(this);
        forgetPswTextView.setOnClickListener(this);
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
            case R.id.tv_login:
                MyToast.showToast(LoginActivity.this,"登录");
                break;
            case R.id.tv_forgetpsw:
                MyToast.showToast(LoginActivity.this,"忘记密码");
                break;
        }
    }
}
