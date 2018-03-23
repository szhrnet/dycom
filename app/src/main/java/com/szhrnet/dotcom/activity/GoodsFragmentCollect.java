package com.szhrnet.dotcom.activity;


import com.szhrnet.dotcom.fragment.BaseFragment;
import com.szhrnet.dotcom.fragment.home.Goods1Fragment;
import com.szhrnet.dotcom.fragment.home.Goods2Fragment;
import com.szhrnet.dotcom.fragment.home.Goods3Fragment;

/**
 * Created by ZCZ on 2017/4/30.
 * 玩法（下注）一样，界面一样。
 */

public class GoodsFragmentCollect implements IGoodsFragmentCollect {

    @Override
    public BaseFragment getCurrentRechargeFragment(int position) {
        BaseFragment targetFragment = null;
        switch (position) {
            case 0:
                targetFragment = new Goods1Fragment();
                break;

            case 1:
                targetFragment = new Goods2Fragment();
                break;

            case 2:
                targetFragment = new Goods3Fragment();
                break;
        }

        return targetFragment;
    }
}
