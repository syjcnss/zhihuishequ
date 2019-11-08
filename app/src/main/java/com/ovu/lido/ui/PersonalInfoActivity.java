package com.ovu.lido.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MyPersonalBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.JPushHelper;
import com.ovu.lido.util.StringUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.CommonAction;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.ovu.lido.widgets.PetSituationSelectorDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class PersonalInfoActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.icon_iv)
    ImageView icon_iv;
    @BindView(R.id.human_name_tv)
    TextView human_name_tv;
    @BindView(R.id.nick_name_et)
    EditText nick_name_et;
    @BindView(R.id.sex_ll)
    LinearLayout sex_ll;
    @BindView(R.id.sex_tv)
    TextView sex_tv;
    @BindView(R.id.sex_line)
    View sex_line;
    @BindView(R.id.mobile_no_tv)
    TextView mobile_no_tv;
    @BindView(R.id.minorities_ll)
    LinearLayout minorities_ll;
    @BindView(R.id.minorities_et)
    EditText minorities_et;
    @BindView(R.id.minorities_line)
    View minorities_line;
    @BindView(R.id.id_card_ll)
    LinearLayout id_card_ll;
    @BindView(R.id.id_card_no_et)
    EditText id_card_no_et;
    @BindView(R.id.id_card_line)
    View id_card_line;
    @BindView(R.id.native_pace_ll)
    LinearLayout native_pace_ll;
    @BindView(R.id.native_pace_et)
    EditText native_pace_et;
    @BindView(R.id.native_pace_line)
    View native_pace_line;
    @BindView(R.id.political_appearance_ll)
    LinearLayout political_appearance_ll;
    @BindView(R.id.political_appearance_tv)
    TextView political_appearance_tv;
    @BindView(R.id.political_appearance_line)
    View political_appearance_line;
    @BindView(R.id.family_situation_tv)
    TextView family_situation_tv;
    @BindView(R.id.family_situation_line)
    View family_situation_line;
    @BindView(R.id.car_situation_tv)
    TextView car_situation_tv;
    @BindView(R.id.car_situation_line)
    View car_situation_line;
    @BindView(R.id.pet_situation_ll)
    LinearLayout pet_situation_ll;
    @BindView(R.id.pet_situation_tv)
    TextView pet_situation_tv;
    @BindView(R.id.pet_situation_line)
    View pet_situation_line;

    private int themeId = R.style.picture_white_style;
    private MyPersonalBean mPersonalBean;

    private String mPetSituation;//宠物情况（0：大型犬，1：小型犬，2：猫，3：其他）
    private String mIconUrl;//头像地址
    private String mHumanName;//用户名
    private String mNickName;//昵称
    private String mSex;//性别
    private String mPhoneNum;//电话
    private String mMinorities;//名族
    private String mIdCardNo;//身份证号
    private String mNativePace;//籍贯
    private String mPoliticalAppearance;//政治面貌（0：党员，1：团员，2：群众）

    private Dialog mDialog;
    private File mFile;
    private int mIdentity;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_personal_info;

    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
    }

    @Override
    protected void loadData() {
        super.loadData();

        mPersonalBean = (MyPersonalBean) getIntent().getSerializableExtra("MyPersonalBean");
        mIconUrl = mPersonalBean.getIcon_url();
        mHumanName = mPersonalBean.getHuman_name();
        mNickName = mPersonalBean.getNick_name();
        mSex = mPersonalBean.getSex();
        mPhoneNum = AppPreference.I().getString("phoneNum", "");
        mMinorities = mPersonalBean.getMinorities();
        mIdCardNo = mPersonalBean.getId_card_no();
        mNativePace = mPersonalBean.getNative_pace();
        mPoliticalAppearance = mPersonalBean.getPolitical_appearance();
        mPetSituation = mPersonalBean.getPet_situation();
        mIdentity = mPersonalBean.getIdentity();

        refreshView();
    }

    /**
     * 刷新界面
     */
    private void refreshView() {
        if (isFinishing()) return;
//        if (mIdentity == 1){
            //性别
            sex_ll.setVisibility(mIdentity == 1 ? View.VISIBLE : View.GONE);
            sex_line.setVisibility(mIdentity == 1 ? View.VISIBLE : View.GONE);
            //名族
            minorities_ll.setVisibility(mIdentity == 1 ? View.VISIBLE : View.GONE);
            minorities_line.setVisibility(mIdentity == 1 ? View.VISIBLE : View.GONE);
            //身份证
            id_card_ll.setVisibility(View.GONE);
            id_card_line.setVisibility(View.GONE);
            //籍贯
            native_pace_ll.setVisibility(View.GONE);
            native_pace_line.setVisibility(View.GONE);
            //政治面貌
            political_appearance_ll.setVisibility(View.GONE);
            political_appearance_line.setVisibility(View.GONE);
            //家庭情况
            family_situation_tv.setVisibility(View.GONE);
            family_situation_line.setVisibility(View.GONE);
            //车辆情况
            car_situation_tv.setVisibility(View.GONE);
            car_situation_line.setVisibility(View.GONE);
            //宠物情况
            pet_situation_ll.setVisibility(View.GONE);
            pet_situation_line.setVisibility(View.GONE);
//        }else {
//            //性别
//            sex_ll.setVisibility(View.GONE);
//            sex_line.setVisibility(View.GONE);
//            //名族
//            minorities_ll.setVisibility(View.GONE);
//            minorities_line.setVisibility(View.GONE);
//            //身份证
//            id_card_ll.setVisibility(View.GONE);
//            id_card_line.setVisibility(View.GONE);
//            //籍贯
//            native_pace_ll.setVisibility(View.GONE);
//            native_pace_line.setVisibility(View.GONE);
//            //政治面貌
//            political_appearance_ll.setVisibility(View.GONE);
//            political_appearance_line.setVisibility(View.GONE);
//            //家庭情况
//            family_situation_tv.setVisibility(View.GONE);
//            family_situation_line.setVisibility(View.GONE);
//            //车辆情况
//            car_situation_tv.setVisibility(View.GONE);
//            car_situation_line.setVisibility(View.GONE);
//            //宠物情况
//            pet_situation_ll.setVisibility(View.GONE);
//            pet_situation_line.setVisibility(View.GONE);
//
//        }

        if (!TextUtils.isEmpty(mIconUrl)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mFile = Glide.with(mContext).asFile().load(Constant.IMG_CONFIG + mIconUrl).submit().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Glide.with(mContext)
                    .load(Constant.IMG_CONFIG + mIconUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.default_icon).error(R.drawable.default_icon).transform(new GlideCircleTransform(this)))
                    .into(icon_iv);
        }

        if (!TextUtils.isEmpty(mHumanName)) {
            human_name_tv.setText(mHumanName);
        }
        if (!TextUtils.isEmpty(mNickName)) {
            nick_name_et.setText(mNickName);
        }
        if (!TextUtils.isEmpty(mSex)) {
            sex_tv.setText(mSex);
        }
        if (!TextUtils.isEmpty(mPhoneNum)) {
            mobile_no_tv.setText(mPhoneNum);
        }
        if (!TextUtils.isEmpty(mMinorities)) {
            minorities_et.setText(mMinorities);
        }
        if (!TextUtils.isEmpty(mIdCardNo)) {
            id_card_no_et.setText(mIdCardNo);
        }
        if (!TextUtils.isEmpty(mNativePace)) {
            native_pace_et.setText(mNativePace);
        }
        if (!TextUtils.isEmpty(mPoliticalAppearance)) {
            if (TextUtils.equals(mPoliticalAppearance, "0")) {
                political_appearance_tv.setText("党员");
            } else if (TextUtils.equals(mPoliticalAppearance, "1")) {
                political_appearance_tv.setText("团员");
            } else if (TextUtils.equals(mPoliticalAppearance, "2")) {
                political_appearance_tv.setText("群众");
            }
        }


        if (!TextUtils.isEmpty(mPetSituation)) {
            String petName = StringUtil.getPetName(mPetSituation, ",");
            pet_situation_tv.setText(petName);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();

        nick_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNickName = s.toString();
            }
        });

        minorities_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mMinorities = s.toString();
