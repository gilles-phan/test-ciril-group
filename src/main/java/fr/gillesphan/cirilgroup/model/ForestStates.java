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
    private ArrayList<Tree> forest;
    /**
     * History of burning trees (BURNING and ASH states).
     */
    private ArrayList<Tree[]> burningTreesHistory;

    public ForestStates(Tree[] initialBurningTrees) {
        this.forest = new ArrayList<Tree>();
        List<Tree> burninTrees = Arrays.asList(burnAllTrees(initialBurningTrees));
        this.forest.addAll(burninTrees);
        this.burningTreesHistory = new ArrayList<Tree[]>();
        this.burningTreesHistory.add(initialBurningTrees);
    }

    /**
     * Set all trees to BURNING state.
     *
     * @param trees
     * @return
     */
    private Tree[] burnAllTrees(Tree[] trees) {
        for (int i = 0; i < trees.length; i++) {
            trees[i].setState(TreeState.BURNING);
        }
        return trees;
    }

    public ArrayList<Tree[]> getBurningTreesHistory() {
        return burningTreesHistory;
    }

    public void addBurningTreesToHistory(Tree[] burningTrees) {
        if (burningTreesHistory == null) {
            burningTreesHistory = new ArrayList<Tree[]>();
        }
        burningTreesHistory.add(burningTrees);
    }

    public Tree[] getCurrentBurningTrees() {
        if (burningTreesHistory == null || burningTreesHistory.isEmpty()) {
            return new Tree[0];
        }
        return burningTreesHistory.get(burningTreesHistory.size() - 1);
    }

}
