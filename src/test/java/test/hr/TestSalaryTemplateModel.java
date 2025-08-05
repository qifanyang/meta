package test.hr;


import com.meta.MetaApplication;
import com.meta.app.model.hr.person.PersonModel;
import com.meta.app.model.hr.salary.SalaryArchiveModel;
import com.meta.app.model.hr.salary.SalaryTemplateModel;
import com.meta.core.model.ModelInput;
import com.meta.core.entity.ModelDataEntity;
import com.meta.util.db.ModelDataHelper;
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
public class TestSalaryTemplateModel {

    @Autowired
    private ModelDataHelper modelDataHelper;

    @Autowired
    private SalaryTemplateModel salaryTemplateModel;

    /**
     * 迭代执行输入
     */
    @Test
    public void testGenerateSalaryTemplate(){

        Map<String, Object> params = new HashMap<>();
        //社保
        params.put("pi", 12);
        params.put("fund", 99);

        //考勤
        params.put("workDays", 20);
        params.put("leaveDays", 2);

        params.put("year", 2025);
        params.put("month", 7);
        params.put("grandTax", 10000);

        //工资表初始化数据
        params.put("incomeType", 0);
        params.put("taxAgent", "中国公司");
//        params.put("personCount", 0);
//        params.put("payableTotal", 0);
        params.put("realWageTotal", 0);

        ModelInput paramsInput = new ModelInput();
        paramsInput.setParams(params);

        ModelInput mainInput = new ModelInput();
        mainInput.setMainInput(true);
        mainInput.setModelCode(SalaryArchiveModel.CODE);
        mainInput.setJoinKey("person_id");

        ModelInput input = new ModelInput();
        input.setModelCode(PersonModel.CODE);
        input.setJoinKey("id");

        List<ModelDataEntity> entities = salaryTemplateModel.run(List.of(paramsInput, mainInput, input));
        entities.forEach(modelDataHelper::saveEntity);


    }
}
