package com.ovu.lido.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.CheckTypeNameInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.help.ConfirmDialog;
import com.ovu.lido.help.StringUtil;
import com.ovu.lido.ui.OccupationActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.ovu.lido.widgets.SelectQuestionTypeDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 入伙管理--入伙验房
 */
public class InspectionRoomFragment extends Fragment {
    @BindView(R.id.inspect_root)
    LinearLayout inspect_root;
    @BindView(R.id.btn_add)
    LinearLayout btn_add;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    public static final String TAG = "wangw";
    private Unbinder unbinder;
    private Context mContext;
    private List<CheckTypeNameInfo.DataBean> checkTypeInfos = new ArrayList<>();
    private Dialog mDialog;

    public InspectionRoomFragment() {
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
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        getCheckTypeName();
    }

    /**
     * 获取问题类型
     */
    private void getCheckTypeName() {
        mDialog.show();
        OkHttpUtils.get()
                .url(Constant.GET_CHECK_TYPE_NAME)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (getActivity() == null || getActivity().isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "获取问题类型: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        CheckTypeNameInfo info = GsonUtil.GsonToBean(response, CheckTypeNameInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            checkTypeInfos.addAll(info.getData());
                        }else {
                            ToastUtil.show(mContext,info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inspection_room, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRoot();
        return view;
    }

    @OnClick({R.id.btn_add, R.id.btn_submit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_add:
                addView();
                break;
            case R.id.btn_submit:
                submitJoinCheck();
                break;
        }
    }

    private List<Map<String,String>> mapList = new ArrayList<>();
    private void submitJoinCheck() {
        mapList.clear();
        int count = inspect_root.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = inspect_root.getChildAt(i);
            TextView item_type_name = view.findViewById(R.id.item_type_name);
            EditText item_edit = view.findViewById(R.id.item_edit);

            String name = item_type_name.getText().toString().trim();
            String content = item_edit.getText().toString().trim();
            if (StringUtil.isEmpty(name) || StringUtil.isEmpty(content)) {
                ToastUtil.show(mContext, "请完善问题信息");
                return;
            }

            Map<String,String> questionMap = new HashMap<>();
            for (CheckTypeNameInfo.DataBean d :
                    checkTypeInfos) {
                if (TextUtils.equals(d.getCheck_type_name(),name)){
                    questionMap.put("id", String.valueOf(d.getId()));
                }

            }
            questionMap.put("content",content);
            questionMap.put("checkTypeName",name);

            mapList.add(questionMap);


        }

        ConfirmDialog dialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmListener() {

            @Override
            public void onConfirm() {
                mDialog.show();
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("resident_id", AppPreference.I().getString("resident_id",""));
                String params = ViewHelper.getParams(paramMap);
                OkHttpUtils.post()
                        .url(Constant.SUBMIT_JOIN_CHECK_URL)
                        .addParams("params",params)
                        .addParams("questionList", GsonUtil.ToGson(mapList))
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
                                Log.i(TAG, "上传验房信息: " + response);
                                LoadProgressDialog.closeDialog(mDialog);

                                UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                                if (info.getErrorCode().equals(Constant.RESULT_OK)){

                                    ToastUtil.show(mContext, "提交成功");
                                    ((Activity)mContext).finish();
                                }else {

                                    ToastUtil.show(mContext, info.getErrorMsg());
                                }
                            }
                        });

            }

            @Override
            public void onCancel() {

            }
        });

        dialog.show();
        dialog.setContentText("确认提交您填写的所有问题，提交后不可更改！");

    }

    private void initRoot() {
        if (inspect_root.getChildCount() == 0) {
            addView();
        }
    }

    private void addView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inspect_item, inspect_root, false);

        final TextView item_type_name = view.findViewById(R.id.item_type_name);
        item_type_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SelectQuestionTypeDialog dialog = new SelectQuestionTypeDialog(mContext, inspect_root, checkTypeInfos);
                dialog.showFilterWindow();
                dialog.setRefreshData(new SelectQuestionTypeDialog.RefreshData() {

                    @Override
                    public void loadData(CheckTypeNameInfo.DataBean checkType) {
                        item_type_name.setText(checkType.getCheck_type_name());
                    }
                });
            }
        });
        inspect_root.addView(view);
    }

    public void removeView() {
        int count = inspect_root.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            CheckBox id_check_box = inspect_root.getChildAt(i).findViewById(R.id.id_check_box);
            if (id_check_box.isChecked()) {
                inspect_root.removeViewAt(i);
            }
        }

        isShowSelectBtn(false);
        initRoot();
    }


    public void isShowSelectBtn(boolean show) {

        int count = inspect_root.getChildCount();
        for (int i = 0; i < count; i++) {
            ViewGroup view = inspect_root.getChildAt(i).findViewById(R.id.id2);
            view.setVisibility(show ? View.VISIBLE : View.GONE);

            CheckBox id_check_box = inspect_root.getChildAt(i).findViewById(R.id.id_check_box);
            id_check_box.setChecked(false);
            id_check_box.setOnCheckedChangeListener(selectListener);

            ViewGroup view2 = inspect_root.getChildAt(i).findViewById(R.id.id1);
            EditText item_edit = view2.findViewById(R.id.item_edit);

            item_edit.setEnabled(!show);
            item_edit.setFocusable(!show);
            item_edit.setFocusableInTouchMode(!show);
        }
        btn_add.setEnabled(!show);

    }

    private CompoundButton.OnCheckedChangeListener selectListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int count = inspect_root.getChildCount();
            boolean detele = false;
            for (int i = 0; i < count; i++) {
                CheckBox id_check_box = (CheckBox) inspect_root.getChildAt(i).findViewById(R.id.id_check_box);
                if (id_check_box.isChecked()) {
                    detele = true;
                }
            }

            ((OccupationActivity) mContext).setStatus(detele ? 1 : 2);
        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
