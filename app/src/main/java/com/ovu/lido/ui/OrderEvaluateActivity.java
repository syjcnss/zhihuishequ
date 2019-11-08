package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.adapter.PicRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 工单评价
 */
public class OrderEvaluateActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.content_et)
    EditText content_et;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.pic_rv)
    RecyclerView pic_rv;

    private PicRvAdapter mPicRvAdapter;
    private int selectMax = 9;
    private List<LocalMedia> picList = new ArrayList<>();
    private int themeId = R.style.picture_white_style;
    private Map<String, File> picPath;
    private String order_id;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_evaluate;
    }

    @Override
    protected void initView() {
        super.initView();

        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
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
    protected void setListener() {
        super.setListener();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
//                showShortToast("rating:" + rating);
            }
        });

        mPicRvAdapter.setOnItemClickListener(new PicRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (picList.size() > 0) {
                    LocalMedia media = picList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    if (mediaType == 1) {
                        PictureSelector.create(OrderEvaluateActivity.this).themeStyle(themeId).openExternalPreview(position, picList);
                    }
                }


            }
        });

        mPicRvAdapter.setOnAddPicClickListener(new PicRvAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                showAddDailog();
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        order_id = getIntent().getStringExtra("order_id");
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
        PictureSelector.create(OrderEvaluateActivity.this)
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
        PictureSelector.create(OrderEvaluateActivity.this)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    picList = PictureSelector.obtainMultipleResult(data);
                    //media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    picPath = new HashMap<>();
                    for (int i = 0; i < picList.size(); i++) {
                        String compressPath = picList.get(i).getCompressPath();
                        String arg[] = compressPath.split("/");
                        String fileName = arg[arg.length - 1];
                        if (i == 0) {
                            File file = new File(compressPath);
                            picPath.put(fileName, file);
                        } else {
                            File file = new File(compressPath);
                            picPath.put(fileName, file);
                        }
                    }
                    Log.i(TAG, "picPath is ----------" + picPath);
                    mPicRvAdapter.setList(picList);
                    mPicRvAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.enter_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.enter_btn:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        String content = content_et.getText().toString().trim();
        float rating = ratingBar.getRating();
        if (TextUtils.isEmpty(content)) {
            showShortToast("请输入评价");
            return;
        }
        if (rating == 0.0){
            showShortToast("请给Ta评分");
            return;
        }
        if (rating <= 3.0) {
            if (picPath == null){
                showShortToast("请添加图片");
            }else {
                upEvaluateHasImg(content, (int) rating);
            }

        } else if (rating > 3.0){
            if (picPath == null){
                upEvaluateNoImg(content, (int) rating);
            }else {
                upEvaluateHasImg(content, (int) rating);
            }
        }

    }

    /**
     * 上传评价内容 （含图片）
     * @param content
     * @param rating
     */
    private void upEvaluateHasImg(String content, int rating) {
        String resident_id = AppPreference.I().getString("resident_id", "");
        Log.i(TAG, "传图片参数: resident_id：" + resident_id + "  order_id：" + order_id + "  comment_state：" + rating + " comment_content：" + content + "  picPath：" + picPath);
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.GET_ORDER_COMMENT)
                .addParams("resident_id", resident_id)
                .addParams("order_id", order_id)
                .addParams("comment_state", String.valueOf(rating))
                .addParams("comment_content", content)
                .files("pic", picPath)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "工单评价数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        String data = getDataCode(response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.RESULT_OK)){
                            if (data == null || data.equals("")) {
                                showShortToast("评价成功");
                                EventBus.getDefault().post(new RefreshEvent(1234));
                                finish();
                            }else {
                                showShortToast(data);
                            }
                        }else {
                            String errorMsg = getErrorMsg(response);
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 上传评价内容 （不含图片）
     * @param content
     * @param rating
     */
    private void upEvaluateNoImg(String content, int rating) {
        String resident_id = AppPreference.I().getString("resident_id", "");
        Log.i(TAG, "不传图片参数: resident_id：" + resident_id + "  order_id：" + order_id + "  comment_state：" + String.valueOf(rating) +  "  comment_content：" + content);
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.GET_ORDER_COMMENT_NOIMG)
                .addParams("resident_id", resident_id)
                .addParams("order_id", order_id)
                .addParams("comment_state", String.valueOf(rating))
                .addParams("comment_content", content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "工单评价数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        String data = getDataCode(response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.RESULT_OK)){
                            if (data == null || data.equals("")) {
                                showShortToast("评价成功");
                                EventBus.getDefault().post(new RefreshEvent(1234));
                                finish();
                            }else {
                                showShortToast(data);
                            }
                        }else {
                            String errorMsg = getErrorMsg(response);
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 获取状态码
     * @param response 数据源
     * @return 状态码
     */
    public String getErrorCode(String response) {
        String errorCode = "";
        //创建容器
        JSONObject rootObject = null;
        try {
            rootObject = new JSONObject(response);
            errorCode = rootObject.getString("errorCode");
            Log.i(TAG, "errorCode: " + errorCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorCode;
    }

    /**
     * 获取状态码
     * @param response 数据源
     * @return 状态码
     */
    public String getDataCode(String response) {
        String data = "";
        //创建容器
        JSONObject rootObject = null;
        try {
            rootObject = new JSONObject(response);
            data = rootObject.getString("data");
            Log.i(TAG, "data: " + data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取状态码
     * @param response 数据源
     * @return 状态码
     */
    public String getErrorMsg(String response) {
        String errorMsg = "";
        //创建容器
        JSONObject rootObject = null;
        try {
            rootObject = new JSONObject(response);
            errorMsg = rootObject.getString("errorMsg");
            Log.i(TAG, "errorMsg: " + errorMsg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorMsg;
    }

}
