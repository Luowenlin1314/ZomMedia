package com.zom.media.sql.entity.vo;

import com.zom.media.sql.entity.Program;

import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public class ProgramVO extends Program{

    //主题元素
    private List<ElementVO> elementList;

    public List<ElementVO> getElementList() {
        return elementList;
    }

    public void setElementList(List<ElementVO> elementList) {
        this.elementList = elementList;
    }
}
