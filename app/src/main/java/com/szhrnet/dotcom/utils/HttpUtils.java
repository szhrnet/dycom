package com.szhrnet.dotcom.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import com.shizhefei.logger.LogUtils;
import com.szhrnet.dotcom.MyApplication;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

import static java.lang.String.valueOf;

/**
 * Created by ZCZ on 2017/4/15.
 */

public class HttpUtils {

  /**
   * Get 请求:不缓存
   */
  public static void httpGet(Context context, String tag, String url,
      HashMap<String, Object> params, NetCallback callback) {
    httpGet(context, tag, url, params, false, callback);
  }

  /**
   * Get 请求：如果缓存，缓存一周
   */
  public static void httpGet(final Context context, String tag, final String url,
      final HashMap<String, Object> params, final Boolean isCache, NetCallback callback) {

    if (TextUtils.isEmpty(url)) {
      throw new InvalidParameterException("请输入正确的请求地址!");
    }

    if (callback == null) {
      callback = new NetCallback.DefaultNetCallback();
    }
    final NetCallback finalCallback = callback;

    GetBuilder getBuilder = OkHttpUtils.get();

    GetBuilder builder = getBuilder.url(url);
    if (isCache) {
      builder.addHeader("custom-cache-type", "true");
    }
//    addCommonHeader(builder, params);
    if (params != null && params.size() > 0) {
      Set<Map.Entry<String, Object>> entries = params.entrySet();
      for (Map.Entry<String, Object> entity : entries) {
        String key = entity.getKey();
        Object value = entity.getValue();
        builder.addParams(key, valueOf(value));
      }
    }

    if (isCache) {
      String fullUrl = appendParams(url, params);
      String string = getString(fullUrl);
      if (!TextUtils.isEmpty(string)) {
        try {
          Object o = finalCallback.parseNetworkResponse(string);
          finalCallback.onResponse(o);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    builder.tag(tag).build().execute(new Callback<Object>() {

      @Override public boolean validateReponse(Response response, int id) {
        if (response.code() == 504) {
          return false;
        }
        return true;
      }

      @Override public Object parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        if (TextUtils.isEmpty(string)) {
          throw throwEmptyContentError(url);
        }
        if (isCache) {
          String fullUrl = appendParams(url, params);
          saveString(fullUrl, string);
        }
        return finalCallback.parseNetworkResponse(string);
      }

      @Override public void onError(Call call, Exception e, int id) {
        finalCallback.onError(call, e);
        LogUtils.e(e);
      }

      @Override public void onResponse(Object response, int id) {
        try {
          finalCallback.onResponse(response);
        } catch (Exception e) {
          e.printStackTrace();
//          CrashReport.postCatchedException(e);
        }
      }
    });
  }

  /**
   * 服务器返回内容为空时直接抛出异常，进入异常处理逻辑
   */
  @NonNull private static NetworkErrorException throwEmptyContentError(String url) {
    NetworkErrorException throwable =
        new NetworkErrorException("The server return content is empty! Api Url is: " + url);
//    CrashReport.postCatchedException(throwable);
    return throwable;
  }

  /**
   * Http Post 表单 请求
   */
  public static void httpPostForm(final Context context, String tag, final String url,
      HashMap<String, Object> params, NetCallback callback) {
    httpPostFormFile(context, tag, url, params, callback, null);
  }

  /**
   * Http Post 表单 请求
   */
  public static void httpPostFormFile(final Context context, String tag, final String url,
      HashMap<String, Object> params, NetCallback callback, File file) {

    if (TextUtils.isEmpty(url)) {
      throw new InvalidParameterException("请输入正确的请求地址!");
    }

    if (callback == null) {
      callback = new NetCallback.DefaultNetCallback();
    }
    final NetCallback finalCallback = callback;

    PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);

    if (params != null && params.size() > 0) {
      Set<Map.Entry<String, Object>> entries = params.entrySet();
      for (Map.Entry<String, Object> entity : entries) {
        String key = entity.getKey();
        Object value = entity.getValue();
        //postFormBuilder.addParams(key, GsonUtils.GsonString(value));
        postFormBuilder.addParams(key, valueOf(value));
        //if (value instanceof File) {
        //  postFormBuilder.addFile("file", ((File) value).getName(), (File) value);
        //}
      }
    }
    if (file != null) {
      postFormBuilder.addFile("file", file.getName(), file);
    }
//    addCommonHeader(postFormBuilder, params);

    postFormBuilder.tag(tag).build().execute(new Callback() {

      @Override public boolean validateReponse(Response response, int id) {
        if (response.code() == 504) {
          return false;
        }
        return true;
      }

      @Override public Object parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        if (TextUtils.isEmpty(string)) {
          throw throwEmptyContentError(url);
        }
        return finalCallback.parseNetworkResponse(string);
      }

      @Override public void onError(Call call, Exception e, int id) {
        finalCallback.onError(call, e);
      }

      @Override public void onResponse(Object response, int id) {
        try {
          finalCallback.onResponse(response);
        } catch (Exception e) {
          e.printStackTrace();
//          CrashReport.postCatchedException(e);
        }
      }
    });
  }

