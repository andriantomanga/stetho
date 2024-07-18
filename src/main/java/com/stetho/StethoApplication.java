package com.stetho;

import com.stetho.config.StethoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(StethoProperties.class)
@EnableScheduling
public class StethoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StethoApplication.class, args);
    }

}
