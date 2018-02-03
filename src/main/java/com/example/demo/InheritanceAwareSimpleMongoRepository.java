package com.example.demo;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.bson.Document;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.lang.Nullable;

public class InheritanceAwareSimpleMongoRepository<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> {

    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, ID> entityInformation;
    private final Document classCriteriaDocument;
    private final Criteria classCriteria;

    public InheritanceAwareSimpleMongoRepository(MongoEntityInformation<T, ID> metadata,
            MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
        this.entityInformation = metadata;

        if (entityInformation.getJavaType().isAnnotationPresent(TypeAlias.class)) {
            classCriteria = where("_class").is(entityInformation.getJavaType().getAnnotation(TypeAlias.class).value());
            classCriteriaDocument = classCriteria.getCriteriaObject();
        } else {
            classCriteriaDocument = new Document();
            classCriteria = null;
        }
    }

    @Override
    public long count() {
        return classCriteria != null ? mongoOperations.getCollection(
                entityInformation.getCollectionName()).count(
                classCriteriaDocument)
                : super.count();
    }

    @Override
    public List<T> findAll() {
        return mongoOperations.find(new Query().addCriteria(classCriteria),
                entityInformation.getJavaType(),
                entityInformation.getCollectionName());
    }
}
