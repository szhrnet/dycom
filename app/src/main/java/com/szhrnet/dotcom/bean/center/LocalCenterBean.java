package com.szhrnet.dotcom.bean.center;

/**
 * Created by ${CL} on 2018/3/22.
 */

public class LocalCenterBean {

    private Integer idres;

    private String label;

    public LocalCenterBean(int idres, String label) {
        this.idres = idres;
        this.label = label;
    }

    public Integer getIdres() {
        return idres;
    }

    public void setIdres(Integer idres) {
        this.idres = idres;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
