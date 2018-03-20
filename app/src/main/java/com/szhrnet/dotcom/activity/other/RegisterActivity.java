package com.szhrnet.dotcom.activity.other;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.CountDownUtil;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.StringUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ${CL} on 2018/3/19.
 */

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.et_phone)
    EditText phoneEditText;
    @Bind(R.id.et_verify)
    EditText verifyEditText;
    @Bind(R.id.et_psw)
    EditText pswEditText;
    @Bind(R.id.et_psw_again)
    EditText pswAgainEditText;
    @Bind(R.id.tv_send)
    TextView sendTextView;

    //验证码**秒过期
    private static final long TIME_OUT = 60 * 1000l;

    @Override
    protected String initTitle() {
        return "注册";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_send, R.id.tv_register})
    public void click(View view) {
        String phone = phoneEditText.getText().toString();
        String verify = verifyEditText.getText().toString();
        String psw = pswEditText.getText().toString();
        String pswAgain = pswAgainEditText.getText().toString();
        switch (view.getId()) {
            case R.id.tv_send:
                if (!TextUtils.isEmpty(phone) || !StringUtils.isMobile(phone)) {
                    MyToast.showToast(this, "请检查输入的手机号！");
                    return;
                } else {
                    //获取手机验证码
                    getVerify(phone);
                    //60秒倒计时
                    new CountDownUtil(sendTextView, TIME_OUT)
                            .setCountDownColor(R.color.white, R.color.white)
                            .start();
                }
                break;
            case R.id.tv_register:
                if (TextUtils.isEmpty(phone) || StringUtils.isMobile(phone)) {
                    MyToast.showToast(this, "请输入正确的手机号！");
                    return;
                } else if (TextUtils.isEmpty(verify)) {
                    MyToast.showToast(this, "请输入验证码！");
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    MyToast.showToast(this, "请输入密码！");
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    MyToast.showToast(this, "请确认密码！");
                    return;
                } else if (!psw.equals(pswAgain)) {
                    MyToast.showToast(this, "两次输入的密码不一致！");
                    return;
                } else {
                    //注册
//                    register();
                }
                MyToast.showToast(RegisterActivity.this, "注册");
                break;
        }
    }

    private void getVerify(String phone) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_mobile", phone);
        params.put("verify_type", 1);
        HttpUtils.httpPostForm(this, TAG_ACTIVITY, NetConstant.SENDCODE, params, new NetCallback<BaseResponseBean>() {

            @Override
            public BaseResponseBean parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, BaseResponseBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                }
                    MyToast.showToast(RegisterActivity.this,response.getMsg());
            }
        });
    }
}
