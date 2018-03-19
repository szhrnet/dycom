package com.shizhefei.view.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.shizhefei.indicator.R;
import com.shizhefei.view.tableview.listeners.TableDataClickListener;
import com.shizhefei.view.tableview.listeners.TableDataLongClickListener;
import com.shizhefei.view.tableview.model.TableColumnModel;
import com.shizhefei.view.tableview.model.TableColumnWeightModel;
import com.shizhefei.view.tableview.providers.TableDataRowBackgroundProvider;
import com.shizhefei.view.tableview.toolkit.TableDataRowBackgroundProviders;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 崇智 on 2017/4/5.
 */

public class TableView<T> extends LinearLayout {

  private static final String LOG_TAG = TableView.class.getName();

  private static final int DEFAULT_COLUMN_COUNT = 4;
  private static final int DEFAULT_HEADER_ELEVATION = 1;
  private static final int DEFAULT_HEADER_COLOR = 0xFFCCCCCC;

  private final Set<TableDataLongClickListener<T>> dataLongClickListeners = new HashSet<>();
  private final Set<TableDataClickListener<T>> dataClickListeners = new HashSet<>();

  private TableHeaderView tableHeaderView;
  private ListView tableDataView;

  private TableDataRowBackgroundProvider<? super T> dataRowBackgroundProvider =
      TableDataRowBackgroundProviders.similarRowColor(0x00000000);
  private TableColumnModel columnModel;

  private TableHeaderAdapter tableHeaderAdapter;
  private TableDataAdapter<T> tableDataAdapter;

  private int headerElevation;
  private int headerColor;
  private boolean tableViewFixHeight;

  public TableView(Context context) {
    this(context, null);
  }

