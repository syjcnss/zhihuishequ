package com.ovu.lido.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.WorkOrderDetailRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.WorkOrderDetailInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ListViewForScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MyWorkOrderDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.work_order_detail_lv)
    ListViewForScrollView work_order_detail_lv;
    @BindView(R.id.work_order_detail_rv)
    RecyclerView work_order_detail_rv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.line_top)
    View line_top;
    @BindView(R.id.line_bottom)
    View line_bottom;
    @BindView(R.id.type_tv)
    TextView type_tv;
    @BindView(R.id.content_tv)
    TextView content_tv;
    @BindView(R.id.evaluate_tv)
    TextView evaluate_tv;
    @BindView(R.id.work_type_name_tv)
    TextView work_type_name_tv;//工单类型
    @BindView(R.id.report_time_tv)
    TextView report_time_tv;//申请时间
    @BindView(R.id.event_addr_tv)
    TextView event_addr_tv;//工单地点
    @BindView(R.id.expected_time_tv)
    TextView expected_time_tv;//期望上门时间

    @BindView(R.id.line_view)
    View line_view;
    @BindView(R.id.finish_person_ll)
    LinearLayout finish_person_ll;
    @BindView(R.id.finish_person_tv)
    TextView finish_person_tv;//执行人
    @BindView(R.id.finish_comment_ll)
    LinearLayout finish_comment_ll;
    @BindView(R.id.finish_comment_tv)
    TextView finish_comment_tv;//执行人备注
    @BindView(R.id.finish_photo_rl)
    RelativeLayout finish_photo_rl;
    @BindView(R.id.finish_photo_rv)
    RecyclerView finish_photo_rv;//完成时上传的图片


    private List<WorkOrderDetailInfo> workOrderDetailInfos = new ArrayList<>();
