package com.ovu.lido.widgets;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class CommonAction {
    private List<Activity> AllActivitites = new ArrayList<Activity>();
    private static CommonAction instance;


    public CommonAction() {

    }

    public synchronized static CommonAction getInstance() {
        if (instance == null) {
            instance = new CommonAction();
        }
        return instance;
    }

    //在Activity基类的onCreate()方法中执行
    public void addActivity(Activity activity) {
        AllActivitites.add(activity);
    }

    public void removeActivity(Activity activity) {
        AllActivitites.remove(activity);
    }

    //注销是销毁所有的Activity
    public void OutSign() {
        for (Activity activity : AllActivitites) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public List<Activity> getAllActivitites() {
        return AllActivitites;
    }


}
