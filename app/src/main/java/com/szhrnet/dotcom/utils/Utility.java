package com.szhrnet.dotcom.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.szhrnet.dotcom.view.SystemBarTintManager;


/**
 * Created by lenovo on 2017/4/6.
 */

public class Utility {
    /**
     * 改变通知栏颜色
     */
    public static void setStatusBar(Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(context, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager((Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    @TargetApi(19)
    public static void setTranslucentStatus(Context context, boolean on) {
        Window win = ((Activity) context).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
