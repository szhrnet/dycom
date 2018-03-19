package com.szhrnet.dotcom.activity.other;

import android.os.Handler;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.BaseActivity;
import com.szhrnet.dotcom.activity.MainActivity;

/**
 * Created by ${CL} on 2018/3/19.
 */

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            goToMainActivity();
        }
    };

    private void goToMainActivity() {
        toOtherActivity(MainActivity.class);
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEvent() {
        //3秒后跳转到主页
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isFullScreen() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeRunnable();
    }

    private void removeRunnable() {
        handler.removeCallbacks(runnable);
    }

}
