package com.ovu.lido.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.FamilyMemberInfo;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加家庭成员
 */
public class AddFamilyMemberActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.iv_user_icon)
    CircleImageView iv_user_icon;
    @BindView(R.id.edit_user_name)
    EditText edit_user_name; //姓名
    @BindView(R.id.edit_user_sex)
    TextView edit_user_sex; //性别
    @BindView(R.id.edit_user_tel)
    EditText edit_user_tel; //电话
    @BindView(R.id.edit_user_relation)
    EditText edit_user_relation; //与业主关系
    @BindView(R.id.edit_user_card_id)
    EditText edit_user_card_id; //身份证
    @BindView(R.id.edit_user_work_unit)
    EditText edit_user_work_unit; //工作单位

    private int themeId = R.style.picture_white_style;
    private List<LocalMedia> picList = new ArrayList<>();
    private int selectMax = 1;
    private Map<String, File> picPath;

    private String userName;
    private String userSex;
    private String userTel;
    private String userRelation;
    private String userCardId;
    private String userWorkUnit;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_family_member;
    }

    @OnClick({R.id.back_iv,R.id.save_tv,R.id.edit_user_sex,R.id.change_photos_iv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_tv:
                doSubmit();
                break;
            case R.id.edit_user_sex:
                showSexDailog();
                break;
            case R.id.change_photos_iv:
                showAddDailog();
                break;
        }
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
                                edit_user_sex.setText("男");
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                edit_user_sex.setText("女");
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



    private void doSubmit() {
        userName = edit_user_name.getText().toString().trim();
        userSex = edit_user_sex.getText().toString();
        userTel = edit_user_tel.getText().toString().trim();
        userRelation = edit_user_relation.getText().toString().trim();
        userCardId = edit_user_card_id.getText().toString().trim();
        userWorkUnit = edit_user_work_unit.getText().toString().trim();
        if (picList == null || picList.isEmpty()){
            ToastUtil.show(mContext,"请选择成员图片");
        }else if (TextUtils.isEmpty(userName)){
            ToastUtil.show(mContext,"请输入姓名");
        }else if (TextUtils.isEmpty(userSex)){
            ToastUtil.show(mContext,"请选择性别");
        }else if (TextUtils.isEmpty(userTel)){
            ToastUtil.show(mContext,"请输入电话号码");
        }else if (TextUtils.isEmpty(userRelation)){
            ToastUtil.show(mContext,"请输入与业主关系");
        }
//        else if (TextUtils.isEmpty(userCardId)){
//            ToastUtil.show(mContext,"请输入身份证号");
//        }else if (TextUtils.isEmpty(userWorkUnit)){
//            ToastUtil.show(mContext,"请输入工作单位");
//        }
        else{
            //添加成员
            addMember();
        }
    }

    private void addMember() {
//        FamilyMemberInfo(String familyMemberIcon, String familyMemberName, String familyMemberSex, String familyMemberMobile, String familyMemberRelation, String familyMemberIDCard, String familyMemberWorkUnit)
        FamilyMemberInfo info = new FamilyMemberInfo(picPath, userName, userSex.equals("男") ? "1" : "2", userTel, userRelation, userCardId, userWorkUnit);
        Intent intent = new Intent();
        intent.putExtra("familyMemberInfo",info);
        setResult(RESULT_OK,intent);
        finish();
        ViewHelper.hideSoftInput(this);
    }

    /**
     * 从相机获取
     */
    private void fromCamera() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(AddFamilyMemberActivity.this)
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
        PictureSelector.create(AddFamilyMemberActivity.this)
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

}
