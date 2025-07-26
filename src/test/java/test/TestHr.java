package test;

import com.meta.MetaApplication;
import com.meta.app.model.hr.salary.SalaryTemplateModel;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestHr {

    @Autowired
    private SalaryTemplateModel salaryTemplateModel;


}
