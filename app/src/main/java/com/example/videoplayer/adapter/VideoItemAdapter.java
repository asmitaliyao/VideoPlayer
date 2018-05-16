package com.example.videoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.videoplayer.R;
import com.example.videoplayer.entity.VideoEntity;
import com.example.videoplayer.util.SearchVideo;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 手机视频数据添加到列表的适配器
 */
public class VideoItemAdapter extends BaseAdapter {
    private List<VideoEntity> videoEntityList;  //定义视频数据集合
    private Context context;    //界面上下文

    public VideoItemAdapter(List<VideoEntity> videoEntityList, Context context) {
        this.videoEntityList = videoEntityList; //获取视频数据集合，赋值
        this.context = context; //赋值界面上下文
    }

    @Override
    public int getCount() { //返回视频数据的个数
        return videoEntityList.size();
    }

    @Override
    public Object getItem(int position) {   //返回第position个视频数据
        return videoEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {   //返回当前视频是第几个
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;  //定义一个viewHolder
        if (convertView == null) {      //获取界面布局
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.activity_video_item, null);
            viewHolder.imageView = convertView.findViewById(R.id.video_item_image_view);    //获取界面的图片imageView
            viewHolder.textView = convertView.findViewById(R.id.video_item_text_view);  //获取界面的标题名字textView
            viewHolder.sizeTextView = convertView.findViewById(R.id.video_item_size_text_view); //获取界面的大小文字sizeTextView
            viewHolder.durationTextView = convertView.findViewById(R.id.video_item_duration_text_view); //获取界面的时长文字durationTextView
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); //加载速度优化的操作
        }

        VideoEntity videoEntity = videoEntityList.get(position);    //获取一个视频数据的内容

        Glide.with(context).load(videoEntity.getUri_thumb()).into(viewHolder.imageView);    //加载视频图片数据到单个列表项的位置

        viewHolder.textView.setText(videoEntity.getTitle());    //设置标题

        DecimalFormat decimalFormat=new DecimalFormat(".00");
        float size =( (float) videoEntity.getSize() ) / 1024 / 1024;
        viewHolder.sizeTextView.setText(decimalFormat.format(size) + "MB"); //设置视频大小

        long duration = videoEntity.getDuration();
        viewHolder.durationTextView.setText(SearchVideo.formatTime(duration));  //设置时长

        return convertView; //返回这个列表项
    }

    class ViewHolder {  //列表项布局，加载速度优化的操作
        public ImageView imageView;
        public TextView textView;
        public TextView sizeTextView;
        public TextView durationTextView;
    }
}
