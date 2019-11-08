package com.ovu.lido.util;

import com.ovu.lido.bean.BuildingInfo;
import com.ovu.lido.bean.CityInfo;
import com.ovu.lido.bean.CommunityInfo;
import com.ovu.lido.bean.RoomsInfo;

import java.util.List;

public interface AddressProvider {
    void provideCitiesWith(String zoneLevel, AddressReceiver<CityInfo> addressReceiver);//城市
    void provideCommunityWith(String zoneCode, AddressReceiver<CommunityInfo.CommunityListBean> addressReceiver);//小区
    void provideBuildingWith(String communityId, AddressReceiver<BuildingInfo.BuildingListBean> addressReceiver);//楼栋
    void provideUnitWith(String countyKey, AddressReceiver<BuildingInfo.BuildingListBean.UnitNosBean> addressReceiver);//单元
    void provideRoomWith(String countyKey, AddressReceiver<RoomsInfo> addressReceiver);//房号

    interface AddressReceiver<T> {
        void send(List<T> data);
    }
}
