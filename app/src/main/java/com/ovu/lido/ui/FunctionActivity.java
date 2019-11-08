package com.ovu.lido.ui;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;


/**
 * 功能介绍
 */
public class FunctionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.wb_function)
    WebView wb_function;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_function;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
    }


    @Override
    protected void loadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("type", "2");
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_VERSION_APP_DETAIL)
                .addParams("params", GsonUtil.ToGson(map1))
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

                            String data = jsonObject.optJSONObject("data").optString("agreement_content");
                            if (!TextUtils.isEmpty(data)) {
                                wb_function.getSettings().setJavaScriptEnabled(true);
                                wb_function.getSettings().setDefaultTextEncodingName("utf-8");
                                wb_function.getSettings().setSupportZoom(false);
                                wb_function.loadData(data, "text/html; charset=UTF-8", null);
                            } else {
                                showToast("获取数据失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
        }
    }

}
