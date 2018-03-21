package com.szhrnet.dotcom.constant;


/**
 * Created by lk on 2017/4/8 0008.
 */

public class NetConstant {

    /**
     * 默认根目录
     */
    public static String ROOT_URL = "http://120.78.213.72";

    //============================个人中心==================================
    /**
     * 1.1、发送手机验证码
     */
    public static String SENDCODE = ROOT_URL + "/api/user/sendCode";

    /**
     * 1.2、账号注册
     */
    public static String DOREGISTER = ROOT_URL + "/api/user/doRegister";

    /**
     * 1.3、用户登录
     */
    public static String DOLOGIN = ROOT_URL + "/api/user/doLogin";

    /**
     * 1.4、忘记密码
     */
    public static String RESETPWD = ROOT_URL + "/api/user/resetPwd";

    /**
     * 1.5、获取用户信息
     */
    public static String GETUSERBASEINFO = ROOT_URL + "/api/user/getUserBaseInfo";

    /**
     * 1.6、获取地区列表
     */
    public static String GETREGIONLIST = ROOT_URL + "/api/user/getRegionList";

    /**
     * 1.7、上传图片（数据流）
     */
    public static String UPLOADIMAGE = ROOT_URL + "/api/user/uploadImage";

    /**
     * 1.8、提交个人信息编辑结果
     */
    public static String EDITUSERINFO = ROOT_URL + "/api/user/editUserInfo";

    /**
     * 1.9、设置支付密码
     */
    public static String SETPAYPASSWORD = ROOT_URL + "/api/user/setPayPassword";

    /**
     * 1.10、修改支付密码
     */
    public static String EDITPAYPASSWORD = ROOT_URL + "/api/user/editPayPassword";
    /**
     * 1.11、找回支付密码
     */
    public static String FINDPAYPASSWORD = ROOT_URL + "/api/user/findPayPassword";

    /**
     * 1.12、修改登录密码
     */
    public static String EDITPASSWORD = ROOT_URL + "/api/user/editPassword";

    /**
     * 1.13、申请/修改个人认证
     */
    public static String APPLYIDENTITYAUTH = ROOT_URL + "/api/user/applyIdentityAuth";

    /**
     * 1.14、获取个人认证信息
     */
    public static String GETUSERIDENTITYINFO = ROOT_URL + "/api/user/getUserIdentityInfo";

    /**
     * 1.15、意见反馈列表
     */
    public static String GETUSERFEEDBACKLIST = ROOT_URL + "/api/user/getUserFeedBackList";

    /**
     * 1.16、反馈问题
     */
    public static String DOUSERFEEDBACK = ROOT_URL + "/api/user/doUserFeedBack";

    /**
     * 1.17、获得最新APP版本信息
     */
    public static String GETAPPINFO = ROOT_URL + "/api/user/getAppInfo";

    /**
     * 1.18、余额管理
     */
    public static String BALANCEMANAGE = ROOT_URL + "/api/user/balanceManage";
    /**
     * 1.19、提现记录
     */
    public static String GETCARRYLIST = ROOT_URL + "/api/user/getCarryList";

    /**
     * 1.20、资金明细
     */
    public static String GETTRADINGRECORDLIST = ROOT_URL + "/api/user/getTradingRecordList";

    /**
     * 1.21、绑定/更改银行卡
     */
    public static String ADDUSERBANK = ROOT_URL + "/api/user/addUserBank";

    /**
     * 1.22、删除绑定的银行卡
     */
    public static String DELUSERBANK = ROOT_URL + "/api/user/delUserBank";

    /**
     * 1.23、设置默认银行卡
     */
    public static String SETUSERBANKDEFAULT = ROOT_URL + "/api/user/setUserBankDefault";

    /**
     * 1.24、用户提现操作
     */
    public static String DOCARRYACT = ROOT_URL + "/api/user/doCarryAct";

    /**
     * 1.25、用户收藏
     */
    public static String GETCOLLECTLIST = ROOT_URL + "/api/user/getCollectList";

