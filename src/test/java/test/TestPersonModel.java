package test;

import com.meta.MetaApplication;
import com.meta.core.BaseModel;
import com.meta.core.ScriptRunner;
import com.meta.core.dao.ModelDataDao;
import com.meta.core.entity.FieldEntity;
import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.FieldType;
import com.meta.core.ModelBean2;
import com.meta.core.dao.ModelDao;
import com.meta.core.surpport.GroovyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptException;
import java.util.List;
import java.util.Map;

/**
 * 组织人事模型
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestPersonModel {

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ModelDataDao modelDataDao;

    @Test
    public void testAddPersonModel(){

        //创建业务模型类或从数据库查询

        //创建业务模型字段或从数据库查询
        //1.创建业务模型自动构建预置字段,
        //2.查询自动合并新增的预置字段, 删除的用户自己控制

        //保存模型自动保存模型字段

//        PersonModel personModel = new PersonModel();


//        PersonFieldBean personFieldBean = new PersonFieldBean();

        ModelBean2 modelBean2 = new ModelBean2();
        modelBean2.setCode("person_model");
        modelBean2.setName("人员模型");
        modelBean2.setDataTable("person");
        modelBean2.addField(FieldBean.of("name", "姓名", FieldType.STRING.name()));
        modelBean2.addField(FieldBean.of("age", "年龄", FieldType.INTEGER.name()));
        modelDao.save(modelBean2.meta());


        //模型运行时产生的数据, 可以存储在通用模型表,
        // 也可以根据model上的dataTable指定特定表(对应表一定要存在, Entity创建对应表)
        //1. 先实现统一存储
        Map<String, Object> params = Map.of("a", 1, "b", 2);
        ModelDataEntity modelDataEntity = new ModelDataEntity();
        List<FieldBean> fields = modelBean2.getFields();
        for (FieldBean field : fields) {
            modelDataEntity.getFields().add((FieldEntity) field.meta());
            Object fieldValue = null;
            if (field.getExpression() != null && !field.getExpression().isBlank()) {
                fieldValue = scriptRunner().eval(params, field.getExpression());
            } else if (field.getAssociatedModel() != null) {
                Class<? extends BaseModel> associatedModel = field.getAssociatedModel();
//                fieldValue = runModel(associatedModel, params);
            }
            modelDataEntity.getFieldValues().put(field.getCode(), fieldValue);
        }

        modelDataDao.save(modelDataEntity);
    }

    public ScriptRunner scriptRunner() {
        return (bindings, script) -> {
            try {
                return GroovyUtil.run(bindings, script);
            } catch (ScriptException e) {
                throw new IllegalStateException(e);
            }
        };
    }




}
