package com.szhrnet.dotcom.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;

import com.szhrnet.dotcom.MyApplication;
import com.szhrnet.dotcom.R;

/**
 * Created by hello on 2017/10/13.
 * [Android GradientDrawable(shape标签定义) 静态使用和动态使用(圆角，渐变实现)](http://www.cnblogs.com/popfisher/p/5606690.html)
 * <p>
 * 命名规则: common-bg-primary-background-corner-stroke
 * 是否通用-背景或其它-颜色分类-颜色分类-是否圆角-是否带边框
 * <p>
 * 基本类型的状态选择
 * 1. {@link #commonBgStatusCustomColorCorner(int, int, int)}
 * <p>
 * 三种基本类型的背景，无状态选择：
 * 1. {@link #commonBgCustomColorCorner(int, int)} 自定义背景，圆角自定义
 * 2. {@link #commonBgCustomColorCornerStroke(int, int)} 无背景，连线自定义，圆角自定义
 * 3. {@link #commonBgCustomColorCornerStroke(int, int, int)} 自定义背景色，自定义线框色，自定义圆角
 * <p>
 * 圆圈带连线类型的背景：
 * 1. {@link #commonBgCustomColorCircle(int)} 圆圈：颜色自定义
 * 2. {@link #commonBgCustomColorCircleStroke(int)} 圆圈：白色背景，自定义边框颜色。
 * <p>
 * 纯色圆类型的背景：
 * 1. {@link #commonBgCustomColorCircle(int)}
 * <p>
 * 文字切换类颜色
 * 1. {@link #commonTextColorCustom(int, int)}
 */

public class CommonDrawableHelper {


    /**
     * Tab切换栏文字颜色。 选中为主题色，未选中为灰色
     */
    public static ColorStateList commonTextColorPrimarySubText() {
        return commonTextColorCustom(getColor(MyApplication.getInstance(), R.color.black),
                getColor(MyApplication.getInstance(), R.color.red));
    }

    /**
     * Tab切换栏文字颜色。
     *
     * @param normalColor   未选中和默认颜色
     * @param selectedColor 选中状态颜色
     */
    public static ColorStateList commonTextColorCustom(@ColorInt int normalColor,
                                                       @ColorInt int selectedColor) {
        int[] colors = new int[]{
                selectedColor, normalColor, normalColor
        };
        int[][] status = new int[][]{
                new int[]{android.R.attr.state_selected}, new int[]{-android.R.attr.state_selected},
                new int[]{}
        };
        return new ColorStateList(status, colors);
    }

    public static
    @ColorInt
    int getColor(Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }
}
