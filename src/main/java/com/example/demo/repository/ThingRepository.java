package com.example.demo.repository;

import com.example.demo.domain.Thing;
import org.springframework.data.repository.CrudRepository;

public interface ThingRepository extends CrudRepository<Thing, String> {

}
