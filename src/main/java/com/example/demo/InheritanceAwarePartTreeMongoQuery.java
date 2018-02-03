package com.example.demo;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.ConvertingParameterAccessor;
import org.springframework.data.mongodb.repository.query.MongoQueryMethod;
import org.springframework.data.mongodb.repository.query.PartTreeMongoQuery;

public class InheritanceAwarePartTreeMongoQuery extends PartTreeMongoQuery {

    private final Criteria inheritanceCriteria;

    public InheritanceAwarePartTreeMongoQuery(MongoQueryMethod method, MongoOperations mongoOperations) {
        super(method, mongoOperations);

        inheritanceCriteria =
                method.getEntityInformation().getJavaType().isAnnotationPresent(TypeAlias.class)
                        ? where("_class")
                        .is(method.getEntityInformation().getJavaType().getAnnotation(TypeAlias.class).value())
                        : null;
    }

    @Override
    protected Query createQuery(ConvertingParameterAccessor accessor) {
        Query query = super.createQuery(accessor);
        if (inheritanceCriteria != null) {
            query.addCriteria(inheritanceCriteria);
        }
        return query;
    }

    @Override
    protected Query createCountQuery(ConvertingParameterAccessor accessor) {
        Query query = super.createCountQuery(accessor);
        if (inheritanceCriteria != null) {
            query.addCriteria(inheritanceCriteria);
        }
        return query;
    }
}
