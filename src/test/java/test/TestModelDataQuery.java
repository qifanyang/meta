package test;


import com.meta.MetaApplication;
import com.meta.app.model.hr.person.PersonModel;
import com.meta.app.model.hr.salary.SalaryArchiveModel;
import com.meta.app.model.hr.social.SocialModel;
import com.meta.util.db.ModelDataQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestModelDataQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test(){
        ModelDataQuery dataQuery = new ModelDataQuery();
        String sql = dataQuery.mainModel(SalaryArchiveModel.CODE, "id")
                .joinModel(PersonModel.CODE, "id")
                .joinModel(SocialModel.CODE, "id")
                .condition("wage", 100)
                .sql();
        System.out.println(sql);
    }


}
