package test.hr;


import com.meta.MetaApplication;
import com.meta.app.model.hr.person.PersonModel;
import com.meta.app.model.hr.salary.SalaryArchiveModel;
import com.meta.core.dao.FieldDao;
import com.meta.core.dao.ModelDao;
import com.meta.core.entity.ModelDataEntity;
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
public class TestSalaryArchiveModel {

    @Autowired
    private ModelDataHelper modelDataHelper;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private SalaryArchiveModel salaryArchiveModel;

    @Test
    public void testAddSalaryArchiveData(){
        //测试模型执行
        Map<String, Object> params = new HashMap<>();
        //基本信息
//        params.put("personId", "224871545561026560");
        params.put("personId", "226735362297434112");
        params.put("wage", 888);

        List<ModelDataEntity> dataEntities = salaryArchiveModel.run(params);
        for (ModelDataEntity dataEntity : dataEntities) {
            modelDataHelper.saveEntity(dataEntity);
        }
    }

    @Test
    public void testJoinModelQuery(){
        List<Map<String, Object>> dataList = ModelDataQuery.builder()
                .mainModel(SalaryArchiveModel.CODE, "person_id")
                .joinModel(PersonModel.CODE, "id")
                .condition("name", "张三") //字段名如果有重名, 可扩展指定采用那个模型
                .execute();
        System.out.println(dataList);
    }
}
