package com.szhrnet.dotcom.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.adapter.assort.AssortLeftAdapter;
import com.szhrnet.dotcom.adapter.assort.AssortTopAdapter;
import com.szhrnet.dotcom.adapter.assort.AssortmentAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.assort.AssortBase;
import com.szhrnet.dotcom.bean.assort.AssortGoods;
import com.szhrnet.dotcom.bean.assort.AssortGoodsTwo;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.view.ExpandGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/19.
 * 分类
 */

public class AssortmentFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.gridview)
    ExpandGridView gridViewTop;
    @Bind(R.id.listView)
    ListView listViewLeft;
    @Bind(R.id.gridview_right)
    ExpandGridView gridviewRight;
    private AssortTopAdapter adapterTop;
    private AssortmentAdapter adapterRight;
    private AssortLeftAdapter listAdapter;
    private List<String> stringList;
    private List<AssortGoods> assortGoodsList;

    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_assortment;
    }

    @Override
    protected String initTitle() {
        return "商品分类";
    }

    @Override
    protected void initEvent() {
        adapterTop = new AssortTopAdapter(getActivity(), R.layout.item_assort);
        gridViewTop.setAdapter(adapterTop);


        adapterRight = new AssortmentAdapter(getActivity(), R.layout.item_assort);
        gridviewRight.setAdapter(adapterRight);

        stringList = new ArrayList<>();
        listAdapter = new AssortLeftAdapter(getActivity(), R.layout.item_text);
        listViewLeft.setAdapter(listAdapter);
        listViewLeft.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        getGoodsAssort();
    }

    private void getGoodsAssort() {
        HttpUtils.httpGet(getActivity(), TAG_FRAGMENT, NetConstant.GETGOODSTYPELIST, null, new NetCallback<BaseResponseBean<AssortBase>>() {
            @Override
            public BaseResponseBean<AssortBase> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, AssortBase.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<AssortBase> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    AssortBase data = response.getData();
                    assortGoodsList = data.getList();
                    setLeft(assortGoodsList);

                }
            }
        });
    }

    private void setLeft(List<AssortGoods> list) {
        for (int i = 0; i < list.size(); i++) {
            stringList.add(list.get(i).getGt_name());
        }
        listAdapter.setStringList(stringList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AssortGoods assortGoods = assortGoodsList.get(position);
        List<AssortGoodsTwo> goods_type_child_1 = assortGoods.getGoods_type_child_1();

    }
}
