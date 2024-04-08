package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@SpringBootApplication
@RestController
public class DemoApplication {
    private static final String VALID_URI = "http://lgtm.com";
    private final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) throws Exception {

        String someConfigurationValue = System.getenv("NA");

        String validatedUri = ValidateUri(name, someConfigurationValue);

        URI uri = new URI(validatedUri);

        HttpRequest r = HttpRequest.newBuilder(uri).build();

        try{
            client.send(r, null);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return String.format("Hello %s!", name);
    }

    private String ValidateUri(String name, String someConfigurationValue) throws Exception {
        // Doesn't fix the problem
        if(name.contains("/")) {
            throw new Exception("Bad");
        }

        String someUrl = someConfigurationValue + name;

        if(!VALID_URI.equals(someUrl)) {
            throw new Exception("Bad");
        }

        return someUrl;
    }
}
