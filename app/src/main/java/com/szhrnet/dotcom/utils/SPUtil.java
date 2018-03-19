package com.szhrnet.dotcom.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.SharedPreferencesCompat;

import com.szhrnet.dotcom.MyApplication;
import com.szhrnet.dotcom.constant.StringConstant;


/**
 * 1.string
 * 2.boolean
 * 3.int
 *
 * @author Administrator
 */
public class SPUtil {

  private static Context ctx = MyApplication.getInstance();

  /** 区分用户 */
  public static final String config = "config";

  public static SharedPreferences getSharedPreferences() {
    return getSharedPreferences(ctx, config);
  }

  public static SharedPreferences getSharedPreferences(Context ctx, String fileName) {
    return ctx.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
  }

  public static String getString(String key) {
    return getSharedPreferences().getString(key, null);
  }

  public static int getInt(String key) {
    return getSharedPreferences().getInt(key, 0);
  }

  public static int getInt(String key, int defValue) {
    return getSharedPreferences().getInt(key, defValue);
  }

  public static boolean getBoolean(String key) {
    return getSharedPreferences().getBoolean(key, false);
  }

  public static long getLong(String key) {
    return getSharedPreferences().getLong(key, 0);
  }

  public static void put(String key, Object object) {
    Editor editor = getSharedPreferences().edit();
    if (object instanceof Integer) {
      editor.putInt(key, (Integer) object);
    } else if (object instanceof Boolean) {
      editor.putBoolean(key, (Boolean) object);
    } else if (object instanceof String) {
      editor.putString(key, (String) object);
    } else if (object instanceof Long) {
      editor.putLong(key, (Long) object);
    }
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
  }

  public static void deleteSP() {
    //Editor clear = ctx.getSharedPreferences(config, 0).edit().clear();
    //SharedPreferencesCompat.EditorCompat.getInstance().apply(clear);

    Editor edit = ctx.getSharedPreferences(config, 0).edit();
    edit.remove(StringConstant.TOKEN);
    edit.remove(StringConstant.NEW_INFO);
    edit.remove(StringConstant.USER_IMAGE_URL);
    edit.remove(StringConstant.BALANCE);
    edit.remove(StringConstant.KEY);
    edit.remove(StringConstant.LOGIN);
    edit.remove(StringConstant.FUND_PSW);
    edit.remove(StringConstant.KEY_TIME);
    edit.remove(StringConstant.BIND_BANK);
    edit.remove(StringConstant.USER_PROXY_REGIST);
    edit.remove(StringConstant.TEMPUSER);
    edit.commit();
  }
}
