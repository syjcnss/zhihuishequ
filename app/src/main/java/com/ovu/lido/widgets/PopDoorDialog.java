package com.ovu.lido.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.ovu.lido.R;

public class PopDoorDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    public PopDoorDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    public interface OnDoorDialogClickListener {
        public void click(int index);
    }

    private OnDoorDialogClickListener onDoorDialogClickListener;

    public void setOnDoorDialogClickListener(OnDoorDialogClickListener onDoorDialogClickListener) {
        this.onDoorDialogClickListener = onDoorDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_click_door);
        Window window = getWindow();
        window.getAttributes().gravity = Gravity.CENTER;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;
        window.setLayout((int) (screenW * 0.9), screenH * 2 / 5);
        window.setBackgroundDrawableResource(R.color.transparent);

        initView();
    }

    private void initView() {
        Button btn_open = findViewById(R.id.btn_open);
        Button btn_invate = findViewById(R.id.btn_invate);
        ImageButton btn_closed = findViewById(R.id.btn_closed);

        btn_open.setOnClickListener(this);
        btn_invate.setOnClickListener(this);
        btn_closed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_closed:
                dismiss();
                break;
            case R.id.btn_open:
                if (null != onDoorDialogClickListener)
                    onDoorDialogClickListener.click(0);
                break;
            case R.id.btn_invate:
                if (null != onDoorDialogClickListener)
                    onDoorDialogClickListener.click(1);
                break;
            default:
                break;
        }
    }

}
