package solutions;

import utils.AdventOfCodePuzzle;
import utils.Coordinate;
import utils.Knot;

import java.io.IOException;
import java.util.*;

public class Day9 extends AdventOfCodePuzzle {

    private List<String[]> moves;

    public Day9() throws IOException {
        super(9);
        parseInput();
    }

    @Override
    public void part1() {
        Set<Coordinate> tailPositions = new HashSet<>();
        Knot head = new Knot(0, 0);
        Knot tail = new Knot(0, 0);
        // Hash positions for unique values each time
        tailPositions.add(new Coordinate(tail));

        for (String[] move : moves) {
            // Can be R, U, L or D
            String direction = move[0];
            // Number of steps the head moves
            int step = Integer.parseInt(move[1]);
            System.out.printf("Processing move %s %s\n", direction, step);

            for (int i = 0; i < step; i++) {
                switch (direction) {
                    case "R" -> head.moveRight();
                    case "U" -> head.moveUp();
                    case "L" -> head.moveLeft();
                    case "D" -> head.moveDown();
                }
                updateTailPosition(head, tail);
                System.out.printf("Head %s, Tail %s\n", head, tail);
                tailPositions.add(new Coordinate(tail));
            }
        }

        System.out.printf("Part 1 solution - %s\n", tailPositions.size());
    }

    @Override
    public void part2() {
        int NO_OF_KNOTS = 10;
        Set<Coordinate> tailPositions = new HashSet<>();
        List<Knot> rope = new ArrayList<>(NO_OF_KNOTS);
        for (int i = 0; i < NO_OF_KNOTS; i++) {
            rope.add(new Knot(0, 0));
        }
        Knot head = rope.get(0);
        Knot tail = rope.get(rope.size() - 1);
        tailPositions.add(new Coordinate(tail));

        for (String[] move : moves) {
            // Can be R, U, L or D
            String direction = move[0];
            // Number of steps the head moves
            int step = Integer.parseInt(move[1]);
            System.out.printf("Processing move %s %s\n", direction, step);

            for (int i = 0; i < step; i++) {
                switch (direction) {
                    case "R" -> head.moveRight();
                    case "U" -> head.moveUp();
                    case "L" -> head.moveLeft();
                    case "D" -> head.moveDown();
                }
                for (int j = 0; j < rope.size() - 1; j++) {
                    updateTailPosition(rope.get(j), rope.get(j + 1));
                }
                System.out.printf("Head %s, Tail %s\n", head, tail);
                tailPositions.add(new Coordinate(tail));
            }
        }

        System.out.printf("Part 2 solution - %s\n", tailPositions.size());
    }

    private void updateTailPosition(Knot head, Knot tail) {
        if (isTouching(head, tail)) {
            return;
        }
        int hDistance = head.getX() - tail.getX();
        int vDistance = head.getY() - tail.getY();

        if (Math.abs(hDistance) == 2) {
            if (head.getX() > tail.getX()) {
                tail.moveRight();
            } else {
                tail.moveLeft();
            }
            if (vDistance >= 1) {
                tail.moveUp();
            } else if (vDistance <= -1) {
                tail.moveDown();
            }
            return;
        }
        if (Math.abs(vDistance) == 2) {
            if (head.getY() > tail.getY()) {
                tail.moveUp();
            } else {
                tail.moveDown();
            }
            if (hDistance >= 1) {
                tail.moveRight();
            } else if (hDistance <= -1) {
                tail.moveLeft();
            }
        }
    }

    private boolean isTouching(Knot head, Knot tail) {
        if (head.equals(tail)) {
            return true;
        }
        return ((Math.abs(tail.getX() - head.getX())) + (Math.abs(tail.getY() - head.getY())) == 1);
    }

    private void parseInput() {
        moves = Arrays.stream(puzzleInput.split("\\R"))
                .map(move -> move.split(" "))
                .toList();
    }
}
