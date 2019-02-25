package com.zom.media.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zom.media.R;

/**
 * Created by USERA on 2019/2/25.
 */

public class ZomNoneView extends ImageView implements BaseView{

    public ZomNoneView(Context context) {
        this(context,null);
    }

    public ZomNoneView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZomNoneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setScaleType(ScaleType.FIT_XY);
        this.setBackgroundResource(R.mipmap.img_error);
    }

    @Override
    public void play() {

    }

    @Override
    public void next() {

    }

    @Override
    public void stop() {

    }
}
