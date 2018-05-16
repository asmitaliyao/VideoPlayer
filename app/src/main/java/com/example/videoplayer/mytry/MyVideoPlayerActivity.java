package com.example.videoplayer.mytry;

import android.content.Intent;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.videoplayer.R;

import java.util.HashMap;

public class MyVideoPlayerActivity extends AppCompatActivity {
    MyVideoPlayerView playView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video_player);
        playView = findViewById(R.id.player);

        Intent intent = getIntent();
        if (intent.getStringExtra("path") != null) {
            playView.setStrVideo(intent.getStringExtra("path"));
        }


        //获取所支持的编码信息的方法
        HashMap<String, MediaCodecInfo.CodecCapabilities> mEncoderInfos = new HashMap<>();
        for(int i = MediaCodecList.getCodecCount() - 1; i >= 0; i--){
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if(codecInfo.isEncoder()){
                for(String t : codecInfo.getSupportedTypes()){
                    try{
                        mEncoderInfos.put(t, codecInfo.getCapabilitiesForType(t));
                    } catch(IllegalArgumentException e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        playView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.stop();
    }


}
