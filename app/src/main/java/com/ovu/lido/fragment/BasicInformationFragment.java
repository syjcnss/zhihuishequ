package com.ovu.lido.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.adapter.CarListAdapter;
import com.ovu.lido.adapter.FamilyMemberListAdapter;
import com.ovu.lido.bean.CarInfo;
import com.ovu.lido.bean.FamilyMemberInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.ui.AddCarActivity;
import com.ovu.lido.ui.AddFamilyMemberActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.CircleImageView;
import com.ovu.lido.widgets.ListViewForScrollView;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
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
 * 入伙管理--基本资料
 */
public class BasicInformationFragment extends Fragment {
    @BindView(R.id.iv_user_icon)
    CircleImageView iv_user_icon;
    @BindView(R.id.edit_user_name)
    EditText edit_user_name;//姓名
    @BindView(R.id.edit_user_sex)
    TextView text_user_sex;//性别
    @BindView(R.id.edit_user_tel)
    EditText edit_user_tel;//电话
    @BindView(R.id.edit_user_code)
    EditText edit_user_code;//身份证号
    @BindView(R.id.edit_user_address)
    EditText edit_user_address;//园内住址
    @BindView(R.id.edit_user_area)
    EditText edit_user_area;//建筑面积
    @BindView(R.id.edit_user_workspace)
    EditText edit_user_workspace;//工作单位
    @BindView(R.id.edit_contact_phone)
    EditText edit_contact_phone;//紧急联系人

    @BindView(R.id.family_layout)
    ListViewForScrollView family_layout;//家庭成员
    @BindView(R.id.car_layout)
    ListViewForScrollView car_layout;//车辆情况


    public static final String TAG = "wangw";

    private Context mContext;
    private Unbinder unbinder;
    private int themeId = R.style.picture_white_style;
    private List<LocalMedia> picList = new ArrayList<>();
    private int selectMax = 1;
    private Map<String, File> picPath;
    private String userName;
    private String userSex;
    private String userTel;
    private String userCode;
    private String userAddress;
    private String userArea;
    private String userWorkspace;
    private String contactPhone;
    private Dialog mDialog;

    private List<FamilyMemberInfo> familyMemberInfos = new ArrayList<>();
    private List<CarInfo> carInfos = new ArrayList<>();
    private FamilyMemberListAdapter familyMemberListAdapter;
    private CarListAdapter carListAdapter;

