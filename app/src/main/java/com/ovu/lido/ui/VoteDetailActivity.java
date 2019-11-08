package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.bean.VoteDetailInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.ovu.lido.util.ViewHelper.getDisplayData;

/**
 * 在线社区 -- 投票 -- 投票详情
 */
public class VoteDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.tp_detail_title)
    TextView tp_detail_title;
    @BindView(R.id.tp_detail_content)
    TextView tp_detail_content;
    @BindView(R.id.vote_detail_time_tv)
    TextView vote_detail_time_tv;
    @BindView(R.id.vote_detail_type)
    TextView vote_detail_type;
    @BindView(R.id.vote_detail_option_ll)
    LinearLayout vote_detail_option_ll;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private String vote_id;
    private List<VoteDetailInfo.OptionListBean> optionListBeans = new ArrayList<>();
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_vote_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        vote_id = getIntent().getStringExtra("vote_id");
    }

    @OnClick({R.id.back_iv,R.id.btn_submit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_submit:
                String option_index = null;
                int count = vote_detail_option_ll.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = vote_detail_option_ll.getChildAt(i);
                    ImageView imageView = child.findViewById(R.id.tp_item_icon);
                    if (imageView.isSelected()) {
                        option_index = imageView.getTag().toString();
                    }
                }
                if (StringUtils.isEmpty(option_index)) {
                    showShortToast("选项不能为空");
                    return;
                }
                sendVote(option_index, vote_id);
                break;
        }
    }

    private void sendVote(String option_index, String vote_id) {
        mDialog.show();
        OkHttpUtils.get()
                .url(Constant.SEN_VOTE_URL)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("option_index",option_index)
                .addParams("vote_id",vote_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LoadProgressDialog.closeDialog(mDialog);

                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                String type = "01";
                                startVoteResultActivity(type);
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        String token = AppPreference.I().getString("token", "");
        String resident_id = AppPreference.I().getString("resident_id", "");
        Log.i(TAG, "loadData: token=" + token + "\n resident_id=" + resident_id + "\n vote_id=" + vote_id);

        OkHttpUtils.post()
                .url(Constant.QUERY_VOTE_DETAIL_URL)
                .addParams("token", token)
                .addParams("resident_id", resident_id)
                .addParams("vote_id",vote_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else {
                            VoteDetailInfo info = GsonUtil.GsonToBean(response, VoteDetailInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)){
                                String vote_title = info.getVote_title();
                                String vote_content = info.getVote_content();
                                String start_time = info.getStart_time();
                                String end_time = info.getEnd_time();
                                String vote_ever = info.getVote_ever();//投过票 0为未投票 1为已投过票
                                String vote_option = info.getVote_option();//
                                String vote_type = info.getVote_type();
                                tp_detail_title.setText(vote_title);
                                tp_detail_content.setText(vote_content);
                                vote_detail_time_tv.setText("投票时间：" + getDisplayData(start_time) + " 至 " + getDisplayData(end_time));
                                vote_detail_type.setText("投票类型：" + getType(vote_type));
                                optionListBeans.addAll(info.getOption_list());
                                setOptionLayout(vote_ever,vote_option,start_time,end_time);
                            }else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });
    }

    /**
     *
     * @param str
     * @return
     */
    private String getType(String str) {
        String type = "";
        if (TextUtils.equals(str, "1")) {
            type = "记名投票";
        } else if (TextUtils.equals(str, "2")) {
            type = "不记名投票";
        }
        return type;
    }

    /**
     * 设置投票选项
     */
    private void setOptionLayout(String vote_ever, String vote_option, String start_time, String end_time) {
        if (optionListBeans != null) {
            vote_detail_option_ll.setVisibility(optionListBeans.size() > 0 ? View.VISIBLE : View.GONE);
            for (int i = 0; i < optionListBeans.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.tp_option_item, vote_detail_option_ll, false);
                final String option_index = optionListBeans.get(i).getOption_index();
                String option_content = optionListBeans.get(i).getOption_content();
                ImageView tp_item_icon = view.findViewById(R.id.tp_item_icon);
                tp_item_icon.setTag(option_index);
                TextView tp_item_text = view.findViewById(R.id.tp_item_text);
                View tp_item_line = view.findViewById(R.id.tp_item_line);
                tp_item_text.setText(option_index + ". " + option_content);
                vote_detail_option_ll.addView(view);

                if (i == 0) {
                    tp_item_icon.setSelected(true);
                }
                if (i < optionListBeans.size() - 1) {
                    tp_item_line.setVisibility(View.VISIBLE);
                } else {
                    tp_item_line.setVisibility(View.GONE);
                }
                view.setTag(i);
                view.setOnClickListener(optionClickListener);
            }
        }

        //投过票 0为未投票 1为已投过票
        if (TextUtils.equals(vote_ever,"1")){
            btn_submit.setText("已投票/查看结果");
            btn_submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String type = "02";
                    startVoteResultActivity(type);
                }
            });


            int index = 0;
            for (int i = 0; i < optionListBeans.size(); i++) {
                if (TextUtils.equals(optionListBeans.get(i).getOption_index(), vote_option)) {
                    index = i;
                    break;
                }
            }
            int count = vote_detail_option_ll.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = vote_detail_option_ll.getChildAt(i);
                ImageView imageView =child.findViewById(R.id.tp_item_icon);
                imageView.setSelected(i == index);
                vote_detail_option_ll.getChildAt(i).setOnClickListener(null);
            }
            return;
        }
        Log.i(TAG, "开始时间: " + start_time + "\t 结束时间：" + end_time);
        if (!ViewHelper.compareDate(start_time)) {
            btn_submit.setEnabled(false);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                btn_submit.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_enable));
            }else{
                btn_submit.setBackground(getResources().getDrawable(R.drawable.btn_enable));
            }
            btn_submit.setText("未开始");
        }
        if (ViewHelper.compareDate(end_time)) {

            btn_submit.setEnabled(false);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                btn_submit.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_enable));
            }else{
                btn_submit.setBackground(getResources().getDrawable(R.drawable.btn_enable));
            }
            btn_submit.setText("已结束");
        }

//        if (ViewHelper.compareDate(start_time)) {
//            btn_submit.setEnabled(false);
//            btn_submit.setText("未开始");
//        }
//        if (ViewHelper.compareDate(end_time)) {
//            btn_submit.setEnabled(false);
//            btn_submit.setText("已结束");
//        }

    }

    /**
     * 跳转到投票结果页面
     * @param type 01表示提交投票结果;02表示之前投票过，现在去查看结果
     */
    private void startVoteResultActivity(String type) {
        Log.i(TAG, "voteDetail---vote_id: " + vote_id);
        Intent intent = new Intent(mContext, VoteResultActivity.class);
        intent.putExtra("vote_id", vote_id);
        intent.putExtra("type", type);
        startActivity(intent);
        if ("01".equals(type)){
            finish();
        }
    }

    private View.OnClickListener optionClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            int count = vote_detail_option_ll.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = vote_detail_option_ll.getChildAt(i);
                ImageView imageView = view.findViewById(R.id.tp_item_icon);
                imageView.setSelected(i == index);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
