package com.ovu.lido.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.ovu.lido.R;
import com.ovu.lido.adapter.PicRvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CertificationEntity;
import com.ovu.lido.bean.DecorationProjectInfo;
import com.ovu.lido.bean.PhotoTypeInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ActionSheetDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.ovu.lido.widgets.SelectDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * 装修管理
 */
public class DecorationActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.name_et)
    EditText name_et;
    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.company_et)
    EditText company_et;
    @BindView(R.id.decoration_project_rl)
    RelativeLayout decoration_project_rl;

    @BindView(R.id.agree_cb)
    CheckBox agree_cb;
    @BindView(R.id.submit_btn)
    Button submit_btn;
    @BindView(R.id.selectTxt)
    TextView selectTxt;
    @BindView(R.id.draw_list_ll)
    LinearLayout draw_list_ll;

    private SelectDialog dialog;
    private StringBuffer ids;
    private Map<Integer, List<CertificationEntity>> cfMaps = new HashMap<>();
    private Map<Integer, List<LocalMedia>> localMaps = new HashMap<>();

    private List<PhotoTypeInfo.DataBeanX.DataBean> typeInfos = new ArrayList<>();
    private List<LocalMedia> picList = new ArrayList<>();
    private int selectMax = 6;
    private int themeId = R.style.picture_white_style;
    private Dialog mDailog;
    private int position;
    private Map<String, File> picPath;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_decoration;
    }

    @Override
    protected void loadData() {
        OkHttpUtils.get()
                .url(Constant.GET_PHOTO_TYPE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "图片类型数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            PhotoTypeInfo typeInfo = GsonUtil.GsonToBean(response, PhotoTypeInfo.class);
                            List<PhotoTypeInfo.DataBeanX.DataBean> data = typeInfo.getData().getData();
                            if (data != null) {
                                typeInfos.addAll(data);
                                int typeInfosSize = typeInfos == null ? 0 : typeInfos.size();
                                for (int i = 0; i < typeInfosSize; i++) {
                                    PhotoTypeInfo.DataBeanX.DataBean pti = typeInfos.get(i);

                                    pti.setCertificate_id(i + "");

                                    View view = LayoutInflater.from(mContext).inflate(R.layout.decoration_drawing_upload, draw_list_ll, false);
                                    //类型名称
                                    TextView name_tv = view.findViewById(R.id.name_tv);
                                    name_tv.setText(pti.getFileName());
                                    //类型图片
                                    ImageView iv_arr = view.findViewById(R.id.iv_arr);
                                    RecyclerView pic_rv = view.findViewById(R.id.rv_add_img);

                                    iv_arr.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            View parent = (View) v.getParent();
                                            if (parent.getVisibility() == View.VISIBLE) {
                                                parent.setVisibility(View.GONE);
                                            } else {
                                                parent.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });

                                    GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
                                    pic_rv.setLayoutManager(layoutManager);
                                    //问题2、ScrollView嵌套RecyclerView后滑动很缓慢，不流畅，没有惯性
                                    pic_rv.setHasFixedSize(true);
                                    pic_rv.setNestedScrollingEnabled(false);
                                    PicRvAdapter adapter = new PicRvAdapter(mContext, selectMax);
                                    List<LocalMedia> localMediaList = new ArrayList<>();
                                    adapter.setList(localMediaList);
                                    pic_rv.setAdapter(adapter);
                                    final int click_pos = i;
                                    adapter.setOnAddPicClickListener(new PicRvAdapter.OnAddPicClickListener() {
                                        @Override
                                        public void onAddPicClick() {
                                            position = click_pos;
                                            showAddDailog(PictureConfig.CHOOSE_REQUEST);
                                        }
                                    });

                                    adapter.setOnDeletePicClickListener(new PicRvAdapter.OnDeletePicClickListener() {
                                        @Override
                                        public void onDeletePicClick(List<LocalMedia> list) {
                                            int id = typeInfos.get(position).getId();
                                            List<CertificationEntity> certificationEntities = new ArrayList<>();
                                            for (int i = 0; i < list.size(); i++) {
                                                String compressPath = list.get(i).getCompressPath();
                                                CertificationEntity certificationEntity = new CertificationEntity(String.valueOf(id), compressPath);
                                                certificationEntities.add(certificationEntity);
                                            }
                                            cfMaps.put(position, certificationEntities);
                                        }
                                    });


                                    draw_list_ll.addView(view);
                                }
                            }
                        }
                    }
                });

    }

    @Override
    protected void initView() {
        super.initView();
        mDailog = LoadProgressDialog.createLoadingDialog(mContext);
        agree_cb.setChecked(false);
        dialog = new SelectDialog(mContext, name_et);
        agree_cb.setChecked(false);
    }

    @Override
    protected void setListener() {
        super.setListener();
        agree_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    submit_btn.setEnabled(true);
                } else {
                    submit_btn.setEnabled(false);
                }
            }
        });
    }

    /**
     * 显示添加图片路径对话框
     */
    private void showAddDailog(final int requestCode) {
        new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                fromPhotoAlbum(requestCode);
                            }
                        })
                .addSheetItem("拍摄", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                fromCamera(requestCode);
                            }
                        })
                .show();
    }

    /**
     * 从相机获取
     */
    private void fromCamera(int requestCode) {
        List<LocalMedia> localMediaList = localMaps.get(position);
        if (null == localMediaList)
            localMediaList = new ArrayList<>();
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
                .openClickSound(false)// 是否开启点击声音 true or false
                //.selectionMedia(localMediaList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .selectionMedia(localMediaList)
                .forResult(requestCode);//结果回调onActivityResult code
    }

    /**
     * 从相册获取
     */
    private void fromPhotoAlbum(int requestCode) {
        List<LocalMedia> localMediaList = localMaps.get(position);
        if (null == localMediaList)
            localMediaList = new ArrayList<>();
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
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia(picList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .selectionMedia(localMediaList)
                .forResult(requestCode);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    picList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "picList is ----------" + picList.toString());
                    RecyclerView rv_add_img = draw_list_ll.getChildAt(position).findViewById(R.id.rv_add_img);
                    PicRvAdapter adapter = (PicRvAdapter) rv_add_img.getAdapter();
                    adapter.setList(picList);
                    adapter.notifyDataSetChanged();

                    int id = typeInfos.get(position).getId();
                    List<CertificationEntity> certificationEntities = new ArrayList<>();
//                    //media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    for (int i = 0; i < picList.size(); i++) {
                        String compressPath = picList.get(i).getCompressPath();
                        CertificationEntity certificationEntity = new CertificationEntity(String.valueOf(id), compressPath);
                        certificationEntities.add(certificationEntity);
                    }
                    cfMaps.put(position,certificationEntities);

                    localMaps.put(position,picList);
                    break;
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.decoration_project_rl, R.id.clause_tv, R.id.submit_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.decoration_project_rl://装修项目
                ViewHelper.hideSoftInput(this);
                showSelectorDialog();
                break;
            case R.id.clause_tv://协议
                startActivity(new Intent(mContext, ClauseActivity.class));
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

        String name = name_et.getText().toString().trim();
        String phone = phone_et.getText().toString().trim();
        String company = company_et.getText().toString().trim();
        String project = selectTxt.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            showShortToast("请填写负责人姓名");
        } else if (StringUtils.isEmpty(phone)) {
            showShortToast("请填写负责人电话");
        } else if (StringUtils.isEmpty(company)) {
            showShortToast("请填写装修公司名称");
        } else if (StringUtils.isEmpty(project)) {
            showShortToast("请选择装修项目");
        } else if (cfMaps.size() < typeInfos.size()) {
            showShortToast("证件还未上传完成");
        } else {

            Log.i(TAG, "doSubmit: cfMaps>>>>" + cfMaps.toString());
            List<CertificationEntity> entityList = new ArrayList<>();
            Iterator<Map.Entry<Integer, List<CertificationEntity>>> iterator = cfMaps.entrySet().iterator();
            while (iterator.hasNext()) {
                List<CertificationEntity> entities = iterator.next().getValue();
                entityList.addAll(entities);
            }
            Log.i(TAG, "doSubmit: entityList>>>" + entityList.toString());

            StringBuffer certificateIds = new StringBuffer();
            picPath = new HashMap<>();
            for (int i = 0; i < entityList.size(); i++) {
                String url = entityList.get(i).getUrl();
                String[] arg = url.split("/");
                String fileName = arg[arg.length - 1];
                File file = new File(url);
                picPath.put(fileName, file);
                if (i == 0) {
                    certificateIds.append(entityList.get(i).getId());
                } else {
                    certificateIds.append(",");
                    certificateIds.append(entityList.get(i).getId());
                }

            }

            Log.i(TAG, "doSubmit: idBuffer>>>" + certificateIds.toString());
            Log.i(TAG, "doSubmit: picPath>>>" + picPath.toString());

            upLoad(name, phone, company, certificateIds.toString());
        }
    }


    /**
     * 提交申请
     */
    private void upLoad(String name, String phone, String company, String certificateIds) {
        Log.i(TAG, "图片文件地址: " + picPath.toString());
        mDailog.show();
        String project = selectTxt.getText().toString().trim();
        OkHttpUtils.post()
                .url(Constant.SUBMIT_APPLICATION_URL)
                .addParams("decorationName", project)
                .files("imgs", picPath)
                .addParams("worker_name", name)
                .addParams("worker_tel", phone)
                .addParams("certificate_id", certificateIds)
                .addParams("passes_count", "10")
                .addParams("decoration_company", company)
                .addParams("ids", String.valueOf(ids))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDailog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "提交申请: " + response);
                        LoadProgressDialog.closeDialog(mDailog);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            showShortToast("提交申请成功");
                            clearPicCaching();
                            finish();
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 清空图片缓存，包括压缩后的图片
     */
    private void clearPicCaching() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(mContext);
                } else {
                    showShortToast("读取内存卡权限被拒绝");
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 项目选择对话框
     */
    private void showSelectorDialog() {
        dialog.showFilterWindow();
        dialog.setRefreshData(new SelectDialog.RefreshData() {
            @Override
            public void loadData(List<DecorationProjectInfo.DataBean> entity) {
                selectTxt.setText("");
                ids = new StringBuffer();
                for (int i = 0; i < entity.size(); i++) {
                    if (i == 0) {
                        ids.append(entity.get(i).getId());
                        selectTxt.append(entity.get(i).getProjectName());
                    } else {
                        ids.append(",");
                        ids.append(entity.get(i).getId());
                        selectTxt.append("/" + entity.get(i).getProjectName());
                    }
                }

                Log.i(TAG, "ids is ----------" + ids);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDailog);
    }
}
