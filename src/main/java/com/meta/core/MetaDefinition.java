package com.meta.core;

import java.util.Map;

public interface MetaDefinition extends Meta {

    /**
     * field编码代表字段变量名, 在模型内code一定唯一, 跨模型不一定唯一
     * model编码在应用内唯一
     *
     * @return 编码
     */
    String getCode();

    void setCode(String code);

    /**
     * 中文名, 国际化?
     *
     * @return
     */
    String getName();

    void setName(String name);

    /**
     * 多个便签使用冒号:分割, 单个标签内可使用小数点.隔开表示层级
     * tab.basic.home:xx.yy.xx
     * <p>
     * tag 标签, 比如个人信息, 教育信息, 家庭信息
     * tag数据很重要 model下面管理tag
     * tab 模型下面管理tab, 用于展示详情, 虽然有tag, 但是所有信息在一个页面也不合适, 所以有tab
     */
    String getTag();

    void setTag(String tag);

    Map getAttr();

    void setAttr(Map attr);

    void pre();

    void post();

}
