package com.pikachu.upvideo.util.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pikachu.upvideo.util.AppInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToolOther {


    private static Toast toast;

    @SuppressLint("ShowToast")
    public static Toast tw(Context context, String msg) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    //获取路径
    public static String getVideoPath(Context context){
        return context/*.getFilesDir()*/.getExternalFilesDir("")
                .getAbsolutePath() + AppInfo.videoPath;
    }


    @SuppressLint("SimpleDateFormat")
    public static String getTime(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String getTime(){
        return getTime(AppInfo.timeStr);
    }

    /**
     * dp  转  px
     *
     * @param dpValue px值
     * @param context 上下文
     */
    public static int dp2px(Context context, int dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics()) + 0.5F);
    }



    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }


    /**
     * 设置一个占位view 用于占位状态栏
     *
     * @param context
     * @param view
     */
    public static void setNonHigh(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = getStatusBarHeight(context);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置一个占位view 用于占位状态栏
     *
     * @param context
     * @param view
     */
    public static void setNonHigh(Context context, View view,int attachValue) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = getStatusBarHeight(context)+attachValue;
        view.setLayoutParams(layoutParams);
    }


}
