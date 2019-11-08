package com.ovu.lido.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ovu.lido.R;
import com.ovu.lido.adapter.CommunityRvAdapter;
import com.ovu.lido.adapter.ThirdServiceRvAdapter;
import com.ovu.lido.ui.CommonWebActivity;
import com.ovu.lido.ui.CommunityServiceActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.LogUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.MD5Util;
import com.ovu.lido.util.MapKeyComparator;
import com.ovu.lido.util.ViewHelper;
import com.sdu.didi.openapi.DIOpenSDK;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommunityFragment extends Fragment {
    @BindView(R.id.community_service_rv)
    RecyclerView community_service_rv;
    @BindView(R.id.third_service_rv)
    RecyclerView third_service_rv;

    public static final String TAG = "wangw";

    private Context mContext;
    private Unbinder unbinder;

//    private Integer[] communityResIds = {R.drawable.jiazhengfuwu, R.drawable.weixiufuwu, R.drawable.tegongshangpin, R.drawable.jiankanghuli,R.drawable.huahuizubai, R.drawable.wuxianyijin, R.drawable.jinrongfuwu, R.drawable.gexingxuqiu};
    private Integer[] communityResIds = {R.drawable.jiazhengfuwu, R.drawable.weixiufuwu, R.drawable.tegongshangpin, R.drawable.jiankanghuli};
//    private String[] names = {"家政服务", "维修服务", "特供商品", "健康护理", "花卉租摆", "五险一金", "金融服务", "个性需求"};
    private String[] names = {"家政服务", "维修服务", "特供商品", "健康护理"};

//    private Integer[] thirdResIds = {R.drawable.edaixi, R.drawable.haoyaoshi, R.drawable.dididache, R.drawable.jingdongshangcheng, R.drawable.youhuisao};
    private Integer[] thirdResIds = {R.drawable.edaixi, R.drawable.dididache, R.drawable.jingdongshangcheng};

    private CommunityRvAdapter mCommunityRvAdapter;
    private ThirdServiceRvAdapter mThirdServiceRvAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
    }


    protected void initView() {
        mCommunityRvAdapter = new CommunityRvAdapter(mContext, communityResIds, names);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        community_service_rv.setLayoutManager(manager);
        community_service_rv.setAdapter(mCommunityRvAdapter);

        mThirdServiceRvAdapter = new ThirdServiceRvAdapter(mContext, thirdResIds);
        GridLayoutManager manager1 = new GridLayoutManager(mContext, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        third_service_rv.setLayoutManager(manager1);
        third_service_rv.setAdapter(mThirdServiceRvAdapter);

    }


    private void setListener() {
        mCommunityRvAdapter.setOnItemClickListener(new CommunityRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = community_service_rv.getChildAdapterPosition(view);
                switch (position) {
                    case 0://家政服务
                        startActivity(new Intent(mContext, CommonWebActivity.class).putExtra("title", "家政服务").putExtra("url", Constant.DOMESTIC_SERVICE));
                        break;
                    case 1://维修服务
                        startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","1"));
                        break;
                    case 2://特供商品
                        startActivity(new Intent(mContext, CommunityServiceActivity.class).putExtra("module_type","2"));
                        break;
                    case 3://健康护理
                        startActivity(new Intent(mContext, CommonWebActivity.class).putExtra("title", "马应龙").putExtra("url", Constant.MA_YING_LONG));
                        break;
                    case 4://花卉租摆
                        break;
                    case 5://五险一金
                        break;
                    case 6://金融服务
                        break;
                    case 7://个性需求
                        break;

                }
            }
        });

        mThirdServiceRvAdapter.setOnItemClickListener(new ThirdServiceRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = third_service_rv.getChildAdapterPosition(view);
                switch (position) {
                    case 0://e代洗
                        edaixi();
                        break;
//                    case 1://好药师
//                        ehaoyao();
//                        break;
                    case 1://嘀嘀打车
                        DIOpenSDK.showDDPage(mContext, new HashMap<String, String>());
                        break;
                    case 2://京东商城
                        startActivity(new Intent(mContext, CommonWebActivity.class).putExtra("title", "京东商城").putExtra("url", Constant.JD_COM_URL+AppPreference.I().getString("resident_id","")));
                        break;
                    case 3://优惠扫

                        break;
                    case 4://马应龙

                        break;
                }
            }
        });
    }

    /**
     * 好药师
     */
    private void ehaoyao() {
        String url2 = "http://www.ehaoyao.com/";
        Intent data = new Intent(mContext, CommonWebActivity.class);
        data.putExtra("url", url2);
        data.putExtra("title", "好药师");
        startActivity(data);
    }

    /**
     * e代洗
     */
    private void edaixi() {
        String appkey = "mGld7YeBL2ZchfyMIDzVaOvuF1bVGMpt";

        String user_id = AppPreference.I().getString("resident_id", "");
        String mobile = AppPreference.I().getString("phoneNum", "");

        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "22");
        map.put("mobile", mobile);
        map.put("timestamp", ViewHelper.getTimeStamp());
        map.put("user_id", user_id);

        Map<String, String> map2 = sortMapByKey(map);

        String code = getHashCode(map2, appkey);
        map2.put("sign", code);

        code = new String(Base64.encode(getCode(map2).getBytes(), Base64.DEFAULT));

        // 测试环境
        // String url =
        // "http://wx05.edaixi.cn:81/mobile.php?m=wap&act=homepage&do=index&mark=1456971417KZ6yt3IN";
        // 正式环境
        String url = "http://wx.rongchain.com/mobile.php?m=wap&act=homepage&do=index&mark=1456971417KZ6yt3IN";
        url += "&code=";
        url += code;

        Logger.i(TAG, url);

        Intent data = new Intent(mContext, CommonWebActivity.class);
        data.putExtra("url", url);
        data.putExtra("title", "e代洗");
        startActivity(data);
    }

