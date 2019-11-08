package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.help.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class FaultTimeActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.ui_datepick)
    DatePicker ui_datepick;//日期选择器
    @BindView(R.id.select_time)
    TextView select_time;//上门时间段
    @BindView(R.id.select_time_layout)
    LinearLayout select_time_layout;
    private boolean isToday;
    private String time;// yyyy-mm-dd
    private SelectTimeDialog dialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fault_time;
    }

    @Override
    protected void initView() {
        super.initView();
        dialog = new SelectTimeDialog(mContext);
    }

    @OnClick({R.id.back_iv,R.id.select_time_layout,R.id.btn_ok})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.select_time_layout:
                dialog.show();
                break;
            case R.id.btn_ok:
                int y = ui_datepick.getYear();
                int m = ui_datepick.getMonth();// 月份从0天始
                int d = ui_datepick.getDayOfMonth();// 天从1开始
                Calendar calendar = new GregorianCalendar(y, m, d);
                Calendar today = new GregorianCalendar();
                if (calendar.before(today)) {
                    // 如果当前日期是今天以前的日期
                    calendar = today;
                    isToday = true;
                } else {
                    isToday = false;
                }
                y = calendar.get(Calendar.YEAR);
                m = calendar.get(Calendar.MONTH) + 1;
                d = calendar.get(Calendar.DAY_OF_MONTH);
                String mm = "";
                String dd = "";
                if (m < 10) {
                    mm = "0" + String.valueOf(m);
                } else {
                    mm = String.valueOf(m);
                }
                if (d < 10) {
                    dd = "0" + String.valueOf(d);
                } else {
                    dd = String.valueOf(d);
                }
                time = y + "-" + mm + "-" + dd;

                String time2 = select_time.getText().toString().trim();
                if (StringUtil.isEmpty(time2)) {
                    showShortToast("请选择时间段");
                    return;
                }

                Date date = new Date();
                Date afterDate = new Date(date.getTime()+900000);
                int currentHours = afterDate.getHours();
                int endHours = Integer.parseInt((time2.split("-")[0]).split(":")[0]);

                if(isToday && currentHours> endHours){
                    showShortToast("请选择正确的时间段");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("time", time + "  " + time2);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


    public class SelectTimeDialog extends Dialog {

        private Context mContext;
        private String times[] = { "09:00-10:00", "10:00-11:00", "11:00-12:00", "14:00-15:00", "15:00-16:00",
                "16:00-17:00" };

        public SelectTimeDialog(Context context) {
            super(context);
            mContext = context;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            Window window = getWindow();
            window.setBackgroundDrawableResource(R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.getAttributes().gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            setCanceledOnTouchOutside(true);

            setContentView(R.layout.dialog_select_time);
            ListView time_list = (ListView) findViewById(R.id.time_list);

            ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < times.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("itemName", times[i]);
                item.add(map);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, item, R.layout.select_time_item,
                    new String[] { "itemName" }, new int[] { R.id.time_item }) {
            };

            time_list.setAdapter(simpleAdapter);
            time_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    select_time.setText(times[arg2]);
                    dismiss();
                }
            });

        }
    }
}
