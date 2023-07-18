package com.ibk.identityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsIdentityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsIdentityServerApplication.class, args);
    }

}
