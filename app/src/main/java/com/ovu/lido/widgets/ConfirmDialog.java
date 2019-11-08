package com.ovu.lido.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ovu.lido.R;

public class ConfirmDialog extends Dialog implements android.view.View.OnClickListener {
    private Context mContext;
    private TextView tv_confirm_text;
    private TextView ok;
    private TextView canel;
    private TextView tv_confirm_title;
    private OnConfirmEvent mListener;

    public ConfirmDialog(Context context, OnConfirmEvent listener) {
        super(context);
        mListener = listener;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_ll);
        //设置对话框的宽高
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        tv_confirm_text = (TextView) findViewById(R.id.tv_confirm_text);
        ok = (TextView) findViewById(R.id.tv_dialog_ok);
        canel = (TextView) findViewById(R.id.tv_dialog_cancel);
        tv_confirm_title = (TextView) findViewById(R.id.tv_confirm_title);
        ok.setOnClickListener(this);
        canel.setOnClickListener(this);
    }

    public void setTitleText(String s) {
        if (!TextUtils.isEmpty(s)) {
            tv_confirm_title.setText(s);
            tv_confirm_title.setVisibility(View.VISIBLE);
        }
    }

    public void setOkText(String str) {
        ok.setText(str);
    }

    public void setOkTextColor(int color) {
        ok.setTextColor(color);
    }

    public void setCancelVisible(int flag) {
        canel.setVisibility(flag);
    }

    public void setCancelColor(int color) {
        canel.setTextColor(mContext.getResources().getColor(color));

    }

    public void setCancelText(String str) {
        canel.setText(str);
    }

    public void setContentText(String text) {
        tv_confirm_text.setText(text);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_dialog_cancel:
                mListener.onCancel();
                break;
            case R.id.tv_dialog_ok:
                mListener.onConfirm();
                break;
            default:
                break;
        }
    }

    public interface OnConfirmEvent {
        void onCancel();

        void onConfirm();
    }
}
