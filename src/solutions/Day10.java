package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10 extends AdventOfCodePuzzle {

    private List<String> instructions = new ArrayList<>();

    public Day10() throws IOException {
        super(10);
        parseInput();
    }

    @Override
    public void part1() {
        List<Integer> signalSnapshots = new ArrayList<>();
        int cycle = 1;
        int register = 1;
        for (String instruction : instructions) {
            if (instruction.startsWith("noop")) {
                cycle++;
                if ((cycle - 20) % 40 == 0) {
                    signalSnapshots.add(register * cycle);
                }
                continue;
            }
            int increment = Integer.parseInt(instruction.split(" ")[1]);
            cycle++;
            if ((cycle - 20) % 40 == 0) {
                signalSnapshots.add(register * cycle);
            }
            register += increment;
            cycle++;
            if ((cycle - 20) % 40 == 0) {
                signalSnapshots.add(register * cycle);
            }
        }

        int totalSignalStrengths = signalSnapshots.stream().reduce(Integer::sum).orElse(0);
        System.out.printf("Part 1 solution - %s\n", totalSignalStrengths);
    }

    @Override
    public void part2() {
        final int CRT_WIDTH = 40;
        final int CRT_HEIGHT = 6;
        List<List<Character>> crtScreen = new ArrayList<>();
        for (int i = 0; i < CRT_HEIGHT; i++) {
            crtScreen.add(new ArrayList<>(CRT_WIDTH));
        }

        int register = 1;
        int row = 0;
        int pixel = 0;
        for (String instruction : instructions) {
            if (instruction.startsWith("noop")) {
                crtScreen.get(row).add(getPixel(register, pixel));
                pixel++;
                if (pixel == CRT_WIDTH) {
                    row++;
                    pixel = 0;
                }
                continue;
            }
            int increment = Integer.parseInt(instruction.split(" ")[1]);
            crtScreen.get(row).add(getPixel(register, pixel));
            pixel++;
            if (pixel == CRT_WIDTH) {
                row++;
                pixel = 0;
            }
            crtScreen.get(row).add(getPixel(register, pixel));
            pixel++;
            register += increment;
            if (pixel == CRT_WIDTH) {
                row++;
                pixel = 0;
            }
        }

        StringBuilder sb = new StringBuilder();
        crtScreen.forEach(r -> {
            r.forEach(sb::append);
            sb.append("\n");
        });
        System.out.printf("Part 2 solution - \n%s\n", sb);
    }

    private char getPixel(int register, int pixel) {
        if (pixel >= register - 1 && pixel <= register + 1) {
            return '#';
        }
        return '.';
    }

    private void parseInput() {
        instructions = List.of(puzzleInput.split("\\R"));
    }
}
