package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.EmergencyContactsLvAdapter;
import com.ovu.lido.adapter.KinsmanSwipeCardsAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.EmergencyContactsInfo;
import com.ovu.lido.bean.FamilySituationInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.ListViewForScrollView;
import com.ovu.lido.widgets.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilySituationActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.swipeCardsView)
    SwipeCardsView swipeCardsView;
    @BindView(R.id.emergency_contacts_lv)
    ListView emergency_contacts_lv;

    private List<FamilySituationInfo.DataBean.EmergencyContactsBean> contactsInfoList = new ArrayList<>();
    private List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanListBeanList = new ArrayList<>();
    private EmergencyContactsLvAdapter contactsLvAdapter;
    private Dialog mDialog;
    private KinsmanSwipeCardsAdapter kinsmanSwipeCardsAdapter;
    private int curIndex;

    private Handler handler = new Handler();

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_family_situation;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mDialog = LoadProgressDialog.createLoadingDialog(this);

        //whether retain last card,default false
        swipeCardsView.retainLastCard(false);
        //Pass false if you want to disable swipe feature,or do nothing.
        swipeCardsView.enableSwipe(true);

        contactsLvAdapter = new EmergencyContactsLvAdapter(this, contactsInfoList, R.layout.emergency_contacts_lv_item);
        emergency_contacts_lv.setAdapter(contactsLvAdapter);
    }


    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        contactsInfoList.clear();
        kinsmanListBeanList.clear();
        OkGo.<String>post(Constant.QUERY_RESIDENT_CONTACTS)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            FamilySituationInfo situationInfo = new Gson().fromJson(response.body(), FamilySituationInfo.class);
                            //紧急联系人
                            List<FamilySituationInfo.DataBean.EmergencyContactsBean> emergencyContacts = situationInfo.getData().getEmergencyContacts();
                            contactsInfoList.addAll(emergencyContacts);
                            contactsLvAdapter.notifyDataSetChanged();
                            //主要成员
                            List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanList = situationInfo.getData().getKinsmanList();
                            kinsmanListBeanList.addAll(kinsmanList);
                            kinsmanSwipeCardsAdapter = new KinsmanSwipeCardsAdapter(mContext, kinsmanListBeanList);
                            swipeCardsView.setAdapter(kinsmanSwipeCardsAdapter);
                            swipeCardsView.notifyDatasetChanged(curIndex);
                        } else if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            showToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (!isFinishing()) LoadProgressDialog.closeDialog(mDialog);
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (event.getPos() == RefreshConstant.EDIT_PERSONAL_INFO_SUCCESS)
            loadData();
    }

    @Override
    protected void setListener() {
        super.setListener();
        //设置滑动监听
        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
            @Override
            public void onShow(int index) {
                curIndex = index;
                Log.i(TAG, "onShow: " + index);
//                if (curIndex == kinsmanListBeanList.size()-1){
//                    //从头开始，重新浏览
//                    swipeCardsView.notifyDatasetChanged(0);
//                }
            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                Log.i(TAG, "onCardVanish: " + index + "/" + kinsmanListBeanList.size());
                if (index == kinsmanListBeanList.size() -1){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeCardsView.notifyDatasetChanged(0);
                        }
                    },350);
                }
//                String orientation = "";
//                switch (type) {
//                    case LEFT:
//                        orientation = "向左飞出";
//                        break;
//                    case RIGHT:
//                        orientation = "向右飞出";
//                        break;
//                }
//                ToastUtil.show(mContext,"position = "+index+";卡片"+orientation);
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
//                ToastUtil.show(mContext,"点击了 position=" + index);
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.kinsman_edit_tv, R.id.emergency_contacts_edit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.kinsman_edit_tv://主要成员
                //主要成员
                startActivity(new Intent(this, EditKinsmanActivity.class));
                break;
            case R.id.emergency_contacts_edit_tv://紧急联系人
                startActivity(new Intent(this, EditEmergencyContactsActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
