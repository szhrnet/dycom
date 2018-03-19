package com.szhrnet.dotcom.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.utils.ViewUtils;


/**
 * Created by hello on 2017/9/8.
 * Function: // 带提示，确认和取消的弹窗
 */

public class DialogCustom extends BaseDialog implements View.OnClickListener {
  private TextView tv_title;
  private TextView tv_tip;
  private LinearLayout ll_content;
  private TextView tv_cancel;
  private TextView tv_confirm;
  private View verView;

  private DialogCustomListener dialogCustomListener;

  public DialogCustom(Activity activity) {
    super(activity);
    init();
  }

  protected void init() {
    setContentView(R.layout.dialog_custom);
    tv_title = (TextView) findViewById(R.id.dialog_custom_tv_title);
    tv_tip = (TextView) findViewById(R.id.dialog_custom_tv_tip);
    ll_content = (LinearLayout) findViewById(R.id.dialog_custom_ll_content);
    tv_cancel = (TextView) findViewById(R.id.dialog_custom_tv_cancel);
    tv_confirm = (TextView) findViewById(R.id.dialog_custom_tv_confirm);
    verView = findViewById(R.id.verview);

    tv_cancel.setOnClickListener(this);
    tv_confirm.setOnClickListener(this);

    ViewUtils.hide(tv_title);
    ViewUtils.hide(tv_cancel);
    ViewUtils.hide(tv_confirm);

//    ViewUtils.setBackground(findViewById(R.id.ll_root),
//        CommonDrawableHelper.commonBgCustomColorCorner(ThemeHelper.getWhiteColor(),
//            DensityUtils.dp2px(5)));
//    setTextTitle("提示").setTextColorTitle(ThemeHelper.getBlackColor())
//        .setTextConfirm("确定")
//        .setTextColorConfirm(ThemeHelper.getPrimaryColor())
//        .setTextCancel("取消")
//        .setTextColorCancel(ThemeHelper.getBlackColor())
//        .setTextColorTips(ThemeHelper.getBlackColor());
//
//    int padding = DensityUtils.dp2px(50);
//    paddingLeft(padding);
//    paddingRight(padding);
  }

  public DialogCustom setCustomView(View view) {
    setCustomView(view, null);
    return this;
  }

  public DialogCustom setCustomView(View view, LinearLayout.LayoutParams params) {
    ll_content.removeAllViews();
    if (params == null) {
      params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.MATCH_PARENT);
    }
    ll_content.addView(view, params);
    return this;
  }

  public DialogCustom setCustomView(int layoutId) {
    ll_content.removeAllViews();
    LayoutInflater.from(getContext()).inflate(layoutId, ll_content, true);
    return this;
  }

  // tip
  public void showTip(String tip, long time) {
    if (!TextUtils.isEmpty(tip) && time > 0) {
      tv_tip.setText(tip);
      ViewUtils.show(tv_tip);
      //timer.startWork(time, new SDTimerListener() {
      //
      //  @Override public void onWorkMain() {
      //    tv_tip.setText("");
      //    ViewUtils.hide(tv_tip);
      //  }
      //
      //  @Override public void onWork() {
      //  }
      //});
    }
  }

  public void showTip(String tip) {
    showTip(tip, 2000);
  }

  // ---------------------------color

  public DialogCustom setTextColorTitle(int color) {
    tv_title.setTextColor(color);
    return this;
  }

  public DialogCustom setTextColorTips(int color) {
    tv_tip.setTextColor(color);
    return this;
  }

  public DialogCustom setTextColorCancel(int color) {
    tv_cancel.setTextColor(color);
    return this;
  }

  public DialogCustom setTextColorConfirm(int color) {
    tv_confirm.setTextColor(color);
    return this;
  }

  // ---------------------------text
  public DialogCustom setTextTitle(String text) {
    if (TextUtils.isEmpty(text)) {
      ViewUtils.hide(tv_title);
    } else {
      ViewUtils.show(tv_title);
      tv_title.setText(text);
    }
    return this;
  }

  public DialogCustom setTextTip(CharSequence text) {
    if (TextUtils.isEmpty(text)) {
      ViewUtils.hide(tv_tip);
    } else {
      ViewUtils.show(tv_tip);
      tv_tip.setText(text);
    }
    return this;
  }

  public DialogCustom setTextTipGravity(int gravity) {
    tv_tip.setGravity(gravity);
    return this;
  }

  public DialogCustom setTextCancel(String text) {
    if (TextUtils.isEmpty(text)) {
      ViewUtils.hide(tv_cancel);
      ViewUtils.hide(verView);
    } else {
      ViewUtils.show(tv_cancel);
      tv_cancel.setText(text);
    }
    return this;
  }

  public DialogCustom setTextConfirm(String text) {
    if (TextUtils.isEmpty(text)) {
      ViewUtils.hide(tv_confirm);
      ViewUtils.hide(verView);
    } else {
      ViewUtils.show(tv_confirm);
      tv_confirm.setText(text);
    }
    return this;
  }

  public DialogCustom setDialogCustomListener(DialogCustomListener dialogCustomListener) {
    this.dialogCustomListener = dialogCustomListener;
    return this;
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.dialog_custom_tv_cancel) {
      super.dismiss();
      if (dialogCustomListener != null) dialogCustomListener.onCancel(v, this);
    } else if (v.getId() == R.id.dialog_custom_tv_confirm) {
      super.dismiss();
      if (dialogCustomListener != null) dialogCustomListener.onConfirm(v, this);
    }
  }

  @Override public void dismiss() {
    super.dismiss();
    if (dialogCustomListener != null) dialogCustomListener.onDismiss(this);
  }

  public interface DialogCustomListener {
    void onCancel(View v, DialogCustom dialog);

    void onConfirm(View v, DialogCustom dialog);

    void onDismiss(DialogCustom dialog);
  }
}
