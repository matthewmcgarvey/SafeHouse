package com.theironyard;

import com.theironyard.api.AmazonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafeHouseApplication {

    public static void main(String[] args) {
//        System.out.println(AmazonUtil.lookupItem("Samsung Galaxy", "All"));
        SpringApplication.run(SafeHouseApplication.class, args);
    }
}
