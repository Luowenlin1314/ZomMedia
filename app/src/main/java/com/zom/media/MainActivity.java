package com.zom.media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.zom.media.sql.entity.Program;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.sql.service.ProgramService;
import com.zom.media.sql.service.impl.ProgramServiceImpl;
import com.zom.media.util.Config;
import com.zom.media.util.GsonUtil;
import com.zom.media.util.PreferenceUtils;
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
    private UpdateProgramReceive updateProgramReceive;

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
    protected void onDestroy() {
        super.onDestroy();
        unregistProgramReceiver();
    }

    @Override
    protected void findViewAfterViewCreate() {
        registProgramReceiver();
        percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.root);
    }

    @Override
    protected void initDataAfterFindView() {
//        String json = "{\"programId\":1,\"corpId\":100,\"programName\":\"test\",\"type\":1,\"size\":500000.0,\"duration\":10000,\"status\":1,\"remark\":null,\"createBy\":null,\"createTime\":\"2019-02-26 13:40:54\",\"updateBy\":null,\"updateTime\":null,\"elementList\":[{\"elementId\":1,\"programId\":1,\"elementName\":\"tp\",\"type\":1,\"background\":null,\"left\":0,\"top\":0,\"width\":50,\"height\":50,\"index\":1,\"duration\":5000,\"effect\":1,\"mode\":1,\"remark\":null,\"materialList\":[{\"materialId\":1,\"corpId\":null,\"md5\":null,\"materialName\":\"tp1.jpg\",\"type\":null,\"size\":null,\"status\":null,\"duration\":5000,\"standard\":null,\"relativePath\":\"/materials/image\",\"thumbnailPath\":null,\"remark\":null,\"createBy\":null,\"createTime\":null,\"auditBy\":null,\"auditTime\":null},{\"materialId\":2,\"corpId\":null,\"md5\":null,\"materialName\":\"tp2.jpg\",\"type\":null,\"size\":null,\"status\":null,\"duration\":5000,\"standard\":null,\"relativePath\":\"/materials/image\",\"thumbnailPath\":null,\"remark\":null,\"createBy\":null,\"createTime\":null,\"auditBy\":null,\"auditTime\":null}]}]}";
//        programService.addProgram(GsonUtil.GsonToBean(json, ProgramVO.class));
        initProgram();
    }

    private void initProgram(){
        percentRelativeLayout.removeAllViews();
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

    private void registProgramReceiver(){
        updateProgramReceive = new UpdateProgramReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ACTION_PROGRAM_UPDATE);
        registerReceiver(updateProgramReceive,intentFilter);
    }

    private void unregistProgramReceiver(){
        unregisterReceiver(updateProgramReceive);
    }

    private class UpdateProgramReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            updateProgram();
        }
    }

    private void updateProgram(){
        initProgram();
        containerView.play();
    }
}
