package com.boot.myioc;

import com.boot.myioc.config.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyiocApplication {

    public static void main(String[] args) {
        ApplicationContext.init("com.boot.myioc");
        SpringApplication.run(MyiocApplication.class, args);
    }

}
