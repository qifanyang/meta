package test;

import com.meta.MetaApplication;
import com.meta.core.ScriptRunner;
import com.meta.core.dao.ModelDataDao;
import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import com.meta.core.field.FieldType;
import com.meta.core.model.ModelRunOptions;
import com.meta.core.model.ModelBean;
import com.meta.core.dao.ModelDao;
import com.meta.core.model.ext.MailModel;
import com.meta.core.model.ext.MailModelDataEntity;
import com.meta.core.surpport.GroovyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptException;
import java.util.Map;

/**
 * 组织人事模型
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestModelData {

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ModelDataDao modelDataDao;

    @Test
    public void testAddModelData(){


        ModelBean modelBean = new ModelBean();
        modelBean.setCode("mail_model");
        modelBean.setName("邮件模型");
        //但是低代码构建时只能设置dataTableName
        modelBean.setDataTable(MailModelDataEntity.TABLE_NAME);
        modelBean.addField(FieldBean.of("name", "姓名", FieldType.TEXT.name()));
        modelBean.addField(FieldBean.of("age", "年龄", FieldType.NUMBER_DECIMAL.name()));
        modelDao.save(modelBean.meta());

        //模型运行时产生的数据, 可以存储在通用模型表,
        // 也可以根据model上的dataTable指定特定表(对应表一定要存在, Entity创建对应表)
        //1.统一存储
        {
            Map<String, Object> params = Map.of("a", 1, "b", 2);
            ModelDataEntity modelDataEntity = modelBean.run(params);
            modelDataDao.save(modelDataEntity);
        }
        //2.可扩展模型存储, 数据表独立
        {
            ModelRunOptions options = new ModelRunOptions();
            options.setDataEntityClass(MailModelDataEntity.class);

            Map<String, Object> params = Map.of("a", 1, "b", 2);

            ModelDataEntity mailModelData = modelBean.run(params, options);

            modelDataDao.save(mailModelData);
        }


    }


    /**
     * 模型元数据可扩展
     */
    @Test
    public void testMailModel(){
        MailModel mailModel = new MailModel();
        mailModel.setCode("mail_model");
        mailModel.setName("邮件模型");
        mailModel.setSmtp("http://smtp.qq.com");
        modelDao.saveEntity(mailModel.meta());
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
