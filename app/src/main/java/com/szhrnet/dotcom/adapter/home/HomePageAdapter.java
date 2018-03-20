package com.szhrnet.dotcom.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.fragment.AssortmentFragment;
import com.szhrnet.dotcom.fragment.CenterFragment;
import com.szhrnet.dotcom.fragment.HomeFragment;
import com.szhrnet.dotcom.fragment.ShoppingCarFragment;
import com.szhrnet.dotcom.view.CommonDrawableHelper;
import com.szhrnet.dotcom.view.TabIconManager;

/**
 * Created by Administrator on 2018/3/19.
 */

public class HomePageAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private final Drawable[] drawables;
    private final String[] titleData;
    private Context context;

    public HomePageAdapter(FragmentManager fragmentManager, Activity activity) {
        super(fragmentManager);
        this.context = activity;
        drawables = TabIconManager.defaultBottomIcon(activity);
        titleData = TabIconManager.defaultButtomTitle();
    }

    @Override
    public int getCount() {
        return drawables == null ? 0 : drawables.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.tab_main, container, false);
        }
        ImageView imageew = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.text_view);
        imageew.setImageDrawable(drawables[position]);
        textView.setText(titleData[position]);
        textView.setTextColor(CommonDrawableHelper.commonTextColorPrimarySubText());


        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, false);
        bundle.putString("TAG_INDEX", String.valueOf(position));
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AssortmentFragment();
                break;
            case 2:
                fragment = new ShoppingCarFragment();
                break;
            case 3:
                fragment = new CenterFragment();
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }
}
