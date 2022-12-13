package solutions;

import utils.AdventOfCodePuzzle;
import utils.GridNode;

import java.io.IOException;
import java.util.*;

public class Day12 extends AdventOfCodePuzzle {

    public Day12() throws IOException {
        super(12);
    }

    @Override
    public void part1() {
        GridNode[][] map = parseInput(false);
        addDestinationNodes(map);
        GridNode start = Arrays.stream(map).flatMap(Arrays::stream).filter(GridNode::isStart).findFirst().orElse(null);
        System.out.printf("Part 1 solution - %s", getShortestPath(start));
    }

    @Override
    public void part2() {
        GridNode[][] baseMap = parseInput(true);
        List<GridNode> starts = Arrays.stream(baseMap).flatMap(Arrays::stream).filter(node -> node.getHeight() == 'a').toList();

        List<Integer> shortestPaths = starts.stream().map(node -> {
            GridNode[][] currentMap = parseInput(true);
            GridNode start = new GridNode(node.getX(), node.getY(), 'a', true, false);
            currentMap[node.getY()][node.getX()] = start;
            addDestinationNodes(currentMap);
            return getShortestPath(start);
        }).toList();

        System.out.printf("Part 2 solution - %s", Collections.min(shortestPaths));
    }

    private int getShortestPath(GridNode start) {
        Queue<GridNode> traversal = new ArrayDeque<>();
        traversal.add(start);
        Set<GridNode> visited = new HashSet<>();
        GridNode currentNode;
        while (!traversal.isEmpty()) {
            currentNode = traversal.remove();
            if (currentNode.isSummit()) {
                System.out.printf("Found summit %s\n", currentNode.getCoordinate());
                int pathLength = 0;
                GridNode parent = currentNode.getParent();
                while (parent != null) {
                    pathLength++;
                    parent = parent.getParent();
                }
                System.out.printf("Shortest path to summit from start %s is %s steps\n", start.getCoordinate(), pathLength);
                return pathLength;
            }
            visited.add(currentNode);
            GridNode finalCurrentNode = currentNode;
            currentNode.getDestinations().forEach(node -> node.setParent(finalCurrentNode));
            traversal.addAll(currentNode.getDestinations());
            traversal.removeAll(visited);
        }
        System.out.printf("Could not find path to summit from start %s\n", start.getCoordinate());
        return Integer.MAX_VALUE;
    }

    private void addDestinationNodes(GridNode[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                GridNode node = map[y][x];
                if (x < map[y].length - 1) {
                    GridNode rightNode = map[y][x + 1];
                    node.addDestination(rightNode);
                }
                if (x > 0) {
                    GridNode leftNode = map[y][x - 1];
                    node.addDestination(leftNode);
                }
                if (y < map.length - 1) {
                    GridNode downNode = map[y + 1][x];
                    node.addDestination(downNode);
                }
                if (y > 0) {
                    GridNode upNode = map[y - 1][x];
                    node.addDestination(upNode);
                }
            }
        }
    }

    private GridNode[][] parseInput(boolean randomStart) {
        String[] rows = puzzleInput.split("\\R");
        GridNode[][] map = new GridNode[rows.length][rows[0].length()];
        for (int y = 0; y < rows.length; y++) {
            char[] hills = rows[y].toCharArray();
            for (int x = 0; x < hills.length; x++) {
                char height = hills[x];
                if (!randomStart) {
                    switch (height) {
                        case 'S' -> map[y][x] = new GridNode(x, y, 'a', true, false);
                        case 'E' -> map[y][x] = new GridNode(x, y, 'z', false, true);
                        default -> map[y][x] = new GridNode(x, y, height);
                    }
                } else {
                    switch (height) {
                        case 'S' -> map[y][x] = new GridNode(x, y, 'a', false, false);
                        case 'E' -> map[y][x] = new GridNode(x, y, 'z', false, true);
                        default -> map[y][x] = new GridNode(x, y, height);
                    }
                }
            }
        }
        return map;
    }
}
