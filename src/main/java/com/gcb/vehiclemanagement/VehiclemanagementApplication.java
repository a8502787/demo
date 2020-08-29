package com.gcb.vehiclemanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gcb.vehiclemanagement.dao")
public class VehiclemanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclemanagementApplication.class, args);
        System.out.println("hello Vehiclemanagement!");
    }

}
