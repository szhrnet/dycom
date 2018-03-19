package com.shizhefei.view.tableview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 崇智 on 2017/4/7.
 */

public class FixHeightListView extends ListView {
  public FixHeightListView(Context context) {
    super(context);
  }

  public FixHeightListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FixHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public FixHeightListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
    super.onMeasure(widthMeasureSpec, expandSpec);
  }
}
