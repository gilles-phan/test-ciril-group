package fr.gillesphan.cirilgroup.test.cg;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import fr.gillesphan.cirilgroup.test.cg.config.AppConfiguration;

@SpringBootApplication
public class TestCirilGroupApplication implements CommandLineRunner {

    private final AppConfiguration config;

    public TestCirilGroupApplication(AppConfiguration config) {
        this.config = config;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestCirilGroupApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (config.getWidth() <= 0 || config.getHeight() <= 0) {
            throw new IllegalArgumentException("Width and Height must be positive integers.");
        }
        config.getStartFirePositions().forEach(pos -> {
            if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= config.getWidth()
                    || pos.getY() >= config.getHeight()) {
                throw new IllegalArgumentException("Invalid fire position: x=" + pos.getX() + ", y=" + pos.getY());
            }
        });
        if (config.getContagionRate() < 0 || config.getContagionRate() > 1) {
            throw new IllegalArgumentException("Contagion rate must be between 0 and 1.");
        }

        System.out.println("Width: " + config.getWidth());
        System.out.println("Height: " + config.getHeight());
        System.out.println("Rate: " + config.getContagionRate());
        config.getStartFirePositions()
                .forEach(pos -> System.out.println("Fire start at x=" + pos.getX() + ", y=" + pos.getY()));
    }
}