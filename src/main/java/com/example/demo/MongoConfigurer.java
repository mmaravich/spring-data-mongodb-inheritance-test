package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(repositoryBaseClass = InheritanceAwareSimpleMongoRepository.class,
        repositoryFactoryBeanClass = InheritanceAwareMongoRepositoryFactoryBean.class)
public class MongoConfigurer {

}
