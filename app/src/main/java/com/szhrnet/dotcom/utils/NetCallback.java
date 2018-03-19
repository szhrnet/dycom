package com.szhrnet.dotcom.utils;

import okhttp3.Call;

/**
 * Created by ZCZ on 2017/4/15.
 */

public interface NetCallback<T> {

  public abstract T parseNetworkResponse(String response) throws Exception;

  public abstract void onError(Call call, Exception e);

  public abstract void onResponse(T response);

  //public abstract void onProgress(float progress, long total);

  public static final class DefaultNetCallback implements NetCallback {

    @Override public Object parseNetworkResponse(String response) throws Exception {
      return null;
    }

    @Override public void onError(Call call, Exception e) {

    }

    @Override public void onResponse(Object response) {

    }
  }
}
