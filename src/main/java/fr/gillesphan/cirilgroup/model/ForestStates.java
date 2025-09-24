package fr.gillesphan.cirilgroup.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Represents the state of the forest. For optimisation, we use a 2D array of
 * Tree, and we only store BURNING and ASH states (with there positions).
 */
public class ForestStates {

    /**
     * Initial state of the forest (only first BURNING trees).
     */
    private ArrayList<BurningTree> burningTrees;
    /**
     * History of burning trees (BURNING and ASH states).
     */
    private ArrayList<BurningTree[]> burningTreesHistory;

    public ForestStates(BurningTree[] initialBurningTrees) {
        this.burningTrees = new ArrayList<BurningTree>();
        this.burningTrees.addAll(Arrays.asList(initialBurningTrees));
        this.burningTreesHistory = new ArrayList<BurningTree[]>();
        this.burningTreesHistory.add(initialBurningTrees);
    }

    public ArrayList<BurningTree[]> getBurningTreesHistory() {
        return burningTreesHistory;
    }

    public void addBurningTreesToHistory(BurningTree[] burningTrees) {
        if (burningTreesHistory == null) {
            burningTreesHistory = new ArrayList<BurningTree[]>();
        }
        burningTreesHistory.add(burningTrees);
    }

    public BurningTree[] getCurrentBurningTrees() throws IllegalStateException {
        if (burningTreesHistory == null || burningTreesHistory.isEmpty()) {
            throw new IllegalStateException("No history available");
        }
        return burningTreesHistory.get(burningTreesHistory.size() - 1);
    }

    public BurningTree[] getPreviousBurningTrees() throws IllegalStateException {
        if (burningTreesHistory.size() < 2) {
            throw new IllegalStateException("No previous state available");
        }
        if (burningTreesHistory == null || burningTreesHistory.isEmpty()) {
            throw new IllegalStateException("No history available");
        }

        return burningTreesHistory.get(burningTreesHistory.size() - 2);
    }

}
