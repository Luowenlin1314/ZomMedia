package com.zom.media.netty.handler.ibs;

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
        ProgramVO programVO = (ProgramVO) GsonUtil.jsonToList(GsonUtil.GsonString(data),ProgramVO.class);
        programService.addProgram(programVO);

//        downLoadQueue.addQueue(programVO);
    }
}
