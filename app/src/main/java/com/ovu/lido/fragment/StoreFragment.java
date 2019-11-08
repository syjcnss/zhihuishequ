package com.ovu.lido.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.NearbyStoresLvAdapter;
import com.ovu.lido.bean.NearbyStoresInfo;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.TaskHelper;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商店
 */
public class StoreFragment extends Fragment {
    @BindView(R.id.nearby_stores_lv)
    ListView nearby_stores_lv;

    public static final String TAG = "wangw";

    private List<NearbyStoresInfo.DataBean> storesInfos = new ArrayList<>();
    private NearbyStoresLvAdapter mNearbyStoresLvAdapter;
    private Unbinder unbinder;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadData();
        setListener();
    }

    private void initView() {
        mNearbyStoresLvAdapter = new NearbyStoresLvAdapter(mContext, storesInfos);
        nearby_stores_lv.setAdapter(mNearbyStoresLvAdapter);
    }


    private void loadData() {
        Map<String,String> map = new HashMap<>();
        OkGo.<String>post(Constant.NEAR_SHOPE_LIST)
                .params("token", AppPreference.I().getString("token",""))
                .params("resident_id", AppPreference.I().getString("resident_id",""))
                .params("params", ViewHelper.getParams(map))
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "附近商店-onSuccess: " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String errorCode = jsonObject.optString("errorCode");
                            String errorMsg = jsonObject.optString("errorMsg");
                            if (errorCode.equals(Constant.TOKEN_ERROR)){
                                Intent intent = new Intent(mContext, EnterMethodActivity.class);
                                TaskHelper.finishAffinity((Activity) mContext,intent);
                                ToastUtil.show(mContext,getString(R.string.token_error));
                            }else if (errorCode.equals(Constant.RESULT_OK)){
                                Type type = new TypeToken<NearbyStoresInfo>() {
                                }.getType();
                                NearbyStoresInfo info = new Gson().fromJson(response.body(),type);
                                storesInfos.addAll(info.getData());
                                mNearbyStoresLvAdapter.notifyDataSetChanged();
                            }else {
                                ToastUtil.show(mContext,errorMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    private void setListener() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