  public static void httpPostFile(final Context context, String tag, String url, String filekey,
      File file, NetCallback callback) {

    if (TextUtils.isEmpty(url)) {
      throw new InvalidParameterException("请输入正确的请求地址!");
    }

    if (callback == null) {
      callback = new NetCallback.DefaultNetCallback();
    }
    final NetCallback finalCallback = callback;

    PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
    postFormBuilder.addFile(filekey, file.getName(), file);
  /*  if (params != null && params.size() > 0) {
      Set<Map.Entry<String, Object>> entries = params.entrySet();
      for (Map.Entry<String, Object> entity : entries) {
        String key = entity.getKey();
        Object value = entity.getValue();
        //postFormBuilder.addParams(key, GsonUtils.GsonString(value));
        postFormBuilder.addParams(key, valueOf(value));
      }
    }
*/
//    addCommonHeader(postFormBuilder, null);

    postFormBuilder.tag(tag).build().execute(new Callback() {
      @Override public Object parseNetworkResponse(Response response, int id) throws Exception {

        String string = response.body().string();

        return finalCallback.parseNetworkResponse(string);
      }

      @Override public void onError(Call call, Exception e, int id) {
        finalCallback.onError(call, e);
      }

      @Override public void onResponse(Object response, int id) {
        finalCallback.onResponse(response);
      }
    });
  }

  /** 取消请求 */
  public static void cancalByTag(String tag) {
    OkHttpUtils.getInstance().cancelTag(tag);
  }

  public static Response httpGetSync(String url) throws IOException {
    if (TextUtils.isEmpty(url)) {
      throw new InvalidParameterException("请输入正确的请求地址!");
    }
    GetBuilder getBuilder = OkHttpUtils.get();

    GetBuilder builder = getBuilder.url(url);
//    builder.addParams("app_type", "android");
//    String token = SPUtil.getString(StringConstant.TOKEN);
//    if (!TextUtils.isEmpty(token)) {
//      builder.addHeader(NetConstant.TOKEN, NetConstant.PROXY_PREFIX + ";" + token);
//    } else {
//      builder.addHeader(NetConstant.TOKEN, NetConstant.PROXY_PREFIX + ";");
//    }
    Response execute =
        builder.build().connTimeOut(3000).readTimeOut(3000).writeTimeOut(3000).execute();
    return execute;
  }

  /** 下载文件 */
  public static void httpGetFile(String url, String fileDir, String fileName,
      NetFileCallback<File> callback) {

    if (TextUtils.isEmpty(url)) {
      throw new InvalidParameterException("请输入正确的请求地址!");
    }
    if (callback == null) {
      callback = new NetFileCallback.DefaultNetFileCallback();
    }
    final NetFileCallback finalCallback = callback;
    OkHttpUtils.get().url(url).build().execute(new FileCallBack(fileDir, fileName) {

      @Override public void inProgress(float progress, long total, int id) {
        finalCallback.inProgress(progress, total);
      }

      @Override public void onError(Call call, Exception e, int id) {
        finalCallback.onError(call, e);
        LogUtils.e(e);
      }

      @Override public void onResponse(File response, int id) {
        finalCallback.onResponse(response);
      }
    });
  }

  /** 缓存处理 */
  private static final String HTTP_TEMP_CACHE = "HTTP_TEMP_CACHE";

  private static void saveString(String key, String value) {
    SharedPreferences sharedPreferences =
        SPUtil.getSharedPreferences(MyApplication.getInstance(), HTTP_TEMP_CACHE);
    SharedPreferences.Editor editor = sharedPreferences.edit().putString(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
  }

  private static String getString(String key) {
    SharedPreferences sharedPreferences =
        SPUtil.getSharedPreferences(MyApplication.getInstance(), HTTP_TEMP_CACHE);
    return sharedPreferences.getString(key, "");
  }

  public static String appendParams(String url, Map<String, Object> params) {
    if (url == null || params == null || params.isEmpty()) {
      return url;
    }
    Uri.Builder builder = Uri.parse(url).buildUpon();
    Set<String> keys = params.keySet();
    Iterator<String> iterator = keys.iterator();
    while (iterator.hasNext()) {
      String key = iterator.next();
      builder.appendQueryParameter(key, valueOf(params.get(key)));
    }
    return builder.build().toString();
  }
}
