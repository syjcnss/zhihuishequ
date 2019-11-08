package com.ovu.lido.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.ovu.lido.bean.CityInfo;
import com.ovu.lido.bean.Contacts;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewHelper {
    private static String TAG = "wangw";

    /**
     * 获取listview条目
     *
     * @param listView
     * @param index
     * @return
     */
    public static View getListViewItem(ListView listView, int index) {
        View view = null;
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (index >= firstVisiblePosition && index <= lastVisiblePosition) {
            view = listView.getChildAt(index - firstVisiblePosition);
        }
        return view;
    }


    /**
     * 跳转到拨号界面
     *
     * @param context
     * @param tel
     */
    public static void toDialView(Context context, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断电话号码格式是否正确
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        Pattern p = Pattern.compile("^[1][3-9][0-9]{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 根据地区等级获取列表
     * @param data 数据源
     * @return
     */
    public static List<CityInfo> getZoneList(String data) {
        List<CityInfo> zoneInfos = new ArrayList<>();
        String data1 = data.replace("(", "").replace(")", "").replace("'", "");
        String data2[] = data1.split("\\|");
        for (int i = 0; i < data2.length; i++) {
            String data3[] = data2[i].split(",");
            CityInfo zone = new CityInfo(data3[0],data3[1],data3[2],data3[3]);
            zoneInfos.add(zone);
        }
        return zoneInfos;
    }

    public static String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = sdf.format(date);
        return s;
    }

    /**
     * 比较当前时间是否超过指定时间
     *
     * @param date
     * @return
     */
    public static boolean compareDate(String date) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dt1 = df.parse(date);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                Log.i(TAG, "dt1在dt2前");
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                Log.i(TAG, "dt1在dt2后");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取显示日期
     *
     * @param str
     * @return
     */
    public static String getDisplayData(String str) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (!StringUtils.isEmpty(str)) {
            try {
                Date date = sim.parse(str);
                return sim.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 获取显示日期
     * @param str 需要转换的时间字符串
     * @param pattern1 转换前格式
     * @param pattern2 转换后格式
     * @return
     */
    public static String getDisplayData(String str,String pattern1,String pattern2) {
        SimpleDateFormat sim = new SimpleDateFormat(pattern1, Locale.getDefault());
        SimpleDateFormat sim2 = new SimpleDateFormat(pattern2, Locale.getDefault());
        if (!StringUtils.isEmpty(str)) {
            try {
                Date date = sim.parse(str);
                return sim2.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getStrTime(long createTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(new Date(createTime));
    }

    /**
     * 获取请求参数
     * @param paramMap map键值对
     * @return json参数
     */
    public static String getParams(Map<String,String> paramMap) {
        Map<String, Object> param = new HashMap<>();
        param.put("data", paramMap);
        String params = GsonUtil.ToGson(param);
        Log.i("wangw", "params: " + params);
        return params;
    }

    /**
     * 获取请求参数
     * @param paramMap map键值对
     * @return json参数
     */
    public static String getParamsV2(Map<String,Object> paramMap) {
        Map<String, Object> param = new HashMap<>();
        param.put("data", paramMap);
        String params = GsonUtil.ToGson(param);
        Log.i("wangw", "params: " + params);
        return params;
    }

    /**
     * 获取显示的价格（保留两位小数）
     *
     * @param value 元
     * @return String类型
     */
    public static String getDisplayPrice(Double value) {
        String result = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return result;
    }
    /**
     * 获取显示的价格（保留两位小数）
     *
     * @param value 元
     * @return Double类型
     */
    public static Double getDisplayPrice2(Double value) {
        String result = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return Double.valueOf(result);
    }

    /**
     * 从通讯录中选择联系人
     */
    public static Contacts getContactPhone2(Context context, Uri contactData) {
        Contacts contacts = new Contacts();
        String showName = null;
        String[] tel = null;
        Cursor cursor = context.getContentResolver().query(contactData, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = 0;
        try {
            phoneNum = cursor.getInt(phoneColumn);
        } catch (Exception e) {
            ToastUtil.show(context, "获取联系人失败！");
            LogUtil.i(null, "get contact failed");
            if (!cursor.isClosed()) {
                cursor.close();
            }
            return null;
        }
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            // 一个联系人上面可以存多个电话号码
            tel = new String[phoneCursor.getCount()];
            int arrIndex = 0;
            if (phoneCursor.moveToFirst()) {
                int nameIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                showName = phoneCursor.getString(nameIndex);
                // 遍历所有的电话号码
                for (; !phoneCursor.isAfterLast(); phoneCursor.moveToNext()) {
                    int index = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phoneCursor.getInt(typeindex);
                    String phoneNumber = phoneCursor.getString(index);
                    switch (phone_type) {
                        case 2:
                            break;
                    }
                    tel[arrIndex++] = phoneNumber;
                }
                if (!phoneCursor.isClosed()) {
                    phoneCursor.close();
                }
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        contacts.setName(showName);
        contacts.setTel(tel);
        return contacts;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String getFileSavePath(Context context) {
        String path = context.getExternalFilesDir(null) + File.separator + "pic" + File.separator;
        checkDirExsit(path);
        return path;
    }

    public static void checkDirExsit(String path) {
        // 如果图片目录不存在，就创建一个目录
        File file = new File(path);
        if (!file.exists()) {
            LogUtil.i(TAG, "path not exsit, create it");
            file.mkdirs();
        }
    }

    /**
     * 设置tab指示器的宽度 与 文字宽度一致
     *
     * @param tabLayout
     * @param padding
     */
    public static void setTabWidth(final TabLayout tabLayout, final int padding) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        mTextView.setTextSize(AppUtil.sp2px(13f, 1));

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
//                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();

                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isSameDate(Date date1, Date date2) {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                    .get(Calendar.YEAR);
            boolean isSameMonth = isSameYear
                    && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            boolean isSameDate = isSameMonth
                    && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                    .get(Calendar.DAY_OF_MONTH);

            return isSameDate;
        } catch (Exception e) {
            Log.e(TAG, "[RatingEngine] error occurred: ERROR : ", e);
        }
        return false;


    }
}
