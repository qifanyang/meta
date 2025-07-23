package test;


import com.meta.MetaApplication;
import com.meta.app.model.salary.SalaryArchiveModel;
import com.meta.core.field.FieldType;
import com.meta.core.dao.FieldDao;
import com.meta.core.dao.ModelDao;
import com.meta.core.field.FieldBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestPresetModel {

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private SalaryArchiveModel salaryArchiveModel;

    @Test
    public void testCreateModelAndField(){

        SalaryArchiveModel modelBean = new SalaryArchiveModel();
        modelBean.addField(FieldBean.of("name", "姓名", FieldType.TEXT.name()));
        FieldBean ageField = FieldBean.of("age", "年龄", FieldType.NUMBER.name());
        ageField.setModelId(modelBean.getId());
        ageField.setModelCode(modelBean.getCode());
        modelBean.addField(ageField);
        modelDao.save(modelBean.meta());

        fieldDao.saveEntity(ageField.meta());

    }

    @Test
    public void testLoadModelField(){

        List<FieldBean> fields = salaryArchiveModel.getFields();
        System.out.println();


    }


}
