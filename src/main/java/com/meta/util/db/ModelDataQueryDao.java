package com.meta.util.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ModelDataQueryDao {

    @PersistenceContext
    private EntityManager entityManager;


}
