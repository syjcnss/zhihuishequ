package com.ovu.lido.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.adapter.PicRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 发布帖子
 */
public class PostedActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.iv_add_img)
    ImageView mIv_add_img;
    @BindView(R.id.rb_meeting)
    RadioButton mRb_meeting;
    @BindView(R.id.rb_second)
    RadioButton mRb_second;
    @BindView(R.id.rv_add_img)
    RecyclerView pic_rv;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.et_content)
    EditText mEt_content;
    @BindView(R.id.et_title)
    EditText mEt_title;
    private StringBuffer ids;
    private StringBuffer picPath;

    private List<LocalMedia> picList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private Map<String, File> map = new HashMap<>();
    private int selectMax = 9;
    private PicRvAdapter mPicRvAdapter;
    private int themeId = R.style.picture_white_style;
    private String mType = "4";
    private String mTitle;
    private String mContent;
    private Dialog mDialog;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_posted_edit;
    }

    @Override
    protected void initView() {
        mDialog= LoadProgressDialog.createLoadingDialog(this);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        pic_rv.setLayoutManager(layoutManager);
        //问题2、ScrollView嵌套RecyclerView后滑动很缓慢，不流畅，没有惯性
        pic_rv.setHasFixedSize(true);
        pic_rv.setNestedScrollingEnabled(false);
        mPicRvAdapter = new PicRvAdapter(mContext, selectMax);
        mPicRvAdapter.setList(picList);
        pic_rv.setAdapter(mPicRvAdapter);

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        mRb_meeting.setOnClickListener(this);
        mRb_second.setOnClickListener(this);
        findViewById(R.id.tv_submit).setOnClickListener(this);
        findViewById(R.id.iv_add_img).setOnClickListener(this);
        mPicRvAdapter.setOnAddPicClickListener(new PicRvAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                showAddDailog();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.iv_add_img:
                showAddDailog();
                break;
            case R.id.rb_second:
                mType = "2";
                break;
            case R.id.rb_meeting:
                mType = "4";
                break;
            case R.id.tv_submit:
                addPost();
                break;
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    picList = PictureSelector.obtainMultipleResult(data);

                    //media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    picPath = new StringBuffer();
                    for (int i = 0; i < picList.size(); i++) {
                        if (i == 0) {
                            picPath.append(picList.get(i).getCompressPath());
                        } else {
                            picPath.append(",");
                            picPath.append(picList.get(i).getCompressPath());
                        }
                    }
                    Log.i(TAG, "picPath is ----------" + picPath);
                    mPicRvAdapter.setList(picList);
                    mPicRvAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    /**
     * 发布帖子
     */
    private void addPost() {
        mTitle = mEt_title.getText().toString();
        mContent = mEt_content.getText().toString();
        if (TextUtils.isEmpty(mTitle)) {
            showToast("标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(mContent)) {
            showToast("内容不能为空");
            return;
        }
        map.clear();
        mDialog.show();
        if (picList.size() > 0) {
            for (int i = 0; i < picList.size(); i++) {
                File file = new File(picList.get(i).getCompressPath());
                map.put(picList.get(i).getCompressPath() + i, file);
            }
            hasImg();
        } else {
            noImg();
        }

    }

    private void noImg() {
        OkHttpUtils.post()
                .url(HttpAddress.ADD_POST)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id",  AppPreference.I().getString("resident_id",""))
                .addParams("title", mTitle)
                .addParams("content", mContent)
                .addParams("info_type_id", "02")
                .addParams("info_typename", mType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                        showToast("获取数据失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(PostedActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (bean.getPoint() == 0) {
                                showToast("发帖成功");
                            } else {
                                showToast("积分:" + bean.getPoint());
                            }
                            EventBus.getDefault().post(new RefreshEvent(RefreshConstant.NEIGHBOR_LIST));
                            finish();

                        }else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });
    }

    private void hasImg() {

        OkHttpUtils.post()
                .url(HttpAddress.ADD_POSTS)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id",  AppPreference.I().getString("resident_id",""))
                .addParams("title", mTitle)
                .addParams("content", mContent)
                .addParams("info_type_id", "02")
                .addParams("info_typename", mType)
                .files("imgs", map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                        showToast("获取数据失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(PostedActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            if (bean.getPoint() == 0) {
                                showToast("发帖成功");
                            } else {
                                showToast("积分:" + bean.getPoint());
                            }
                            EventBus.getDefault().post(new RefreshEvent(RefreshConstant.NEIGHBOR_LIST));
                            finish();

                        }else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 从相机获取
     */
    private void fromCamera() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create((Activity) mContext)
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
