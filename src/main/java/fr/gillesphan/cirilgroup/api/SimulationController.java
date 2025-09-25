package fr.gillesphan.cirilgroup.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.services.SimulationPrinter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import fr.gillesphan.cirilgroup.services.SimulationService;

@RestController
@RequestMapping("/simulation")
public class SimulationController {
    private final AppConfiguration config;

    public SimulationController(AppConfiguration config) {
        this.config = config;
    }

    @GetMapping(produces = "application/json")
    public List<String> all() {

        List<String> result = new ArrayList<>();
        SimulationService simulationService = new SimulationService(config);
        String first = SimulationPrinter.drawForestAsString(simulationService.getSimulation(),
                simulationService.getWidth(),
                simulationService.getHeight());
        result.add(first);

        while (simulationService.isStillBurningTrees()) {
            simulationService.nextStep();
            String current = SimulationPrinter.drawForestAsString(simulationService.getSimulation(),
                    simulationService.getWidth(),
                    simulationService.getHeight());
            result.add(current);
        }

        return result;
    }

}
