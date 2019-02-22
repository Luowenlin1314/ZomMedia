package com.zom.media;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;

import com.zom.media.base.ActivityFragmentInject;
import com.zom.media.base.BaseActivity;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.sql.service.ProgramService;
import com.zom.media.sql.service.impl.ProgramServiceImpl;

import io.realm.Realm;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_main,
        hasNavigationView = false,
        hasToolbar = false
)
public class MainActivity extends BaseActivity {

    private PercentRelativeLayout percentRelativeLayout;
    private ProgramService programService = new ProgramServiceImpl();

    @Override
    protected void toHandleMessage(Message msg) {

    }

    @Override
    protected void findViewAfterViewCreate() {
        percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.root);
    }

    @Override
    protected void initDataAfterFindView() {

    }

    private void initProgram(){
        ProgramVO programVO = programService.getLastProgram();


    }
}
