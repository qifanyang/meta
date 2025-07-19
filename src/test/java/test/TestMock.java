package test;


import com.meta.MetaApplication;
import com.meta.core.FieldType;
import com.meta.core.MailField;
import com.meta.core.dao.FieldDao;
import com.meta.core.dao.MailFieldDao;
import com.meta.core.dao.ModelDao;
import com.meta.core.entity.FieldEntity;
import com.meta.core.entity.MailFieldEntity;
import com.meta.core.entity.ModelEntity;
import com.meta.util.IdGenerator;
import com.meta.util.NativeQueryUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;

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

    @Autowired
    private MailFieldDao mailFieldDao;

    @Autowired
    private NativeQueryUtils nativeQueryUtils;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/addModel"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    public void testAddModelField(){
        String modelId = "221962642707845120";
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setId(modelId);
        modelEntity.setName("测试模型字段");
        modelEntity.setTenantId("1");
        modelDao.save(modelEntity);

        String fieldId = IdGenerator.nextId();
        FieldEntity field = new FieldEntity();
        field.setTenantId("1");
        field.setModelId(modelId);
        field.setCode("name");
        field.setName("姓名");
        field.setType(FieldType.STRING.name());
        field.setId(fieldId);
        fieldDao.saveField(field);

        Optional<FieldEntity> byId = fieldDao.findById(fieldId);
        System.out.println("");

        String mailFieldId = IdGenerator.nextId();
        MailFieldEntity mailFieldEntity = new MailFieldEntity();
        mailFieldEntity.setTenantId("1");
        mailFieldEntity.setModelId(modelId);
        mailFieldEntity.setCode("name");
        mailFieldEntity.setName("姓名");
        mailFieldEntity.setType(FieldType.STRING.name());
        mailFieldEntity.setId(mailFieldId);
        mailFieldEntity.setSender("yang");
        mailFieldEntity.setReceiver("gong");

        MailField mailField = new MailField(mailFieldEntity);
        mailFieldDao.saveField(mailFieldEntity);

        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        Pageable pageable = PageRequest.of(0, 3, sort); // 第1页，每页10条
        Page<MailFieldEntity> page = mailFieldDao.findPageByModelIdAndDeletedFalse("221962642707845120", pageable);

        String sql = "select * from meta_field where id = :id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", mailFieldId);
        List<FieldEntity> fieldEntities = nativeQueryUtils.queryList(sql, parameters, FieldEntity.class);
        System.out.println();
    }

    @Test
    public void testNativeQuery(){
       String sql = "select * from meta_field where id = :id";
       Map<String, Object> parameters = new HashMap<>();
       parameters.put("id", "221945718183694336");
        List<FieldEntity> fieldEntities = nativeQueryUtils.queryList(sql, parameters, FieldEntity.class);
        System.out.println();

    }

}