//    private WorkOrderDetailLvAdapter mWorkOrderDetailLvAdapter;

    private List<Integer> resIds = new ArrayList<>();
    private String[] reportPictureList;
    private String[] finishPictureList;
    private WorkOrderDetailRvAdapter mWorkOrderDetailRvAdapter;
    private WorkOrderDetailRvAdapter mFinishRvAdapter;
    private String order_id;
    private String workStatus;
    private String reportTime;
    private String finishTime;
    private String evaluateComment;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_work_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        super.initView();
        //工单进度
        initWorkOrderTree();

        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        work_order_detail_rv.setLayoutManager(layoutManager);
        //问题2、ScrollView嵌套RecyclerView后滑动很缓慢，不流畅，没有惯性
        work_order_detail_rv.setHasFixedSize(true);
        work_order_detail_rv.setNestedScrollingEnabled(false);
        mWorkOrderDetailRvAdapter = new WorkOrderDetailRvAdapter(mContext, reportPictureList);
        work_order_detail_rv.setAdapter(mWorkOrderDetailRvAdapter);

        GridLayoutManager layoutManager2 = new GridLayoutManager(mContext,4);
        finish_photo_rv.setLayoutManager(layoutManager2);
        finish_photo_rv.setHasFixedSize(true);
        finish_photo_rv.setNestedScrollingEnabled(false);
        mFinishRvAdapter = new WorkOrderDetailRvAdapter(mContext, finishPictureList);
        finish_photo_rv.setAdapter(mWorkOrderDetailRvAdapter);
    }

    private void initWorkOrderTree() {
        line_top.setVisibility(View.INVISIBLE);
        line_bottom.setVisibility(View.INVISIBLE);
        order_id = getIntent().getStringExtra("order_id");
        workStatus = getIntent().getStringExtra("workStatus");
        reportTime = getIntent().getStringExtra("reportTime");
        finishTime = getIntent().getStringExtra("finishTime");
        evaluateComment = getIntent().getStringExtra("evaluateComment");


        //1.待处理  2.处理中 3.待评价  4.已评价
        switch (workStatus){
            case "1":
                time_tv.setText(ViewHelper.getDisplayData(reportTime));
                type_tv.setText("待处理");
                content_tv.setText("您已成功创建工单，请耐心等待物业人员接单");
                break;
            case "2":
                time_tv.setText(null == finishTime ? ViewHelper.getDisplayData(reportTime) : ViewHelper.getDisplayData(finishTime));
                type_tv.setText("处理中");
                content_tv.setText("物业人员已接单,正在耐心处理");
                break;
            case "3":
                time_tv.setText(null == finishTime ? ViewHelper.getDisplayData(reportTime) : ViewHelper.getDisplayData(finishTime));
                type_tv.setText("待评价");
                content_tv.setText("处理完成,对Ta的工作进行评价");
                Log.i(TAG, "initWorkOrderTree: evaluateComment" + evaluateComment);
                evaluate_tv.setVisibility(View.VISIBLE);
                evaluate_tv.setEnabled(evaluateComment == null);
                break;
            case "4":
                time_tv.setText(null == finishTime ? ViewHelper.getDisplayData(reportTime) : ViewHelper.getDisplayData(finishTime));
                type_tv.setText("已评价");
                content_tv.setText("谢谢您的评价,我们会努力做的更好");

                break;
        }
//        mWorkOrderDetailLvAdapter = new WorkOrderDetailLvAdapter(mContext, workOrderDetailInfos);
//        work_order_detail_lv.setAdapter(mWorkOrderDetailLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
//        mWorkOrderDetailLvAdapter.setOnChildViewClickListener(new WorkOrderDetailLvAdapter.OnChildViewClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, OrderEvaluateActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @OnClick({R.id.back_iv,R.id.evaluate_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.evaluate_tv:
                Intent intent = new Intent(mContext, OrderEvaluateActivity.class);
                intent.putExtra("order_id",order_id);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
//        workOrderDetailInfos.add(new WorkOrderDetailInfo("02-19 15:45", "已完成", "某某上门处理完毕"));
//        workOrderDetailInfos.add(new WorkOrderDetailInfo("02-19 08:30", "已接单", "某某某  即将上门处理"));
//        workOrderDetailInfos.add(new WorkOrderDetailInfo("02-18 20:30", "已派发", "安排某某某进行上门处理"));
//        workOrderDetailInfos.add(new WorkOrderDetailInfo("02-18 18:60", "已受理", "您已创建工单，请耐心等待物业人员接单"));
//        mWorkOrderDetailLvAdapter.notifyDataSetChanged();
        Log.i(TAG, "resident_id: " + AppPreference.I().getString("resident_id","")
                + "\n order_id: " + order_id
                + "\n workStatus: " + workStatus
                + "\n reportTime: " + reportTime
                + "\n finishTime: " + finishTime);
        OkHttpUtils.post()
                .url(Constant.GET_ORDER_DETAIL)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("order_id",order_id)
                .addParams("workStatus",workStatus)
                .addParams("reportTime",reportTime)
                .addParams("finishTime",finishTime == null ? "" : finishTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "工单详情数据: " + response);
                        WorkOrderDetailInfo info = GsonUtil.GsonToBean(response, WorkOrderDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            WorkOrderDetailInfo.DataBean.DataListBean dataList = info.getData().getDataList();
                            String reportPicture = dataList.getReportPicture();
                            String finishPhoto = dataList.getFinishPhoto();


                            if (!TextUtils.isEmpty(reportPicture)) {
                                String allReportPicture = reportPicture.replaceAll("/home/data/app", Constant.BASE_URL);
                                Log.i(TAG, "reportPicture: " + allReportPicture);
                                reportPictureList = allReportPicture.split(",");
                                mWorkOrderDetailRvAdapter = new WorkOrderDetailRvAdapter(mContext, reportPictureList);
                                work_order_detail_rv.setAdapter(mWorkOrderDetailRvAdapter);
                                mWorkOrderDetailRvAdapter.notifyDataSetChanged();
                            }

                            if (!TextUtils.isEmpty(finishPhoto)){
                                String allReportPicture = finishPhoto.replaceAll("/home/data/app", Constant.BASE_URL);
                                Log.i(TAG, "reportPicture: " + allReportPicture);
                                finishPictureList = allReportPicture.split(",");
                                mFinishRvAdapter = new WorkOrderDetailRvAdapter(mContext, finishPictureList);
                                finish_photo_rv.setAdapter(mFinishRvAdapter);
                                mFinishRvAdapter.notifyDataSetChanged();
                            }
                            //刷新 我的工单 数据
                            setTextView(dataList);
                        }

                    }
                });

    }

    private void setTextView(WorkOrderDetailInfo.DataBean.DataListBean dataList) {
        work_type_name_tv.setText(dataList.getWorktypeName());
        report_time_tv.setText(dataList.getReportTime());


        String expectTime = "";
        String eventAddr = dataList.getEventAddr();
        Log.i(TAG, "eventAddr: " + eventAddr);

        if (eventAddr.equals("null")) {
            String description = dataList.getDescription();
            expectTime = description.substring(description.length() - 23);
        }
        event_addr_tv.setText(eventAddr == null || eventAddr.equals("null") ? "" : eventAddr);
        expected_time_tv.setText(expectTime);


        if (workStatus.equals("4")) {
            line_view.setVisibility(View.VISIBLE);
            finish_person_ll.setVisibility(View.VISIBLE);
            finish_comment_ll.setVisibility(View.VISIBLE);
            finish_photo_rl.setVisibility(View.VISIBLE);
            finish_person_tv.setText(dataList.getFinishPeson());
            finish_comment_tv.setText(dataList.getFinishComment());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == 1234) {
            finish();
        }
    }

}
