package com.meta.controller;

import com.meta.core.dao.ModelDao;
import com.meta.core.entity.ModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Controller("/model")
public class ModelController {

    @Autowired
    private ModelDao modelDao;

    @GetMapping("/addModel")
    @ResponseBody
    public String addModel(){
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setName("测试模型");
        modelDao.save(modelEntity);
        return "ok";
    }
}
