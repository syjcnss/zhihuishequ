package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.AddressBean;
import com.ovu.lido.bean.CertificationInfo;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 我的收货地址
 */
public class MyAddressActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.lv_address)
    ListView mLv_address;
    private CommonAdapter<AddressBean.AddressListBean> mCommonAdapter;
    private ArrayList<AddressBean.AddressListBean> mList = new ArrayList<>();
    private int current = 0;
    private String mAddressId;
    private String mOrderActivityId = "-1";
    private static final int EDIT_ADDRESS_REQUCODE = 99;
    private int mEditPoistion;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        mOrderActivityId = intent.getStringExtra("addressId");
        mCommonAdapter = new CommonAdapter<AddressBean.AddressListBean>(this, mList, R.layout.lv_address_item) {
            @Override
            protected void convert(final ViewHolder viewHolder, AddressBean.AddressListBean item, final int position) {
                if (item.getIs_default().equals("1")) {
                    viewHolder.getView(R.id.iv_choose).setSelected(true);
                    current = position;
                } else {
                    viewHolder.getView(R.id.iv_choose).setSelected(false);
                }
                viewHolder.setText(R.id.tv_name, item.getShipper());
                viewHolder.setText(R.id.tv_tel, item.getMobile_no());
                viewHolder.setText(R.id.tv_address, item.getZone_code() + item.getAddress_detail());
                viewHolder.getView(R.id.iv_choose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.get(position).getIs_default().equals("1")) {
                            return;
                        }
                        mAddressId = mList.get(position).getAddress_id();
                        editAddress(position);
                    }
                });
                viewHolder.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddress(mList.get(position).getAddress_id());
                    }
                });
                viewHolder.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEditPoistion = position;
                        Intent intent1 = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                        intent1.putExtra("addressBean", mList.get(position));
                        startActivityForResult(intent1, EDIT_ADDRESS_REQUCODE);
                    }
                });
            }
        };
        mLv_address.setAdapter(mCommonAdapter);
    }


    /**
     * 切换默认地址
     *
     * @param position
     */
    private void editAddress(final int position) {
        OkHttpUtils.post()
                .url(HttpAddress.EDIT_ADDRESS)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("address_id", mAddressId)
                .addParams("is_default", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        CertificationInfo bean = GsonUtil.GsonToBean(response, CertificationInfo.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.get(position).setIs_default("1");
                            mList.get(current).setIs_default("0");
                            current = position;
                            mCommonAdapter.setDatas(mList);
                        }

                    }
                });

    }


    private void getAddressList() {
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_ADDRESS)
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
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<AddressBean.AddressListBean>>() {
                            }.getType();
                            List<AddressBean.AddressListBean> list = gson.fromJson(jsonObject.optString("address_list"), listType);
                            mList.clear();
                            if (list != null) {
                                mList.addAll(list);
                            }
                            mCommonAdapter.setDatas(mList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        AddressBean bean = GsonUtil.GsonToBean(response, AddressBean.class);
//                        if (bean.getErrorCode().equals("0000")) {
//                            mList.clear();
//                            mList.addAll(bean.getAddress_list());
//                            mCommonAdapter.setDatas(mList);
//                        } else {
//                            showToast(bean.getErrorMsg());
//                        }
                    }
                });
    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.tv_add_address).setOnClickListener(this);
        mLv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOrderActivityId == null) {
                    Intent intent1 = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                    intent1.putExtra("addressBean", mList.get(position));
                    startActivity(intent1);
                } else {
                    AddressBean.AddressListBean bean = mList.get(position);
//                    Log.i(TAG, "收货人:" + bean.getShipper());
//                    Log.i(TAG, "收货地址:" + bean.getAddress_detail());
                    Intent intent = new Intent();
                    intent.putExtra("address", bean);
                    intent.putExtra("isExist", "1");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.tv_add_address:
                startActivity(new Intent(this, AddAddressActivity.class));
                break;
        }
    }


    /**
     * 删除收货地址
     *
     * @param address_id
     */
    private void deleteAddress(String address_id) {
        OkHttpUtils.post()
                .url(HttpAddress.DELETE_ADDRESS)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("address_id", address_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("删除成功");
                            getAddressList();
                        } else {
                            showToast(bean.getErrorMsg());
                        }

                    }
                });
    }


    @Override
    public void onBackPressed() {
        String isExist = "0";
        if (mOrderActivityId != null && !mOrderActivityId.equals("-1")) {
            for (int i = 0; i < mList.size(); i++) {
                if (mOrderActivityId.equals(mList.get(i).getAddress_id())) {
                    isExist = "1";
                    Intent intent = new Intent();
                    intent.putExtra("addressId", mOrderActivityId);
                    intent.putExtra("address", mList.get(i));
                    intent.putExtra("isExist", isExist);
                    setResult(RESULT_OK, intent);
                    super.onBackPressed();
                    return;
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra("isExist", isExist);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5 && requestCode == EDIT_ADDRESS_REQUCODE && data != null) {
            AddressBean.AddressListBean bean = (AddressBean.AddressListBean) data.getSerializableExtra("addressBean");
            mList.get(mEditPoistion).setIs_default(bean.getIs_default());
            mList.get(mEditPoistion).setAddress_detail(bean.getAddress_detail());
            mList.get(mEditPoistion).setAddress_id(bean.getAddress_id());
            mList.get(mEditPoistion).setMobile_no(bean.getMobile_no());
            mList.get(mEditPoistion).setZone_code(bean.getZone_code());
            mList.get(mEditPoistion).setShipper(bean.getShipper());
            mCommonAdapter.setDatas(mList);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }
}
