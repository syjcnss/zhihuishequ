package com.ovu.lido.util;

public class Constant {
    public static final String RESULT_OK = "0000";
    public static final String TOKEN_ERROR = "9989";
    public static final int DEFAULT_QUERY_NUMBER = 20;
    public static final int TAKE_PHOTO = 189;

    public static final int STORAGE_PERMISSION = 100;//存储权限
    public static final int LOCATION_PERMISSION = 101;//位置权限
    public static final int CAMERA_PERMISSION = 102;//照相机权限

    //基地址
    public static final String BASE_URL = "http://10.7.18.41:9999";//王汝超
//    public static final String BASE_URL = "http://10.7.0.225:8080";//王襄宜
//    public static final String BASE_URL = "http://test.ovuwork.com";//内网
//    public static final String BASE_URL = "http://s.whlido.com";//外网


    public static final String DIDI_appid = "didi646E48596279776F3132697354384742";
    public static final String DIDI_secret = "7a434e81ce5a71a1c05f4383d3a3adae";

    public static final String IMG_CONFIG = BASE_URL + "/community/info_img_path/";


    /**************************************************************************************/
    //登陆请求
    public static final String LOGIN_URL = BASE_URL + "/community-rest/rest/resident/login";
    /********************************注册***********************************************************************************/
    //获取验证码
    public static final String GET_CAPTCHA_URL = BASE_URL + "/community-rest/rest/resident/getCaptcha";
    //注册
    public static final String REGIST_NEXT_URL = BASE_URL + "/community-rest/rest/resident/registerV101";
    //获取城市列表
    public static final String GET_CITY_URL = BASE_URL + "/community-rest/rest/community/queryZone";
    //获取小区列表
    public static final String GET_COMMUNITY_URL = BASE_URL + "/community-rest/rest/community/queryCommunity";
    //获取楼栋、单元、房号
    public static final String GET_BUILDING_URL = BASE_URL + "/community-rest/rest/community/queryBuildingInfo";
    //查询认证信息
    public static final String QUERY_CERTIFICATION_URL = BASE_URL + "/community-rest/rest/resident/queryCertificationInfo";
    //注册认证
    public static final String REGISTER_TO_ATTESTATION_URL = BASE_URL + "/community-rest/rest/resident/registerToAttestation";
    /*********************************修改密码**************************************************************************************/
    //修改密码
    public static final String MODIFY_PASSWORD_URL = BASE_URL + "/community-rest/rest/resident/modifyPassword";

    //检查更新
    public static final String CHECK_VERSION_UPDATE = BASE_URL + "/community-rest/rest/community/getBundleAppInfo";

    /******************************贴心管家 START********************************************************************************/

    //贴心管家 详情
    public static final String INTIMATE_STEWARD_URL = BASE_URL + "/community-rest/rest/housekeeper/getHouseKeeper2";
    //贴心管家 评价
    public static final String EVALUATE_URL = BASE_URL + "/community-rest/rest/housekeeper/operateKeeperAgreeatisfaction";
    //是否提交报装
    public static final String IS_SUBMIT_DECORATION = BASE_URL + "/community-rest/rest/decoration/isSubmit";
    //装修项目
    public static final String DECORATION_PROJECT_URL = BASE_URL + "/community-rest/rest/decoration/getDecorationType";
    //装修图片类型
    public static final String GET_PHOTO_TYPE = BASE_URL + "/community-rest/rest/decoration/getPhotoType";
    //装修协议
    public static final String CLAUSE_URL = BASE_URL + "/community-rest/rest/decoration/getprotocolList";
    //协议详情
    public static final String CLAUSE_DETAIL_URL = BASE_URL + "/community-rest/rest/decoration/getprotocolDetail";
    //提交装修申请
    public static final String SUBMIT_APPLICATION_URL = BASE_URL + "/community-rest/rest/decoration/submitApplyToOvu";
    //装修列表
    public static final String GET_DECORATION_LIST = BASE_URL + "/community-rest/rest/decoration/myDecorationList";
    //装修列表详情
    public static final String GET_DECORATION_DETAIL = BASE_URL + "/community-rest/rest/decoration/myDecorationDetail";
    //便民电话
    public static final String CONVENIENCE_PHONE_URL = BASE_URL + "/community-rest/rest/phoneList/list";
    //留言列表
    public static final String LEAVE_COMMENTS_URL = BASE_URL + "/community-rest/rest/housekeeper/getHouseKeeperCommentList";
    //发送留言
    public static final String SEND_COMMENTS_URL = BASE_URL + "/community-rest/rest/housekeeper/operateKeeperComment";
    //判断是否入伙，是否验房
    public static final String ISJOIN_OR_ISCHECK = BASE_URL + "/community-rest/restV101/join/showPage";
    //入伙资料展示
    public static final String REQ_URL_PRE = BASE_URL + "/community-rest";
    //入伙管理 -- 基本资料保存
    public static final String SAVE_JION_TO_OVE = BASE_URL + "/community-rest/restV101/join/saveJoinToOve";
    //验房问题列表
    public static final String GET_CHECK_TYPE_NAME = BASE_URL + "/community-rest/restV101/join/getCheckTypeName";
    //提交验房反馈问题
    public static final String SUBMIT_JOIN_CHECK_URL = BASE_URL + "/community-rest/restV101/join/submitJoinCheck";
    //获取验房问题列表
    public static final String GET_QUESTION_LIST = BASE_URL + "/community-rest/restV101/join/getQuestionList";
    //获取问题列表详情
    public static final String GET_QUESTION_DETAIL = BASE_URL + "/community-rest/restV101/join/getQuestionDetail";

/*******************************贴心管家 END**********************************************/

