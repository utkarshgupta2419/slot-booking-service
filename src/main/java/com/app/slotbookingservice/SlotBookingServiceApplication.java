package com.app.slotbookingservice;

import com.app.slotbookingservice.auth.dto.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SlotBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlotBookingServiceApplication.class, args);
    }

}
