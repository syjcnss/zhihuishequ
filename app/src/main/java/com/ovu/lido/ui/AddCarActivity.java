package com.ovu.lido.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CarInfo;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCarActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.car_name_et)
    EditText car_name_et; //车主姓名
    @BindView(R.id.car_number_et)
    EditText car_number_et; //拍照号码
    @BindView(R.id.car_kind_et)
    EditText car_kind_et; //车辆种类
    @BindView(R.id.car_model_et)
    EditText car_model_et; //车辆型号
    @BindView(R.id.car_brands_et)
    EditText car_brands_et; //车辆品牌
    @BindView(R.id.car_color_et)
    EditText car_color_et; //车辆颜色
    private String car_name;
    private String car_number;
    private String car_kind;
    private String car_model;
    private String car_brands;
    private String car_color;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_car;
    }

    @OnClick({R.id.back_iv,R.id.save_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_tv:
                //TODO 保存
                doSubmit();
                break;

        }
    }

    private void doSubmit() {
        car_name = car_name_et.getText().toString().trim();
        car_number = car_number_et.getText().toString().trim();
        car_kind = car_kind_et.getText().toString().trim();
        car_model = car_model_et.getText().toString().trim();
        car_brands = car_brands_et.getText().toString().trim();
        car_color = car_color_et.getText().toString().trim();
        if (TextUtils.isEmpty(car_name)){
            ToastUtil.show(mContext,"请输入姓名");
        }else if (TextUtils.isEmpty(car_number)){
            ToastUtil.show(mContext,"请输入牌照号码");
        }
//        else if (TextUtils.isEmpty(car_kind)){
//            ToastUtil.show(mContext,"请输入车辆种类");
//        }else if (TextUtils.isEmpty(car_model)){
//            ToastUtil.show(mContext,"请输入车辆型号");
//        }else if (TextUtils.isEmpty(car_brands)){
//            ToastUtil.show(mContext,"请输入车辆品牌");
//        }else if (TextUtils.isEmpty(car_color)){
//            ToastUtil.show(mContext,"请输入车辆颜色");
//        }
        else{
            //添加成员
            addCar();
        }
    }

    private void addCar() {
//        CarInfo(String carName, String carNumber, String carKind, String carModel, String carBrand, String carColor)
        CarInfo info = new CarInfo(car_name, car_number, car_kind, car_model, car_brands, car_color);
        Intent intent = new Intent();
        intent.putExtra("carInfo",info);
        setResult(RESULT_OK,intent);
        finish();
        ViewHelper.hideSoftInput(this);
    }
}
