package com.szhrnet.dotcom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.Utility;
import com.szhrnet.dotcom.view.ETitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG_ACTIVITY = this.getClass().getName();


    protected abstract String initTitle();

    public abstract int getContentViewId();

    protected abstract void initEvent();

    protected abstract void initData();

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**set it to be full screen*/
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(getContentViewId());
        if (isShowStatusBar()) {
            Utility.setStatusBar(this, ContextCompat.getColor(this, R.color.colorPrimary));
        }
        ButterKnife.bind(this);

        initEvent();
        initData();

    }

    public boolean isFullScreen() {
        return false;
    }

    protected boolean isShowStatusBar() {
        return true;
    }

    protected void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtils.cancalByTag(TAG_ACTIVITY);
        ButterKnife.unbind(this);
    }

    protected <T> void toOtherActivityForResult(Class<T> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(getBaseContext(), cls);
        startActivityForResult(intent, requestCode);
    }

    protected <T> void toOtherActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    /**
     * 重载toOtherActivity
     *
     * @param cls 跳转activity
     */
    protected <T> void toOtherActivity(Class<?> cls, Bundle bdl) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(getBaseContext(), cls);
        intent.putExtras(bdl);
        startActivity(intent);
    }

    /**
     * 重载toOtherActivity
     *
     * @param cls 跳转activity
     */
    protected <T> void toOtherActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(getBaseContext(), cls);
        startActivity(intent);
    }

    protected <T> void toOtherActivity(Intent intent) {
        startActivity(intent);
    }

    protected void closeActivity() {
        hideSoftKeyboard();
        finish();
    }
}