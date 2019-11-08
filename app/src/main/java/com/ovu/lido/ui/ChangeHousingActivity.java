package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.MainActivity;
import com.ovu.lido.R;
import com.ovu.lido.adapter.HouseLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ChangeHousingBean;
import com.ovu.lido.bean.SureChangeBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.widgets.CommonAction;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 多小区切换
 */

public class ChangeHousingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.lv_housing)
    ListView mLv_housing;
    private HouseLvAdapter mCommonAdapter;
    private ArrayList<ChangeHousingBean.ExtraEstatesBean> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_change_housing;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
//        mCommonAdapter = new CommonAdapter<ChangeHousingBean.ExtraEstatesBean>(this, mList, R.layout.lv_change_housing_item) {
//            @Override
//            protected void convert(ViewHolder viewHolder, ChangeHousingBean.ExtraEstatesBean item, int position) {
//                viewHolder.setText(R.id.tv_name, item.getHuman_name());
//                viewHolder.setText(R.id.tv_housing, item.getCommunity_name());
//                viewHolder.setText(R.id.tv_floor, item.getRoom_no());
//                String identity = item.getIdentity();
//                ImageView iv_choose = viewHolder.getView(R.id.iv_choose);
//                if (identity.equals("2")|| identity.equals("1")) {
//                    iv_choose.setVisibility(View.VISIBLE);
//                } else {
//                    iv_choose.setVisibility(View.GONE);
//                }
//            }
//        };
        mCommonAdapter = new HouseLvAdapter(mContext, mList);
        mLv_housing.setAdapter(mCommonAdapter);

    }


    private void getData() {
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_RESIDENT_INFO)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(ChangeHousingActivity.this);
                            return;
                        }
                        ChangeHousingBean bean = GsonUtil.GsonToBean(response, ChangeHousingBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            ChangeHousingBean.ExtraEstatesBean estatesBean = new ChangeHousingBean.ExtraEstatesBean();
                            estatesBean.setHuman_name(bean.getHuman_name());
                            estatesBean.setRoom_no(bean.getRoom_no());
                            estatesBean.setCommunity_name(bean.getCommunity_name());
                            estatesBean.setIdentity("0");
                            mList.add(0, estatesBean);
                            mList.addAll(bean.getExtra_estates());
                            mCommonAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.tv_add_housing).setOnClickListener(this);
        mLv_housing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                showChange(position);
                mCommonAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 切换小区
     */
    private void showChange(final int pos) {
        final Dialog mDialog = new Dialog(this, R.style.DialogStyle);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.setContentView(R.layout.dialog_delete_paper);
        TextView tv_cancel = (TextView) mDialog.findViewById(R.id.tv_dialog_cancel);
        TextView sure = (TextView) mDialog.findViewById(R.id.tv_dialog_save);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                changeHousing(pos);
            }
        });

    }

    private void changeHousing(int pos) {
        ChangeHousingBean.ExtraEstatesBean bean = mList.get(pos);
        OkHttpUtils.post()
                .url(HttpAddress.SWITCH_ESTATES)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("community_id", bean.getCommunity_id())
                .addParams("building_no", bean.getBuilding_no())
                .addParams("unit_no", bean.getUnit_no())
                .addParams("room_no", bean.getRoom_no())
                .addParams("room_id", bean.getRoom_id())
                .addParams("identity", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(ChangeHousingActivity.this);
                            return;
                        }
                        SureChangeBean bean = GsonUtil.GsonToBean(response, SureChangeBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            AppPreference.I().putString("token", bean.getData().getToken());
                            AppPreference.I().putString("community_id", bean.getData().getCommunity_id() + "");
                            AppPreference.I().putString("resident_id", bean.getData().getResident_id() + "");
                            AppPreference.I().putString("community_name", bean.getData().getCommunity_name() + "");
                            startActivity(new Intent(ChangeHousingActivity.this, MainActivity.class));
                            CommonAction.getInstance().OutSign();
                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_add_housing:
                Intent intent=new Intent(this, AuthenticationActivity.class);
                intent.putExtra("code",1);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
