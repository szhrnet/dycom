package com.szhrnet.dotcom.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.SearchResultActivity;
import com.szhrnet.dotcom.adapter.assort.AssortLeftAdapter;
import com.szhrnet.dotcom.adapter.assort.AssortTopAdapter;
import com.szhrnet.dotcom.adapter.assort.AssortmentAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.assort.AssortBase;
import com.szhrnet.dotcom.bean.assort.AssortGoods;
import com.szhrnet.dotcom.bean.assort.AssortGoodsThree;
import com.szhrnet.dotcom.bean.assort.AssortGoodsTwo;
import com.szhrnet.dotcom.bean.assort.AssortTopBean;
import com.szhrnet.dotcom.bean.home.Goods;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.utils.SPUtil;
import com.szhrnet.dotcom.view.ExpandGridView;

import java.util.ArrayList;
import java.util.HashMap;
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
    private List<AssortGoodsThree> datasTop;
    private List<AssortGoodsTwo> datasRight;

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
        datasTop = new ArrayList<>();
        adapterTop = new AssortTopAdapter(getActivity(), R.layout.item_assort, datasTop);
        gridViewTop.setAdapter(adapterTop);

        datasRight = new ArrayList<>();
        adapterRight = new AssortmentAdapter(getActivity(), R.layout.item_assort, datasRight);
        gridviewRight.setAdapter(adapterRight);

        stringList = new ArrayList<>();
        listAdapter = new AssortLeftAdapter(getActivity(), R.layout.item_text, stringList);
        listViewLeft.setAdapter(listAdapter);
        listViewLeft.setOnItemClickListener(this);

        gridViewTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toSearchResultActivity(position);
            }
        });

        gridviewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toSearchResultActivity(position);
            }
        });
    }

    private void toSearchResultActivity(int position) {
        Integer gt_id = datasTop.get(position).getGt_id();
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra(StringConstant.ARG1, "assort");
        intent.putExtra("gt_id", gt_id);
        toOtherActivity(intent);
    }

    @Override
    protected void initData() {
        //获取商品分类
        getGoodsAssort();
        //获取销量排行
        getSoleSort();
    }

    private void getSoleSort() {
        HttpUtils.httpGet(mContext, TAG_FRAGMENT, NetConstant.GETSALESRANK, null, new NetCallback<BaseResponseBean<AssortTopBean>>() {
            @Override
            public BaseResponseBean<AssortTopBean> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, AssortTopBean.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<AssortTopBean> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    if (!ListUtils.isEmpty(response.getData().getList())) {
                        datasTop.addAll(response.getData().getList());
                        adapterTop.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void getGoodsAssort() {
        HttpUtils.httpGet(mContext, TAG_FRAGMENT, NetConstant.GETGOODSTYPELIST, null, new NetCallback<BaseResponseBean<AssortBase>>() {
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
                    if (!ListUtils.isEmpty(assortGoodsList)) {
                        setLeft(assortGoodsList);
                        setRight(0);
                    }
                }
            }
        });
    }

    private void setLeft(List<AssortGoods> list) {
        for (int i = 0; i < list.size(); i++) {
            stringList.add(list.get(i).getGt_name());
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listAdapter.setSelectItem(position);
        listAdapter.notifyDataSetChanged();
        setRight(position);
    }

    private void setRight(int position) {
        AssortGoods assortGoods = assortGoodsList.get(position);
        List<AssortGoodsTwo> goods_type_child_1 = assortGoods.getGoods_type_child();
        if (!ListUtils.isEmpty(goods_type_child_1)) {
            datasRight.clear();
            datasRight.addAll(goods_type_child_1);
            adapterRight.notifyDataSetChanged();
        }
    }
}
