package com.shizhefei.pickerview;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import com.shizhefei.indicator.R;
import com.shizhefei.pickerview.view.CustomListView;
import com.shizhefei.pickerview.view.OnClicklistener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZCZ on 2017/4/17.
 */

public class WheelTwoOptions {
  private final CharacterPickerView view;

  private CustomListView lv_option1;
  private CustomListView lv_option2;
  private CustomListView lv_option3;

  private List<String> mOptions1Items;
  private List<List<String>> mOptions2Items;
  private List<List<List<String>>> mOptions3Items;
  private OnOptionChangedListener mOnOptionChangedListener;

  private OnClicklistener clicklistener;

  public void setClicklistener(OnClicklistener clicklistener) {
    this.clicklistener = clicklistener;
  }

  public View getView() {
    return view;
  }

  public WheelTwoOptions(CharacterPickerView view) {
    super();
    this.view = view;
  }

  private int primaryColor = Color.RED;
  private int textColor = Color.BLACK;

  public void setPrimaryColorAndTextColor(int primaryColor, int textColor) {
    this.primaryColor = primaryColor;
    this.textColor = textColor;
  }

  public void setOnOptionChangedListener(OnOptionChangedListener listener) {
    this.mOnOptionChangedListener = listener;
  }

  public void setPicker(ArrayList<String> optionsItems) {
    setPicker(optionsItems, null, null);
  }

  public void setPicker(List<String> options1Items, List<List<String>> options2Items) {
    setPicker(options1Items, options2Items, null);
  }

  public void setPicker(List<String> options1Items, List<List<String>> options2Items,
      List<List<List<String>>> options3Items) {
    this.mOptions1Items = options1Items == null ? new ArrayList<String>() : options1Items;
    this.mOptions2Items = options2Items == null ? new ArrayList<List<String>>() : options2Items;
    this.mOptions3Items =
        options3Items == null ? new ArrayList<List<List<String>>>() : options3Items;
    // 选项1
    lv_option1 = (CustomListView) view.findViewById(R.id.j_options1);
    lv_option1.setPrimaryColorAndTextColor(primaryColor, textColor);
    ViewUtils.setAbsListViewColor(lv_option1, primaryColor);
    lv_option1.setItems(mOptions1Items);// 设置显示数据
    lv_option1.setCurrentItem(0);// 初始化时显示的数据
    //滚动监听
    lv_option1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == -1) {
          return;
        }
        if (clicklistener != null) {
          clicklistener.onClick();
        }
        lv_option1.setCurrentItem(position);

        if (mOptions2Items.isEmpty()) {
          doItemChange();
          return;
        }

        List<String> items = mOptions2Items.get(position);

        lv_option2.setItems(items);
        lv_option2.setCurrentItem(0);

        if (mOptions3Items.isEmpty()) {
          doItemChange();
          return;
        }

        List<String> items1 = new ArrayList<String>();
        List<List<String>> lists = mOptions3Items.get(position);
        if (lists != null && lists.size() > 0) {
          items1 = lists.get(0);
        }

        lv_option3.setItems(items1);
        lv_option3.setCurrentItem(-1);

        //如果二级数据 没有数据，回调选择方法
        if (items.size() == 0) {
          doItemChange();
        }
      }
    });

    // 选项2
    lv_option2 = (CustomListView) view.findViewById(R.id.j_options2);
    lv_option2.setPrimaryColorAndTextColor(primaryColor, textColor);
    ViewUtils.setAbsListViewColor(lv_option2, primaryColor);
    if (!mOptions2Items.isEmpty()) {
      lv_option2.setItems(mOptions2Items.get(0));// 设置显示数据
      //lv_option2.setCurrentItem(0);// 初始化时显示的数据
      //滚动监听
      lv_option2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          if (position == -1) {
            return;
          }
          if (clicklistener != null) {
            clicklistener.onClick();
          }
          //上一级选择后才能选择下一级
          if (lv_option1.getCurrentItem() == -1) {
            return;
          }

          lv_option2.setCurrentItem(position);

          if (mOptions3Items.isEmpty()) {
            doItemChange();
            return;
          }

          if (lv_option1.getCurrentItem() < mOptions3Items.size()) {
            List<List<String>> allItems3 = mOptions3Items.get(lv_option1.getCurrentItem());
            if (position >= allItems3.size()) {
              position = 0;
            }
            List<String> items = allItems3.get(position);
            lv_option3.setItems(items);
            lv_option3.setCurrentItem(-1);

            //如果三级数据 没有数据，回调选择方法
            if (items.size() == 0) {
              doItemChange();
            }
          }
        }
      });
    }

    // 选项3
    lv_option3 = (CustomListView) view.findViewById(R.id.j_options3);
    lv_option3.setPrimaryColorAndTextColor(primaryColor, textColor);
    ViewUtils.setAbsListViewColor(lv_option3, primaryColor);
    if (!mOptions3Items.isEmpty()) {
      lv_option3.setItems(mOptions3Items.get(0).get(0));// 设置显示数据
      //lv_option3.setCurrentItem(0);// 初始化时显示的数据
      //滚动监听
      lv_option3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

          //上一级选择后才能选择下一级
          if (lv_option1.getCurrentItem() == -1 || lv_option2.getCurrentItem() == -1) {
            return;
          }
          if (clicklistener != null) {
            clicklistener.onClick();
          }
          lv_option3.setCurrentItem(position);

          doItemChange();
        }
      });
    }

    if (mOptions2Items.isEmpty()) view.findViewById(R.id.j_layout2).setVisibility(View.GONE);
    if (mOptions3Items.isEmpty()) view.findViewById(R.id.j_layout3).setVisibility(View.GONE);

    //setCurrentItems(0, 0, 0);
  }

  /**
   * 选中项改变
   */
  private void doItemChange() {
    if (mOnOptionChangedListener != null) {
      //Object selectedItem = viewById.getSelectedItem();
      int option1 = lv_option1.getCurrentItem();
      int option2 = lv_option2.getCurrentItem();
      int option3 = lv_option3.getCurrentItem();

      mOnOptionChangedListener.onOptionChanged(option1, option2, option3);
    }
  }

  /**
   * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
   */
  public int[] getCurrentItems() {
    int[] currentItems = new int[3];
    currentItems[0] = lv_option1.getCurrentItem();
    currentItems[1] = lv_option2.getCurrentItem();
    currentItems[2] = lv_option3.getCurrentItem();
    return currentItems;
  }

  /**
   * 设置选中的item位置
   */
  public void setCurrentItems(int option1, int option2, int option3) {
    lv_option1.setCurrentItem(option1);
    lv_option2.setItems(mOptions2Items.get(option1));
    lv_option2.setCurrentItem(option2);
    lv_option3.setItems(mOptions3Items.get(option1).get(option2));
    lv_option3.setCurrentItem(option3);
  }
}
