package com.ovu.lido.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ovu.lido.MyApp;
import com.ovu.lido.bean.LockInfo;

import java.util.List;

/**
 * Created by jx on 16/5/16.
 */
public class AppPreference {

    private static final String CONFIG = "wwyl.config";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private AppPreference() {
        preferences = MyApp.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    private static class AppPreferenceHolder {
        public static final AppPreference INSTANCE = new AppPreference();
    }

    public static AppPreference I() {
        return AppPreferenceHolder.INSTANCE;
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public Long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
