package com.ovu.lido.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.NearbyStoresLvAdapter;
import com.ovu.lido.bean.NearbyStoresInfo;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.TaskHelper;
import com.ovu.lido.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 附近商店
 */
public class NearbyStoresFragment extends Fragment {
    @BindView(R.id.nearby_stores_lv)
    ListView nearby_stores_lv;

    public static final String TAG = "wangw";
    private Unbinder unbinder;
    private Context mContext;
    private List<NearbyStoresInfo.DataBean> storesInfos = new ArrayList<>();
    private NearbyStoresLvAdapter mNearbyStoresLvAdapter;

    public NearbyStoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby_stores, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    private void initView() {
        mNearbyStoresLvAdapter = new NearbyStoresLvAdapter(mContext, storesInfos);
        nearby_stores_lv.setAdapter(mNearbyStoresLvAdapter);
    }

    private void loadData() {
        OkHttpUtils.get()
                .url(Constant.NEAR_SHOPE_LIST)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "附近商店数据: " + response);
                        if (isTokenError(response)){
                            startLoginActivity();
                        }else {
                            NearbyStoresInfo info = GsonUtil.GsonToBean(response, NearbyStoresInfo.class);
                            if (info.getErrorCode().equals(Constant.RESULT_OK)){
                                storesInfos.addAll(info.getData());
                                mNearbyStoresLvAdapter.notifyDataSetChanged();
                            }else {
                                ToastUtil.show(mContext,info.getErrorMsg());
                            }
                        }
                    }
                });
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 获取状态码
     * @param response 数据源
     * @return 状态码
     */
    private String getErrorCode(String response) {
        String errorCode = "";
        //创建容器
        JSONObject rootObject = null;
        try {
            rootObject = new JSONObject(response);
            errorCode = rootObject.getString("errorCode");
            Log.i(TAG, "errorCode: " + errorCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorCode;
    }

    /**
     * 判断token是否有效
     * @param response 数据源
     * @return 是否有效
     */
    public boolean isTokenError(String response){
        String errorCode = getErrorCode(response);
        //如果状态码为TOKEN_ERROR，说明token失效
        if (errorCode.equals(Constant.TOKEN_ERROR)){
            return true;
        }
        return false;
    }

    /**
     * 关闭所有activity，并跳转至登陆页
     */
    public void startLoginActivity() {
        Intent intent = new Intent(mContext, EnterMethodActivity.class);
        TaskHelper.finishAffinity((Activity) mContext,intent);
        ToastUtil.show(mContext,getString(R.string.token_error));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
