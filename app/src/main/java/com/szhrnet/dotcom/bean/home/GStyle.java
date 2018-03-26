package com.szhrnet.dotcom.bean.home;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/26.
 */

public class GStyle {

    private String style_name;

    private Map<String,StyleValue> value;

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public Map<String, StyleValue> getValue() {
        return value;
    }

    public void setValue(Map<String, StyleValue> value) {
        this.value = value;
    }
}
