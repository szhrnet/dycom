package com.szhrnet.dotcom.fragment.home;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szhrnet.dotcom.R;
import com.szhrnet.dotcom.activity.home.CommentAdapter;
import com.szhrnet.dotcom.bean.BaseResponseBean;
import com.szhrnet.dotcom.bean.home.CommentBean;
import com.szhrnet.dotcom.bean.home.Comments;
import com.szhrnet.dotcom.bean.home.GoodsDetailBean;
import com.szhrnet.dotcom.constant.NetConstant;
import com.szhrnet.dotcom.constant.StringConstant;
import com.szhrnet.dotcom.fragment.BaseFragment;
import com.szhrnet.dotcom.utils.GsonUtils;
import com.szhrnet.dotcom.utils.HttpUtils;
import com.szhrnet.dotcom.utils.ListUtils;
import com.szhrnet.dotcom.utils.NetCallback;
import com.szhrnet.dotcom.view.XRatingBar;
import com.szhrnet.dotcom.view.xlistview.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/23.
 */

public class Goods3Fragment extends BaseFragment {

    @Bind(R.id.ratingbar)
    XRatingBar ratingbar;
    @Bind(R.id.tv_fenshu)
    TextView tv_fenshu;
    @Bind(R.id.rl_all)
    RelativeLayout rl_all;
    @Bind(R.id.rl_pic)
    RelativeLayout rl_pic;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.xlistvie)
    XListView xlistvie;
    private CommentAdapter adapter;
    private List<CommentBean> list;

    private int page = 1;
    //0为全部 1为有图
    private int isAll = 0;
    private GoodsDetailBean goods;
    private int g_id;
    private List<CommentBean> allList;

    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_goods3;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected void initEvent() {
        goods = (GoodsDetailBean) getArguments().getSerializable("goods");
        g_id = goods.getGoodsarr().getG_id();

        list = new ArrayList<>();
        allList = new ArrayList<>();
        adapter = new CommentAdapter(mContext, R.layout.item_pingjia, list);
        xlistvie.setAdapter(adapter);
    }

    @OnClick({R.id.rl_all, R.id.rl_pic})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_all:
                isAll = 0;
                break;
            case R.id.rl_pic:
                isAll = 1;
                break;
        }
        getComments();
    }

    @Override
    protected void initData() {
        getComments();
    }

    private void getComments() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("g_id", g_id);
        params.put("gc_is_img", isAll);
        params.put("page_size", 15);
        params.put("page", page);
        HttpUtils.httpPostForm(mContext, TAG_FRAGMENT, NetConstant.GETGOODSCOMMENTLIST, params, new NetCallback<BaseResponseBean<Comments>>() {
            @Override
            public BaseResponseBean<Comments> parseNetworkResponse(String response) throws Exception {
                return GsonUtils.GsonToNetObject(response, Comments.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(BaseResponseBean<Comments> response) {
                if (response.getCode() == StringConstant.RESPONCE_OK) {
                    Comments data = response.getData();
                    List<CommentBean> list = data.getList();
                    if (!data.isIs_last()) {
                        page++;
                    }

                    if (!ListUtils.isEmpty(list)) {
                        list.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
