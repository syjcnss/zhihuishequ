package com.ovu.lido.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.ActivityBean;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.ui.ActivityDetailedActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class ActivityFragment extends Fragment {
    @BindView(R.id.lv_activity)
    ListView mLv_activity;
    private CommonAdapter<ActivityBean.DataBean> mCommonAdapter;
    private ArrayList<ActivityBean.DataBean> mList = new ArrayList<>();
    private Context mContext;
    private String activityId;
    private Dialog mDialog;
    private Unbinder unbinder;
    private String TAG = "wangw";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        loadData();
        return view;
    }

    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(getActivity());
        mCommonAdapter = new CommonAdapter<ActivityBean.DataBean>(getActivity(), mList, R.layout.lv_activity_item) {
            @Override
            protected void convert(ViewHolder viewHolder, ActivityBean.DataBean item, final int position) {
                viewHolder.setText(R.id.tv_title, item.getActivity_name());
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.activity_image_error)
                        .error(R.drawable.activity_image_error);
                Glide.with(mContext)
                        .load(item.getActivity_img())
                        .apply(requestOptions)
                        .into((ImageView) viewHolder.getView(R.id.iv_content));
                if (item.isIs_end()) {
                    viewHolder.getView(R.id.iv_state).setSelected(false);
                    viewHolder.setText(R.id.tv_join, "已结束");
                    viewHolder.getView(R.id.tv_join).setSelected(false);
                    viewHolder.getView(R.id.tv_join).setEnabled(false);
                } else {
                    viewHolder.getView(R.id.iv_state).setSelected(true);
                    viewHolder.getView(R.id.tv_join).setSelected(true);
                    viewHolder.getView(R.id.tv_join).setEnabled(true);
                    if (item.isIs_enroll()) {
                        viewHolder.setText(R.id.tv_join, "已参加");
                        viewHolder.getView(R.id.tv_join).setSelected(false);
                    } else {
                        viewHolder.setText(R.id.tv_join, "去参加");
                        viewHolder.getView(R.id.tv_join).setSelected(true);
                    }


                }
                viewHolder.setText(R.id.tv_num, item.getEnrollCount() + "/" + item.getEnroll_limit());

                viewHolder.getView(R.id.tv_join).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityId = mList.get(position).getId() + "";
                                activityApply();


                            }
                        }
                );
            }
        };
        mLv_activity.setAdapter(mCommonAdapter);
    }


    /**
     * 参加活动
     */
    private void activityApply() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("activity_id", activityId);
        map.put("enroll_remark", "");
        map.put("community_id", AppPreference.I().getString("community_id", ""));
        map1.put("data", map);
        Logger.i(TAG, GsonUtil.ToGson(map1));
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_APPLY)
                .addParams("params", GsonUtil.ToGson(map1))
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
                            ToastUtil.show(mContext,"积分:+" + bean.getPoint());
                            getData();
                        }else {
                            ToastUtil.show(mContext,bean.getErrorMsg());
                        }

                    }
                });

    }

    public void loadData() {
        mDialog.show();
        getData();

    }

    public void getData() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        Logger.i(TAG, "请求信息:" + GsonUtil.ToGson(map1));
        OkHttpUtils.post()
                .url(HttpAddress.ACTIVITY_LIST)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, "请求信息:" + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        ActivityBean bean = GsonUtil.GsonToBean(response, ActivityBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            mList.addAll(bean.getData());
                            mCommonAdapter.setDatas(mList);
                        }

                    }
                });
    }

    private void setListener() {
        mLv_activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityDetailedActivity.class);
                intent.putExtra("activityId", mList.get(position).getId() + "");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
        unbinder.unbind();
    }
}
