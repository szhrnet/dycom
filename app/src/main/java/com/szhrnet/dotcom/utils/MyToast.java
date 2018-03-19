package com.szhrnet.dotcom.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyToast {

  private static Toast toast;

  /**
   * 显示提示信息——通过id
   *
   * @param id 字符串id
   */
  public static void showToast(Context mContext, int id) {
    if (toast == null) {
      toast = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
    }
    toast.setText(id);
    toast.show();
  }

  /**
   * 显示提示信息——通过字符串
   *
   * @param content 字符串
   */
  public static void showToast(Context mContext, String content) {
    if (toast == null) {
      toast = Toast.makeText(mContext, content, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
    }
    toast.setText(content);
    toast.show();
  }

  public static void showSingleToast(Context mContext, String content) {
    Toast toast = Toast.makeText(mContext, content, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }
}
