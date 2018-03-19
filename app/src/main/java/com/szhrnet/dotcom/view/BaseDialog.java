package com.szhrnet.dotcom.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szhrnet.dotcom.MyApplication;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.utils.DensityUtils;


/**
 * Created by hello on 2017/9/8.
 * Function: 通用Dialog基类
 */

public class BaseDialog extends Dialog {

  public static final int DEFAULT_PADDING_LEFT_RIGHT =
      DensityUtils.dp2px(MyApplication.getInstance(), 20);
  public static final int DEFAULT_PADDING_TOP_BOTTOM =
      DensityUtils.dp2px(MyApplication.getInstance(), 10);

  private LinearLayout linearLayoutRoot;
  private View contentView;

  public BaseDialog(Activity activity) {
    this(activity, R.style.MyDialogStyleBottom);
  }

  public BaseDialog(@NonNull Activity context, @StyleRes int themeResId) {
    super(context, themeResId);
    setOwnerActivity(context);
    baseInit();
  }

  private void baseInit() {
    linearLayoutRoot = new LinearLayout(getContext());
    linearLayoutRoot.setBackgroundColor(Color.parseColor("#00000000"));
    linearLayoutRoot.setGravity(Gravity.CENTER);
    setCanceledOnTouchOutside(false);
  }

  //在指定view下显示时 上部分灰色区域点击无反应
  @Override public void setCanceledOnTouchOutside(boolean cancel) {
    super.setCanceledOnTouchOutside(cancel);
    if (cancel) {
      linearLayoutRoot.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismiss();
        }
      });
    }
  }

  // ---------------------show gravity

  /**
   * 设置窗口显示的位置
   */
  public BaseDialog setGrativity(int gravity) {
    getWindow().setGravity(gravity);
    return this;
  }

  public void showTop() {
    setGrativity(Gravity.TOP);

    show();
  }

  public void showBottom() {
    setGrativity(Gravity.BOTTOM);
    show();
  }

  @Override public void show() {
    if (getOwnerActivity() != null && !getOwnerActivity().isFinishing()) {
      super.show();
    }
  }

  /**
   * 显示在指定控件的下方位置
   */
  public void showViewBottom(View v) {
    if (v == null) {
      show();
      return;
    }

    int height = v.getMeasuredHeight();

    int[] location = new int[2];
    v.getLocationInWindow(location);//若是普通activity,则y坐标为可见的状态栏高度+可见的标题栏高度+view左上角到标题栏底部的距离.
    //减去状态栏高度

    int statusBarHeight = 0;
    //获取status_bar_height资源的ID
    int resourceId =
        v.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      //根据资源ID获取响应的尺寸值
      statusBarHeight = v.getContext().getResources().getDimensionPixelSize(resourceId);
    }
    int showTopPadding = location[1] - statusBarHeight + height;

    paddingTop(showTopPadding);
    showTop();
  }

  /**
   * 设置窗口的显示和隐藏动画
   */
  public void setAnimations(int resId) {
    getWindow().setWindowAnimations(resId);
  }

  // -----------------------padding

  public BaseDialog paddingTop(int top) {
    linearLayoutRoot.setPadding(linearLayoutRoot.getPaddingLeft(), top,
        linearLayoutRoot.getPaddingRight(), linearLayoutRoot.getPaddingBottom());
    return this;
  }

  public BaseDialog paddingBottom(int bottom) {
    linearLayoutRoot.setPadding(linearLayoutRoot.getPaddingLeft(), linearLayoutRoot.getPaddingTop(),
        linearLayoutRoot.getPaddingRight(), bottom);
    return this;
  }

  public BaseDialog paddingLeft(int left) {
    linearLayoutRoot.setPadding(left, linearLayoutRoot.getPaddingTop(),
        linearLayoutRoot.getPaddingRight(), linearLayoutRoot.getPaddingBottom());
    return this;
  }

  public BaseDialog paddingRight(int right) {
    linearLayoutRoot.setPadding(linearLayoutRoot.getPaddingLeft(), linearLayoutRoot.getPaddingTop(),
        right, linearLayoutRoot.getPaddingBottom());
    return this;
  }

  public BaseDialog paddings(int paddings) {
    linearLayoutRoot.setPadding(paddings, paddings, paddings, paddings);
    return this;
  }

  /**
   * 设置窗口上下左右的边距
   */
  public BaseDialog padding(int left, int top, int right, int bottom) {
    linearLayoutRoot.setPadding(left, top, right, bottom);
    return this;
  }

  private BaseDialog setDialogView(View view, ViewGroup.LayoutParams params) {
    contentView = view;
    wrapperView(contentView);
    if (params == null) {
      params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    super.setContentView(linearLayoutRoot, params);
    //设置全屏生效
    getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    return this;
  }

  private void wrapperView(View view) {
    linearLayoutRoot.removeAllViews();
    linearLayoutRoot.addView(view,
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT));
  }

  // ------------------------setContentView

  @Override public void setContentView(int layoutResID) {
    View view = LayoutInflater.from(getContext()).inflate(layoutResID, null);
    this.setContentView(view, null);
  }

  public void setContentView(int layoutResID, ViewGroup.LayoutParams params) {
    View view = LayoutInflater.from(getContext()).inflate(layoutResID, null);
    this.setContentView(view, params);
  }

  @Override public void setContentView(View view) {
    this.setContentView(view, null);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    setDialogView(view, params);
  }

  public View getContentView() {
    return contentView;
  }
}
