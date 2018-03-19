package com.szhrnet.dotcom.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lenovo on 2017/4/5.
 */

public class NetUtils {

  private NetUtils() {
        /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * 判断网络是否连接
   */
  public static boolean isConnected(Context context) {

    ConnectivityManager connectivity =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    if (null != connectivity) {

      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (null != info && info.isConnected()) {
        if (info.getState() == NetworkInfo.State.CONNECTED) {
          return true;
        }
      }
    }
    return false;
  }
}
