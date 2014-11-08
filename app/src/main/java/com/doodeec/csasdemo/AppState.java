package com.doodeec.csasdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dusan Doodeec Bartos on 26.10.2014.
 *
 * Keeps track of application state
 */
public class AppState extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    /**
     * @return application context
     */
    public static Context getContext() {
        return mContext;
    }
}

