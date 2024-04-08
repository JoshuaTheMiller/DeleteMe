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

        String someConfigurationValue = System.getenv("NA");

        // Doesn't fix the problem
        if(name.contains("/")) {
            return "Bad";
        }


        // Trying to force a specific CodeQL warning to demonstrate an issue
        URL what = new URL(someConfigurationValue + name);

        // Seems pretty... silly that we would validate the URL starting
        // with something when we can clearly see how it is set.
        // Perhaps using the URL class is simply a bad choice (the constructor
        // has been deprecated, after all.
        if(!what.getHost().startsWith("https://example.com")){
            return "Bad";
        }

        try{
            what.openConnection();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return String.format("Hello %s!", name);
    }
}
