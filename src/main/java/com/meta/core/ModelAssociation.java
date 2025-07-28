package com.meta.core;

import com.meta.core.model.ModelBean;

import java.util.List;

/**
 * 模型关联定义, 模型执行参数来源
 * 1.用户直接输入原始数据, 比如最基础数据人员信息
 * 2.引用其他模型+用户输入
 *
 * 关联可以是一对多的关系(工资表关联薪酬档案,关联社保,考勤), 也可以是一对一(薪酬档案关联人员档案)
 */
public interface ModelAssociation {

    Association source();

    /**
     * 被关联的模型
     * @return
     */
    List<Association> target();
}
