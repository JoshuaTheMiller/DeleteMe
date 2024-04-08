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
    private final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) throws IOException, URISyntaxException, InterruptedException {

        String someConfigurationValue = System.getenv("NA");

        // Doesn't fix the problem
        if(name.contains("/")) {
            return "Bad";
        }

        URI uri = new URI(someConfigurationValue + name);

        // Seems pretty... silly that we would validate the URL starting
        // with something when we can clearly see how it is set.
        // Perhaps using the URL class is simply a bad choice (the constructor
        // has been deprecated, after all.
        if(!uri.getHost().startsWith("https://example.com")){
            return "Bad";
        }

        HttpRequest r = HttpRequest.newBuilder(uri).build();

        try{
            client.send(r, null);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return String.format("Hello %s!", name);
    }
}
