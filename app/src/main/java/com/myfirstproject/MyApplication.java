package com.myfirstproject;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by zhengxudong on 2018/1/22.
 */

public class MyApplication extends Application {
    public static final String HAS_FINGERPRINT_API = "hasFingerPrintApi";
    public static final String SETTINGS = "settings";

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        if (sp.contains(HAS_FINGERPRINT_API)) { // 检查是否存在该值，不必每次都通过反射来检查
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager"); // 通过反射判断是否存在该类
            editor.putBoolean(HAS_FINGERPRINT_API, true);
        } catch (ClassNotFoundException e) {
            editor.putBoolean(HAS_FINGERPRINT_API, false);
            e.printStackTrace();
        }
        editor.apply();
    }

}
