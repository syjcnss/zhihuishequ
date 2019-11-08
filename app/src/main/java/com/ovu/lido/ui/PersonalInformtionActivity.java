package com.ovu.lido.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 个人资料
 */
public class PersonalInformtionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.iv_person_detail_head)
    ImageView mIv_detailed_head;
    @BindView(R.id.et_detail_name)
    TextView mEt_name;
    @BindView(R.id.et_detail_nickname)
    EditText mEt_nickname;
    @BindView(R.id.et_job)
    EditText mEt_job;
    @BindView(R.id.et_hobby)
    EditText mEt_hobby;
    @BindView(R.id.et_detail_phone)
    TextView mEt_phone;
    @BindView(R.id.et_detail_sex)
    TextView mEt_sex;

    private List<LocalMedia> picList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private Map<String, File> map = new HashMap<>();
    private int themeId = R.style.picture_white_style;
    private Dialog mDialog;
    private String mNickName = "";
    private String mHumanName = "";
    private String mSex = "";
    private String mJob = "";
    private String mHobby = "";
    private String mSexId = "";


    @Override
    protected int setLayoutId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("nick_name");
        String sex = intent.getStringExtra("sex");
        String icon = intent.getStringExtra("icon");
        String profession = intent.getStringExtra("profession");
        String human_name = intent.getStringExtra("human_name");
        String interest = intent.getStringExtra("interest");
        RequestOptions options = new RequestOptions()
                .transform(new GlideCircleTransform(this))
                .error(R.drawable.default_head)
                .placeholder(R.drawable.default_head);
        Glide.with(this).load(icon).apply(options).into(mIv_detailed_head);
        mEt_phone.setText(AppPreference.I().getString("phoneNum",""));
        mEt_name.setText(human_name);
        mEt_nickname.setText(name);
        if (!TextUtils.isEmpty(profession)) {
            mEt_job.setText(profession);
        }
        if (!TextUtils.isEmpty(interest)) {
            mEt_hobby.setText(interest);
        }
        if (!TextUtils.isEmpty(sex)) {
            mEt_sex.setText(sex);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.ll_person_detail_head).setOnClickListener(this);
        findViewById(R.id.top_save).setOnClickListener(this);
        findViewById(R.id.ll_change_passwod).setOnClickListener(this);
        findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.et_detail_sex).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_person_detail_head:
                showAddDailog();
                break;
            case R.id.top_save:
                saveInformation();
                break;
            case R.id.ll_change_passwod:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.et_detail_sex:
                chooseSex();
                break;
        }

    }


    /**
     * 从相机获取
     */
    private void fromCamera() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create((Activity) mContext)
                .openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
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
     * 从相册获取
     */
    private void fromPhotoAlbum() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create((Activity) mContext)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
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
     * 显示添加图片路径对话框
     */
    private void showAddDailog() {
        new ActionSheetDialog(this)
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
     * 显示添加图片路径对话框
     */
    private void chooseSex() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mEt_sex.setText("男");
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mEt_sex.setText("女");

                            }
                        })
                .show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    picList = PictureSelector.obtainMultipleResult(data);
                    RequestOptions options = new RequestOptions().error(R.drawable.activity)
                            .placeholder(R.drawable.activity)
                            .transform(new GlideCircleTransform(this));
                    Glide.with(this)
                            .load(picList.get(0).getCompressPath())
                            .apply(options)
                            .into(mIv_detailed_head);
                    break;
            }
        }
    }

    private void saveInformation() {
        mNickName = mEt_nickname.getText().toString();
        mHumanName = mEt_name.getText().toString();
        mSex = mEt_sex.getText().toString();
        mJob = mEt_job.getText().toString();
        mHobby = mEt_hobby.getText().toString();
        if (TextUtils.isEmpty(mNickName)) {
            showToast("昵称不能为空");
            return;
        }
        if (mNickName.length() < 2) {
            showToast("昵称长度不能小于2位");
            return;
        }
        if (TextUtils.isEmpty(mHumanName)) {
            showToast("姓名不能为空");
            return;
        }
//        if (mSex.equals("男")) {
//            mSexId = "1";
//        } else if (mSex.equals("女")) {
//            mSexId = "2";
//        } else {
//            mSexId = "";
//        }
        map.clear();
        mDialog.show();
        if (picList.size() > 0) {
            map.put(System.currentTimeMillis() + "icon", new File(picList.get(0).getCompressPath()));
            updateInforWithImg();
        } else {
            updateInforNoImg();
        }

    }


    /**
     * 更改信息（头像）
     */
    private void updateInforWithImg() {
        OkHttpUtils.post()
                .url(HttpAddress.MODIFY_RESIDENT_BASEINFOS)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id",  AppPreference.I().getString("resident_id",""))
                .addParams("nick_name", mNickName)
                .addParams("human_name", mHumanName)
                .addParams("sex", mSex)
                .addParams("profession", mJob)
                .addParams("interest", mHobby)
                .files("icon", map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(PersonalInformtionActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            finish();
                        }else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });

    }

    /**
     * 更改信息（不修改头像）
     */
    private void updateInforNoImg() {
        OkHttpUtils.post()
                .url(HttpAddress.MODIFY_RESIDENT_BASEINFO)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id",  AppPreference.I().getString("resident_id",""))
                .addParams("nick_name", mNickName)
                .addParams("human_name", mHumanName)
                .addParams("sex", mSex)
                .addParams("profession", mJob)
                .addParams("interest", mHobby)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG + "信息呢", response + TAG);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(PersonalInformtionActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            finish();
                        }else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
