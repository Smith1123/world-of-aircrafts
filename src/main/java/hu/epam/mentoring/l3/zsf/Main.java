package hu.epam.mentoring.l3.zsf;

import hu.epam.mentoring.l3.zsf.engine.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        new Game().run();
    }
}
