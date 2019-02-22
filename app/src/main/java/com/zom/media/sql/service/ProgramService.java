package com.zom.media.sql.service;

import com.zom.media.sql.entity.vo.ProgramVO;

import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public interface ProgramService {

    /**
     * 添加主题
     * @param programvo
     * @return
     */
    int addProgram(ProgramVO programvo);

    /**
     * 获取最新主题
     * @return
     */
    ProgramVO getLastProgram();

}
