package com.szhrnet.dotcom.activity.other;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.activity.MainActivity;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.other.LoginBean;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;

import java.util.HashMap;

import butterknife.Bind;
import okhttp3.Call;

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
        String account = accountEditText.getText().toString();
        String psw = pswEditText.getText().toString();
        switch (v.getId()) {
            case R.id.tv_right:
                toOtherActivity(RegisterActivity.class);
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw)) {
                    MyToast.showToast(this, getResources().getString(R.string.account_psw));
                    return;
                } else {
                    //登录
                    login(account, psw);
                }

                break;
            case R.id.tv_forgetpsw:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(StringConstant.ARG1, "forget");
                toOtherActivity(intent);
//                MyToast.showToast(LoginActivity.this, getResources().getString(R.string.fotgetpsw));
                break;
        }
    }

    private void login(String phone, final String psw) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_name", phone);
        params.put("user_pwd", psw);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.DOLOGIN, params, new NetCallback<BaseResponseBean<LoginBean>>() {
            @Override
            public BaseResponseBean<LoginBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, LoginBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<LoginBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    SPUtil.put(StringConstant.LOGIN, true);
                    SPUtil.put(StringConstant.ACCOUNT, response.getData().getUserarr().getUser_name());
                    SPUtil.put(StringConstant.PSW, psw);
                    SPUtil.put(StringConstant.USERID, response.getData().getUserarr().getUser_id());
                    SPUtil.put(StringConstant.TOKEN, response.getData().getUser_token());

                    toOtherActivity(MainActivity.class);
                    finish();
                }
            }
        });
    }
}
