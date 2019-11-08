package com.ovu.lido.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ovu.lido.R;
import com.ovu.lido.bean.IntegraRuleInfo;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 积分规则
 */
public class IntegralRuleFragment extends Fragment {
    @BindView(R.id.rule_webview)
    WebView rule_webview;

    private Context mContext;
    private Unbinder unbinder;
    public static final String TAG = "wangw";
    private Dialog mDialog;

    public IntegralRuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_integral_rule, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    private void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);

        rule_webview.getSettings().setJavaScriptEnabled(true);
        rule_webview.getSettings().setDefaultTextEncodingName("utf-8");
        rule_webview.getSettings().setSupportZoom(false);
        rule_webview.getSettings().setUseWideViewPort(true);
        rule_webview.getSettings().setLoadWithOverviewMode(true);
    }

    private void loadData() {
        mDialog.show();
        OkHttpUtils.get()
                .url(Constant.GET_POINT_RULE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || getActivity().isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "积分规则数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        IntegraRuleInfo info = GsonUtil.GsonToBean(response, IntegraRuleInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            String pointRule = info.getData().getPointRule();
                            rule_webview.loadData(pointRule, "text/html; charset=UTF-8", null);
                        }else {
                            ToastUtil.show(mContext,info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
