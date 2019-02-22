package com.zom.media.sql.entity.vo;

import com.zom.media.sql.entity.Element;
import com.zom.media.sql.entity.Material;

import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public class ElementVO extends Element {

    //元素素材列表
    private List<Material> materialList;

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }
}
