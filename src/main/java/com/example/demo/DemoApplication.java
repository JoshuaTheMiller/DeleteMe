package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) throws MalformedURLException {

        String someConfigurationValue = System.getenv("NA").isEmpty() ? "https://example.com" : System.getenv("NA");

        // Trying to force a specific CodeQL warning to demonstrate an issue
        URL what = new URL(someConfigurationValue);

        try{
            what.openConnection();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return String.format("Hello %s!", name);
    }
}
