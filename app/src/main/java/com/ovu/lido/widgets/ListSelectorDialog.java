package com.ovu.lido.widgets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.BuildingInfo;
import com.ovu.lido.bean.CityInfo;
import com.ovu.lido.bean.CommunityInfo;
import com.ovu.lido.util.DefaultAddressProvider;
import com.ovu.lido.util.AddressProvider;
import com.ovu.lido.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ListSelectorDialog extends Dialog implements View.OnClickListener{
    public static final String TAG = "wangw";

    private static final int WHAT_CITIES_PROVIDED = 0;//检索到城市
    private static final int WHAT_COMMUNITY_PROVIDED = 1;//检索到社区
    private static final int WHAT_BUILDING_PROVIDED = 2;//检索到楼栋
    private static final int WHAT_UNITNO_PROVIDED = 3;//检索到单元
    private static final int WHAT_ROOM_PROVIDED = 4;//检索到房间

    private Context mContext;//上下文
    private final DefaultAddressProvider defaultAddressProvider;//地址数据提供管理类
    private ImageView cancel_iv;//取消按钮
    private ListView select_lv;//选择列表
    private TextView city_tab, community_tab, building_tab, unit_tab, room_tab;//tab标题

    private ProgressBar progressBar;//加载对话框

    private int mTabIndex = 0;//tab下标
    private int mCitySelectedPosition = -1;//城市选择的位置
    private int mCommunitySelectedPosition = -1;//小区选择的位置
    private int mBuildingSelectedPosition = -1;//楼栋选择的位置
    private int mUnitSelectedPosition = -1;//单元选择的位置
    private int mRoomSelectedPosition = -1;//房间选择的位置

    private CityLvAdapter cityLvAdapter;//城市适配器
    private CommunityLvAdapter communityLvAdapter;//社区列表适配器
    private BuildingLvAdapter buildingLvAdapter;//楼栋列表适配器
    private UnitLvAdapter unitLvAdapter;//单元列表适配器
    private RoomLvAdapter roomLvAdapter;//房间列表适配器

    private List<CityInfo> cityInfoList = new ArrayList<>();//城市列表
    private List<CommunityInfo.CommunityListBean> communityInfoList = new ArrayList<>();//城市列表
    private List<BuildingInfo.BuildingListBean> buildingInfoList = new ArrayList<>();//楼栋列表
    private List<BuildingInfo.BuildingListBean.UnitNosBean> unitInfoList = new ArrayList<>();//单元列表
    private List<String> roomInfoList = new ArrayList<>();//房间列表

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_CITIES_PROVIDED://查询到城市数据
                    cityInfoList.addAll((List<CityInfo>) msg.obj);
                    select_lv.setAdapter(cityLvAdapter);
                    cityLvAdapter.notifyDataSetChanged();
                    break;
                case WHAT_COMMUNITY_PROVIDED://查询到小区数据
                    communityInfoList.clear();
                    List<CommunityInfo.CommunityListBean> communityInfos = (List<CommunityInfo.CommunityListBean>) msg.obj;
                    if (communityInfos != null && !communityInfos.isEmpty()) {
                        //以次级内容更新列表
                        communityInfoList.addAll(communityInfos);
                        select_lv.setAdapter(communityLvAdapter);
                        communityLvAdapter.notifyDataSetChanged();
                        //更新索引为 次级
                        mTabIndex = 1;
                    }else {
                        //次级无内容，回调
                        ToastUtil.show(mContext,"该城市暂无小区");
                        callbackInternal();
                    }

                    break;
                case WHAT_BUILDING_PROVIDED://查询到楼栋数据
                    buildingInfoList.clear();
                    List<BuildingInfo.BuildingListBean> buildingInfos = (List<BuildingInfo.BuildingListBean>) msg.obj;
                    if (buildingInfos != null && !buildingInfos.isEmpty()) {
                        //以次级内容更新列表
                        buildingInfoList.addAll(buildingInfos);
                        select_lv.setAdapter(buildingLvAdapter);
                        buildingLvAdapter.notifyDataSetChanged();
                        //更新索引为 次级
                        mTabIndex = 2;
                    }else {
                        //次级无内容，回调
                        ToastUtil.show(mContext,"该小区暂无楼栋");
                        callbackInternal();
                    }
                    break;
                case WHAT_UNITNO_PROVIDED://查询到单元数据
                    unitInfoList.clear();
                    List<BuildingInfo.BuildingListBean.UnitNosBean> unitInfos = (List<BuildingInfo.BuildingListBean.UnitNosBean>) msg.obj;
                    if (unitInfos != null && !unitInfos.isEmpty()) {
                        //以次级内容更新列表
                        unitInfoList.addAll(unitInfos);
                        select_lv.setAdapter(unitLvAdapter);
                        unitLvAdapter.notifyDataSetChanged();
                        //更新索引为 次级
                        mTabIndex = 3;
                    }else {
                        //次级无内容，回调
                        ToastUtil.show(mContext,"该楼栋暂无单元");
                        callbackInternal();
                    }
                    break;
                case WHAT_ROOM_PROVIDED://查询到房间数据
                    roomInfoList.clear();
                    List<String> roomInfos = (List<String>) msg.obj;
                    if (roomInfos != null && !roomInfos.isEmpty()) {
                        roomInfoList.addAll(roomInfos);
                        select_lv.setAdapter(roomLvAdapter);
                        roomLvAdapter.notifyDataSetChanged();
                        //更新索引为 次级
                        mTabIndex = 4;
                    }else {
                        //次级无内容，回调
                        callbackInternal();
                    }
                    break;
            }
            updateTabVisibility();//刷新tab显示
            updateProgressVisibility();//刷新加载进度显示
            return true;
        }
    });




    public ListSelectorDialog(Context context) {
        super(context, R.style.BottomDialogStyle);//设置弹出动画
        mContext = context;
        defaultAddressProvider = new DefaultAddressProvider(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_selector_layout);
        //设置对话框的宽高
        Window window = getWindow();
        //设置弹出位置
        window.getAttributes().gravity = Gravity.BOTTOM;
        //设置对话框大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        initView();
        initEvent();
        //查询城市
        retrieveCities();
    }


    private void initView() {
        cancel_iv = findViewById(R.id.cancel_iv);

        city_tab = findViewById(R.id.city_tab);
        community_tab = findViewById(R.id.community_tab);
        building_tab = findViewById(R.id.building_tab);
        unit_tab = findViewById(R.id.unit_tab);
        room_tab = findViewById(R.id.room_tab);

        progressBar = findViewById(R.id.progressBar);

        select_lv = findViewById(R.id.select_lv);
        initAdapter();
    }

    private void initAdapter() {
        cityLvAdapter = new CityLvAdapter(mContext, cityInfoList, R.layout.list_selector_lv_item);
        communityLvAdapter = new CommunityLvAdapter(mContext, communityInfoList, R.layout.list_selector_lv_item);
        buildingLvAdapter = new BuildingLvAdapter(mContext, buildingInfoList, R.layout.list_selector_lv_item);
        unitLvAdapter = new UnitLvAdapter(mContext, unitInfoList, R.layout.list_selector_lv_item);
        roomLvAdapter = new RoomLvAdapter(mContext, roomInfoList, R.layout.list_selector_lv_item);

    }

    private void initEvent() {
        cancel_iv.setOnClickListener(this);
        city_tab.setOnClickListener(this);
        community_tab.setOnClickListener(this);
        building_tab.setOnClickListener(this);
        unit_tab.setOnClickListener(this);
        room_tab.setOnClickListener(this);
        select_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mTabIndex) {
                    case 0://城市
                        CityInfo cityInfo = cityInfoList.get(position);
                        //设置tab标题
                        city_tab.setText(cityInfo.getItem_name());
                        community_tab.setText("请选择");
                        building_tab.setText("请选择");
                        unit_tab.setText("请选择");
                        room_tab.setText("请选择");
                        //清空次级列表
                        communityInfoList.clear();
                        buildingInfoList.clear();
                        unitInfoList.clear();
                        roomInfoList.clear();
                        //刷新tab显示
                        updateTabVisibility();

                        //更新选中项
                        mCitySelectedPosition = position;
                        mCommunitySelectedPosition = -1;
                        mBuildingSelectedPosition = -1;
                        mUnitSelectedPosition = -1;
                        mRoomSelectedPosition = -1;
                        //更新选中效果
                        cityLvAdapter.notifyDataSetChanged();
                        //获取小区列表数据
                        retrieveCommunity(cityInfo.getZone_code());
                        break;
                    case 1://小区
                        CommunityInfo.CommunityListBean communityInfo = communityInfoList.get(position);
                        //设置标题
                        community_tab.setText(communityInfo.getItem_name());
                        building_tab.setText("请选择");
                        unit_tab.setText("请选择");
                        room_tab.setText("请选择");
                        //清空次级列表
                        buildingInfoList.clear();
                        unitInfoList.clear();
                        roomInfoList.clear();
                        //刷新tab显示
                        updateTabVisibility();

                        //更新选中项
                        mCommunitySelectedPosition = position;
                        mBuildingSelectedPosition = -1;
                        mUnitSelectedPosition = -1;
                        mRoomSelectedPosition = -1;
                        //更新选中效果
                        communityLvAdapter.notifyDataSetChanged();
                        //获取楼栋列表数据
                        retrieveBuilding(communityInfo.getId());
                        break;
                    case 2://楼栋
                        BuildingInfo.BuildingListBean buildingInfo = buildingInfoList.get(position);
                        //设置标题
                        building_tab.setText(buildingInfo.getItem_name());
                        unit_tab.setText("请选择");
                        room_tab.setText("请选择");
                        //清空次级列表
                        unitInfoList.clear();
                        roomInfoList.clear();
                        //刷新tab显示
                        updateTabVisibility();

                        //更新选中项
                        mBuildingSelectedPosition = position;
                        mUnitSelectedPosition = -1;
                        mRoomSelectedPosition = -1;
                        //更新选中效果
                        buildingLvAdapter.notifyDataSetChanged();

                        //获取单元列表数据
                        retrieveUnitNo(buildingInfo);
                        break;
                    case 3://单元
                        BuildingInfo.BuildingListBean.UnitNosBean unitInfo = unitInfoList.get(position);
                        //设置标题
                        unit_tab.setText(unitInfo.getItem_name());
                        //清空次级列表
                        roomInfoList.clear();
                        room_tab.setText("请选择");
                        //刷新tab显示
                        updateTabVisibility();
                        //更新选中项
                        mUnitSelectedPosition = position;
                        mRoomSelectedPosition = -1;
                        //更新选中效果
                        unitLvAdapter.notifyDataSetChanged();
                        //获取房间列表数据
                        retrieveRoom(unitInfo);
                        break;
                    case 4://房号
                        String roomName = roomInfoList.get(position);
                        room_tab.setText(roomName);
                        //刷新tab显示
                        updateTabVisibility();
                        //更新选中项
                        mRoomSelectedPosition = position;
                        //更新选中效果
                        roomLvAdapter.notifyDataSetChanged();
                        //次级无内容，回调
                        callbackInternal();
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_iv://取消
                dismiss();
                break;
            case R.id.city_tab://城市
                mTabIndex = 0;
                select_lv.setAdapter(cityLvAdapter);
                if (mCitySelectedPosition != -1){
                    select_lv.setSelection(mCitySelectedPosition);
                }
                updateTabVisibility();
                break;
            case R.id.community_tab:
                mTabIndex = 1;
                select_lv.setAdapter(communityLvAdapter);
                if (mCommunitySelectedPosition != -1){
                    select_lv.setSelection(mCommunitySelectedPosition);
                }
                updateTabVisibility();
                break;
            case R.id.building_tab:
                mTabIndex = 2;
                select_lv.setAdapter(buildingLvAdapter);
                if (mBuildingSelectedPosition != -1){
                    select_lv.setSelection(mBuildingSelectedPosition);
                }
                updateTabVisibility();
                break;
            case R.id.unit_tab:
                mTabIndex = 3;
                select_lv.setAdapter(unitLvAdapter);
                if (mUnitSelectedPosition != -1){
                    select_lv.setSelection(mUnitSelectedPosition);
                }
                updateTabVisibility();
                break;
            case R.id.room_tab:
                mTabIndex = 4;
                select_lv.setAdapter(roomLvAdapter);
                if (mRoomSelectedPosition != -1){
                    select_lv.setSelection(mRoomSelectedPosition);
                }
                updateTabVisibility();
                break;
        }
    }

    /**
     * 更新tab显示
     */
    private void updateTabVisibility() {

        city_tab.setVisibility(cityLvAdapter.getCount() > 0 ? View.VISIBLE: View.GONE);
        community_tab.setVisibility(communityLvAdapter.getCount() > 0 ? View.VISIBLE : View.GONE);
        building_tab.setVisibility(buildingLvAdapter.getCount() > 0 ? View.VISIBLE : View.GONE);
        unit_tab.setVisibility(unitLvAdapter.getCount() > 0 ? View.VISIBLE : View.GONE);
        room_tab.setVisibility(roomLvAdapter.getCount() > 0 ? View.VISIBLE : View.GONE);

        city_tab.setSelected(mTabIndex == 0);
        community_tab.setSelected(mTabIndex ==1);
        building_tab.setSelected(mTabIndex == 2);
        unit_tab.setSelected(mTabIndex == 3);
        room_tab.setSelected(mTabIndex == 4);
    }

    /**
     * 刷新加载进度显示
     */
    private void updateProgressVisibility() {
        ListAdapter adapter = select_lv.getAdapter();
        int itemCount = adapter.getCount();
        progressBar.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
    }

    private void callbackInternal() {
        if (onAddressSelectedListener != null) {
            dismiss();//关闭dialog

            CityInfo cityInfo = cityInfoList.isEmpty() || mCitySelectedPosition == -1 ? null : cityInfoList.get(mCitySelectedPosition);
            CommunityInfo.CommunityListBean communityInfo = communityInfoList.isEmpty() || mCommunitySelectedPosition == -1 ? null : communityInfoList.get(mCommunitySelectedPosition);
            BuildingInfo.BuildingListBean buildingInfo = buildingInfoList.isEmpty() || mBuildingSelectedPosition == -1 ? null : buildingInfoList.get(mBuildingSelectedPosition);
            BuildingInfo.BuildingListBean.UnitNosBean unitInfo = unitInfoList.isEmpty() || mUnitSelectedPosition == -1 ? null : unitInfoList.get(mUnitSelectedPosition);
            String roomName = roomInfoList.isEmpty() || mRoomSelectedPosition == -1 ? "" : roomInfoList.get(mRoomSelectedPosition);

            onAddressSelectedListener.onAddressSelected(cityInfo, communityInfo, buildingInfo, unitInfo, roomName);
        }
    }

    public interface OnAddressSelectedListener{
        void onAddressSelected(CityInfo cityInfo, CommunityInfo.CommunityListBean communityInfo, BuildingInfo.BuildingListBean buildingInfo, BuildingInfo.BuildingListBean.UnitNosBean unitInfo, String roomName);
    }

    private OnAddressSelectedListener onAddressSelectedListener;

    public void setOnAddressSelectedListener(OnAddressSelectedListener onAddressSelectedListener) {
        this.onAddressSelectedListener = onAddressSelectedListener;
    }

    /**
     * 查询城市
     */
    private void retrieveCities() {
        progressBar.setVisibility(View.VISIBLE);
        defaultAddressProvider.provideCitiesWith("2", new AddressProvider.AddressReceiver<CityInfo>() {

            @Override
            public void send(List<CityInfo> data) {
                Log.i(TAG, "send: " + data.toString());
                mHandler.sendMessage(Message.obtain(mHandler, WHAT_CITIES_PROVIDED, data));
            }
        });
    }

    /**
     * 获取小区列表数据
     * @param zoneCode
     */
    private void retrieveCommunity(String zoneCode) {
        progressBar.setVisibility(View.VISIBLE);
        defaultAddressProvider.provideCommunityWith(zoneCode, new AddressProvider.AddressReceiver<CommunityInfo.CommunityListBean>() {

            @Override
            public void send(List<CommunityInfo.CommunityListBean> data) {
                mHandler.sendMessage(Message.obtain(mHandler, WHAT_COMMUNITY_PROVIDED, data));
            }
        });
    }

    /**
     * 获取楼栋列表数据
     * @param communityId
     */
    private void retrieveBuilding(String communityId) {
        progressBar.setVisibility(View.VISIBLE);
        defaultAddressProvider.provideBuildingWith(communityId, new AddressProvider.AddressReceiver<BuildingInfo.BuildingListBean>() {

            @Override
            public void send(List<BuildingInfo.BuildingListBean> data) {
                mHandler.sendMessage(Message.obtain(mHandler, WHAT_BUILDING_PROVIDED, data));
            }
        });
    }

    /**
     * 获取单元列表数据
     * @param buildingInfo
     */
    private void retrieveUnitNo(BuildingInfo.BuildingListBean buildingInfo) {
        progressBar.setVisibility(View.VISIBLE);
        List<BuildingInfo.BuildingListBean.UnitNosBean> unit_nos = buildingInfo.getUnit_nos();
        mHandler.sendMessage(Message.obtain(mHandler, WHAT_UNITNO_PROVIDED, unit_nos));

    }

    /**
     * 获取房间列表数据
     * @param unitInfo
     */
    private void retrieveRoom(BuildingInfo.BuildingListBean.UnitNosBean unitInfo) {
        progressBar.setVisibility(View.VISIBLE);
        List<String> room_nos = unitInfo.getRoom_nos();
        mHandler.sendMessage(Message.obtain(mHandler, WHAT_ROOM_PROVIDED, room_nos));

    }


    class CityLvAdapter extends CommonAdapter<CityInfo> {

        public CityLvAdapter(Context context, List<CityInfo> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        protected void convert(ViewHolder holder, CityInfo item, int position) {
            TextView name_tv = holder.getView(R.id.name_tv);
            name_tv.setText(item.getItem_name());
            boolean selected = mCitySelectedPosition != -1 && TextUtils.equals(item.getZone_code(), cityInfoList.get(mCitySelectedPosition).getZone_code());
            name_tv.setSelected(selected);
            holder.getView(R.id.selected_icon).setVisibility(selected ? View.VISIBLE : View.GONE);

        }
    }

    class CommunityLvAdapter extends CommonAdapter<CommunityInfo.CommunityListBean> {

        public CommunityLvAdapter(Context context, List<CommunityInfo.CommunityListBean> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        protected void convert(ViewHolder holder, CommunityInfo.CommunityListBean item, int position) {
            TextView name_tv = holder.getView(R.id.name_tv);
            name_tv.setText(item.getItem_name());
            boolean selected = mCommunitySelectedPosition != -1 && TextUtils.equals(item.getId(), communityInfoList.get(mCommunitySelectedPosition).getId());
            name_tv.setSelected(selected);
            holder.getView(R.id.selected_icon).setVisibility(selected ? View.VISIBLE : View.GONE);

        }
    }

    class BuildingLvAdapter extends CommonAdapter<BuildingInfo.BuildingListBean> {

        public BuildingLvAdapter(Context context, List<BuildingInfo.BuildingListBean> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        protected void convert(ViewHolder holder, BuildingInfo.BuildingListBean item, int position) {
            TextView name_tv = holder.getView(R.id.name_tv);
            name_tv.setText(item.getItem_name());
            boolean selected = mBuildingSelectedPosition != -1 && TextUtils.equals(item.getBuilding_no(), buildingInfoList.get(mBuildingSelectedPosition).getBuilding_no());
            name_tv.setSelected(selected);
            holder.getView(R.id.selected_icon).setVisibility(selected ? View.VISIBLE : View.GONE);

        }
    }

    class UnitLvAdapter extends CommonAdapter<BuildingInfo.BuildingListBean.UnitNosBean> {

        public UnitLvAdapter(Context context, List<BuildingInfo.BuildingListBean.UnitNosBean> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        protected void convert(ViewHolder holder, BuildingInfo.BuildingListBean.UnitNosBean item, int position) {
            TextView name_tv = holder.getView(R.id.name_tv);
            name_tv.setText(item.getItem_name());
            boolean selected = mUnitSelectedPosition != -1 && TextUtils.equals(item.getUnit_no(), unitInfoList.get(mUnitSelectedPosition).getUnit_no());
            name_tv.setSelected(selected);
            holder.getView(R.id.selected_icon).setVisibility(selected ? View.VISIBLE : View.GONE);

        }
    }

    class RoomLvAdapter extends CommonAdapter<String> {

        public RoomLvAdapter(Context context, List<String> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        protected void convert(ViewHolder holder, String item, int position) {
            TextView name_tv = holder.getView(R.id.name_tv);
            name_tv.setText(item);
            boolean selected = mRoomSelectedPosition != -1 && TextUtils.equals(item, roomInfoList.get(mRoomSelectedPosition));
            name_tv.setSelected(selected);
            holder.getView(R.id.selected_icon).setVisibility(selected ? View.VISIBLE : View.GONE);

        }
    }
}
