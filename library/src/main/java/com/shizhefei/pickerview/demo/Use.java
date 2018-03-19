package com.shizhefei.pickerview.demo;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.shizhefei.pickerview.CharacterPickerView;
import com.shizhefei.pickerview.OnOptionChangedListener;

/**
 * Created by ZCZ on 2017/4/17.
 */

public class Use {
  private RelativeLayout showView(final Context context) {
    RelativeLayout layout = new RelativeLayout(context);

    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

    CharacterPickerView pickerView = new CharacterPickerView(context);
    layout.addView(pickerView, layoutParams);

    //初始化选项数据
    OptionsWindowHelper.setPickerData(pickerView);

    //设置监听事件
    pickerView.setOnOptionChangedListener(new OnOptionChangedListener() {
      @Override public void onOptionChanged(int option1, int option2, int option3) {
        String msg = option1 + "," + option2 + "," + option3;
        Log.e("test", msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
      }
    });

    return layout;
  }
}
