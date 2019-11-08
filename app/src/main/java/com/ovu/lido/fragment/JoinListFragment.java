package com.ovu.lido.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.JoinQuestionLvAdapter;
import com.ovu.lido.bean.JoinQuestionListInfo;
import com.ovu.lido.ui.QuestionDetailActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 入伙验房信息--展示页面
 */
public class JoinListFragment extends Fragment {
    @BindView(R.id.question_lv)
    ListView question_lv;

    public static final String TAG = "wangw";
    private Unbinder unbinder;
    private Context mContext;
    private List<JoinQuestionListInfo.DataBean> questionListInfos = new ArrayList<>();
    private JoinQuestionLvAdapter mJoinQuestionLvAdapter;
    private Dialog mDialog;

    public JoinListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = LoadProgressDialog.createLoadingDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    private void initView() {
        mJoinQuestionLvAdapter = new JoinQuestionLvAdapter(mContext, questionListInfos);
        question_lv.setAdapter(mJoinQuestionLvAdapter);
        question_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                JoinQuestionListInfo.DataBean bean = questionListInfos.get(position);
                intent.putExtra("id", bean.getId());
                intent.putExtra("checkTypeName", bean.getCheckTypeName());
                intent.putExtra("content",bean.getContent());
                intent.putExtra("createTime",bean.getCreateTime());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        mDialog.show();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        String params = ViewHelper.getParams(paramMap);
        OkHttpUtils.post()
                .url(Constant.GET_QUESTION_LIST)
                .addParams("params",params)
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
                        Log.i(TAG, "入伙验房问题列表: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        JoinQuestionListInfo info = GsonUtil.GsonToBean(response, JoinQuestionListInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            if (null != info.getData()){
                                questionListInfos.addAll(info.getData());
                                mJoinQuestionLvAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.show(mContext,info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
