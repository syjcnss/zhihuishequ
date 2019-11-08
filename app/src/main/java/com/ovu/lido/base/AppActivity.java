package com.ovu.lido.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;


public class AppActivity extends AppCompatActivity {
    private static AppActivity instance;
    public SparseArray<Activity> activityStack;// Activity栈
    public int activityStackIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStack = new SparseArray<>();
        instance = this;
    }

    public static AppActivity getInstance() {
        return instance;
    }
}
