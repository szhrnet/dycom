package com.szhrnet.dotcom.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.logger.LogUtils;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.view.ETitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/19.
 */

public abstract class BaseFragment extends LazyFragment {

    public final String TAG_FRAGMENT = this.getClass().getName();
    public boolean isLogin;
    @Bind(R.id.e_title)
    ETitleBar etitle;


    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    protected abstract int getChildLayoutRes();

    protected abstract String initTitle();

    protected abstract void initEvent();

    protected abstract void initData();

    @Override protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(getChildLayoutRes());
        ButterKnife.bind(this, getContentView());
        LogUtils.d("Fragment 将要创建View onCreateViewLazy " + this);

        if (etitle != null) {
            etitle.getLeftImageView().setVisibility(View.GONE);
            etitle.setTitle(initTitle());
        }

        initEvent();
        initData();
    }

    @Override protected void onResumeLazy() {
        super.onResumeLazy();
        LogUtils.d("Fragment所在的Activity onResume, onResumeLazy " + this);

    }

    @Override protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        LogUtils.d("Fragment 显示 onFragmentStartLazy " + this);

    }

    @Override protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        LogUtils.d("Fragment 掩藏 onFragmentStopLazy " + this);
    }

    @Override protected void onPauseLazy() {
        super.onPauseLazy();
        LogUtils.d("Fragment所在的Activity onPause, onPauseLazy " + this);
    }

    @Override protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        HttpUtils.cancalByTag(TAG_FRAGMENT);
        ButterKnife.unbind(this);//解绑
        LogUtils.d("Fragment View将被销毁 onDestroyViewLazy " + this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        LogUtils.d("Fragment 所在的Activity onDestroy " + this);
    }

    protected void hideSoftKeyboard() {
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected <T> void toOtherActivityForResult(Class<T> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }

    protected <T> void toOtherActivity(Intent intent) {
        startActivity(intent);
    }

    protected void closeActivity() {
        hideSoftKeyboard();
        getActivity().finish();
    }
}
