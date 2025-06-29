package com.kwizera.springbootlab13trackerbooster;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootLab13TrackerBoosterApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        System.setProperty("spring.datasource.url", dotenv.get("DOCKER_DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DB_USER"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        System.setProperty("spring.data.mongodb.uri", dotenv.get("DOCKER_MONGODB_URI"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-id", dotenv.get("GOOGLE_AUTH_CLIENT_ID"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-secret", dotenv.get("GOOGLE_AUTH_CLIENT_SECRET"));

        SpringApplication.run(SpringbootLab13TrackerBoosterApplication.class, args);
    }

}
