package com.ovu.lido.util;

import android.os.Environment;

public class HttpAddress {
    public static final String UPLOAD_PATH = Environment.getExternalStorageDirectory() + "/LidoApk/";


    //public static final String COMMON_URL = "http://172.16.205.6:8082/community-rest/";
    //public static final String COMMON_URL = "http://172.16.205.20:8080/community-rest/";
   //public static final String COMMON_URL = "http://192.168.1.114:8080/community-rest/";
    public static final String COMMON_URL = Constant.BASE_URL + "/community-rest/";

    /**
     * 我的发帖
     */
    public static final String QUERY_MY_INFO = COMMON_URL + "rest/info/queryMyInfo";

    /**
     * 我的点赞
     */
    public static final String QUERY_MY_AGREE = COMMON_URL + "rest/info/queryMyAgree";

    /**
     * 我的回复
     */
    public static final String QUERY_MY_RESPONSE = COMMON_URL + "rest/info/queryMyResponse";


    /**
     * 邻里信息列表
     */
    public static final String QUERY_INFO_LIST = COMMON_URL + "rest/info/queryInfoList";


    /**
     * 登录mobile_no，[{"key":"password","value":"123456","description":""}]，login_type
     */
    public static final String LOGIN = COMMON_URL + "rest/resident/login";

    /**
     * 给信息点赞/收藏
     */
    public static final String GIVE_GOOD = COMMON_URL + "rest/info/operateInfo";


    /**
     * 信息详情
     */
    public static final String POST_DETAIL = COMMON_URL + "rest/info/queryInfoDetail";


    /**
     * 发表回复
     */
    public static final String ADD_RESPONSE = COMMON_URL + "rest/info/addInfoResponse";


    /**
     * 信息回复列表
     */
    public static final String QUERY_RESPONSE = COMMON_URL + "rest/info/queryInfoResponseList";


    /**
     * 删除评论
     */
    public static final String DELETE_RESPONSE = COMMON_URL + "rest/info/deleteResponse";


    /**
     * 举报信息
     */
    public static final String FEEDBACK = COMMON_URL + "rest/info/addInform";


    /**
     * 删除帖子
     */
    public static final String DELETE_POST = COMMON_URL + "rest/info/deleteInformation";


    /**
     * 发布帖子
     */
    public static final String ADD_POST = COMMON_URL + "rest/info/addInfo";


    /**
     * 发布帖子
     */
    public static final String ADD_POSTS = COMMON_URL + "rest/info/addInfos";


    /**
     * 活动列表
     */
    public static final String ACTIVITY_LIST = COMMON_URL + "restV101/activity/queryActivityList";

    /**
     * 我的活动列表
     */
    public static final String MY_ACTIVITY_LIST = COMMON_URL + "restV101/activity/queryUserActivityList";

    /**
     * 活动详情
     */
    public static final String ACTIVITY_DETAILED = COMMON_URL + "restV101/activity/queryActivityDetails";

    /**
     * 活动详情点赞
     */
    public static final String ACTIVITY_DETAILED_ISLIKE = COMMON_URL + "restV101/activity/activityLike";

    /**
     * 活动报名
     */
    public static final String ACTIVITY_APPLY = COMMON_URL + "restV101/activity/activityApply";


    /**
     * 活动评论列表
     */
    public static final String ACTIVITY_COMMENT_LIST = COMMON_URL + "restV101/activity/activityCommentList";

    /**
     * 活动评论
     */
    public static final String ACTIVITY_COMMENT = COMMON_URL + "restV101/activity/activityComment";


    /**
     * 团购列表
     */
    public static final String GET_GROUP_PURCHASE = COMMON_URL + "restV101/groupPurchase/queryGroupPurchaseList";
    /**
     * 商品搜索
     */
    public static final String GROUP_BUY_KEY = COMMON_URL + "restV101/groupPurchase/queryGroupPurchaseListByGpname";

    /**
     * 团购详情
     */
    public static final String GET_GROUP_DETAILS = COMMON_URL + "restV101/groupPurchase/queryGroupPurchaseDetails";

