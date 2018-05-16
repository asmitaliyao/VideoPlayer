package com.example.videoplayer.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 请求权限的工具类
 */
public class RequestPermission {

    public static final int REQUEST_CODE = 1;   //自定义的请求权限的指令码
    private Context context;
    private String[] permissionArr; //权限数组

    /**
     * 此类的单例模式
     */
    private static RequestPermission requestPermission = null;  //静态类对象

    private RequestPermission(Context context, String[] permissionArr) {    //私有的构造方法
        this.context = context;
        this.permissionArr = permissionArr;
    }

    public static RequestPermission getInstance(Context context, String[] permissionArr) {  //静态获取对象的方法
        if (requestPermission == null) {    //如果requestPermission对象为空，则返回新构造的
            requestPermission = new RequestPermission(context, permissionArr);
        }
        return requestPermission;   //否则返回现有的requestPermission对象，保证单例
    }

    public boolean isAllGranted() { //是否全部已经申请了权限
        return checkPermissionAllGranted(permissionArr);
    }

    private boolean checkPermissionAllGranted(String[] permissions) {   //检查是否全部已经申请了权限
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void requestPermissions(){   //请求权限
        ActivityCompat.requestPermissions((Activity)context, permissionArr, REQUEST_CODE);
    }

}
