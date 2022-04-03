package pl.kcit.tof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan("pl.kcit.tof")
@SpringBootApplication
public class TofClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TofClientApplication.class, args);
    }
}
