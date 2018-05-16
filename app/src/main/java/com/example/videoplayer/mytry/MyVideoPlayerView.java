package com.example.videoplayer.mytry;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.videoplayer.thread.SoundDecodeThread;
import com.example.videoplayer.thread.VideoDecodeThread;

public class MyVideoPlayerView extends SurfaceView implements SurfaceHolder.Callback {

    private String strVideo;

    private VideoDecodeThread thread;
    private SoundDecodeThread soundDecodeThread;
    public static boolean isCreate = false;

    public void setStrVideo(String strVideo) {
        this.strVideo = strVideo;
    }

    public MyVideoPlayerView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public MyVideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public MyVideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("VideoPlayView", "surfaceCreated");
        isCreate = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("VideoPlayView", "surfaceChanged");
        if (thread == null) {
            thread = new VideoDecodeThread(holder.getSurface(), strVideo);
            thread.start();
        }
        if (soundDecodeThread == null) {
            soundDecodeThread = new SoundDecodeThread(strVideo);
            soundDecodeThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("VideoPlayView", "surfaceDestroyed");
        isCreate = false;
        if (thread != null) {
            thread.interrupt();
        }
        if (soundDecodeThread != null) {
            soundDecodeThread.interrupt();
        }
    }

    public void start(){
        Log.e("VideoPlayView", "start");
        thread = new VideoDecodeThread(getHolder().getSurface(), strVideo);
        soundDecodeThread = new SoundDecodeThread(strVideo);
        soundDecodeThread.start();
        thread.start();
    }

    public void stop(){
        thread.destroy();
        soundDecodeThread.destroy();
//        thread.interrupt();
//        soundDecodeThread.interrupt();
    }
}