    /**
     * 提交团购订单
     */
    public static final String GROUP_PURCHASE_ORDER = COMMON_URL + "restV101/groupPurchase/groupPurchaseOrder";

    /**
     * 我的团购列表
     */
    public static final String MY_GROUP_LIST = COMMON_URL + "restV101/groupPurchase/myQueryGroupPurchaseList";

    /**
     * 我的团购详情
     */
    public static final String MY_GROUP_DETAIL = COMMON_URL + "restV101/groupPurchase/myQueryGroupPurchaseDetails";


    /*
     * 获取用户余额
     */
    public static final String GET_BALANCE = COMMON_URL + "rest/payment/getBalance";


    /**
     * 收货地址列表
     */
    public static final String QUERY_ADDRESS = COMMON_URL + "rest/supermarket/queryAddress";

    /**
     * 编辑收货地址
     */
    public static final String EDIT_ADDRESS = COMMON_URL + "rest/supermarket/editAddress";

    /**
     * 删除收货地址
     */
    public static final String DELETE_ADDRESS = COMMON_URL + "rest/supermarket/deleteAddress";

    /**
     * 查询默认收货地址
     */
    public static final String QUERY_DEFAULT_ADDRESS = COMMON_URL + "rest/supermarket/queryDefaultAddress";


    /**
     * 查询用户信息
     */
    public static final String QUERY_PERSONAL_INFO = COMMON_URL + "rest/resident/queryResidentInfoV101";


    /**
     * 修改用户基本信息
     */
    public static final String MODIFY_RESIDENT_BASEINFO = COMMON_URL + "rest/resident/modifyResidentBaseInfo";

    /**
     * 修改用户基本信息(传头像)
     */
    public static final String MODIFY_RESIDENT_BASEINFOS = COMMON_URL + "rest/resident/modifyResidentBaseInfos";


    /**
     * 获取业主下属子账户列表
     */
    public static final String QUERY_CHILD_LIST = COMMON_URL + "rest/friends/queryChildList";


    /**
     * 获取收支历史列表
     */
    public static final String QUERY_HISTORY_LIST = COMMON_URL + "rest/payment/queryHistoryList";

    /**
     * 查看用户资料
     */
    public static final String QUERY_USER_INFO = COMMON_URL + "rest/resident/queryUserInfo";


    /**
     * 修改密码
     */
    public static final String MODIFY_PASSWORD = COMMON_URL + "rest/resident/modifyPassword";


    /**
     * 意见反馈
     */
    public static final String USER_FEEDBACK = COMMON_URL + "rest/app/feedback";

    /**
     * 启用或者禁用账户
     */
    public static final String CHANGE_ACCOUNT_STATE = COMMON_URL + "rest/friends/reEffective";


    /**
     * 我的活动列表
     */
    public static final String QUERY_USER_ACTIVITY_LIST = COMMON_URL + "restV101/activity/queryUserActivityList";

    /**
     * 切换小区
     */
    public static final String SWITCH_ESTATES = COMMON_URL + "rest/resident/switchEstatesV102";


    /**
     * 创建商品支付订单
     */
    public static final String CREATE_ORDER_V1 = COMMON_URL + "rest/payment/createOrder";


    /**
     * 查询用户信息
     */
    public static final String QUERY_RESIDENT_INFO = COMMON_URL + "rest/resident/queryResidentInfoV101";


    /**
     * 版本更新
     */
    public static final String UPDATE_VERSION = COMMON_URL + "rest/community/getBundleAppInfo";


    /**
     * 获取充值id
     */
    public static final String GET_PAY_ID = COMMON_URL + "rest/payment/getPayId";


    /**
     * 获取支付结果
     */
    public static final String QUERY_WX_PAY_RESULT = COMMON_URL + "rest/payment/queryPayResult";



    /**
     * 使用协议，功能介绍
     */
    public static final String QUERY_VERSION_APP_DETAIL =COMMON_URL + "restV101/version/queryVersionAppDetail";

}
