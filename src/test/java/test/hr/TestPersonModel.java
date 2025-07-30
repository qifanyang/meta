package test.hr;


import com.meta.MetaApplication;
import com.meta.app.model.hr.person.PersonModel;
import com.meta.app.model.hr.salary.SalaryArchiveModel;
import com.meta.core.dao.FieldDao;
import com.meta.core.dao.ModelDao;
import com.meta.core.entity.ModelDataEntity;
import com.meta.core.field.FieldBean;
import com.meta.util.db.ModelDataHelper;
import com.meta.util.db.ModelDataQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestPersonModel {

    @Autowired
    private ModelDataHelper modelDataHelper;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private PersonModel personModel;


    @Test
    public void testAddPersonModel(){
        //添加模型, 模型code应该是唯一, 不能重复添加
        modelDao.saveEntity(personModel.meta());
    }

    @Test
    public void testAddPersonModelField(){
        //保存模型字段
        List<FieldBean> fields = personModel.getFields();
        for (FieldBean field : fields) {
            fieldDao.saveEntity(field.meta());
        }
    }

    @Test
    public void testAddPersonData(){
        //测试模型执行
        Map<String, Object> params = new HashMap<>();
        //基本信息
        params.put("name", "张三");
        params.put("idNumber", "110");
        params.put("phone", "110");
//        ModelDataEntity modelData = personModel.run(params);
//        modelDataHelper.saveEntity(modelData);
    }

    @Test
    public void testModelQuery(){
        ModelDataQuery dataQuery = new ModelDataQuery();
        String sql = dataQuery.mainModel(SalaryArchiveModel.CODE, "person_id")
                .joinModel(PersonModel.CODE, "id")
                .condition("name", "张三")
                .sql();
        System.out.println(sql);
    }

}
