/**
 *
 *
 *
 */

package com.pikachu.upvideo.util;

import android.Manifest;

public class AppInfo {



    //时间格式
    public static String timeStr =  "yyyy-MM-dd HH:mm:ss" ;

    //定位失败重试次数
    public static int gaoDeInt   = 3;
    // 项目保存路径 /data/user/0/packname/files + videoPath
    public static String videoPath = "/.nomedia/";//.nomedia
    //项目保存格式
    public static String videoProjectName  = ".idea.json";






////////////////////////////////////////////////////////////////////////////////////////////////////

    //权限
    public static final String[] permissions = {
            Manifest.permission.CAMERA,//相机权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入SD权限
            Manifest.permission.READ_EXTERNAL_STORAGE,//读取SD权限
            Manifest.permission.RECORD_AUDIO,//录音权限
            Manifest.permission.ACCESS_COARSE_LOCATION,//网络定位
            Manifest.permission.ACCESS_FINE_LOCATION,//GPS定位
            Manifest.permission.ACCESS_NETWORK_STATE,//运营商网络定位
            Manifest.permission.ACCESS_WIFI_STATE,//wifi定位
            Manifest.permission.CHANGE_WIFI_STATE,//获取Wifi信息
            Manifest.permission.INTERNET,//联网权限
            Manifest.permission.READ_PHONE_STATE,//手机状态
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS //申请调用A-GPS模块
    };

    public static final String START_ACTIVITY_KEY_1  = "KEY_1";
    public static final String START_ACTIVITY_KEY_2  = "KEY_2";


    ////////////////////完成子项目 1.添加  2.删除  3.历史
    ////////////////// 适配器完成 ，明天做添加项目

}
