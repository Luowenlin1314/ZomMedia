package com.zom.media.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

import java.io.File;
import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public class ZomVideoView extends VideoView implements BaseView{

    private String TAG = "ZomVideoView";
    private String localPath;
    private List<String> fileList;
    private int index = 0;

    public ZomVideoView(Context context) {
        this(context,null);
    }

    public ZomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zomMedia/";
        setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next();
            }
        });
    }

    public void setFileList(List<String> fileList){
        this.fileList = fileList;
    }

    public void play(){
        Log.e(TAG,"播放视频：" + index);
        String file = localPath + fileList.get(index);
        setVideoPath(file);
        start();
    }

    public void next(){
        index++;
        if(index > (fileList.size() - 1)){
            index = 0;
        }
        play();
    }

    public void stop(){
        stopPlayback();
    }

}
