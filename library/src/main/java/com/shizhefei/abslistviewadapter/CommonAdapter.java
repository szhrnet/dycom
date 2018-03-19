package com.shizhefei.abslistviewadapter;

import android.content.Context;
import android.view.View;

import com.shizhefei.abslistviewadapter.base.ItemViewDelegate;
import java.util.List;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemView(layoutId);
    }

    public CommonAdapter(Context context, final int layoutId)
    {
        super(context);

        addItemView(layoutId);
    }

    private void addItemView(final int layoutId) {
        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
