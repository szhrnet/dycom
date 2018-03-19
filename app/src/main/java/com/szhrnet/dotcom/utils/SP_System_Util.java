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
public class SP_System_Util {

    /**
     * 系统级别
     */
    public static final String config = StringConstant.SETTING;

    public static SharedPreferences getSharedPreferences() {
        return getSharedPreferences(MyApplication.getInstance(), config);
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
        return getSharedPreferences().getBoolean(key, true);
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
}
