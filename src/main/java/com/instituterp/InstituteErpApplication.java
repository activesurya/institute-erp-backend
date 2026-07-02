package com.instituterp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.instituterp"})
public class InstituteErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstituteErpApplication.class, args);
    }
}
