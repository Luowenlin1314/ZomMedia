package com.zom.media;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zom.media.base.ActivityFragmentInject;
import com.zom.media.base.BaseActivity;
import com.zom.media.sql.entity.CacheData;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.sql.service.ProgramService;
import com.zom.media.sql.service.impl.ProgramServiceImpl;
import com.zom.media.util.ProgramUtil;
import com.zom.media.widget.ContainerView;

import io.realm.Realm;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_main,
        hasNavigationView = false,
        hasToolbar = false
)
public class MainActivity extends BaseActivity {

    private ProgramService programService = new ProgramServiceImpl();
    private PercentRelativeLayout percentRelativeLayout;
    private ContainerView containerView;

    @Override
    protected void toHandleMessage(Message msg) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void findViewAfterViewCreate() {
        percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.root);
    }

    @Override
    protected void initDataAfterFindView() {
        initProgram();
    }

    private void initProgram(){
        ProgramVO programVO = programService.getLastProgram();
        containerView = ProgramUtil.buildProgramView(this,programVO);
        percentRelativeLayout.addView(containerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        containerView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerView.play();
    }


}
