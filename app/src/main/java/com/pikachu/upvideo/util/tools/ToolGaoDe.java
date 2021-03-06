package com.pikachu.upvideo.util.tools;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class ToolGaoDe {

    private final AMapLocationListener var1;
    //定位
    private AMapLocationClient mLocationClient;//声明AMapLocationClient类对象
    private AMapLocationClientOption mLocationOption;//声明AMapLocationClientOption对象

    private final Activity activity;
    @SuppressLint("StaticFieldLeak")
    private static ToolGaoDe toolGaoDe;
    private boolean start;


    public static ToolGaoDe getGaoDeTools(Activity activity) {
        /*if (toolGaoDe == null)
            toolGaoDe = new ToolGaoDe(activity, aMapLocation -> {
            });*/
        return /*toolGaoDe*/ toolGaoDe = new ToolGaoDe(activity, aMapLocation -> {
        });
    }

    public static ToolGaoDe getGaoDeTools(Activity activity, AMapLocationListener var1) {
        /*if (toolGaoDe == null)
            toolGaoDe = new ToolGaoDe(activity, var1);*/
        return /*toolGaoDe*/new ToolGaoDe(activity, var1);
    }


    public ToolGaoDe(Activity activity, AMapLocationListener var1) {
        this.activity = activity;
        this.var1 = var1;
        initGaoDe();
    }

    //初始高德定位
    private void initGaoDe() {
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(activity);
            //设置定位回调监听
            mLocationClient.setLocationListener(var1);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();

            //高精度定位
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //方向
            mLocationOption.setSensorEnable(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否允许模拟位置,默认为true，允许模拟位置
            mLocationOption.setMockEnable(true);
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(1000);


            //设置首次定位是否等待卫星定位结果
            mLocationOption.setGpsFirst(true);
            //设置是否允许模拟位置
            mLocationOption.setMockEnable(true);
            //联网超时时间 10s
            mLocationOption.setHttpTimeOut(10000);
        }
    }


    //开始定位
    public void start() {
        if (mLocationClient != null) {
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
            start = true;
        }
    }

    //结束定位
    public void stop() {
        if (mLocationClient != null) {
            //停止定位后，本地定位服务并不会被销毁
            mLocationClient.stopLocation();
            start = false;
        }
    }

    

    public boolean isStart(){
        return start;
    }
}
