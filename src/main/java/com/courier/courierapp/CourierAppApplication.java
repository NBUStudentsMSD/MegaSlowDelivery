package com.courier.courierapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CourierAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourierAppApplication.class, args);
    }

    // This is a test comment

}
