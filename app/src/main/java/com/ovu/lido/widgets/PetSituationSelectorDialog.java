package com.ovu.lido.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.util.StringUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetSituationSelectorDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView ok;
    private TextView canel;
    private OnConfirmEvent mListener;
    private TextView big_dog_tv;
    private TextView cat_tv;
    private TextView small_dog_tv;
    private TextView other_tv;

    private String petStr;

    public PetSituationSelectorDialog(Context context, String petStr, OnConfirmEvent listener) {
        super(context,R.style.BottomDialogStyle);
        mListener = listener;
        mContext = context;
        this.petStr = petStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pet_situation_ll);
        //设置对话框的宽高
        Window window = getWindow();
        //设置弹出位置
        window.getAttributes().gravity = Gravity.BOTTOM;
        //设置对话框大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        ok = (TextView) findViewById(R.id.tv_dialog_ok);
        canel = (TextView) findViewById(R.id.tv_dialog_cancel);
        big_dog_tv = (TextView) findViewById(R.id.big_dog_tv);
        cat_tv = (TextView) findViewById(R.id.cat_tv);
        small_dog_tv = (TextView) findViewById(R.id.small_dog_tv);
        other_tv = (TextView) findViewById(R.id.other_tv);

        List<String> petList = Arrays.asList(petStr.split(","));
        for (int i = 0; i < petList.size(); i++) {
            String s = petList.get(i);
            switch (s){
                case "0":
                    big_dog_tv.setSelected(true);
                    break;
                case "1":
                    small_dog_tv.setSelected(true);
                    break;
                case "2":
                    cat_tv.setSelected(true);
                    break;
                case "3":
                    other_tv.setSelected(true);
                    break;
            }
        }


        ok.setOnClickListener(this);
        canel.setOnClickListener(this);
        big_dog_tv.setOnClickListener(this);
        cat_tv.setOnClickListener(this);
        small_dog_tv.setOnClickListener(this);
        other_tv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_dialog_cancel:
                mListener.onCancel();
                dismiss();
                break;
            case R.id.tv_dialog_ok:
                List<String> pets = new ArrayList<>();
                if (big_dog_tv.isSelected()) {
                    pets.add("0");
                }
                if (small_dog_tv.isSelected()) {
                    pets.add("1");
                }
                if (cat_tv.isSelected()) {
                    pets.add("2");
                }
                if (other_tv.isSelected()) {
                    pets.add("3");
                }
                mListener.onConfirm(StringUtil.listToString(pets,","));
                dismiss();
                break;
            case R.id.big_dog_tv:
                big_dog_tv.setSelected(!big_dog_tv.isSelected());
                break;
            case R.id.cat_tv:
                cat_tv.setSelected(!cat_tv.isSelected());
                break;
            case R.id.small_dog_tv:
                small_dog_tv.setSelected(!small_dog_tv.isSelected());
                break;
            case R.id.other_tv:
                other_tv.setSelected(!other_tv.isSelected());
                break;
            default:
                break;
        }
    }

    public interface OnConfirmEvent {
        void onCancel();

        void onConfirm(String pets);
    }
}
