package com.example.pet.mode.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.pet.mode.models.User;
import com.google.gson.Gson;

import java.util.Objects;

public class Utils {
    private static SharedPreferences sharedPreferences;
    private static int screenWidth  = 0;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static  String getToken(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "1");
    }
    public static int getScreenWidth(Context context) {

        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        return screenWidth;
    }
//    public static String getUserInfor(Activity activity) {
//        sharedPreferences = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
//        String user = sharedPreferences.getString("user_infor", "1");
//        return user;
//    }
}
