package co.g2academy.tokomurah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TokoMurahApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokoMurahApplication.class, args);
    }
    
}
