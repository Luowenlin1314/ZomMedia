package com.zom.media.widget;

/**
 * Created by USERA on 2019/2/25.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;


/**
 * 作者：Sky on 2018/3/5.
 * 用途：视频播放控件：暂停、播放、记忆、音量控制
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class YTVideoView extends SurfaceView implements BaseView{
    public static final String TAG = "YTVideoView";
    private MediaPlayer mPlayer;
    private SurfaceHolder mSurfaceHolder;
    private String path;

    private OnPreparedListener mOnPreparedListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;

    private String localPath;
    private List<String> fileList;
    private int index = 0;

    public YTVideoView(Context context) {
        this(context, null);
    }

    public YTVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YTVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        localPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        initYTVideoView();
    }

    private void initYTVideoView() {

        mPlayer = new MediaPlayer();

        //对 MediaPlayer 的各种监听事件
        mPlayer.setOnPreparedListener(mPreparedListener);
        mPlayer.setOnCompletionListener(mCompletionListener);
        mPlayer.setOnErrorListener(mErrorListener);
        mPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener);
        mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setScreenOnWhilePlaying(true);
        getHolder().addCallback(mSHCallback);
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next();
            }
        });
    }

    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.e("ZM", "surfaceCreated");
            mSurfaceHolder = holder;
            if (mPlayer != null) {
                mPlayer.setDisplay(mSurfaceHolder);
                if (path != null) {
                    play();
                }
            } else {
                openMediaPlayer();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            mSurfaceHolder = holder;

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mPlayer != null) {
                mPlayer.reset();
                mPlayer.release();
                mPlayer = null;
            }

            if (holder != null) {
                holder.getSurface().release();
            }
        }

    };

    protected void openMediaPlayer() {
        if (path == null || mSurfaceHolder == null) {
            return;
        }
        try {
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
            }
            if (mPlayer != null) {
                mPlayer.reset();
                mPlayer.setOnPreparedListener(mPreparedListener);
                mPlayer.setOnCompletionListener(mCompletionListener);
                mPlayer.setOnErrorListener(mErrorListener);
                mPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener);
                mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
                mPlayer.setDisplay(this.getHolder());//设置通过surfaceView来显示
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setScreenOnWhilePlaying(true);
                //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
                mPlayer.setDataSource(path);//设置视频播放路径
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mPlayer.prepareAsync();//视频播放异步准备状态
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            path = localPath + fileList.get(index);
            mPlayer.reset();
            mPlayer.setDataSource(path);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void next() {
        index++;
        if(index > (fileList.size() - 1)){
            index = 0;
        }
        play();
    }

    @Override
    public void stop() {
        try {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnPreparedListener mPreparedListener = new OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mOnPreparedListener != null) {
                mOnPreparedListener.onPrepared(mp);
            } else {
                mp.start();
            }
        }
    };
    private OnCompletionListener mCompletionListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(mp);
            }
        }
    };
    private OnErrorListener mErrorListener = new OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            if (mOnErrorListener != null) {
                mOnErrorListener.onError(mp, what, extra);
                return true;
            }
            return true;
        }
    };
    private OnVideoSizeChangedListener mVideoSizeChangedListener = new OnVideoSizeChangedListener() {

        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            if (mOnVideoSizeChangedListener != null) {
                mOnVideoSizeChangedListener.onVideoSizeChanged(mp, width,
                        height);
            } else {
                int mVideoWidth = mp.getVideoWidth();
                int mVideoHeight = mp.getVideoHeight();
                if (mVideoWidth != 0 && mVideoHeight != 0) {
                    getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                }
            }
        }
    };
    private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mOnBufferingUpdateListener != null) {
                mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };

    public void setOnPreparedListener(OnPreparedListener listener) {
        mOnPreparedListener = listener;
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        mOnCompletionListener = listener;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        mOnErrorListener = listener;
    }

    public void setOnBufferListener(OnBufferingUpdateListener listener) {
        mOnBufferingUpdateListener = listener;
    }

    /**
     * 当前是否在播放
     *
     * @return
     */
    public boolean isPlaying() {
        try {
            if (mPlayer != null) {
                return mPlayer.isPlaying();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置播放地址
     *
     * @param currentPlayFilePath
     */
    public void setVideoPath(String currentPlayFilePath) {
        path = currentPlayFilePath;
        //但是这样可能无法无缝切换(主要是轮播下一个视频的时候,每次重新new 一个 MediaPlayer 则无法做到无缝播放)
        openMediaPlayer();
    }

    public String getVideoPath() {
        return path;
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    public int getCurrentPosition() {
        if (mPlayer != null) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 跳转到指定进度
     *
     * @param currentPosition
     */
    public void seekTo(int currentPosition) {
        if (mPlayer != null) {
            mPlayer.seekTo(currentPosition);
        }
    }

    /**
     * MediaPlayer 各项工作都准备好后，
     * 调用该方法，开始播放视频
     */
    public void start() {
        if (mPlayer != null) {
            mPlayer.start(); //开始播放视频
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }


    public MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    public void setFileList(List<String> fileList){
        this.fileList = fileList;
    }

}
