package com.example.demo.repository;

import com.example.demo.domain.Car;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, String> {

    List<Car> findCarsByIdNotNull();

    long countCarsByIdNotNull();

}
