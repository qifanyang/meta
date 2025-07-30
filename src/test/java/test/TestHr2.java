package test;

import com.meta.MetaApplication;
import com.meta.app.model.hr.salary.SalaryTemplateModel;
import com.meta.core.dao.ModelDataDao;
import com.meta.core.entity.ModelDataEntity;
import com.meta.util.db.ModelDataHelper;
import com.meta.util.db.RepositoryLocator;
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
public class TestHr2 {

    @Autowired
    private SalaryTemplateModel salaryTemplateModel;

    @Autowired
    private ModelDataDao modelDataDao;

    @Autowired
    private RepositoryLocator repositoryLocator;

    @Autowired
    private ModelDataHelper modelDataHelper;

    @Test
    public void testSalaryTemplate(){
        Map<String, Object> params = new HashMap<>();
        //基本信息
        params.put("name", "张三");
        params.put("idNumber", "110");
        params.put("phone", "110");

        //社保
        params.put("pi", 12);
        params.put("fund", 99);

        //考勤
        params.put("workDays", 20);
        params.put("leaveDays", 2);

        //薪酬
        params.put("wage", 1000);
        params.put("grandTax", 10000);
        params.put("year", 2025);
        params.put("month", 7);

        //工资表初始化数据
        params.put("incomeType", 0);
        params.put("taxAgent", "中国公司");
        params.put("personCount", 0);
        params.put("payableTotal", 0);
        params.put("realWageTotal", 0);

        //1.0要手动填写数据
        //2.0通过配置的关联数据直接拉取
        //入口还是salaryTemplateModel.run(params);
        //还是有部分参数需要通过用户传入, 比如选择年月
        //执行优先检查有没有关联

        List<ModelDataEntity> modelData = salaryTemplateModel.run(params);
        modelData.forEach(modelDataHelper::saveEntity);
        System.out.println();


    }


}
