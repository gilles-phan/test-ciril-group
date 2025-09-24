package fr.gillesphan.cirilgroup.services;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.config.FirePosition;
import fr.gillesphan.cirilgroup.model.ForestStates;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationServiceTest {

    @Test
    void testDrawForest_withOneBurningTree() {
        AppConfiguration config = new AppConfiguration();
        config.setWidth(3);
        config.setHeight(3);

        FirePosition pos = new FirePosition(0, 2);

        config.setStartFirePositions(List.of(pos));
        config.setContagionRate(0.5);

        SimulationService service = new SimulationService(config);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SimulationService simulationService = new SimulationService(config);
        SimulationPrinter.drawForest(simulationService.getSimulation(), simulationService.getWidth(),
                simulationService.getHeight(), System.out);

        System.setOut(System.out);

        String expected = "Forest generation: #0\n" +
                "🌲 🌲 🌲 \n" +
                "🌲 🌲 🌲 \n" +
                "🔥 🌲 🌲 \n";

        // Vérifie que ça matche
        assertThat(outContent.toString()).isEqualTo(expected);
    }

    @Test
    void testDrawForest_withoutBurningTree() {
        AppConfiguration config = new AppConfiguration();
        config.setWidth(3);
        config.setHeight(3);

        config.setStartFirePositions(List.of());
        config.setContagionRate(0.5);

        SimulationService service = new SimulationService(config);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SimulationService simulationService = new SimulationService(config);
        SimulationPrinter.drawForest(simulationService.getSimulation(), simulationService.getWidth(),
                simulationService.getHeight(), System.out);

        System.setOut(System.out);

        String expected = "Forest generation: #0\n" +
                "🌲 🌲 🌲 \n" +
                "🌲 🌲 🌲 \n" +
                "🌲 🌲 🌲 \n";

        // Vérifie que ça matche
        assertThat(outContent.toString()).isEqualTo(expected);
    }

    @Test
    void testDrawForest_allBurningTree() {
        AppConfiguration config = new AppConfiguration();
        config.setWidth(3);
        config.setHeight(3);

        FirePosition pos1 = new FirePosition(0, 0);
        FirePosition pos2 = new FirePosition(0, 1);
        FirePosition pos3 = new FirePosition(0, 2);
        FirePosition pos4 = new FirePosition(1, 0);
        FirePosition pos5 = new FirePosition(1, 1);
        FirePosition pos6 = new FirePosition(1, 2);
        FirePosition pos7 = new FirePosition(2, 0);
        FirePosition pos8 = new FirePosition(2, 1);
        FirePosition pos9 = new FirePosition(2, 2);

        config.setStartFirePositions(List.of(pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9));
        config.setContagionRate(0.5);

        SimulationService service = new SimulationService(config);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SimulationService simulationService = new SimulationService(config);
        SimulationPrinter.drawForest(simulationService.getSimulation(), simulationService.getWidth(),
                simulationService.getHeight(), System.out);

        System.setOut(System.out);

        String expected = "Forest generation: #0\n" +
                "🔥 🔥 🔥 \n" +
                "🔥 🔥 🔥 \n" +
                "🔥 🔥 🔥 \n";

        // Vérifie que ça matche
        assertThat(outContent.toString()).isEqualTo(expected);
    }

    @Test
    void testDrawForest_burningOutsideOfForest() {
        AppConfiguration config = new AppConfiguration();
        config.setWidth(3);
        config.setHeight(3);

        FirePosition pos = new FirePosition(7, 7);

        config.setStartFirePositions(List.of(pos));
        config.setContagionRate(0.5);

        SimulationService service = new SimulationService(config);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SimulationService simulationService = new SimulationService(config);
        SimulationPrinter.drawForest(simulationService.getSimulation(), simulationService.getWidth(),
                simulationService.getHeight(), System.out);

        System.setOut(System.out);

        String expected = "Forest generation: #0\n" +
                "🌲 🌲 🌲 \n" +
                "🌲 🌲 🌲 \n" +
                "🌲 🌲 🌲 \n";

        // Vérifie que ça matche
        assertThat(outContent.toString()).isEqualTo(expected);
    }
}