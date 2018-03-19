package com.shizhefei.pickerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.shizhefei.indicator.R;
import com.shizhefei.pickerview.view.OnClicklistener;
import java.util.List;

/**
 * 文本选择器
 *
 * @version 0.1 king 2015-11
 */
public class CharacterPickerView extends FrameLayout {

  private WheelTwoOptions wheelOptions;

  public CharacterPickerView(Context context) {
    super(context);
    init(context);
  }

  public CharacterPickerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CharacterPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CharacterPickerView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    View.inflate(context, R.layout.j_picker_two_items, this);
    wheelOptions = new WheelTwoOptions(this);
  }

  public void setPicker(List<String> optionsItems) {
    wheelOptions.setPicker(optionsItems, null, null);
  }

  public void setPicker(List<String> options1Items, List<List<String>> options2Items) {
    wheelOptions.setPicker(options1Items, options2Items, null);
  }

  public void setPicker(List<String> options1Items, List<List<String>> options2Items,
      List<List<List<String>>> options3Items) {
    wheelOptions.setPicker(options1Items, options2Items, options3Items);
  }

  /**
   * 设置选中的item位置
   */
  public void setSelectOptions(int option1) {
    wheelOptions.setCurrentItems(option1, 0, 0);
  }

  /**
   * 设置选中的item位置
   */
  public void setSelectOptions(int option1, int option2) {
    wheelOptions.setCurrentItems(option1, option2, 0);
  }

  /**
   * 设置选中的item位置
   */
  public void setSelectOptions(int option1, int option2, int option3) {
    wheelOptions.setCurrentItems(option1, option2, option3);
  }

  public void setCurrentItems(int option1, int option2, int option3) {
    wheelOptions.setCurrentItems(option1, option2, option3);
  }

  /** 设置每个listview的item点击事件 */
  public void setClicklistener(OnClicklistener clicklistener) {
    wheelOptions.setClicklistener(clicklistener);
  }

  /**
   * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
   */
  public int[] getCurrentItems() {
    return wheelOptions.getCurrentItems();
  }

  public void setOnOptionChangedListener(OnOptionChangedListener listener) {
    this.wheelOptions.setOnOptionChangedListener(listener);
  }

  public void setPrimaryColorAndTextColor(int primaryColor, int textColor) {
    wheelOptions.setPrimaryColorAndTextColor(primaryColor, textColor);
  }
}
