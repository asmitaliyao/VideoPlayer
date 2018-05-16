package com.example.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.videoplayer.R;
import com.example.videoplayer.adapter.VideoItemAdapter;
import com.example.videoplayer.entity.VideoEntity;
import com.example.videoplayer.mytry.MyVideoPlayerActivity;
import com.example.videoplayer.util.SearchVideo;

import java.text.DecimalFormat;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.backPress;

/**
 * 播放器主界面
 */
public class VideoPlayerActivity extends AppCompatActivity {
    private List<VideoEntity> videoEntityList;  //视频数据的集合
    private ListView videoListView; //界面展示列表ListView
    private JCVideoPlayerStandard jcVideoPlayerStandard;    //JCVideoPlayerStandard播放器框架
    private View headerView;    //ListView头部信息块
    private TextView headerTitleTextView;   //信息块的标题文字
    private TextView headerPathTextView;    //信息块的路径文字
    private TextView headerSizeTextView;    //信息块的大小文字
    private TextView headerDurationTextView;    //信息块的时长文字

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player); //设置界面

        videoEntityList = SearchVideo.searchVideo(VideoPlayerActivity.this);    //获取视频数据
        videoListView = findViewById(R.id.video_list_view); //关联界面的列表

        headerView = getLayoutInflater().inflate(R.layout.activity_video_header, null); //关联信息块的布局
        videoListView.addHeaderView(headerView);    //列表顶部放上信息块

        //列表设置适配器，将视频数据和列表行的布局适配起来，放入列表中
        videoListView.setAdapter(new VideoItemAdapter(videoEntityList,VideoPlayerActivity.this));
        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //设置单个列表项的点击事件
                VideoEntity videoEntity = videoEntityList.get(position - 1);    //添加headView后position第一个为headView
                //设置播放器的播放数据，和视频上显示的文字
                jcVideoPlayerStandard.setUp(videoEntity.getPath(),JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,videoEntity.getTitle());
                jcVideoPlayerStandard.startButton.callOnClick();    //启动播放器的播放按钮，开始播放

                headerTitleTextView = headerView.findViewById(R.id.video_header_title_text_view);
                headerTitleTextView.setText(videoEntity.getTitle());    //找到并设置播放的视频的标题
                headerPathTextView = headerView.findViewById(R.id.video_header_path_text_view);
                headerPathTextView.setText(videoEntity.getPath());  //找到并设置播放的视频的路径

                DecimalFormat decimalFormat=new DecimalFormat(".00");
                float size =( (float) videoEntity.getSize() ) / 1024 / 1024;    //找到并格式化视频大小数据
                headerSizeTextView = headerView.findViewById(R.id.video_header_size_text_view);
                headerSizeTextView.setText(decimalFormat.format(size) + "MB");  //找到并设置播放的视频的大小

                headerDurationTextView = headerView.findViewById(R.id.video_header_duration_text_view);
                headerDurationTextView.setText(SearchVideo.formatTime(videoEntity.getDuration()));  //找到并设置播放的视频的时长，设置前格式化

            }
        });

        /**
         * 取消注释后长按可跳转进行自解码视频播放，存在诸多bug，可自行研究。
         */
        videoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(VideoPlayerActivity.this, MyVideoPlayerActivity.class);
                intent.putExtra("path",videoEntityList.get(position - 1).getPath());
                startActivity(intent);
                return true;
            }
        });

        jcVideoPlayerStandard = findViewById(R.id.jiecao_player);//设置播放器的播放数据，和视频上显示的文字，不点击默认播放第一个视频
        jcVideoPlayerStandard.setUp(videoEntityList.get(0).getPath(),JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,videoEntityList.get(0).getTitle());

        headerTitleTextView = headerView.findViewById(R.id.video_header_title_text_view);
        headerTitleTextView.setText(videoEntityList.get(0).getTitle()); //找到并设置播放的视频的标题
        headerPathTextView = headerView.findViewById(R.id.video_header_path_text_view);
        headerPathTextView.setText(videoEntityList.get(0).getPath());   //找到并设置播放的视频的路径

        DecimalFormat decimalFormat=new DecimalFormat(".00");
        float size =( (float) videoEntityList.get(0).getSize() ) / 1024 / 1024; //找到并格式化视频大小数据
        headerSizeTextView = headerView.findViewById(R.id.video_header_size_text_view);
        headerSizeTextView.setText(decimalFormat.format(size) + "MB");  //找到并设置播放的视频的大小

        headerDurationTextView = headerView.findViewById(R.id.video_header_duration_text_view);
        headerDurationTextView.setText(SearchVideo.formatTime(videoEntityList.get(0).getDuration()));   //找到并设置播放的视频的时长，设置前格式化


        jcVideoPlayerStandard.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //点击全屏按钮的事件
                if (jcVideoPlayerStandard.currentState == CURRENT_STATE_AUTO_COMPLETE) return;
                if (jcVideoPlayerStandard.currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                    //quit fullscreen
                    backPress();
                } else {
                    jcVideoPlayerStandard.onEvent(JCUserAction.ON_ENTER_FULLSCREEN);
                    jcVideoPlayerStandard.startWindowFullscreen();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {   //点击播放器返回按钮
        if (backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {  //界面不显示时调用的方法
        super.onPause();
        jcVideoPlayerStandard.release();    //释放播放资源
    }
}
