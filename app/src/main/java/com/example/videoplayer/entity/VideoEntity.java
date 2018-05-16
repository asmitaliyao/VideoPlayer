package com.example.videoplayer.entity;

/**
 * 视频实体类
 */
public class VideoEntity {
    private String title;   //标题
    private String path;    //路径
    private long size;  //大小
    private String uri_thumb;   //图片索引
    private long duration;  //时长

    //下面是get和set方法和带参和不带参方法
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUri_thumb() {
        return uri_thumb;
    }

    public void setUri_thumb(String uri_thumb) {
        this.uri_thumb = uri_thumb;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public VideoEntity(String title, String path, long size, String uri_thumb, long duration) {

        this.title = title;
        this.path = path;
        this.size = size;
        this.uri_thumb = uri_thumb;
        this.duration = duration;
    }

    public VideoEntity() {
    }
}
