package com.meta.core.field;

import com.meta.core.DataType;
import com.meta.core.field.handler.*;

import java.util.Map;

/**
 * 字段类型, 对应前端控件类型, 不是底层数据库类型
 * 字段类型扩展性设计
 * 1.枚举+策略(策略采用子类,也保留了枚举,对于要经常访问字段类型, 这种方法较好)
 * 2.枚举+枚举方法(枚举定义方法, 每个枚举重写)
 * 3.接口+子类(每个子类一个字段类型,本质和枚举类似,只是枚举是编译自动生成)
 */
public enum FieldType implements FieldTypeHandler {

    TEXT("单行文本", DataType.STRING, "TextInput", false, new TextFieldType()),
//    TEXTAREA(String.class, "Textarea", false),
    NUMBER_DECIMAL("数字",DataType.NUMBER_DECIMAL, "NumberInput", false, new NumberFieldType()),
    NUMBER_INTEGER("整数",DataType.NUMBER_DECIMAL, "NumberInput", false, new NumberFieldType()),
//    BOOLEAN(Boolean.class, "Checkbox", false),
    SINGLE_SELECT("下拉框",DataType.STRING, "Select", true, new SingleSelectFieldType()),
//    MULTI_SELECT(List.class, "MultiSelect", true),
    RADIO("单选",DataType.STRING, "RadioGroup", true),
    DATE("日期", DataType.DATE, "DatePicker", false),
    MODEL("模型", DataType.MODEL, "Table", false, new ModelFieldType()),
//    DATETIME(LocalDateTime.class, "DateTimePicker", false),
//    FILE(List.class, "FileUpload", false),
//    PHONE(String.class, "PhoneInput", false),
//    EMAIL(String.class, "EmailInput", false),
//    RICH_TEXT(String.class, "RichTextEditor", false),
//    USER_PICKER(String.class, "UserPicker", true),
//    DEPT_PICKER(String.class, "DeptPicker", true)
    //RELATIONAL 关联关系字段, 可以引用另一个模型中的字段或动态生成
    //关联字段, 需要根据对应模型处理,
      ;

    private final DataType dataType;         // Java存储类型（内部值）
    private final String defaultComponent;   // 默认前端组件名（方便前后端一致）
    private final String displayName; //
    private final boolean optionBased;       // 是否基于选项（如下拉）
    private FieldTypeHandler<?> handler;

    FieldType(String displayName, DataType dataType, String defaultComponent, boolean optionBased) {
        this(displayName,dataType, defaultComponent, optionBased, null);
    }

    FieldType(String displayName, DataType dataType, String defaultComponent, boolean optionBased, FieldTypeHandler handler) {
        this.displayName = displayName;
        this.dataType = dataType;
        this.defaultComponent = defaultComponent;
        this.optionBased = optionBased;
        this.handler = handler;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getDefaultComponent() {
        return defaultComponent;
    }

    public boolean isOptionBased() {
        return optionBased;
    }


    //以下是策略方法, enum实现类似wrapper模式,代理给具体实现,不用注册代理服务
    @Override
    public String getTypeId() {
        return handler.getTypeId();
    }

    @Override
    public Class getStorageType() {
        return handler.getStorageType();
    }

    @Override
    public Object parseFromString(String input) {
        return handler.parseFromString(input);
    }

    @Override
    public Object parseFromUntyped(Object rawValue, ConversionContext context) throws FieldDataException {
        return handler.parseFromUntyped(rawValue,context);
    }

    @Override
    public String formatForDisplay(Object value, ConversionContext context) {
        //默认让handler处理, 默认实现统一调用dataType.convert
        return handler.formatForDisplay(value, context);
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public boolean validate(Object value, Map metadata) {
        return false;
    }

}

