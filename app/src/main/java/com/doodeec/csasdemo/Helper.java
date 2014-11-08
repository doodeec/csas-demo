package com.doodeec.csasdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dusan Doodeec Bartos on 26.10.2014.
 *
 * Helper for resource handling
 */
public class Helper {

    public static final String EMPTY_STRING = "";

    /**
     * @param stringId string resource id
     * @return string
     */
    public static String getString(int stringId) {
        return AppState.getContext().getResources().getString(stringId);
    }

    /**
     * @param colorId color resource id
     * @return color
     */
    public static int getColor(int colorId) {
        return AppState.getContext().getResources().getColor(colorId);
    }

    /**
     * Determines if device is connected to network
     *
     * @return true if connected
     */
    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) AppState.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
}