  public TableView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, android.R.attr.listViewStyle);
  }

  public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    setOrientation(VERTICAL);
    setAttributes(attrs);
    setupTableHeaderView(attrs);
    setupTableDataView(attrs, defStyleAttr);
  }

  private void setupTableHeaderView(AttributeSet attrs) {
    tableHeaderAdapter = new DefaultTableHeaderAdapter(getContext());

    final TableHeaderView tableHeaderView = new TableHeaderView(getContext());
    tableHeaderView.setBackgroundColor(0xFFCCCCCC);
    //取消点击效果
    tableHeaderView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    setHeaderView(tableHeaderView);
  }

  private void setHeaderView(TableHeaderView headerView) {
    this.tableHeaderView = headerView;

    tableHeaderView.setAdapter(tableHeaderAdapter);
    tableHeaderView.setBackgroundColor(headerColor);
    tableHeaderView.setId(R.id.table_header_view);

    if (getChildCount() == 2) {
      removeViewAt(0);
    }

    addView(tableHeaderView, 0);

    forceRefresh();
  }

  private void setupTableDataView(AttributeSet attributes, int styleAttributes) {
    final LayoutParams dataViewLayoutParams =
        new LayoutParams(getWidthAttribute(attributes), LayoutParams.MATCH_PARENT);

    tableDataAdapter = new DefaultTableDataAdapter(getContext());

    tableDataAdapter.setRowBackgroundProvider(dataRowBackgroundProvider);
    if (tableViewFixHeight) {
      tableDataView = new FixHeightListView(getContext(), attributes, styleAttributes);
    } else {
      tableDataView = new ListView(getContext(), attributes, styleAttributes);
    }
    //取消点击效果
    tableDataView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    tableDataView.setOnItemClickListener(new InternalDataClickListener());
    tableDataView.setOnItemLongClickListener(new InternalDataLongClickListener());
    tableDataView.setLayoutParams(
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    tableDataView.setAdapter(tableDataAdapter);
    tableDataView.setId(R.id.table_data_view);
    tableDataView.setDivider(null);
    //tableDataView.setOnScrollListener(new InternalOnScrollListener());
    tableDataView.setLayoutParams(dataViewLayoutParams);

    addView(tableDataView);
  }

  private void setAttributes(AttributeSet attributes) {
    final TypedArray styledAttributes =
        getContext().obtainStyledAttributes(attributes, R.styleable.TableView);

    headerColor =
        styledAttributes.getInt(R.styleable.TableView_tableView_headerColor, DEFAULT_HEADER_COLOR);
    headerElevation = styledAttributes.getInt(R.styleable.TableView_tableView_headerElevation,
        DEFAULT_HEADER_ELEVATION);
    final int columnCount =
        styledAttributes.getInt(R.styleable.TableView_tableView_columnCount, DEFAULT_COLUMN_COUNT);
    columnModel = new TableColumnWeightModel(columnCount);

    tableViewFixHeight =
        styledAttributes.getBoolean(R.styleable.TableView_tableView_fixHeight, false);
    styledAttributes.recycle();
  }

  private int getWidthAttribute(final AttributeSet attributes) {
    final TypedArray ta =
        getContext().obtainStyledAttributes(attributes, new int[] { android.R.attr.layout_width });
    final int layoutWidth = ta.getLayoutDimension(0, ViewGroup.LayoutParams.MATCH_PARENT);
    ta.recycle();
    return layoutWidth;
  }

  @Override public void setSaveEnabled(final boolean enabled) {
    super.setSaveEnabled(enabled);
    tableHeaderView.setSaveEnabled(enabled);
    tableDataView.setSaveEnabled(enabled);
  }

  private void forceRefresh() {
    if (tableHeaderView != null) {
      tableHeaderView.invalidate();
      tableHeaderAdapter.notifyDataSetChanged();
    }
    if (tableDataView != null) {
      tableDataView.invalidate();
      tableDataAdapter.notifyDataSetChanged();
    }
  }

  public void setHeaderViewVisible(int visible) {
    tableHeaderView.setVisibility(visible);
  }

  /**
   * Sets the given resource as background of the table header.
   *
   * @param resId The if of the resource tht shall be set as background of the table header.
   */
  public void setHeaderBackground(@DrawableRes final int resId) {
    tableHeaderView.setBackgroundResource(resId);
  }

  /**
   * Sets the given color as background of the table header.
   *
   * @param color The color that shall be set as background of the table header.
   */
  public void setHeaderBackgroundColor(@ColorInt final int color) {
    tableHeaderView.setBackgroundColor(color);
  }

  public void setDataRowBackgroundProvider(
      final TableDataRowBackgroundProvider<? super T> backgroundProvider) {
    dataRowBackgroundProvider = backgroundProvider;
    tableDataAdapter.setRowBackgroundProvider(dataRowBackgroundProvider);
  }

  public void addDataClickListener(final TableDataClickListener<T> listener) {
    dataClickListeners.add(listener);
  }

  public void addDataLongClickListener(final TableDataLongClickListener<T> listener) {
    dataLongClickListeners.add(listener);
  }

  public TableHeaderAdapter getHeaderAdapter() {
    return tableHeaderAdapter;
  }

  public void setHeaderAdapter(final TableHeaderAdapter headerAdapter) {
    tableHeaderAdapter = headerAdapter;
    tableHeaderAdapter.setColumnModel(columnModel);
    tableHeaderView.setAdapter(tableHeaderAdapter);
    forceRefresh();
  }

  public TableDataAdapter<T> getDataAdapter() {
    return tableDataAdapter;
  }

  public void setDataAdapter(final TableDataAdapter<T> dataAdapter) {
    tableDataAdapter = dataAdapter;
    tableDataAdapter.setColumnModel(columnModel);
    tableDataAdapter.setRowBackgroundProvider(dataRowBackgroundProvider);
    tableDataView.setAdapter(tableDataAdapter);
    forceRefresh();
  }

  public TableColumnModel getColumnModel() {
    return columnModel;
  }

  public void setColumnModel(final TableColumnModel columnModel) {
    this.columnModel = columnModel;
    this.tableHeaderAdapter.setColumnModel(this.columnModel);
    this.tableDataAdapter.setColumnModel(this.columnModel);
    forceRefresh();
  }

  public int getColumnCount() {
    return columnModel.getColumnCount();
  }

  public void setColumnCount(final int columnCount) {
    columnModel.setColumnCount(columnCount);
    forceRefresh();
  }

  /**
   * Internal management of clicks on the data view.
   *
   * @author ISchwarz
   */
  private class InternalDataClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int i,
        final long l) {
      informAllListeners(i);
    }

    private void informAllListeners(final int rowIndex) {
      final T clickedObject = tableDataAdapter.getItem(rowIndex);

      for (final TableDataClickListener<T> listener : dataClickListeners) {
        try {
          listener.onDataClicked(rowIndex, clickedObject);
        } catch (final Throwable t) {
          Log.w(LOG_TAG, "Caught Throwable on listener notification: " + t.toString());
          // continue calling listeners
        }
      }
    }
  }

  /**
   * Internal long click management of clicks on the data view.
   *
   * @author ISchwarz
   */
  private class InternalDataLongClickListener implements AdapterView.OnItemLongClickListener {

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int rowIndex,
        final long id) {
      return informAllListeners(rowIndex);
    }

    private boolean informAllListeners(final int rowIndex) {
      final T clickedObject = tableDataAdapter.getItem(rowIndex);
      boolean isConsumed = false;

      for (final TableDataLongClickListener<T> listener : dataLongClickListeners) {
        try {
          isConsumed |= listener.onDataLongClicked(rowIndex, clickedObject);
        } catch (final Throwable t) {
          Log.w(LOG_TAG, "Caught Throwable on listener notification: " + t.toString());
          // continue calling listeners
        }
      }
      return isConsumed;
    }
  }

  private class DefaultTableHeaderAdapter extends TableHeaderAdapter {

    public DefaultTableHeaderAdapter(final Context context) {
      super(context, columnModel);
    }

    @Override public View getHeaderView(final int columnIndex, final ViewGroup parentView) {
      final TextView view = new TextView(getContext());
      view.setText(" ");
      view.setPadding(20, 40, 20, 40);
      return view;
    }
  }

  private class DefaultTableDataAdapter extends TableDataAdapter<T> {

    public DefaultTableDataAdapter(final Context context) {
      super(context, columnModel, new ArrayList<T>());
    }

    @Override
    public View getCellView(final int rowIndex, final int columnIndex, final ViewGroup parentView) {
      return new TextView(getContext());
    }
  }
}
