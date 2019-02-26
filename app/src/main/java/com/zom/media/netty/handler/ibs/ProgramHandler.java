package com.zom.media.netty.handler.ibs;

import android.util.Log;

import com.zom.media.queue.DownLoadQueue;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.sql.service.ProgramService;
import com.zom.media.sql.service.impl.ProgramServiceImpl;
import com.zom.media.util.GsonUtil;

/**
 * Created by USERA on 2019/2/25.
 */

public class ProgramHandler extends BaseIbsHandler{

    private ProgramService programService = new ProgramServiceImpl();
//    private DownLoadQueue downLoadQueue = DownLoadQueue.getInstance();

    @Override
    public void handler(Object data) {
        String json = GsonUtil.GsonString(data);
        Log.e("ProgramHandler","json");
        ProgramVO programVO = GsonUtil.GsonToBean(json,ProgramVO.class);
        programService.addProgram(programVO);

//        downLoadQueue.addQueue(programVO);
    }
}
