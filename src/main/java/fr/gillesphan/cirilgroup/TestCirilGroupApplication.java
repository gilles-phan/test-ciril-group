package fr.gillesphan.cirilgroup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.services.SimulationPrinter;
import fr.gillesphan.cirilgroup.services.SimulationService;
import fr.gillesphan.cirilgroup.utils.SimulationUtils;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = AppConfiguration.class)
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
        SimulationUtils.checkConfiguration(config);

        SimulationService simulationService = new SimulationService(config);
        System.out.println(
                SimulationPrinter.drawForestAsString(simulationService.getSimulation(), simulationService.getWidth(),
                        simulationService.getHeight()));

        while (simulationService.isStillBurningTrees()) {
            simulationService.nextStep();
            String forest = SimulationPrinter.drawForestAsString(simulationService.getSimulation(),
                    simulationService.getWidth(), simulationService.getHeight());
            System.out.println(forest);
        }

    }
}