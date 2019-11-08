package com.ovu.lido.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.bean.BuildingInfo;
import com.ovu.lido.bean.CityBaseInfo;
import com.ovu.lido.bean.CityInfo;
import com.ovu.lido.bean.CommunityInfo;
import com.ovu.lido.bean.RoomsInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DefaultAddressProvider implements AddressProvider {
    public static final String TAG = "wangw";
    private Context mContext;

    public DefaultAddressProvider(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取城市列表
     * @param zoneLevel
     * @param addressReceiver
     */
    @Override
    public void provideCitiesWith(String zoneLevel, final AddressReceiver<CityInfo> addressReceiver) {
        OkGo.<String>get(Constant.GET_CITY_URL)
                .params("zone_level", zoneLevel) //Zone_level = 1 获取全国的收货地址;Zone_level=2 app注册
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "城市列表-onSuccess: " + response.body());
                        Type listType = new TypeToken<CityBaseInfo>() {
                        }.getType();
                        CityBaseInfo info = new Gson().fromJson(response.body(), listType);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            try {
                                byte[] unzip = ZipUtil.unzip(Base64.decode(info.getZone_data(), Base64.DEFAULT));
                                String zone_data = new String(unzip, "UTF-8");
                                List<CityInfo> cityInfoList = ViewHelper.getZoneList(zone_data);
                                addressReceiver.send(cityInfoList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            ToastUtil.show(mContext,info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取小区列表
     * @param zoneCode
     * @param addressReceiver
     */
    @Override
    public void provideCommunityWith(String zoneCode, final AddressReceiver<CommunityInfo.CommunityListBean> addressReceiver) {
        OkGo.<String>get(Constant.GET_COMMUNITY_URL)
                .params("zone_code", zoneCode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "小区列表-onSuccess: " + response.body());
                        Type type = new TypeToken<CommunityInfo>() {
                        }.getType();
                        CommunityInfo info = new Gson().fromJson(response.body(), type);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            List<CommunityInfo.CommunityListBean> communityList = info.getCommunity_list();
                            addressReceiver.send(communityList);
                        }else {
                            ToastUtil.show(mContext, info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取楼栋列表
     * @param communityId
     * @param addressReceiver
     */
    @Override
    public void provideBuildingWith(String communityId, final AddressReceiver<BuildingInfo.BuildingListBean> addressReceiver) {
        OkGo.<String>get(Constant.GET_BUILDING_URL)
                .params("community_id", communityId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "楼栋列表-onSuccess: " + response.body());
                        Type type = new TypeToken<BuildingInfo>() {
                        }.getType();
                        BuildingInfo info = new Gson().fromJson(response.body(), type);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            List<BuildingInfo.BuildingListBean> buildingList = info.getBuilding_list();
                            addressReceiver.send(buildingList);
                        }else {
                            ToastUtil.show(mContext, info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取单元列表
     * @param countyKey
     * @param addressReceiver
     */
    @Override
    public void provideUnitWith(String countyKey, AddressReceiver<BuildingInfo.BuildingListBean.UnitNosBean> addressReceiver) {

    }

    /**
     * 获取房号列表
     * @param countyKey
     * @param addressReceiver
     */
    @Override
    public void provideRoomWith(String countyKey, AddressReceiver<RoomsInfo> addressReceiver) {

    }
}
