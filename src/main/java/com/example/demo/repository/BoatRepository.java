package com.example.demo.repository;

import com.example.demo.domain.Boat;
import org.springframework.data.repository.CrudRepository;

public interface BoatRepository extends CrudRepository<Boat, String> {

}