    /**
     * 1.26、收藏/取消操作
     */
    public static String DOCOLLECTACT = ROOT_URL + "/api/user/doCollectAct";

    /**
     * 1.27、获取收货地址列表
     */
    public static String GETUSERADDRESSLIST = ROOT_URL + "/api/user/getUserAddressList";

    /**
     * 1.28、增加/修改收货地址
     */
    public static String ADDUSERADDRESS = ROOT_URL + "/api/user/addUserAddress";

    /**
     * 1.29、设置默认收货地址
     */
    public static String SETUSERDEFAULTADDRESS = ROOT_URL + "/api/user/setUserDefaultAddress";

    /**
     * 1.30、删除收货地址
     */
    public static String DELETEUSERADDRESS = ROOT_URL + "/api/user/deleteUserAddress";

    /**
     * 1.31、获取用户订单列表
     */
    public static String GETUSERGOODSORDERLIST = ROOT_URL + "/api/user/getUserGoodsOrderList";

    /**
     * 1.32、获取订单详情
     */
    public static String GETUSERGOODSORDERINFO = ROOT_URL + "/api/user/getUserGoodsOrderInfo";

    /**
     * 1.33、获取签收/退货信息
     */
    public static String GETORDERRESULTINFO = ROOT_URL + "/api/user/getOrderResultInfo";

    /**
     * 1.34、用户确认收货（仅限已支付的到店取货订单）
     */
    public static String DOORDERCONFIRM = ROOT_URL + "/api/user/doOrderConfirm";

    /**
     * 1.35、商品评价
     */
    public static String DOGOODSCOMMENT = ROOT_URL + "/api/user/doGoodsComment";

    /**
     * 1.36、配送员评价
     */
    public static String DEALDELIVERYCOMMENT = ROOT_URL + "/api/user/dealDeliveryComment";

    /**
     * 1.37、取消订单
     */
    public static String CANCELGOODSORDER = ROOT_URL + "/api/user/cancelGoodsOrder";

    /**
     * 1.38、申请退货（仅限未发货订单）
     */
    public static String REFUNDGOODS = ROOT_URL + "/api/user/refundGoods";

    /**
     * 1.39、会员信息
     */
    public static String GETVIPINFO = ROOT_URL + "/api/user/getVipInfo";

    /**
     * 1.40、积分中心
     */
    public static String GETINTEGRALLIST = ROOT_URL + "/api/user/getIntegralList";
    /**
     * 1.41、获取年度收益
     */
    public static String GETANNIVPROFIT = ROOT_URL + "/api/user/getAnnivProfit";

    //===================================首页======================================
    /**
     * 2.1、获取首页轮播图、视频/横排图
     */
    public static String GETINDEXBANNERLIST = ROOT_URL + "/api/index/getIndexBannerList";

    /**
     * 2.2、获取首页推荐商品
     */
    public static String GETINDEXRECOMMENTLIST = ROOT_URL + "/api/index/getIndexRecommentList";

    /**
     * 2.3、搜索首页获取热门搜索数据
     */
    public static String GETSEARCHHOTLIST = ROOT_URL + "/api/index/getSearchHotList";

    /**
     * 2.4、轮播详情
     */
    public static String GETBANNERDETAIL = ROOT_URL + "/api/index/getBannerDetail";

    /**
     * 2.5、获取商品分类
     */
    public static String GETGOODSTYPELIST = ROOT_URL + "/api/goods/getGoodsTypeList";

    /**
     * 2.6、销量排行
     */
    public static String GETSALESRANK = ROOT_URL + "/api/goods/getSalesRank";

    /**
     * 2.7、获取商品列表
     */
    public static String GETGOODSLIST = ROOT_URL + "/api/goods/getGoodsList";

    /**
     * 2.8、获取商品详情
     */
    public static String GETGOODSDETAIL = ROOT_URL + "/api/goods/getGoodsDetail";