    public BasicInformationFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        return view;
    }

    private void initView() {
        familyMemberListAdapter = new FamilyMemberListAdapter(mContext, familyMemberInfos);
        family_layout.setAdapter(familyMemberListAdapter);

        carListAdapter = new CarListAdapter(mContext, carInfos);
        car_layout.setAdapter(carListAdapter);
    }

    private void setListener() {
        familyMemberListAdapter.setmOnDeleteBtnClickListener(new FamilyMemberListAdapter.OnDeleteBtnClickListener() {
            @Override
            public void onClick(View view, int position) {
                familyMemberInfos.remove(position);
                familyMemberListAdapter.notifyDataSetChanged();
            }
        });

        carListAdapter.setmOnDeleteBtnClickListener(new CarListAdapter.OnDeleteBtnClickListener() {
            @Override
            public void onClick(View view, int position) {
                carInfos.remove(position);
                carListAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.change_photos_iv, R.id.edit_user_sex, R.id.btn_add_family, R.id.btn_add_car, R.id.btn_save})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.change_photos_iv:
                showAddDailog();
                break;
            case R.id.edit_user_sex:
                showSexDailog();
                break;
            case R.id.btn_add_family:
                int memberInfoSize = familyMemberInfos == null ? 0 : familyMemberInfos.size();
                if (memberInfoSize < 7) {
                intent.setClass(mContext, AddFamilyMemberActivity.class);
                startActivityForResult(intent, 1);
                }else {
                    ToastUtil.show(mContext,"家庭成员最多添加7人");
                }
                break;
            case R.id.btn_add_car:
                intent.setClass(mContext, AddCarActivity.class);
                startActivityForResult(intent,2);
                break;
            case R.id.btn_save:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        userName = edit_user_name.getText().toString().trim();//姓名
        userSex = text_user_sex.getText().toString().trim();//性别
        userTel = edit_user_tel.getText().toString().trim();//电话
        userCode = edit_user_code.getText().toString().trim();//身份证号
        userAddress = edit_user_address.getText().toString().trim();//园内住址
        userArea = edit_user_area.getText().toString().trim();//建筑面积
        userWorkspace = edit_user_workspace.getText().toString().trim();//工作单位
        contactPhone = edit_contact_phone.getText().toString().trim();//紧急联系人
        if (picList == null || picList.isEmpty()){
            ToastUtil.show(mContext,"请选择业主图片");
        }else if (TextUtils.isEmpty(userName)){
           ToastUtil.show(mContext,"请输入姓名");
        }else if (TextUtils.isEmpty(userSex)){
            ToastUtil.show(mContext,"请选择性别");
        }else if (TextUtils.isEmpty(userTel)){
            ToastUtil.show(mContext,"请输入电话号码");
        }else if (TextUtils.isEmpty(userCode)){
            ToastUtil.show(mContext,"请输入身份证号");
        }else if (TextUtils.isEmpty(userAddress)){
            ToastUtil.show(mContext,"请输入园内住址");
        }else if (TextUtils.isEmpty(userArea)){
            ToastUtil.show(mContext,"请输入建筑面积");
        }else if (TextUtils.isEmpty(userWorkspace)){
            ToastUtil.show(mContext,"请输入工作单位");
        }else if (TextUtils.isEmpty(contactPhone)){
            ToastUtil.show(mContext,"请输入紧急联系人");
        }else {
            //上传入伙资料
            upLoadData();
        }
    }

    private void upLoadData() {
        mDialog.show();
        Map<String, Object> params = getStringObjectMap();
        OkHttpUtils.post()
                .url(Constant.SAVE_JION_TO_OVE)
                .files("imgs",picPath)
                .addParams("params", GsonUtil.ToGson(params))
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
                        Log.i(TAG, "onResponse: ");
                        LoadProgressDialog.closeDialog(mDialog);

                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            ToastUtil.show(mContext,"保存成功");
                            ((Activity)mContext).finish();
                        }else {
                            ToastUtil.show(mContext,info.getErrorMsg());
                        }
                    }
                });
    }

    @NonNull
    private Map<String, Object> getStringObjectMap() {
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        paramsMap.put("resident_name",userName);
        paramsMap.put("mobile",userTel);
        paramsMap.put("sex",userSex.equals("男") ? "1" : "2");
        paramsMap.put("id_card",userCode);
        paramsMap.put("address",userAddress);
        paramsMap.put("covered_area",userArea);
        paramsMap.put("work_unit",userWorkspace);
        paramsMap.put("contact_phone",contactPhone);
        Map<String,Object> params = new HashMap<>();
        params.put("data",paramsMap);
        params.put("devicePhoneNo","");
        return params;
    }

    /**
     * 显示添加图片路径对话框
     */
    private void showSexDailog() {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                text_user_sex.setText("男");
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                text_user_sex.setText("女");
                            }
                        })
                .show();
    }


    /**
     * 显示添加图片路径对话框
     */
    private void showAddDailog() {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                fromPhotoAlbum();
                            }
                        })
                .addSheetItem("拍摄", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                fromCamera();
                            }
                        })
                .show();
    }


    /**
     * 从相机获取
     */
    private void fromCamera() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(BasicInformationFragment.this)
                .openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(selectMax)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .compress(true)// 是否压缩 true or false
                .openClickSound(true)// 是否开启点击声音 true or false
//                .selectionMedia(picList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 从相册获取
     */
    private void fromPhotoAlbum() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(BasicInformationFragment.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(selectMax)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .compress(true)// 是否压缩 true or false
                .openClickSound(true)// 是否开启点击声音 true or false
//                .selectionMedia(picList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }


        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    picList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "onActivityResult: " + picList.size());
                    //media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    picPath = new HashMap<>();
                    for (int i = 0; i < picList.size(); i++) {
                        String compressPath = picList.get(i).getCompressPath();
                        String arg[] = compressPath.split("/");
                        String fileName = arg[arg.length-1];
                        if (i == 0) {
                            File file = new File(compressPath);
                            picPath.put(fileName,file);
                        } else {
                            File file = new File(compressPath);
                            picPath.put(fileName,file);
                        }
                        setImageView(compressPath);
                    }
                    Log.i(TAG, "picPath is ----------" + picPath);
                    break;
                case 1:
                    FamilyMemberInfo familyMemberInfo = (FamilyMemberInfo) data.getSerializableExtra("familyMemberInfo");
                    if (familyMemberInfo == null) return;
                    Log.i(TAG, "家庭成员数据: " + familyMemberInfo.toString() + "\n" + familyMemberInfo.getFamilyMemberName());
                    familyMemberInfos.add(familyMemberInfo);
                    familyMemberListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    CarInfo carInfo = (CarInfo) data.getSerializableExtra("carInfo");
                    if (carInfo == null) return;
                    Log.i(TAG, "车辆数据: " + carInfo.toString() + "\n number:" + carInfo.getCarNumber() + "\n color:" + carInfo.getCarColor());
                    carInfos.add(carInfo);
                    carListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * 填充照片头像
     *
     * @param path
     */
    private void setImageView(String path) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_error)
                .error(R.drawable.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(path)
                .apply(options)
                .into(iv_user_icon);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        unbinder.unbind();
    }
    
}