    /********************************线上缴费 START*******************************************/
    //缴费列表
    public static final String PAYMENT_LIST_URL = BASE_URL + "/community-rest/rest/payment/getItemListByHouseV101";
    //缴费历史
    public static final String PAYMENT_HISTORY_URL = BASE_URL + "/community-rest/rest/payment/queryHistoryLists";
    //应缴金额
    public static final String QUERY_NED_PAY_URL = BASE_URL + "/community-rest/rest/payment/getBalance";
    //全部抵扣完
    public static final String ALL_DEDUCTIONS_URL = BASE_URL + "/community-rest/rest/payment/payV101";
    //获取订单号
    public static final String GET_ORDER_ID_URL = BASE_URL + "/community-rest/rest/payment/createOrder";
    //通知后台刷新--支付宝--支付结果
    public static final String ALIPAY_NOTIFY_URL = BASE_URL + "/community-rest/rest/payment/zfb_notify";
    //通知后台刷新--微信--支付结果
    public static final String WECHAT_NOTIFY_URL = BASE_URL + "/community-rest/rest/payment/wx_notify";
    //微信统一下单接口
    public static final String WX_TOTAL_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /********************************报事报修**********************************************************************************************************/
    //获取故障类型
    public static final String GET_FAULT_TYPE = BASE_URL + "/community-rest/rest/fault/getFaultType";
    //提交报修信息
    public static final String SUBMIT_FAMILY_REPAIRS = BASE_URL + "/community-rest/rest/fault/savehouse";
    public static final String SUBMIT_FAMILY_REPAIRS_NOIMG = BASE_URL + "/community-rest/rest/fault/savehouseOvu";
    //获取工单列表
    public static final String GET_WORK_ORDER_LIST = BASE_URL + "/community-rest/rest/fault/getOrderListOvm";
    //获取工单详情
    public static final String GET_ORDER_DETAIL = BASE_URL + "/community-rest/rest/fault/getOrderDetail1";
    //工单评价
    public static final String GET_ORDER_COMMENT = BASE_URL + "/community-rest/rest/fault/getOrderComment";
    public static final String GET_ORDER_COMMENT_NOIMG = BASE_URL + "/community-rest/rest/fault/getOrderCommentOvu";
    /********************************在线社区*********************************************************************************************************/
    //投票列表
    public static final String QUERY_VOTE_LIST_URL = BASE_URL + "/community-rest/rest/estate/queryVoteList";
    //投票详情
    public static final String QUERY_VOTE_DETAIL_URL = BASE_URL + "/community-rest/rest/estate/queryVoteDetail";
    //投票结果
    public static final String VOTE_RESULT_URL = BASE_URL + "/community-rest/rest/estate/showResult/";
    //开始投票
    public static final String SEN_VOTE_URL = BASE_URL + "/community-rest/rest/estate/sendVote";
    //网格管理员
    public static final String GET_GRID_KEEPER_URL = BASE_URL + "/community-rest/rest/gridkeeper/getGridkeeper";
    //社区党员
    public static final String QUERY_PARTY_MEMBER_URL = BASE_URL + "/community-rest/restV101/partymember/queryPartyMemberList";
    //满意度调查列表
    public static final String QUERY_SP_VOTE_LIST_URL = BASE_URL + "/community-rest/rest/spVote/queryspVoteList";
    //投票之前判断是否已经投过
    public static final String WHETHER_VOTE_URL = BASE_URL + "/community-rest/rest/spVote/showPage";
    //提交投票
    public static final String SEND_VOTE_URL = BASE_URL + "/community-rest/rest/spVote/sendSpVote";
    //投票历史
    public static final String VOTE_HISTORY_URL = BASE_URL + "/community-rest/rest/spVote/queryspVoteHistoryList";
    //志愿者团队
    public static final String QUERY_VOLUNTEER_TEAM = BASE_URL + "/community-rest/rest/volunteer/queryVolunteerS";
    //志愿者活动
    public static final String QUERY_VOLUNTEER_EVENT = BASE_URL + "/community-rest/rest/volunteer/queryVolunteerList";
    //办事流程
    public static final String QUERY_WORK_LIST = BASE_URL + "/community-rest/restV101/work/queryWorkList";
    //办事流程详情
    public static final String QUERU_WORK_DETAIL = BASE_URL + "/community-rest/restV101/work/queryWorkDetail";

