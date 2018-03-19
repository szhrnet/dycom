package com.shizhefei.abslistviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shizhefei.abslistviewadapter.base.ItemViewDelegate;
import com.shizhefei.abslistviewadapter.base.ItemViewDelegateManager;
import java.util.ArrayList;
import java.util.List;

public class MultiItemTypeAdapter<T> extends BaseAdapter {
  protected Context mContext;
  protected List<T> mDatas;

  private ItemViewDelegateManager<T> mItemViewDelegateManager;

  public MultiItemTypeAdapter(Context context, List<T> datas) {
    this.mContext = context;
    this.mDatas = datas == null ? new ArrayList<T>() : datas;
    mItemViewDelegateManager = new ItemViewDelegateManager();
  }

  public MultiItemTypeAdapter(Context context) {
    this.mContext = context;
    this.mDatas = new ArrayList<T>();
    mItemViewDelegateManager = new ItemViewDelegateManager();
  }

  public void addItem(T item) {
    mDatas.add(item);
    notifyDataSetChanged();
  }

  public void addItem(List<T> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void addAllDatas(List<T> datas) {
    mDatas.clear();
    mDatas.addAll(datas == null ? new ArrayList<T>() : datas);
    notifyDataSetChanged();
  }

  public List<T> getDatas() {
    return mDatas;
  }

  public void removeAllDatas() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void removeItem(T item) {
    if (mDatas.contains(item)) {
      mDatas.remove(item);
      notifyDataSetChanged();
    }
  }

  public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
    mItemViewDelegateManager.addDelegate(itemViewDelegate);
    return this;
  }

  public MultiItemTypeAdapter removeItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
    mItemViewDelegateManager.removeDelegate(itemViewDelegate);
    return this;
  }

  private boolean useItemViewDelegateManager() {
    return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
  }

  @Override public int getViewTypeCount() {
    if (useItemViewDelegateManager()) return mItemViewDelegateManager.getItemViewDelegateCount();
    return super.getViewTypeCount();
  }

  @Override public int getItemViewType(int position) {
    if (useItemViewDelegateManager()) {
      int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
      return viewType;
    }
    return super.getItemViewType(position);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ItemViewDelegate itemViewDelegate =
        mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
    int layoutId = itemViewDelegate.getItemViewLayoutId();
    ViewHolder viewHolder = null;
    if (convertView == null) {
      View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
      viewHolder = new ViewHolder(mContext, itemView, parent, position);
      viewHolder.mLayoutId = layoutId;
      onViewHolderCreated(viewHolder, viewHolder.getConvertView());
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
      viewHolder.mPosition = position;
    }

    convert(viewHolder, getItem(position), position);
    return viewHolder.getConvertView();
  }

  protected void convert(ViewHolder viewHolder, T item, int position) {
    mItemViewDelegateManager.convert(viewHolder, item, position);
  }

  public void onViewHolderCreated(ViewHolder holder, View itemView) {
  }

  @Override public int getCount() {
    return mDatas.size();
  }

  @Override public T getItem(int position) {
    return mDatas.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }
}
