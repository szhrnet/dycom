package com.szhrnet.dotcom.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;


import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.FileUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.NetFileCallback;
import com.szhrnet.dotcom.utils.SP_System_Util;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

public class DownloadService extends Service {

  private static final String TAG = "DownloadService";
  private int dowloadStatus = 0;
  private Builder mBuilder;
  private NotificationManager mNotifyManager;
  private static final int NOTIFICATION_ID = 0;

  @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    mBuilder = new Builder(this);
    mBuilder.setContentTitle(getResources().getString(R.string.app_name))
        .setSmallIcon(R.mipmap.ic_launcher);
    startDownload();
    return super.onStartCommand(intent, flags, startId);
  }

  private void startDownload() {

    String urlStr = SP_System_Util.getString(StringConstant.NEW_APP_DOWNLOADURL);
    if (TextUtils.isEmpty(urlStr)) {
      return;
    }

    String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1);

    if ((dowloadStatus == 0) || (dowloadStatus == 2)) {
      File apkDir = FileUtils.getCacheDir(this, "apk");
      if (!apkDir.exists()) {
        apkDir.mkdirs();
      }

      //再次下载时检测之前 是否下载过，下载过删除重新下载
      File downloadedFile = new File(apkDir, apkName);
      if (downloadedFile.exists()) {
        downloadedFile.delete();
      }

      HttpUtils.httpGetFile(urlStr, apkDir.getAbsolutePath(), apkName, new NetFileCallback<File>() {
        @Override public File parseNetworkResponse(String response) throws Exception {
          return null;
        }

        @Override public void onError(Call call, Exception e) {
          dowloadStatus = 0;
          sendDownloadError(DownloadService.this);
          stopThis();
        }

        @Override public void onResponse(File response) {
          mNotifyManager.cancel(NOTIFICATION_ID);
          sendDownloadSuccess();

          openFile(DownloadService.this, response);
          dowloadStatus = 2;
          stopThis();
        }

        @Override public void inProgress(float progress, long total) {
          int precent = (int) (progress * 100);
          //LogUtils.d("progress:" + progress + ";precent:" + precent + ";total:" + total);

          if (precent % 2 == 0) {
            Intent intent1 = new Intent();
            intent1.setAction("DOWNLOAD_APK_FILE_PROGRESS");
            intent1.putExtra("precent", precent);
            LocalBroadcastManager instance =
                LocalBroadcastManager.getInstance(DownloadService.this);
            instance.sendBroadcast(intent1);
            updateProgress(precent);
          }
        }
      });
      dowloadStatus = 1;
    }
  }

  private static void sendDownloadError(Context context) {
    Intent intent1 = new Intent();
    intent1.setAction("DOWNLOAD_APK_FILE_ERROR");
    LocalBroadcastManager instance = LocalBroadcastManager.getInstance(context);
    instance.sendBroadcast(intent1);
  }

  private void sendDownloadSuccess() {
    // 下载完成
    Intent intent1 = new Intent();
    intent1.setAction("DOWNLOAD_APK_FILE_SUCCESS");
    LocalBroadcastManager instance = LocalBroadcastManager.getInstance(DownloadService.this);
    instance.sendBroadcast(intent1);
  }

  public static void openFile(Context context, File file) {

    if (file == null || !file.exists()) {
      sendDownloadError(context);
      return;
    }

    //参照：APK放到data/data/下面提示解析失败 (http://blog.csdn.net/lonely_fireworks/article/details/27693073)
    try {
      //需要对父级目录和当前文件都执行操作
      String cmd1 = "chmod 777 " + file.getParent();
      Runtime.getRuntime().exec(cmd1);

      String cmd = "chmod 777 " + file.getAbsolutePath();
      Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    File apkFile = file;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      Uri contentUri = FileProvider.getUriForFile(context,
          context.getApplicationContext().getPackageName() + ".fileProvider", apkFile);
      intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
    } else {
      intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
    }
    context.startActivity(intent);
  }

  private static boolean hasExternalStoragePermission(Context context) {
    int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
    return perm == PackageManager.PERMISSION_GRANTED;
  }

  public void stopThis() {
    stopSelf();
  }

  private void updateProgress(int progress) {
    //"正在下载:" + progress + "%"
    mBuilder.setContentText(
        this.getString(R.string.android_auto_update_download_progress) + " " + progress + "%")
        .setProgress(100, progress, false);
    //setContentInent如果不设置在4 .0 + 上没有问题，在4 .0 以下会报异常
    PendingIntent pendingintent =
        PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
    mBuilder.setContentIntent(pendingintent);
    mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
  }
}
