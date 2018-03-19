package com.shizhefei.pickerview;

import android.os.Build;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import java.lang.reflect.Field;

/**
 * Created by hello on 2017/11/22.
 */

public class ViewUtils {

  /** 设置  listview 上下滑动时的边界颜色 */
  public static void setAbsListViewColor(AbsListView scrollableView, int color) {
    final String[] edgeGlows = {
        "mEdgeGlowTop", "mEdgeGlowBottom"
    };
    for (String edgeGlow : edgeGlows) {
      Class<?> clazz = scrollableView.getClass();
      while (clazz != null) {
        try {
          final Field edgeGlowField = clazz.getDeclaredField(edgeGlow);
          edgeGlowField.setAccessible(true);
          final EdgeEffect edgeEffect = (EdgeEffect) edgeGlowField.get(scrollableView);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edgeEffect.setColor(color);
          }
          break;
        } catch (Exception e) {
          clazz = clazz.getSuperclass();
        }
      }
    }
  }
}
