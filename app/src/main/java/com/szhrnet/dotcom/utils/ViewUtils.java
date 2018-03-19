package com.szhrnet.dotcom.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by hello on 2017/9/8.
 * Function:
 */

public class ViewUtils {

  public static void show(View view) {
    if (view != null && view.getVisibility() != View.VISIBLE) view.setVisibility(View.VISIBLE);
  }

  public static void hide(View view) {
    if (view != null && view.getVisibility() != View.GONE) view.setVisibility(View.GONE);
  }

  public static void invisible(View view) {
    if (view != null && view.getVisibility() != View.INVISIBLE) view.setVisibility(View.INVISIBLE);
  }

  public static void toggle(View view) {
    if (view == null) return;
    if (view.getVisibility() == View.VISIBLE) {
      view.setVisibility(View.GONE);
    } else {
      view.setVisibility(View.VISIBLE);
    }
  }

  public static void setBackground(View view, Drawable drawable) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      view.setBackground(drawable);
    } else {
      view.setBackgroundDrawable(drawable);
    }
  }
}
