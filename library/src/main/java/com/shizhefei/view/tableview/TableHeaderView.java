package com.shizhefei.view.tableview;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * This view represents the header of a table. The given {@link TableHeaderAdapter} is used to fill
 * this view with data.
 *
 * @author ISchwarz
 */
class TableHeaderView extends ListView {

  private TableHeaderAdapter adapter;

  /**
   * Creates a new TableHeaderView.
   *
   * @param context The context that shall be used.
   */
  public TableHeaderView(final Context context) {
    super(context);
    setLayoutParams(
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
  }

  /**
   * Sets the {@link TableHeaderAdapter} that is used to render the header views of every single
   * column.
   *
   * @param adapter The {@link TableHeaderAdapter} that should be set.
   */
  public void setAdapter(final TableHeaderAdapter adapter) {
    this.adapter = adapter;
    super.setAdapter(adapter);
  }

  @Override public TableHeaderAdapter getAdapter() {
    return adapter;
  }

  @Override public void invalidate() {
    if (adapter != null) {
      adapter.notifyDataSetChanged();
    }
    super.invalidate();
  }
}
