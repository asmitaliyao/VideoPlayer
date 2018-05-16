package com.example.videoplayer.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.videoplayer.entity.VideoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索视频数据的工具类
 */
public class SearchVideo {
    /**
     * 搜索返回本地视频文件的信息
     * @param activity
     * @return
     */
    public static List<VideoEntity> searchVideo(Activity activity) {
        List<VideoEntity> videoEntityList = new ArrayList<>();  //视频文件的临时存放集合
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  //判断内部存储卡是否已经挂载
            Uri originalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  //获取视频文件所需的Uri，安卓系统设定的
            ContentResolver contentResolver = activity.getApplication().getContentResolver();   //获取系统的ContentResolver
            Cursor cursor = contentResolver.query(originalUri, null, null, null, null); //通过ContentResolver查询指定视频Uri的数据内容
            if (cursor != null) {   //如果获取到数据
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {    //遍历获取到的数据
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));    //从数据中提取视频标题
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));  //从数据中提取视频路径
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));  //从数据中提取视频大小
                    long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));   //从数据中提取视频时长
                    //获取当前Video对应的Id，然后根据该ID获取其缩略图的uri
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    //通过缩略图的uri来获取缩略图的查找语句
                    String[] selectionArgs = new String[]{id + ""};
                    String[] thumbColumns = new String[]{MediaStore.Video.Thumbnails.DATA,
                            MediaStore.Video.Thumbnails.VIDEO_ID};
                    String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";

                    String uri_thumb = "";
                    //得到查找到的缩略图结果
                    Cursor thumbCursor = (activity.getApplicationContext().getContentResolver()).query(
                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs,
                            null);

                    if (thumbCursor != null && thumbCursor.moveToFirst()) { //缩略图查找结果存在
                        uri_thumb = thumbCursor
                                .getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));    //从数据中提取视频缩略图

                    }

                    VideoEntity videoEntity = new VideoEntity(title, path, size, uri_thumb, duration);  //把视频数据放入视频实体类的实例中

                    videoEntityList.add(videoEntity);   //把视频实体类的实例放入集合

                }

                cursor.close(); //关闭cursor
            }
        }
        return videoEntityList; //返回结果集合
    }

    /**
     * 格式化时间，输出格式为 00:00
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;
        } else {
            return time / 1000 / 60 + ":" +time / 1000 % 60;
        }
    }
}
