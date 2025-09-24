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

    public void drawForest() {
        for (int y = 0; y < getHeight(); y++) { // lignes
            for (int x = 0; x < getWidth(); x++) { // colonnes
                System.out.print(isBurning(x, y) ? "🔥 " : "🌲 ");
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
    public boolean isBurning(int x, int y) {
        if (simulation.getBurningTreesHistory() == null || simulation.getBurningTreesHistory().isEmpty()) {
            return false;
        }

        // Récupérer le dernier tableau de la liste
        Tree[] currentStep = simulation.getCurrentBurningTrees();
        if (currentStep == null) {
            return false;
        }

        // Parcourir les arbres de ce step
        for (Tree tree : currentStep) {
            if (tree != null && tree.getX() == x && tree.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
