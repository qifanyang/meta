package test;


import com.meta.MetaApplication;
import com.meta.core.Field;
import com.meta.core.FieldType;
import com.meta.core.dao.FieldDao;
import com.meta.core.dao.ModelDao;
import com.meta.core.entity.FieldEntity;
import com.meta.core.entity.ModelEntity;
import com.meta.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaApplication.class)
@AutoConfigureMockMvc
public class TestMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private FieldDao fieldDao;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/addModel"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    public void testAddModelField(){
        String modelId = IdGenerator.nextId();
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setId(modelId);
        modelEntity.setName("测试模型字段");
        modelEntity.setTenantId("1");
        modelDao.save(modelEntity);

        FieldEntity field = new FieldEntity();
        field.setTenantId("1");
        field.setModelId(modelId);
        field.setCode("name");
        field.setName("姓名");
        field.setType(FieldType.STRING.name());
        fieldDao.save(field);
    }
}
