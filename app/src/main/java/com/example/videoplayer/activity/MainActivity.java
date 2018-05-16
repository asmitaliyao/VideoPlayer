package com.example.videoplayer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.videoplayer.R;
import com.example.videoplayer.util.RequestPermission;

/**
 * 程序首页
 */
public class MainActivity extends Activity {

    private RequestPermission requestPermission;    //定义请求权限的对象
    private ImageView enterVideoPlayerImageView;    //定义主界面的图标ImageView
    private Button enterVideoPlayerButton;  //定义主界面的进入播放器按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //首页创建时调用的方法
        super.onCreate(savedInstanceState); //调用父类Activity的onCreate方法
        setContentView(R.layout.activity_main); //设置首页的界面布局
        initPermission();   //调用请求权限
        enterVideoPlayerButton = findViewById(R.id.main_activity_button);   //获取界面的按钮关联给enterVideoPlayerButton
        enterVideoPlayerButton.setOnClickListener(new View.OnClickListener() {  //添加点击按钮的事件
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoPlayerActivity.class));    //跳转到VideoPlayerActivity界面
            }
        });
        enterVideoPlayerImageView = findViewById(R.id.main_activity_image_view);    //获取界面图标关联给enterVideoPlayerImageView
        enterVideoPlayerImageView.setOnClickListener(new View.OnClickListener() {   //添加点击事件
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoPlayerActivity.class));    //跳转
            }
        });
    }

    private void initPermission() { //-》1、动态申请手机权限的方法实现
        requestPermission = RequestPermission.getInstance(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}); //向外部存储写入数据的权限
        if (!requestPermission.isAllGranted())  //判断权限有没有获取，这里是如果没有获取权限
            requestPermission.requestPermissions(); //没有获取权限就申请权限，requestPermission是一开始声明的获取权限的对象
    }
}
