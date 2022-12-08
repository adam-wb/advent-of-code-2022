package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8 extends AdventOfCodePuzzle {

    private final List<List<Integer>> forest = new ArrayList<>();

    public Day8() throws IOException {
        super(8);
        parseInput();
    }

    @Override
    public void part1() {
        forest.forEach(System.out::println);

        int visibleTreeCount = 0;
        // First index is y coordinate
        for (int y = 0; y < forest.size(); y++) {
            // Second index is x coordinate
            for (int x = 0; x < forest.get(y).size(); x++) {
                if (isTreeVisible(x, y)) {
                    visibleTreeCount++;
                }
            }
        }

        System.out.printf("Part 1 solution - %s", visibleTreeCount);
    }

    @Override
    public void part2() {
        forest.forEach(System.out::println);

        int maxScenicScore = 0;
        // First index is y coordinate
        for (int y = 0; y < forest.size(); y++) {
            // Second index is x coordinate
            for (int x = 0; x < forest.get(y).size(); x++) {
                int scenicScore = getScenicScore(x, y);
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }

        System.out.printf("Part 2 solution - %s", maxScenicScore);
    }

    private boolean isTreeVisible(int x, int y) {
        int tree = forest.get(y).get(x);

        if (y == 0 || x == 0 || y == forest.size() - 1 || x == forest.get(y).size() - 1) {
            System.out.printf("Tree at [%s,%s] is on the edge of the forest\n", x, y);
            return true;
        }

        return isUpVisible(x, y, tree)
                || isDownVisible(x, y, tree)
                || isLeftVisible(x, y, tree)
                || isRightVisible(x, y, tree);
    }

    private boolean isUpVisible(int x, int y, int tree) {
        for (int i = 0; i < y; i++) {
            if (forest.get(i).get(x) >= tree) {
                return false;
            }
        }
        System.out.printf("Tree at [%s,%s] with size %s is visible from above\n", x, y, tree);
        return true;
    }

    private boolean isDownVisible(int x, int y, int tree) {
        for (int i = y + 1; i < forest.size(); i++) {
            if (forest.get(i).get(x) >= tree) {
                return false;
            }
        }
        System.out.printf("Tree at [%s,%s] with size %s is visible from below\n", x, y, tree);
        return true;
    }

    private boolean isLeftVisible(int x, int y, int tree) {
        for (int i = 0; i < x; i++) {
            if (forest.get(y).get(i) >= tree) {
                return false;
            }
        }
        System.out.printf("Tree at [%s,%s] with size %s is visible from the left\n", x, y, tree);
        return true;
    }

    private boolean isRightVisible(int x, int y, int tree) {
        for (int i = x + 1; i < forest.get(y).size(); i++) {
            if (forest.get(y).get(i) >= tree) {
                return false;
            }
        }
        System.out.printf("Tree at [%s,%s] with size %s is visible from the right\n", x, y, tree);
        return true;
    }

    private int getScenicScore(int x, int y) {
        int tree = forest.get(y).get(x);

        if (y == 0 || x == 0 || y == forest.size() - 1 || x == forest.get(y).size() - 1) {
            System.out.printf("Tree at [%s,%s] is on the edge of the forest\n", x, y);
            return 0;
        }

        return getUpViewingDistance(x, y, tree)
                * getDownViewingDistance(x, y, tree)
                * getLeftViewingDistance(x, y, tree)
                * getRightViewingDistance(x, y, tree);
    }

    private int getUpViewingDistance(int x, int y, int tree) {
        int viewingDistance = 0;
        for (int i = y - 1; i >= 0; i--) {
            viewingDistance++;
            if (forest.get(i).get(x) >= tree) {
                System.out.printf("Tree at [%s,%s] has viewing distance from above of %s  \n", x, y, viewingDistance);
                return viewingDistance;
            }
        }
        System.out.printf("Tree at [%s,%s] has viewing distance from above of %s  \n", x, y, viewingDistance);
        return viewingDistance;
    }

    private int getDownViewingDistance(int x, int y, int tree) {
        int viewingDistance = 0;
        for (int i = y + 1; i < forest.size(); i++) {
            viewingDistance++;
            if (forest.get(i).get(x) >= tree) {
                System.out.printf("Tree at [%s,%s] has viewing distance from below of %s  \n", x, y, viewingDistance);
                return viewingDistance;
            }
        }
        System.out.printf("Tree at [%s,%s] has viewing distance from below of %s  \n", x, y, viewingDistance);
        return viewingDistance;
    }

    private int getLeftViewingDistance(int x, int y, int tree) {
        int viewingDistance = 0;
        for (int i = x - 1; i >= 0; i--) {
            viewingDistance++;
            if (forest.get(y).get(i) >= tree) {
                System.out.printf("Tree at [%s,%s] has viewing distance from the left of %s  \n", x, y, viewingDistance);
                return viewingDistance;
            }

        }
        System.out.printf("Tree at [%s,%s] has viewing distance from the left of %s  \n", x, y, viewingDistance);
        return viewingDistance;
    }

    private int getRightViewingDistance(int x, int y, int tree) {
        int viewingDistance = 0;
        for (int i = x + 1; i < forest.get(y).size(); i++) {
            viewingDistance++;
            if (forest.get(y).get(i) >= tree) {
                System.out.printf("Tree at [%s,%s] has viewing distance from the right of %s  \n", x, y, viewingDistance);
                return viewingDistance;
            }
        }
        System.out.printf("Tree at [%s,%s] has viewing distance from the right of %s  \n", x, y, viewingDistance);
        return viewingDistance;
    }

    private void parseInput() {
        List<String> rows = List.of(puzzleInput.split("\\R"));
        for (String row : rows) {
            forest.add(row.chars()
                    .mapToObj(i -> (char) i)
                    .map(c -> Integer.parseInt(String.valueOf(c)))
                    .toList());
        }
    }
}
