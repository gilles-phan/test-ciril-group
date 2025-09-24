package fr.gillesphan.cirilgroup.services;

import fr.gillesphan.cirilgroup.model.ForestStates;
import fr.gillesphan.cirilgroup.utils.SimulationUtils;
import java.io.PrintStream;

public final class SimulationPrinter {
    private SimulationPrinter() {
    }

    public static void drawForest(ForestStates simulation, int width, int height, PrintStream out) {
        out.println("Forest generation: #" + (simulation.getBurningTreesHistory().size() - 1));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (SimulationUtils.isBurning(x, y, simulation))
                    out.print("🔥 ");
                else if (SimulationUtils.isAsh(x, y, simulation.getBurningTreesHistory()))
                    out.print("⚫ ");
                else
                    out.print("🌲 ");
            }
            out.println();
        }
    }
}