//    @OnClick({R.id.vote_ll, R.id.gridkeeper_ll, R.id.community_members_ll,
//            R.id.notification_notice_ll, R.id.community_message_ll, R.id.working_schedule_ll,
//            R.id.convenience_phone_ll, R.id.satisfaction_survey_ll, R.id.volunteer_service_ll,
//            R.id.dribbling_ll, R.id.e_substitution_ll, R.id.discount_sweep_ll,
//            R.id.good_pharmacist_ll})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.vote_ll://投票
//                startActivity(new Intent(mContext,VoteActivity.class));
//                break;
//            case R.id.gridkeeper_ll://网格员
//                startActivity(new Intent(mContext,GridAdministratorActivity.class));
//                break;
//            case R.id.community_members_ll://社区党员
//                startActivity(new Intent(mContext,CommunityMembersActivity.class));
//                break;
//            case R.id.notification_notice_ll://通知公告
//                startActivity(new Intent(mContext,AnnouncementActivity.class));
//                break;
//            case R.id.community_message_ll://社区留言
//                startActivity(new Intent(mContext,LeaveCommentsActivity.class));
//                break;
//            case R.id.working_schedule_ll://办事流程
//                startActivity(new Intent(mContext,WorkingScheduleActivity.class));
//                break;
//            case R.id.convenience_phone_ll://便民电话
//                startActivity(new Intent(mContext,ConveniencePhoneActivity.class));
//                break;
//            case R.id.satisfaction_survey_ll://满意度调查
//                startActivity(new Intent(mContext,SatisfactionSurveyActivity.class));
//                break;
//            case R.id.volunteer_service_ll://志愿者服务
//                startActivity(new Intent(mContext,VolunteerServiceActivity.class));
//                break;
//            case R.id.dribbling_ll://滴滴打车
//                DIOpenSDK.showDDPage(mContext, new HashMap<String, String>());
//
//                break;
//            case R.id.e_substitution_ll://e代洗
//                // e代洗
//
//                String appkey = "mGld7YeBL2ZchfyMIDzVaOvuF1bVGMpt";
//
//                String user_id = AppPreference.I().getString("resident_id","");
//                String mobile = AppPreference.I().getString("phoneNum","");
//
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("appid", "22");
//                map.put("mobile", mobile);
//                map.put("timestamp", RequestParamsUtil.getTimeStamp());
//                map.put("user_id", user_id);
//
//                Map<String, String> map2 = sortMapByKey(map);
//
//                String code = getHashCode(map2, appkey);
//                map2.put("sign", code);
//
//                code = new String(Base64.encode(getCode(map2).getBytes(), Base64.DEFAULT));
//
//                // 测试环境
//                // String url =
//                // "http://wx05.edaixi.cn:81/mobile.php?m=wap&act=homepage&do=index&mark=1456971417KZ6yt3IN";
//                // 正式环境
//                String url = "http://wx.rongchain.com/mobile.php?m=wap&act=homepage&do=index&mark=1456971417KZ6yt3IN";
//                url += "&code=";
//                url += code;
//
//                LogUtil.i(TAG, url);
//
//                Intent data = new Intent(mContext, CommonWebActivity.class);
//                data.putExtra("url", url);
//                data.putExtra("title", "e代洗");
//                startActivity(data);
//                break;
//            case R.id.discount_sweep_ll://优惠扫
////                String tel = AppPreference.I().getString("phoneNum","");
////                Intent intent = new Intent(mContext, PaykeePayMain.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                intent.putExtra("usrMap", tel);
////                intent.putExtra("merId", "870022");
////                startActivity(intent);
//                break;
//            case R.id.good_pharmacist_ll://好药师
//                // 好医药
//
//                String url2 = "http://www.ehaoyao.com/";
//
//                data = new Intent(mContext, CommonWebActivity.class);
//                data.putExtra("url", url2);
//                data.putExtra("title", "好药师");
//                startActivity(data);
//                break;
//        }
//    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    @SuppressLint("DefaultLocale")
    public static String getHashCode(Map<String, String> map, String appkey) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> en : set) {
            sb.append(en.getKey()).append(en.getValue());
        }
        sb.append(appkey);

        LogUtil.d(null, "params before md5 = " + sb.toString());
        String s = MD5Util.MD5(sb.toString()).toUpperCase();
        // s =
        // MD5Util.MD564("appid22mobile15972953955timestamp20160826151654user_id4531mGld7YeBL2ZchfyMIDzVaOvuF1bVGMpt")
        // .toUpperCase();

        LogUtil.d(null, "params after md5 = " + s);
        return s;
    }

    public static String getCode(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> en : set) {
            sb.append(en.getKey()).append("=").append(en.getValue()).append("&");
        }

        LogUtil.d(null, "params before code = " + sb.substring(0, sb.length() - 1).toString());
        return sb.substring(0, sb.length() - 1).toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

