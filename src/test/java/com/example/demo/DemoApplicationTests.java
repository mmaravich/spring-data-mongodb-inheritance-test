package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.Boat;
import com.example.demo.domain.Car;
import com.example.demo.repository.BoatRepository;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ThingRepository;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DemoApplicationTests {

    @Autowired
    private ThingRepository thingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Test
    public void checkInheritanceAwareStuff() {
        Car honda = new Car();
        honda.setModel("Honda Civic");
        honda.setName("A Car");
        carRepository.save(honda);

        Boat enterprise = new Boat();
        enterprise.setLength(10);
        enterprise.setName("A Boat");
        boatRepository.save(enterprise);

        // We should have 2 things in the collection
        assertThat(thingRepository.count()).isEqualTo(2);

        // But only one of each specific types of things
        assertThat(carRepository.count()).isEqualTo(1);
        assertThat(boatRepository.count()).isEqualTo(1);

        // And the generated queries should work correctly as well
        assertThat(carRepository.findCarsByIdNotNull().size()).isEqualTo(1);
        assertThat(carRepository.countCarsByIdNotNull()).isEqualTo(1);
    }

}

