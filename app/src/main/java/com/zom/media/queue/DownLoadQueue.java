package com.zom.media.queue;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zom.media.base.MyAppLication;
import com.zom.media.entity.FtpResult;
import com.zom.media.sql.entity.Material;
import com.zom.media.sql.entity.vo.ElementVO;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.util.Config;
import com.zom.media.util.FTPUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by USERA on 2019/2/25.
 */

public class DownLoadQueue implements Runnable {

    private String TAG = "DownLoadQueue";
    private static DownLoadQueue downLoadQueue = new DownLoadQueue();
    private LinkedBlockingDeque<ProgramVO> linkedDeque = new LinkedBlockingDeque();
    private ExecutorService exec;
    private FTPUtil ftpUtil;

    public static DownLoadQueue getInstance(){
        return downLoadQueue;
    }

    private DownLoadQueue() {
        ftpUtil = new FTPUtil(Config.FTP_HOST, Config.FTP_PUSER, Config.FTP_PWD);
        exec = Executors.newFixedThreadPool(1);
        exec.execute(this);
    }

    public void stop() {
        exec.shutdown();
    }

    public void addQueue(ProgramVO msg) {
        Log.i(TAG, "添加消息：" + msg);
        linkedDeque.add(msg);
    }

    public void delQueue(ProgramVO msg) {
        Log.i(TAG, "删除消息：" + msg);
        linkedDeque.remove(msg);
    }

    @Override
    public void run() {
        Log.i(TAG, "下载消息队列启动");
        while (true) {
            ProgramVO programVO = null;
            try {
                programVO = linkedDeque.take();

                downLoad(programVO);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                delQueue(programVO);
            }
        }
    }

    private void downLoad(ProgramVO programVO) throws Exception{
        ftpUtil.openConnect();

        Long programId = programVO.getProgramId();
        ArrayList<Material> materials = new ArrayList<>();
        List<ElementVO> elementVOList = programVO.getElementList();
        for (ElementVO elementVO : elementVOList) {
            materials.addAll(elementVO.getMaterialList());
        }

        for (Material material : materials) {
            String localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zomMedia";
            File file = new File(localPath);
            if(!file.exists()){
                file.mkdirs();
            }
            try {
                FtpResult ftpResult = ftpUtil.download(material.getRelativePath(), material.getMaterialName(), localPath);
                if (ftpResult.isFlag()) {
                    Log.d(TAG,"下载成功：" + material.getMaterialName());
                } else {
                    Log.d(TAG,"下载失败：" + material.getMaterialName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ftpUtil.closeConnect();

        MyAppLication.sendReceiver(Config.ACTION_PROGRAM_UPDATE);
    }
}
