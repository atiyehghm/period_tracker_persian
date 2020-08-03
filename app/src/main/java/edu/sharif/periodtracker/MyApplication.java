package edu.sharif.periodtracker;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        JodaTimeAndroid.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
