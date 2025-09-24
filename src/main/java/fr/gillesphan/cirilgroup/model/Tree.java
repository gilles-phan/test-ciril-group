package fr.gillesphan.cirilgroup.model;

public class Tree {
    private TreeState state;
    private int x;
    private int y;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = TreeState.BURNING;
    }

    public TreeState getState() {
        return state;
    }

    public void setState(TreeState state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
