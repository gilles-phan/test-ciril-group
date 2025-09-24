package fr.gillesphan.cirilgroup.services;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.model.ForestStates;
import fr.gillesphan.cirilgroup.model.Tree;
import fr.gillesphan.cirilgroup.model.TreeState;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SimulationService {

    private ForestStates simulation;
    private int width;
    private int height;
    private double contagionRate;

    public SimulationService(AppConfiguration config) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.contagionRate = config.getContagionRate();

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

    public double getContagionRate() {
        return contagionRate;
    }

    public void setContagionRate(double contagionRate) {
        this.contagionRate = contagionRate;
    }

    /**
     * Draw the forest in the console.
     */
    public void drawForest() {
        System.out.println(
                "Forest generation: #" + (simulation.getBurningTreesHistory().size() - 1));
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
     * Generate the next step of the simulation.
     */
    public void nextStep() {
        // 1 - get all ash
        List<Tree> ash = getAllBurnedTrees(simulation);

        // 2 - get currently burning trees
        Tree[] currentlyBurningTrees = simulation.getCurrentBurningTrees();

        // 3 - for each burning tree, try to ignite its neighboors
        List<Tree> newBurningTrees = new ArrayList<Tree>();
        for (Tree burningTree : currentlyBurningTrees) {
            checkNeighboors(burningTree, ash, newBurningTrees);
        }
        Tree[] newBurningTreesArray = new Tree[newBurningTrees.size()];
        newBurningTreesArray = newBurningTrees.toArray(newBurningTreesArray);

        // 4 - transform all currently burning trees to ash
        simulation.addBurningTreesToHistory(newBurningTreesArray);
    }

    /**
     * Check if there is still burning trees in the simulation.
     * 
     * @return
     */
    public Boolean isStillBurningTrees() {
        try {
            return simulation.getCurrentBurningTrees().length > 0;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /**
     * Check the 4 neighboors of a burning tree and try to ignite them if they are
     * not.
     *
     * @param burningTree
     * @param ash
     * @param newBurningTrees
     */
    private void checkNeighboors(Tree burningTree, List<Tree> ash, List<Tree> newBurningTrees) {
        int x = burningTree.getX();
        int y = burningTree.getY();

        // coordinates of the 4 neighboors
        int[][] neighbors = {
                { x - 1, y }, // left
                { x + 1, y }, // right
                { x, y - 1 }, // top
                { x, y + 1 } // bottom
        };

        for (int[] neighbor : neighbors) {
            int nx = neighbor[0];
            int ny = neighbor[1];

            // Check is the neighbor is within bounds
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                boolean alreadyAsh = ash.stream().anyMatch(tree -> tree.getX() == nx && tree.getY() == ny);
                boolean alreadyBurning = isBurning(nx, ny);

                if (!alreadyAsh && !alreadyBurning) {
                    if (tryIgnite(nx, ny)) {
                        newBurningTrees.add(new Tree(nx, ny));
                    }
                }
            }
        }
    }

    /**
     * Try to ignite a tree at position (x, y) based on the contagion rate.
     *
     * @param x
     * @param y
     * @return
     */
    private Boolean tryIgnite(int x, int y) {
        double p = getContagionRate();
        return (Math.random() < p);
    }

    public List<Tree> getAllBurnedTrees(ForestStates simulation) {
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

        // check all states of the forest and check if one of them as already burning at
        // (x, y). Is yes, it is now ash.
        ArrayList<Tree[]> history = simulation.getBurningTreesHistory();
        if (history.size() <= 1)
            return false;

        return history.subList(0, history.size() - 1).stream()
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .anyMatch(tree -> tree != null && tree.getX() == x && tree.getY() == y);

    }
}