    /********************************智能开门*******************************************************************************************************/
    public static final String GET_AUTH_DEVICE = BASE_URL + "/community-rest/rest/entrance/getAuthDeviceList";
    public static final String SUBMIT_ENTRANCE_RECORD = BASE_URL + "/community-rest/rest/entrance/submitEntranceRecord";
    /**********************************首页************************************************************************************************************/
    //首页小区头条
    public static final String GET_HOME_LIST_URL = BASE_URL + "/community-rest/rest/community/homeList";
    //小区公告列表 01; 通知公告列表 04
    public static final String QUERY_INFO_LIST = BASE_URL + "/community-rest/rest/info/queryInfoList";
    //小区公告详情
    public static final String QUERY_INFO_DETAIL = BASE_URL + "/community-rest/rest/info/queryInfoDetail";
    //报事进程
    public static final String GET_ORDER_LIST_INDEX = BASE_URL + "/community-rest/rest/fault/getOrderListIndex";
    //社区活动
    public static final String QUERY_COMMUNITY_ACTIVITY_LIST = BASE_URL + "/community-rest/restV101/activity/queryActivityList";
    //服务团队
    public static final String QUERY_SERVICE_TEAM_URL = BASE_URL + "/community-rest/rest/propertyTeam/queryPropertyTeamList";
    //用户对服务团队进行评价
    public static final String USER_REVIEWS_URL = BASE_URL + "/community-rest/rest/propertyTeam/operatePropertyTeamStisfaction";
    //邻里
    public static final String QUERY_NEIGHBOR_LIST_URL = BASE_URL + "/community-rest/rest/info/queryInfoList";
    //访客邀请门禁列表
    public static final String GET_DOOR_LIST = BASE_URL + "/community-rest/rest/entrance/getDoorList";
    //访客邀请
    public static final String INVITE_GUEST = BASE_URL + "/community-rest/rest/entrance/inviteGuest";
    //开锁
    public static final String GET_AUTH_DEVICE_LIST = BASE_URL + "/community-rest/rest/entrance/getAuthDeviceList";
    //开门后保存记录
    public static final String SAVE_RECORD = BASE_URL + "/community-rest/rest/entrance/submitEntranceRecord";
    //开门红包
    public static final String OPEN_DOOR_RED_PAKET = BASE_URL + "/community-rest/rest/entrance/opendoorredpaket";
    /************************************消息中心*************************************************************************************************************/
    //消息列表
    public static final String GET_MESSAGE_CORE = BASE_URL + "/community-rest/rest/messageCore/getMessageCore";
    //消息详情
    public static final String GET_MESSAGE_CORE_DETAIL = BASE_URL + "/community-rest/rest/messageCore/getMessageCoreDetails";
    /*************************************福利********************************************************************************************************/
    //积分明细
    public static final String GET_USER_HISTORY_INTE_GRATION = BASE_URL + "/community-rest/rest/resident/UserHistoryIntegration";
    //我的奖品
    public static final String GET_USER_AWARDS_RECORD = BASE_URL + "/community-rest/rest/lottery/getUserAwardsRecord";
    //积分余额
    public static final String GET_USER_INTE_GRATION = BASE_URL + "/community-rest/rest/resident/getUserIntegration";
    //签到
    public static final String SIGN_IN = BASE_URL + "/community-rest/rest/resident/sign";
    //奖品列表
    public static final String GET_AWARDS = BASE_URL + "/community-rest/rest/lottery/getAwards";
    //抽奖
    public static final String BEGIN_AWARDS = BASE_URL + "/community-rest/rest/lottery/beginAwards";
    //所有用户的中奖记录
    public static final String GET_AWARDS_RECORD = BASE_URL + "/community-rest/rest/lottery/getAwardsRecord";
    //积分规则
    public static final String GET_POINT_RULE = BASE_URL + "/community-rest/rest/resident/getPointRule";
    /***************************************商店****************************************************************************************************/
    //附近商店
    public static final String NEAR_SHOPE_LIST = BASE_URL + "/community-rest/rest/shoporder/nearShopList";
    //商城
    public static final String MALL_URL = "http://sums.suning.com/help/unionAgreement/detail.htm";
    /*********************************************************************************************************************************************************/


//留言红点状态
    public static final String TIP_STATE = BASE_URL + "/community-rest/rest/housekeeper/isRepay";

