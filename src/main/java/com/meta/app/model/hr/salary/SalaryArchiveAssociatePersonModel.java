package com.meta.app.model.hr.salary;

import com.meta.core.model.ModelBean;
import org.springframework.stereotype.Component;


//其实薪酬档案本身就是关联模型
//手动添加薪酬档案,
//定义档案自己本身的字段 + 引用花名册的字段(不可编辑, 展示或不展示, 比如person_id = person id)
//关联模型需要制定关联字段, 一般是当前模型新增一个字段关联另外一个字段的id
//比如关联person模型, 则字段为person_id

//新建工资表引用的考勤,社保数据, 叫关联么?
//使用了年月+person_id确定唯一数据, 这个也算关联, 只是不是通过id关联

//结论
// 模型可以关联其他多个模型, 需要制定关联字段(可多个)
// 迭代执行只在一个关联模型上, 其它的关联用作数据拉取(比如引用社保数据, 关联字段仍是person_id)
@Component(SalaryArchiveAssociatePersonModel.CODE)
public class SalaryArchiveAssociatePersonModel extends ModelBean {
    public static final String CODE = "meta_salary_archive_associate_person";

    public SalaryArchiveAssociatePersonModel(){
        setCode(CODE);
        setName("薪酬档案");
    }

}
