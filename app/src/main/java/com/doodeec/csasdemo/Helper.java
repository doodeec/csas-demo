package com.doodeec.csasdemo;

import android.graphics.drawable.Drawable;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Dusan Doodeec Bartos on 26.10.2014.
 *
 * Helper for resource handling
 */
public class Helper {

    /**
     * @param stringId string resource id
     * @return string
     */
    public static String getString(int stringId) {
        return AppState.getContext().getResources().getString(stringId);
    }

    /**
     * @param drawableId drawable resource id
     * @return drawable
     */
    public static Drawable getDrawable(int drawableId) {
        return AppState.getContext().getResources().getDrawable(drawableId);
    }

    /**
     * Parses "YYYY-MM-DD" date format to Date object
     * @param dateString string to parse
     * @return date object
     */
    public static Date parseDateFromString(String dateString) {
        String[] parts = dateString.split("-");

        GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

        return calendar.getTime();
    }
}