    /************************************************************************************************************************************************/
    //首页接口
    public static final String GET_HOME_PAGE_INFO = BASE_URL + "/community-rest/rest/community/getHomePageInfo";
    //我的地址列表
    public static final String MY_RECEIVE_ADDRESS = BASE_URL + "/community-rest/rest/residentReceiveAddress/page";
    //我的地址保存与更新
    public static final String SAVE_ADDRESS = BASE_URL + "/community-rest/rest/residentReceiveAddress/save";
    //我的地址删除
    public static final String DELETE_ADDRESS = BASE_URL + "/community-rest/rest/residentReceiveAddress/delete";
    //类型列表
    public static final String TYPE_LIST = BASE_URL + "/community-rest/rest/common/typeList";
    //商品--列表
    public static final String PRODUCT_LIST = BASE_URL + "/community-rest/rest/csProduct/page";
    //商品--生成订单详情
    public static final String GENERATE_ORDER = BASE_URL + "/community-rest/rest/csOrderProduct/generateOrder";
    //订单列表
    public static final String GET_ORDER_LIST = BASE_URL + "/community-rest/rest/csOrderProduct/page";
    //订单详情
    public static final String GET_ORDER_LIST_DETAIL = BASE_URL + "/community-rest/rest/csOrderProduct/detail";
    //创建订单
    public static final String CREATE_ORDER = BASE_URL + "/community-rest/rest/csOrderProduct/createOrder";
    //订单编号
    public static final String CREATE_ORDER_ID = BASE_URL + "/community-rest/rest/common/createOrder";
    //获取支付id
    public static final String GET_PAY_ID = BASE_URL + "/community-rest/rest/common/getPayId";
    //点赞/取消点赞
    public static final String OPERATE_INFO = BASE_URL + "/community-rest/rest/info/operateInfo";
     //获取支付结果
    public static final String QUERY_PAY_RESULT = BASE_URL + "/community-rest/rest/payment/queryPayResult";
    //确认收货
    public static final String CONFIRM_RECEIVE = BASE_URL + "/community-rest/rest/csOrderProduct/confirmReceive";
    //
    public static final String PAYMENT_CREATE_ORDER = BASE_URL + "/community-rest/rest/payment/createOrder";
    //免费商品支付
    public static final String FREE_PAYMENT = BASE_URL + "/community-rest/rest/csOrderProduct/freePayment";
    //马应龙
    public static final String MA_YING_LONG = "http://myljkj.mayinglong.net/drugstore/whmyldyf/mstore/index.shtml?from=singlemessage";
    //家政服务
    public static final String DOMESTIC_SERVICE = "https://100000350538.retail.n.weimob.com/saas/retail/100000350538/113922538/shop/index";
    //隐私政策
    public static final String PRIVACY_URL = BASE_URL + "/ilido/privacyPolicy";
    //京东商城
    public static final String JD_COM_URL = BASE_URL + "/community-rest/weigo/autoLogin/";
    //修改用户基本信息(不传头像)
    public static final String MODIFY_RESIDENT_BASE_INFO = BASE_URL + "/community-rest/rest/resident/modifyResidentBaseInfo";
    //修改用户基本信息(传头像)
    public static final String MODIFY_RESIDENT_BASEINFOS = BASE_URL + "/community-rest/rest/resident/modifyResidentBaseInfos";
    //业主详细信息（家庭情况、紧急联系人）
    public static final String QUERY_RESIDENT_CONTACTS = BASE_URL + "/community-rest/rest/resident/queryResidentContacts";
    //家庭情况（紧急联系人）保存
    public static final String SAVE_EMERGENCY_CONTACT = BASE_URL + "/community-rest/rest/resident/saveEmergencyContact";
    //业主详细信息（车辆情况）
    public static final String QUERY_RESIDENT_CAR_LIST = BASE_URL + "/community-rest/rest/resident/queryResidentCarList";
    //业主车辆情况保存
    public static final String SAVE_CAR_INFO = BASE_URL + "/community-rest/rest/resident/saveCarInfo";
    //家庭情况（亲属）保存
    public static final String SAVE_KINS_MAN = BASE_URL + "/community-rest/rest/resident/saveKinsman";


}
