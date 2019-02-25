package com.zom.media.widget;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zom.media.R;
import com.zom.media.util.Config;

import java.io.File;
import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public class ZomImageView extends ImageView implements BaseView{

    private String TAG = "ZomImageView";
    private String localPath;
    private List<String> fileList;
    private List<Long> durations;
    private boolean showing = false;
    private int index = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Config.RPOGRAM_NEXT :
                    next();
                    break;
            }
        }
    };

    public ZomImageView(Context context) {
        this(context,null);
    }

    public ZomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        localPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 设置内容
     * @param fileList
     */
    public void setFileList(List<String> fileList){
        this.fileList = fileList;
    }

    /**
     * 设置时间
     * @param durations
     */
    public void setDuration(List<Long> durations){
        this.durations = durations;
    }

    /**
     * 开始
     */
    public void play(){
        Log.e(TAG,"播放图片：" + index);
        showing = true;
        String filePath = localPath + fileList.get(index);
        Glide.with(getContext()).load(new File(filePath)).into(this);
        handler.sendEmptyMessageDelayed(Config.RPOGRAM_NEXT, durations.get(index));
    }

    /**
     * next
     */
    public void next(){
        if(showing){
            index++;
            if(index > (fileList.size() - 1)){
                index = 0;
            }
            play();
        }
    }

    public void stop(){
        showing = false;
    }

}
