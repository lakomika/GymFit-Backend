package pl.lakomika.gymfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GymFitApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymFitApplication.class, args);
    }

}
