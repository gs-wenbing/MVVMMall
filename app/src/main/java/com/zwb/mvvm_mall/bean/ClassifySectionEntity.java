package com.zwb.mvvm_mall.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class ClassifySectionEntity extends SectionEntity<ClassifyEntity> {

    public ClassifySectionEntity(ClassifyEntity classify) {
        super(classify);
    }

    public ClassifySectionEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }
}
