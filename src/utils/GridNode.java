package utils;

import java.util.ArrayList;
import java.util.List;

public class GridNode extends Coordinate {

    private final List<GridNode> adjacentNodes = new ArrayList<>();

    private GridNode parent = null;
    private final char height;

    private final boolean isStart;
    private final boolean isSummit;

    public GridNode(int x, int y, char height) {
        this(x, y, height, false, false);
    }

    public GridNode(int x, int y, char height, boolean isStart, boolean isSummit) {
        super(x, y);
        this.height = height;
        this.isStart = isStart;
        this.isSummit = isSummit;
    }

    public List<GridNode> getDestinations() {
        return adjacentNodes;
    }

    public boolean addDestination(GridNode node) {
        if (this == node) {
            return false;
        }
        if (node.getHeight() <= this.getHeight() + 1 ) {
            return adjacentNodes.add(node);
        }
        return false;
    }

    public char getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.valueOf(height);
    }

    public String getCoordinate() {
        return super.toString();
    }

    public boolean isSummit() {
        return isSummit;
    }

    public boolean isStart() {
        return isStart;
    }

    public GridNode getParent() {
        return parent;
    }

    public void setParent(GridNode parent) {
        if (this.parent == null && !isStart) {
            this.parent = parent;
        }
    }
}
