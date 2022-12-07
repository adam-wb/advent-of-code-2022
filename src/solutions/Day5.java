package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.*;

public class Day5 extends AdventOfCodePuzzle {

    private static final int[] CRATE_X_POSITIONS = {1, 5, 9, 13, 17, 21, 25, 29, 33};
    private final ArrayList<Stack<Character>> crateStacks = new ArrayList<>();
    private final List<int[]> moves;

    public Day5() throws IOException {
        super(5);
        initialiseStacks();
        moves = mapMoves();
    }

    @Override
    public void part1() {
        moves.forEach(this::crateMover9000);

        System.out.println(getTopOfStacks());
    }

    @Override
    public void part2() {
        moves.forEach(this::crateMover9001);

        System.out.println(getTopOfStacks());
    }

    private void crateMover9000(int[] move) {
        int quantity = move[0];
        int fromStack = move[1];
        int toStack = move[2];

        if (quantity <= 0) {
            System.out.printf("Invalid quantity to move - %s\n", quantity);
            return;
        }
        if (fromStack <= 0 || fromStack > CRATE_X_POSITIONS.length) {
            System.out.printf("Invalid stack to move from - %s (should be 1-%s)\n", fromStack, CRATE_X_POSITIONS.length);
            return;
        }
        if (toStack <= 0 || toStack > CRATE_X_POSITIONS.length) {
            System.out.printf("Invalid stack to move to - %s (should be 1-%s)\n", toStack, CRATE_X_POSITIONS.length);
            return;
        }

        for (int i = 0; i < quantity; i++) {
            Character crate = crateStacks.get(fromStack - 1).pop();
            crateStacks.get(toStack - 1).push(crate);
        }
    }

    private void crateMover9001(int[] move) {
        int quantity = move[0];
        int fromStack = move[1];
        int toStack = move[2];

        if (quantity <= 0) {
            System.out.printf("Invalid quantity to move - %s\n", quantity);
            return;
        }
        if (fromStack <= 0 || fromStack > CRATE_X_POSITIONS.length) {
            System.out.printf("Invalid stack to move from - %s (should be 1-%s)\n", fromStack, CRATE_X_POSITIONS.length);
            return;
        }
        if (toStack <= 0 || toStack > CRATE_X_POSITIONS.length) {
            System.out.printf("Invalid stack to move to - %s (should be 1-%s)\n", toStack, CRATE_X_POSITIONS.length);
            return;
        }

        Stack<Character> cratesToDrop = new Stack<>();
        for (int i = 0; i < quantity; i++) {
            cratesToDrop.add(crateStacks.get(fromStack - 1).pop());
        }
        for (int i = 0; i < quantity; i++) {
            crateStacks.get(toStack - 1).push(cratesToDrop.pop());
        }
    }

    private String getTopOfStacks() {
        StringBuilder sb = new StringBuilder();
        crateStacks.forEach(stack -> sb.append(stack.peek()));
        return sb.toString();
    }

    private void initialiseStacks() {
        // Initialise empty stacks
        for (int i = 0; i < CRATE_X_POSITIONS.length; i++) {
            crateStacks.add(new Stack<Character>());
        }

        // Parse text input
        String initialCrateState = puzzleInput.split("\\R\\R\\R")[0];
        List<String> cratesByLine = new ArrayList<>(Arrays.stream(initialCrateState.split("\\R")).toList());
        // Remove number line
        cratesByLine.remove(cratesByLine.size() - 1);
        // Reverse order for inputting bottom first
        Collections.reverse(cratesByLine);

        for (String s : cratesByLine) {
            int length = s.length();
            int[] cratePositions = Arrays.stream(CRATE_X_POSITIONS).filter(x -> x <= length).toArray();
            for (int i = 0; i < cratePositions.length; i++) {
                char crateLabel = s.charAt(cratePositions[i]);
                if (crateLabel == ' ') {
                    continue;
                }
                crateStacks.get(i).add(crateLabel);
            }
        }
    }

    private List<int[]> mapMoves() {
        List<String> crateMoves = Arrays.stream(puzzleInput.split("\\R\\R\\R")[1].split("\\R")).toList();
        return crateMoves.stream().map(this::mapMove).toList();
    }

    private int[] mapMove(String move) {
        String[] numbers = move.replaceAll("[^0-9]+", " ").trim().split(" ");
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
    }
}
