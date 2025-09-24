package fr.gillesphan.cirilgroup.services;

import fr.gillesphan.cirilgroup.config.AppConfiguration;
import fr.gillesphan.cirilgroup.model.ForestStates;
import fr.gillesphan.cirilgroup.utils.SimulationUtils;
import fr.gillesphan.cirilgroup.model.BurningTree;
import java.util.List;
import java.util.ArrayList;

public class SimulationService {

    private ForestStates simulation;
    private int width;
    private int height;
    private double contagionRate;

    public SimulationService(AppConfiguration config) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.contagionRate = config.getContagionRate();

        BurningTree[] initialBurningTrees = config.getStartFirePositions().stream()
                .map(pos -> new BurningTree(pos.getX(), pos.getY()))
                .toArray(BurningTree[]::new);

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
     * Generate the next step of the simulation.
     */
    public void nextStep() {
        // 1 - get all ash
        List<BurningTree> ash = SimulationUtils.getAllBurnedTrees(simulation);

        // 2 - get currently burning trees
        BurningTree[] currentlyBurningTrees = simulation.getCurrentBurningTrees();

        // 3 - for each burning tree, try to ignite its neighboors
        List<BurningTree> newBurningTrees = new ArrayList<BurningTree>();
        for (BurningTree burningTree : currentlyBurningTrees) {
            checkNeighboors(burningTree, ash, newBurningTrees);
        }
        BurningTree[] newBurningTreesArray = new BurningTree[newBurningTrees.size()];
        newBurningTreesArray = newBurningTrees.toArray(newBurningTreesArray);

        // 4 - transform all currently burning trees to ash
        simulation.addBurningTreesToHistory(newBurningTreesArray);
    }

    /**
     * Check if there is still burning trees inz the simulation.
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
    private void checkNeighboors(BurningTree burningTree, List<BurningTree> ash,
            List<BurningTree> newBurningTrees) {
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
                boolean alreadyBurning = SimulationUtils.isBurning(nx, ny, simulation);

                if (!alreadyAsh && !alreadyBurning) {
                    if (Math.random() < contagionRate) {
                        newBurningTrees.add(new BurningTree(nx, ny));
                    }
                }
            }
        }
    }
}
