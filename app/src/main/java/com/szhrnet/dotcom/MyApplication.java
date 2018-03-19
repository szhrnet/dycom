package com.szhrnet.dotcom;

import android.app.Application;

import com.shizhefei.logger.LogLevel;
import com.shizhefei.logger.LogUtils;
import com.szhrnet.dotcom.utils.OkHttpCustomUtil;
import com.szhrnet.dotcom.utils.PicassoUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/3/19.
 */

public class MyApplication extends Application {

    private static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        boolean debug = BuildConfig.DEBUG;
        //Log.d("application", "onCreate: " + System.currentTimeMillis());
        LogUtils.getLogConfig()
                .configAllowLog(debug)
                .configTagPrefix("dotcom")
                .configShowBorders(false)
                .configLevel(LogLevel.TYPE_VERBOSE);

        //okhttpClient 全局一个实例
        OkHttpClient instance = OkHttpCustomUtil.getInstance();
        //网络请求框架
        OkHttpUtils.initClient(instance);
        //图片加载框架
        PicassoUtils.initPicassoWithoutSecurity(mContext, instance);

    }

    public static MyApplication getInstance() {
        return mContext;
    }
}