//                if (!TextUtils.isEmpty(mMinorities)) {
//                    minorities_et.setText(mMinorities);
//                }
            }
        });

        id_card_no_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIdCardNo = s.toString();
            }
        });

        native_pace_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNativePace = s.toString();
//                if (!TextUtils.isEmpty(mNativePace)) {
//                    native_pace_et.setText(mNativePace);
//                }
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.save_tv, R.id.icon_iv, R.id.sex_ll, R.id.political_appearance_ll, R.id.family_situation_tv,
            R.id.car_situation_tv, R.id.pet_situation_ll, R.id.change_password_tv, R.id.log_out_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_tv://保存
                savePersonalInfo();
                break;
            case R.id.icon_iv://头像
                chooseIcon();
                break;
            case R.id.sex_ll://性别
                chooseSex();
                break;
            case R.id.political_appearance_ll://政治面貌
                choosePoliticalAppearance();
                break;
            case R.id.family_situation_tv://家庭情况
                startActivity(new Intent(mContext, FamilySituationActivity.class));
                break;
            case R.id.car_situation_tv://车辆情况
                startActivity(new Intent(mContext, EditResidentCarActivity.class));
                break;
            case R.id.pet_situation_ll://宠物情况
                choosePet();
                break;
            case R.id.change_password_tv://修改密码
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.log_out_btn:
                startActivity(new Intent(this, EnterMethodActivity.class));
                AppPreference.I().putString("password", "");
                JPushHelper.delAlias(mContext,AppPreference.I().getString("resident_id",""));
                CommonAction.getInstance().OutSign();
                break;

        }
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id){
        if (id.toUpperCase().matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 保存用户信息
     */
    private void savePersonalInfo() {
        //检查信息完整性
        if (!isInformationIsComplete()) return;

        mDialog.show();
        //提交信息到后台
        if (mFile == null) {
            uploadNoIcon();
        }else {
            uploadWithIcon();
        }
    }

    private void uploadNoIcon() {
        OkGo.<String>post(Constant.MODIFY_RESIDENT_BASE_INFO)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("human_name", mHumanName)//用户名
                .params("nick_name", mNickName)//昵称
                .params("sex", mSex)//性别
                .params("minorities", mMinorities)// 民族
                .params("id_card_no", mIdCardNo)//身份证号码
                .params("native_pace", mNativePace)//籍贯
                .params("political_appearance", mPoliticalAppearance)// 政治面貌（0：党员，1：团员，2：群众）
                .params("pet_situation", mPetSituation)//宠物情况（0：大型犬，1：小型犬，2：猫，3：其他）
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            finish();
                        } else if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            showToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (!isFinishing()) LoadProgressDialog.closeDialog(mDialog);
                    }
                });
    }

    private void uploadWithIcon() {
        OkGo.<String>post(Constant.MODIFY_RESIDENT_BASEINFOS)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("human_name", mHumanName)//用户名
                .params("nick_name", mNickName)//昵称
                .params("sex", mSex)//性别
                .params("minorities", mMinorities)// 民族
                .params("id_card_no", mIdCardNo)//身份证号码
                .params("native_pace", mNativePace)//籍贯
                .params("political_appearance", mPoliticalAppearance)// 政治面貌（0：党员，1：团员，2：群众）
                .params("pet_situation", mPetSituation)//宠物情况（0：大型犬，1：小型犬，2：猫，3：其他）
                .params("icon",mFile,System.currentTimeMillis() + "icon")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            finish();
                        } else if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            showToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (!isFinishing()) LoadProgressDialog.closeDialog(mDialog);
                    }
                });
    }

    /**
     * 检查信息完整性
     */
    private boolean isInformationIsComplete() {
        boolean isComplete = false;
        if (TextUtils.isEmpty(mNickName)) {
            ToastUtil.show(this, "请填写你的昵称");
        } else if (TextUtils.isEmpty(mSex) && mIdentity == 1) {
            ToastUtil.show(this, "请选择你的性别");
        } else if (TextUtils.isEmpty(mMinorities) && mIdentity == 1) {
            ToastUtil.show(this, "请填写你的名族");
        }
//        else if (TextUtils.isEmpty(mIdCardNo) && mIdentity == 1) {
//            ToastUtil.show(this, "请填写你的身份证");
//        } else if (!isLegalId(mIdCardNo) && mIdentity == 1){
//            ToastUtil.show(this, "请填写合法的身份证");
//        }else if (TextUtils.isEmpty(mNativePace) && mIdentity == 1) {
//            ToastUtil.show(this, "请填写你的籍贯");
//        } else if (TextUtils.isEmpty(mPoliticalAppearance) && mIdentity == 1) {
//            ToastUtil.show(this, "请选择你的政治面貌");
//        } else if (TextUtils.isEmpty(mPetSituation) && mIdentity == 1) {
//            ToastUtil.show(this, "请选择你的宠物情况");
//        }
        else {
            isComplete = true;
        }

        return isComplete;
    }


    /**
     * 选择头像对话框
     */
    private void chooseIcon() {
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
     * 选择性别对话框
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
                                mSex = "男";
                                sex_tv.setText(mSex);
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mSex = "女";
                                sex_tv.setText(mSex);

                            }
                        })
                .show();

    }

    /**
     * 选择政治面貌对话框
     */
    private void choosePoliticalAppearance() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("党员", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mPoliticalAppearance = "0";
                                political_appearance_tv.setText("党员");
                            }
                        })
                .addSheetItem("团员", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mPoliticalAppearance = "1";
                                political_appearance_tv.setText("团员");

                            }
                        })
                .addSheetItem("群众", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mPoliticalAppearance = "2";
                                political_appearance_tv.setText("群众");

                            }
                        })
                .show();
    }

    /**
     * 选择宠物对话框
     */
    private void choosePet() {
        PetSituationSelectorDialog petSituationSelectorDialog = new PetSituationSelectorDialog(this, mPetSituation, new PetSituationSelectorDialog.OnConfirmEvent() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm(String pets) {
                mPetSituation = pets;
                String petName = StringUtil.getPetName(mPetSituation, ",");
                pet_situation_tv.setText(petName);
            }
        });
        petSituationSelectorDialog.show();
        petSituationSelectorDialog.setCancelable(false);
        petSituationSelectorDialog.setCanceledOnTouchOutside(false);

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
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .compress(true)// 是否压缩 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

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
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .compress(true)// 是否压缩 true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    if (localMedia.isEmpty() || isFinishing()) return;
                    String compressPath = localMedia.get(0).getCompressPath();
                    Glide.with(this)
                            .load(compressPath)
                            .apply(new RequestOptions().placeholder(R.drawable.default_icon).error(R.drawable.default_icon).transform(new GlideCircleTransform(this)))
                            .into(icon_iv);
                    mFile = new File(compressPath);
                    break;
            }
        }
    }

}
