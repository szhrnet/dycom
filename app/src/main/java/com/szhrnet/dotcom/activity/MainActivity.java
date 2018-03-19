package com.szhrnet.dotcom.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;
import com.szhrnet.dotcom.MyApplication;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.adapter.HomePageAdapter;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.service.DownloadService;
import com.szhrnet.dotcom.utils.MyToast;
import com.szhrnet.dotcom.utils.OkHttpCustomUtil;
import com.szhrnet.dotcom.utils.PermissionUtils;
import com.szhrnet.dotcom.utils.SP_System_Util;
import com.szhrnet.dotcom.view.DialogCustom;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.s_viewPager)
    SViewPager sViewPager;
    @Bind(R.id.fixedIndicatorView)
    FixedIndicatorView indicatorView;
    private IndicatorViewPager indicatorViewPager;
    private ProgressBar progressBar;
    private TextView textView;
    private double lastTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        indicatorViewPager = new IndicatorViewPager(indicatorView, sViewPager);
        indicatorViewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager(), this));

        // 禁止viewpager的滑动事件
        sViewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        sViewPager.setOffscreenPageLimit(8);


        checkPhoneStates();
    }

    private void checkPhoneStates() {
        PermissionUtils.requestMultiPermissions(this, permissionGrant);
    }

    PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            forcedUpdate();
        }
    };

    private void forcedUpdate() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),
                    PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            String versionName = packageInfo.versionName;
            /** 版本号比较 */
            String newVersion = SP_System_Util.getString(StringConstant.NEW_VERSION);
            if (!TextUtils.isEmpty(newVersion) && newVersion.compareTo(versionName) > 0) {
                String isMustUpdate = SP_System_Util.getString(StringConstant.IS_MUST_UPDATE);
                String url = SP_System_Util.getString(StringConstant.NEW_APP_DOWNLOADURL);
                if (isMustUpdate.equals("1") && !TextUtils.isEmpty(url)) {
                    showUpdateDialog();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog() {
        DialogCustom dialogCustom = new DialogCustom(this);
        dialogCustom.setTextTitle("")
                .setTextTip("检测到新版本,需要強制更新")
                .setTextConfirm("更新")
                .setTextCancel("")
                .setDialogCustomListener(new DialogCustom.DialogCustomListener() {
                    @Override
                    public void onCancel(View v, DialogCustom dialog) {
                    }

                    @Override
                    public void onConfirm(View v, DialogCustom dialog) {
                        //检查sd卡权限，没有关闭。必须开启
                        int i = ActivityCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (i == PackageManager.PERMISSION_DENIED) {
                            MyToast.showToast(MainActivity.this, "请打开SD卡读写权限!");
                            finishMainActivity();
                        } else {
                            IntentFilter filter = new IntentFilter("DOWNLOAD_APK_FILE_PROGRESS");
                            filter.addAction("DOWNLOAD_APK_FILE_ERROR");
                            filter.addAction("DOWNLOAD_APK_FILE_SUCCESS");
                            LocalBroadcastManager.getInstance(MainActivity.this)
                                    .registerReceiver(receiver, filter);

                            showDownloadProgressDialog();

                            Intent service = new Intent(MainActivity.this, DownloadService.class);
                            startService(service);
                        }
                    }

                    @Override
                    public void onDismiss(DialogCustom dialog) {
                    }
                })
                .setCanceledOnTouchOutside(false);
        dialogCustom.show();
    }

    private void showDownloadProgressDialog() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        View inflate = View.inflate(this, R.layout.dialog_progress, null);
        progressBar = (ProgressBar) inflate.findViewById(R.id.progressbar);
        textView = (TextView) inflate.findViewById(R.id.textView);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(inflate);
        builder.setCancelable(false);
        if (!MainActivity.this.isFinishing()) {
            builder.show();
        }
    }

    private void finishMainActivity() {
        releaseAllResources();
        finish();
    }

    private void releaseAllResources() {
        //取消所有网络请求
        OkHttpCustomUtil.getInstance().dispatcher().cancelAll();
        //是否清楚网络缓存
//        OkHttpCustomUtil.clearHttpCache();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("DOWNLOAD_APK_FILE_PROGRESS".equalsIgnoreCase(action)) {
                int precent = intent.getIntExtra("precent", 0);
                //LogUtils.d("precent:" + precent);
                progressBar.setProgress(precent);
                textView.setText(String.valueOf(precent) + "%");
            } else if ("DOWNLOAD_APK_FILE_ERROR".equalsIgnoreCase(action)) {
                MyToast.showToast(context, "下载失败,请检查网络!");
                LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
                finishMainActivity();
            } else if ("DOWNLOAD_APK_FILE_SUCCESS".equalsIgnoreCase(action)) {
                MyToast.showToast(context, "新版本下载完成,即将安装...");
                LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
                finishMainActivity();
            }
        }
    };

    //两次返回退出
    @Override public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 2000) {
            finishMainActivity();
        } else {
            //如果大于两秒，继续弹出提示
            MyToast.showToast(this, "再按一次退出!");
            lastTime = System.currentTimeMillis();
        }
    }
}
