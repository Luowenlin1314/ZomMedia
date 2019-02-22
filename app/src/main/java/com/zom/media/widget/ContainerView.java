package com.zom.media.widget;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.view.View;

import com.zom.media.R;

/**
 * Created by USERA on 2019/2/22.
 */

public class ContainerView extends PercentRelativeLayout {

    private PercentRelativeLayout rootView;

    public ContainerView(Context context) {
        this(context,null);
    }

    public ContainerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.activity_main,this);
        rootView = (PercentRelativeLayout) view.findViewById(R.id.root);
    }


}
