package com.szhrnet.dotcom.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;

import com.szhrnet.dotcom.R;

/**
 * Created by ZCZ on 2017/5/11.
 */

public class TabIconManager {

    /**
     * 默认的 Bottom Drawable
     */
    public static Drawable[] defaultBottomIcon(Context context) {

        Drawable tab1 = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhzyb);
        Drawable tab1Normal = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhzya);

        Drawable tab2 = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhflb);
        Drawable tab2Normal = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhfla);

        Drawable tab3 = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhgwcb);
        Drawable tab3Normal = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhgwca);

        Drawable tab4 = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhwdb);
        Drawable tab4Normal = ContextCompat.getDrawable(context, R.mipmap.ic_home_dhwda);


        Drawable tab1Drawable = setSelectorDrawable(tab1Normal, tab1);
        Drawable tab2Drawable = setSelectorDrawable(tab2Normal, tab2);
        Drawable tab3Drawable = setSelectorDrawable(tab3Normal, tab3);
        Drawable tab4Drawable = setSelectorDrawable(tab4Normal, tab4);

        Drawable[] icons = new Drawable[4];
        icons[0] = tab1Drawable;
        icons[1] = tab2Drawable;
        icons[2] = tab3Drawable;
        icons[3] = tab4Drawable;
        return icons;
    }

    /**
     * 默认的 Bottom Title
     */
    public static String[] defaultButtomTitle() {
        return new String[]{"首页", "分类", "购物车", "我的"};
    }

//    /**
//     * 根据网络获取到的Icon生成图标集合
//     */
//    public static Drawable[] netBottomIcon(Context context, List<BottomImgBean> bottomImageUnselected) {
//
//        String cacheDir = TabIconManager.iconCacheDir(context);
//
//        int size = bottomImageUnselected.size();
//        Drawable[] icons = new Drawable[size];
//
//        for (int i = 0; i < size; i++) {
//            //BottomImgBean iconSelected = bottomImageSelected.get(i);
//            //String iconName = iconSelected.getIconName();
//
//            BottomImgBean bottomUnSelected = bottomImageUnselected.get(i);
//            String iconName1 = bottomUnSelected.getIconName();
//
//            Drawable tabSelected =
//                    BitmapDrawable.createFromPath(new File(cacheDir, iconName1).getAbsolutePath());
//            ThemeHelper.setDrawableColor(tabSelected);
//
//            Drawable tabUnSelected =
//                    BitmapDrawable.createFromPath(new File(cacheDir, iconName1).getAbsolutePath());
//
//            Drawable tab1Drawable = setSelectorDrawable(tabUnSelected, tabSelected);
//
//            icons[i] = tab1Drawable;
//        }
//        return icons;
//    }
//
//    /**
//     * 根据网络获取到的Icon生成图标集合
//     */
//    public static String[] netBottomTitle(List<BottomImgBean> bottomImageSelected) {
//        String[] titleDatas = new String[bottomImageSelected.size()];
//        for (int i = 0; i < bottomImageSelected.size(); i++) {
//            BottomImgBean bottomImgBean = bottomImageSelected.get(i);
//            titleDatas[i] = bottomImgBean.getTitle();
//        }
//        return titleDatas;
//    }
//
//    /**
//     * 底部Icon是否下载 完成
//     */
//    public static boolean bottomIconIsDownload(Context context,
//                                               List<BottomImgBean> bottmImageUnselected) {
//        String cacheDir = iconCacheDir(context);
//        boolean isFinished = true;
//        for (BottomImgBean bean : bottmImageUnselected) {
//            String iconName = bean.getIconName();
//            File file = new File(cacheDir, iconName);
//            if (!file.exists()) {
//                isFinished = false;
//                break;
//            }
//        }
//        return isFinished;
//    }
//
//    /**
//     * 根据 底部菜单信息 生成 图标名称
//     */
//    public static String getBottomIconName(BottomImgBean bean, boolean isSelected) {
//        String pic = bean.getPic();
//        String fileName = pic.substring(pic.lastIndexOf("/") + 1);
//        return fileName;
//    }

    /**
     * 图片动态添加 两种状态： 选中和未选中
     */
    public static Drawable setSelectorDrawable(Drawable drawableNormal, Drawable drawableSelect) {
        StateListDrawable drawable = new StateListDrawable();
        //选中
        drawable.addState(new int[]{android.R.attr.state_selected}, drawableSelect);
        //未选中
        drawable.addState(new int[]{-android.R.attr.state_selected}, drawableNormal);
        return drawable;
    }

    /**
     * Icon图标缓存目录
     */
    public static String iconCacheDir(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }
}
