package com.sayedbaladoh.storagemanager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageProperties {
    private String path;
    private String deleteCycleTimeInMinutes;
}
