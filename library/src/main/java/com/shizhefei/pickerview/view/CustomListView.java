package com.shizhefei.pickerview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.shizhefei.indicator.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZCZ on 2017/4/17.
 */

public class CustomListView extends ListView {

  private MyAdapter myAdapter;
  private int initPosition;
  public com.shizhefei.pickerview.view.OnItemSelectedListener onItemSelectedListener;
  public OnTextViewSelectedListener onTextViewSelectedListener;
  private int primaryColor = Color.RED;
  private int textColor = Color.BLACK;

  public CustomListView(Context context) {
    super(context);
    init(context);
  }

  public CustomListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  public void setPrimaryColorAndTextColor(int primaryColor, int textColor) {
    this.primaryColor = primaryColor;
    this.textColor = textColor;
  }

  private void init(Context context) {
    setScrollbarFadingEnabled(false);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    myAdapter = new MyAdapter();
    setAdapter(myAdapter);
    initPosition = -1;
    ((MyAdapter) getAdapter()).setCurrentItem(initPosition);
  }

  public void setItems(List<String> items) {
    myAdapter.setItems(items);
    myAdapter.notifyDataSetChanged();
  }

  public void setCurrentItem(int position) {
    if (position < 0) {
      this.initPosition = -1;
    } else {
      int count = getAdapter().getCount();
      if (count > position) {
        this.initPosition = position;
      }
    }

    ((MyAdapter) getAdapter()).setCurrentItem(position);

    // 回归顶部
    //invalidate();

    onItemSelected();
  }

  /**
   * @return 返回当前选择的位置，如果没有数据，返回 -1
   */
  public int getCurrentItem() {
    return initPosition;
  }

  protected final void onItemSelected() {
    if (onItemSelectedListener != null) {
      postDelayed(new OnCustomItemSelectedRunnable(this), 200L);
    }
  }

  public void setOnItemSelectedListener(
      com.shizhefei.pickerview.view.OnItemSelectedListener onItemSelectedListener) {
    this.onItemSelectedListener = onItemSelectedListener;
  }

  public void setOnTextViewSelectedListener(OnTextViewSelectedListener onTextViewSelectedListener) {
    this.onTextViewSelectedListener = onTextViewSelectedListener;
  }

  public class MyAdapter extends BaseAdapter {
    private List<String> items = new ArrayList<>();
    private int currentItem;

    public void setItems(List<String> items) {
      this.items = items;
    }

    @Override public int getCount() {
      return items.size();
    }

    @Override public String getItem(int position) {
      return items.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {

      ViewHolder holder = null;
      if (convertView == null) {
        convertView = View.inflate(parent.getContext(), R.layout.j_picker_list_items, null);
        holder = new ViewHolder();
        holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
        holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      String item = getItem(position);
      if (position == currentItem) {
        if (onTextViewSelectedListener != null) {
          onTextViewSelectedListener.setOnTextViewSelected(holder.textView);
        }

        holder.textView.setTextColor(primaryColor);
        Drawable drawable = parent.getContext().getResources().getDrawable(R.mipmap.ic_item_select);
        drawable.setColorFilter(new PorterDuffColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP));
        holder.imageView.setImageDrawable(drawable);
        holder.imageView.setVisibility(View.VISIBLE);
      } else {
        if (onTextViewSelectedListener != null) {
          onTextViewSelectedListener.setOnTextViewUnSelected(holder.textView);
        }
        holder.textView.setTextColor(textColor);
        holder.imageView.setVisibility(View.INVISIBLE);
      }
      holder.textView.setText(item);
      return convertView;
    }

    public class ViewHolder {
      TextView textView;
      ImageView imageView;
    }

    public void setCurrentItem(int position) {
      this.currentItem = position;
      notifyDataSetChanged();
    }
  }

  public interface OnTextViewSelectedListener {
    void setOnTextViewSelected(TextView textview);

    void setOnTextViewUnSelected(TextView textview);
  }
}
