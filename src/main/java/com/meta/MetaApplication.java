package com.meta;


import com.meta.app.DynamicSchemaRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetaApplication.class, args);
    }

    @Bean
    public DynamicSchemaRegistrar dynamicSchemaRegistrar() {
        return new DynamicSchemaRegistrar();
    }
}
