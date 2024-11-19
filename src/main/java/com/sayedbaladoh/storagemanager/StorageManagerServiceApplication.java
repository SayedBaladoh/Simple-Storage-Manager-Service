package com.sayedbaladoh.storagemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StorageManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageManagerServiceApplication.class, args);
    }

}
