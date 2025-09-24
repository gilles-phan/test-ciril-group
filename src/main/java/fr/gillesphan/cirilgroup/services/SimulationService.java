package fr.gillesphan.cirilgroup.services;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.model.ForestStates;
import fr.gillesphan.cirilgroup.model.Tree;
import fr.gillesphan.cirilgroup.model.TreeState;

public class SimulationService {

    private ForestStates simulation;
    private int width;
    private int height;

    public SimulationService(AppConfiguration config) {
        this.width = config.getWidth();
        this.height = config.getHeight();

        Tree[] initialBurningTrees = config.getStartFirePositions().stream()
                .map(pos -> new Tree(pos.getX(), pos.getY()))
                .toArray(Tree[]::new);

        this.simulation = new ForestStates(initialBurningTrees);
    }

    public ForestStates getSimulation() {
        return simulation;
    }

    public void setSimulation(ForestStates simulation) {
        this.simulation = simulation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Draw the forest in the console.
     */
    public void drawForest() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                try {
                    if (isBurning(x, y)) {
                        System.out.print("🔥 ");
                    } else if (isAsh(x, y)) {
                        System.out.print("⚫ ");
                    } else {
                        System.out.print("🌲 ");
                    }
                } catch (IllegalStateException e) {
                    System.out.print("⚠️ ");
                }

            }
            System.out.println();
        }
    }

    /**
     * Check if a tree at position (x, y) is burning in the current step.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isBurning(int x, int y) throws IllegalStateException {
        if (simulation.getBurningTreesHistory() == null || simulation.getBurningTreesHistory().isEmpty()) {
            return false;
        }

        // get the current state of the forest and check if one of them is burning at
        // (x, y)
        Tree[] currentStep = simulation.getCurrentBurningTrees();
        if (currentStep == null) {
            return false;
        }

        for (Tree tree : currentStep) {
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
     * @return
     */
    public boolean isAsh(int x, int y) throws IllegalStateException {
        if (simulation.getBurningTreesHistory() == null || simulation.getBurningTreesHistory().isEmpty()) {
            return false;
        }

        // get the previous state of the forest and check if one of them was burning at
        // (x, y). Is yes, it is now ash.
        Tree[] currentStep = simulation.getPreviousBurningTrees();
        if (currentStep == null) {
            return false;
        }

        for (Tree tree : currentStep) {
            if (tree != null && tree.getX() == x && tree.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
