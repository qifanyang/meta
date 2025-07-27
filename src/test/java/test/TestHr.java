package test;

import com.meta.MetaApplication;
import com.meta.app.model.hr.salary.SalaryTemplateModel;
import com.meta.core.dao.ModelDataDao;
import com.meta.core.entity.ModelDataEntity;
import com.meta.util.ModelDataHelper;
import com.meta.util.RepositoryLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestHr {

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
        ModelDataEntity modelData = salaryTemplateModel.run(params);
        modelDataHelper.saveEntity(modelData);
        System.out.println();


    }


}