    /**
     * 2.9、获取商品款式
     */
    public static String GETGOODSSTYLE = ROOT_URL + "/api/goods/getGoodsStyle";

    /**
     * 2.10、获取商品评论
     */
    public static String GETGOODSCOMMENTLIST = ROOT_URL + "/api/goods/getGoodsCommentList";

    //======================================订单=======================================
    /**
     * 3.1、加入购物车
     */
    public static String ADDCART = ROOT_URL + "/api/order/addCart";

    /**
     * 3.2、获取购物车列表
     */
    public static String GETCARTLIST = ROOT_URL + "/api/order/getCartList";

    /**
     * 3.3、编辑购物车
     */
    public static String EDITCART = ROOT_URL + "/api/order/editCart";

    /**
     * 3.4、删除购物车
     */
    public static String DELCART = ROOT_URL + "/api/order/delCart";

    /**
     * 3.5、获得确认订单页面信息
     */
    public static String GETCONFIRMORDERLIST = ROOT_URL + "/api/order/getConfirmOrderList";

    /**
     * 3.6、创建商品订单接口
     */
    public static String CREATEGOODSORDER = ROOT_URL + "/api/order/createGoodsOrder";

    /**
     * 3.7、待支付页面
     */
    public static String GETPAYINFO = ROOT_URL + "/api/order/getPayInfo";

    /**
     * 3.8、订单余额支付
     */
    public static String PAYORDER = ROOT_URL + "/api/order/payOrder";

    /**
     * 3.9、订单追踪
     */
    public static String GETORDERTRACE = ROOT_URL + "/api/order/getOrderTrace";

    /**
     * 3.10、获取配送员评价
     */
    public static String GETDELIVERYCOMMENT = ROOT_URL + "/api/order/getDeliveryComment";

    /**
     * 3.11、获取确认运费订单页面
     */
    public static String GETCONFIRMFEE = ROOT_URL + "/api/order/getConfirmFee";

    /**
     * 3.12、创建运费订单
     */
    public static String CREATEFEEORDER = ROOT_URL + "/api/order/createFeeOrder";

    /**
     * 3.13、运费待支付页面
     */
    public static String GETPAYFEEINFO = ROOT_URL + "/api/order/getPayFeeInfo";

    /**
     * 3.14、运费余额支付
     */
    public static String PAYFEEORDER = ROOT_URL + "/api/order/payFeeOrder";

    //==================================配送员===================================
    /**
     * 4.1、配送管理
     */
    public static String DELIVERYMANAGE = ROOT_URL + "/api/user/deliveryManage";

    /**
     * 4.2、开始配送
     */
    public static String DODELIVERYACT = ROOT_URL + "/api/user/doDeliveryAct";

    /**
     * 4.3、收益记录
     */
    public static String GETDELIVERYPROFIT = ROOT_URL + "/api/user/getDeliveryProfit";

    /**
     * 4.4、配送员订单追踪
     */
    public static String GETORDERTRACED = ROOT_URL + "/api/user/getOrderTraceD";

    /**
     * 4.5、配送员订单详情
     */
    public static String GETORDERINFOD = ROOT_URL + "/api/user/getOrderInfoD";

    /**
     * 4.6、获取签收验证手机号（使用验证码方式签收时使用）
     */
    public static String GETVERIFYTEL = ROOT_URL + "/api/user/getVerifyTel";

    /**
     * 4.7、签收验证
     */
    public static String DOORDERVERIFY = ROOT_URL + "/api/user/doOrderVerify";

    /**
     * 4.8、配送员中心
     */
    public static String DCENTER = ROOT_URL + "/api/user/dCenter";

    /**
     * 4.9、客户评价
     */
    public static String GETDCOMMENT = ROOT_URL + "/api/user/getDComment";

    /**
     * 4.10、时效考核
     */
    public static String GETDPERFORMANCE = ROOT_URL + "/api/user/ getDPerformance";

}
