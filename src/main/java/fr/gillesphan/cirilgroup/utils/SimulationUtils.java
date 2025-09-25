package fr.gillesphan.cirilgroup.utils;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.model.BurningTree;
import fr.gillesphan.cirilgroup.model.ForestStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

public final class SimulationUtils {

    public static List<BurningTree> getAllBurnedTrees(ForestStates simulation) {
        return simulation.getBurningTreesHistory().stream()
                .filter(step -> step != null)
                .flatMap(step -> Arrays.stream(step))
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Check if a tree at position (x, y) is burning in the current step.
     *
     * @param x
     * @param y
     * @param simulation
     * @return
     */
    public static boolean isBurning(int x, int y, ForestStates simulation) throws IllegalStateException {
        if (simulation.getBurningTreesHistory() == null || simulation.getBurningTreesHistory().isEmpty()) {
            throw new IllegalStateException("No history available");
        }

        // get the current state of the forest and check if one of them is burning at
        // (x, y)
        BurningTree[] currentStep = simulation.getCurrentBurningTrees();
        if (currentStep == null) {
            return false;
        }

        for (BurningTree tree : currentStep) {
            if (tree != null && tree.getX() == x && tree.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a tree at position (x, y) is ash in the current step.
     *
     * @param x
     * @param y
     * @param history
     * @return
     */
    public static boolean isAsh(int x, int y, ArrayList<BurningTree[]> history) throws IllegalStateException {
        if (history == null || history.isEmpty()) {
            throw new IllegalStateException("No history available");
        }
        if (history.size() < 2) {
            return false;
        }

        // check all states of the forest and check if one of them as already burning at
        // (x, y). Is yes, it is now ash.
        return history.subList(0, history.size() - 1).stream()
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .anyMatch(tree -> tree != null && tree.getX() == x && tree.getY() == y);

    }

    public static void checkConfiguration(AppConfiguration config) throws IllegalArgumentException {
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
    }

}
