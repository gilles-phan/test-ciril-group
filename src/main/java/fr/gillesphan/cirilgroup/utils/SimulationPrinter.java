package fr.gillesphan.cirilgroup.services;

import fr.gillesphan.cirilgroup.model.ForestStates;
import fr.gillesphan.cirilgroup.utils.SimulationUtils;
import java.io.PrintStream;

public final class SimulationPrinter {
    private SimulationPrinter() {
    }

    /**
     * Draw forest in PrintStream.
     *
     * @deprecated use drawForestAsString instead
     * @param simulation
     * @param width
     * @param height
     * @param out
     */
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

    /**
     * Draw forest and return as String.
     *
     * @param simulation
     * @param width
     * @param height
     * @return
     */
    public static String drawForestAsString(ForestStates simulation, int width, int height) {
        StringBuilder sb = new StringBuilder();
        sb.append("Forest generation: #")
                .append(simulation.getBurningTreesHistory().size() - 1)
                .append("\n");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (SimulationUtils.isBurning(x, y, simulation)) {
                    sb.append("🔥 ");
                } else if (SimulationUtils.isAsh(x, y, simulation.getBurningTreesHistory())) {
                    sb.append("⚫ ");
                } else {
                    sb.append("🌲 ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}