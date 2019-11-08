package com.ovu.lido.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.adapter.PicRvAdapter;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.ui.FaultTimeActivity;
import com.ovu.lido.ui.FaultTypeActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.ActionSheetDialog.OnSheetItemClickListener;
import com.ovu.lido.widgets.ActionSheetDialog.SheetItemColor;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 家庭报修
 */
public class FamilyRepairsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.fault_name_tv)
    TextView fault_name_tv;
    @BindView(R.id.fault_date_time)
    TextView fault_date_time;
    @BindView(R.id.describe_et)
    EditText describe_et;
    @BindView(R.id.pic_rv)
    RecyclerView pic_rv;

    public static final String TAG = "wangw";

    private Context mContext;
    private Unbinder unbinder;

    private static final int SELECT_FAULT_TYPE = 0;
    private static final int SELECT_FAULT_TIME = 1;
    private PicRvAdapter mPicRvAdapter;
    private int selectMax = 9;
    private List<LocalMedia> picList = new ArrayList<>();
    private int  themeId = R.style.picture_white_style;
    private Map<String,File> picPath = null;
    private String fault_id;

    private Dialog mDialog;
    private String path_prefix;
    private Uri tempUri;
    private String origAbsPath;
    private Uri origUri;
    private String mPictureFile;
    private String filePath;

    public FamilyRepairsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectFileUriExposure();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //绑定控件
        View view = inflater.inflate(R.layout.fragment_family_repairs, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        return view;
    }


    private void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        pic_rv.setLayoutManager(layoutManager);
        //问题2、ScrollView嵌套RecyclerView后滑动很缓慢，不流畅，没有惯性
        pic_rv.setHasFixedSize(true);
        pic_rv.setNestedScrollingEnabled(false);
        mPicRvAdapter = new PicRvAdapter(mContext, selectMax);
        mPicRvAdapter.setList(picList);
        pic_rv.setAdapter(mPicRvAdapter);
    }


    private void setListener() {
        mPicRvAdapter.setOnItemClickListener(new PicRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (picList.size() > 0){
                    LocalMedia media = picList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    if (mediaType == 1){
                        PictureSelector.create(FamilyRepairsFragment.this).themeStyle(themeId).openExternalPreview(position, picList);
                    }
                }


            }
        });

        mPicRvAdapter.setOnAddPicClickListener(new PicRvAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (hasPermission(Manifest.permission.CAMERA)) {
                    showAddDailog();
                }else {
                    requestPermission(Constant.CAMERA_PERMISSION, Manifest.permission.CAMERA);
                }
            }
        });
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        requestPermissions(permissions, code);
    }

    /**
     * 显示添加图片路径对话框
     */
    private void showAddDailog() {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("从手机相册选择", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                fromPhotoAlbum();
                            }
                        })
                .addSheetItem("拍摄", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                fromCamera();
//                                startTakePhoto();
                            }
                        })
                .show();
    }


    /**
     * 调用系统相机拍照
     */
    private void startTakePhoto() {
        // 调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        // 取当前时间为照片名
        mPictureFile = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".png";

        Log.d("onactivity", "mPictureFile：" + mPictureFile);

        filePath = getPhotoPath() + mPictureFile;
        Log.e(TAG, "filePath: " + filePath);

        // 通过文件创建一个uri中
        Uri imageUri = Uri.fromFile(new File(filePath));
        Log.e(TAG, "imageUri: " + imageUri);

        // 保存uri对应的照片于指定路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, Constant.TAKE_PHOTO);

    }

    /**
     * 获得照片路径
     *
     * @return
     */
    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/";
    }

    /**
     * 从相册获取
     */
    private void fromPhotoAlbum() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(FamilyRepairsFragment.this)
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
                .selectionMedia(picList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    /**
     * 从相机获取
     */
    private void fromCamera() {
        // 单独拍照
        PictureSelector.create(FamilyRepairsFragment.this)
                .openCamera(PictureMimeType.ofImage())
                .theme(themeId)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(160,160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片 true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .selectionMedia(picList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @OnClick({R.id.fault_name_tv, R.id.fault_date_time, R.id.submit_btn})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.fault_name_tv:
                intent.setClass(mContext,FaultTypeActivity.class);
                //服务类别 0:家庭报修 1:公共区域 2:小区报事
                intent.putExtra("service_type", 0);
                startActivityForResult(intent, SELECT_FAULT_TYPE);
                break;
            case R.id.fault_date_time:
                intent.setClass(mContext,FaultTimeActivity.class);
                startActivityForResult(intent, SELECT_FAULT_TIME);
                break;
            case R.id.submit_btn://提交申请
                doSubmit();
                break;

        }
    }

    /**
     * 处理提交按钮
     */
    private void doSubmit() {
        String name = fault_name_tv.getText().toString().trim();
        String time = fault_date_time.getText().toString().trim();
        String describe = describe_et.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(fault_id)){
            showShortToast("请选择故障类型");
        }else if (TextUtils.isEmpty(time)){
            showShortToast("请选择上门时间");
        }else if (TextUtils.isEmpty(describe)){
            showShortToast("请填写描述信息");
        }else if (picPath == null){
            upLoadNoImg(time,describe);
        }else {
            upLoad(time,describe);
        }
    }

    /**
     *
     * 提交报修信息
     *
     * @param time 上门时间
     * @param describe 维保详情
     */
    private void upLoad(String time, String describe) {
        Log.i(TAG, "图片文件地址: " + picPath.toString());
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.SUBMIT_FAMILY_REPAIRS)
                .files("img_list",picPath)
                .addParams("order_type","5")
                .addParams("type_id",fault_id)
                .addParams("accpet_time",time)
                .addParams("descripte",describe + " -- " + time)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("token", AppPreference.I().getString("token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "提交报修信息: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            showShortToast("提交成功");
                            ((Activity)mContext).finish();
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     *
     * 提交报修信息
     *
     * @param time 上门时间
     * @param describe 维保详情
     */
    private void upLoadNoImg(String time, String describe) {
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.SUBMIT_FAMILY_REPAIRS_NOIMG)
                .addParams("order_type","5")
                .addParams("type_id",fault_id)
                .addParams("accpet_time",time)
                .addParams("descripte",describe + " -- " + time)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .addParams("token", AppPreference.I().getString("token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "提交报修信息: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            showShortToast("提交成功");
                            ((Activity)mContext).finish();
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }


    private void showShortToast(String str) {
        Toast.makeText(mContext,str, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case SELECT_FAULT_TYPE:
                    String fault_name = data.getStringExtra("fault_name");
                    fault_name_tv.setText(fault_name);
                    fault_id = data.getStringExtra("fault_id");
                    break;
                case SELECT_FAULT_TIME:
                    String time = data.getStringExtra("time");
                    fault_date_time.setText(time);
                    break;
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
                    }
                    Log.i(TAG, "picPath is ----------" + picPath);
                    mPicRvAdapter.setList(picList);
                    mPicRvAdapter.notifyDataSetChanged();
                    break;
                case  Constant.TAKE_PHOTO:
                    picPath = new HashMap<>();
                    Log.e("takePhoto", filePath); //  filePath = "/storage/emulated/0/DCIM/20180902_072018.jpg"
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    if (bitmap == null) {
                        ToastUtil.show(mContext,"获取图片失败");
                        return;
                    }

                    String arg[] = filePath.split("/");
                    String fileName = arg[arg.length-1];
                    File file = new File(filePath);
                    picPath.put(fileName,file);
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setCompressPath(filePath);
                    localMedia.setCompressed(true);
                    localMedia.setChecked(true);
                    localMedia.setPath(filePath);
                    picList.add(localMedia);
                    mPicRvAdapter.setList(picList);
                    mPicRvAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
