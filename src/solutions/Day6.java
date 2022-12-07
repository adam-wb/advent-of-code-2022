package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class Day6 extends AdventOfCodePuzzle {

    private final static int PACKET_MARKER_SIZE = 4;
    private final static int MESSAGE_MARKER_SIZE = 14;

    public Day6() throws IOException {
        super(6);
    }

    @Override
    public void part1() {
        System.out.printf("Part 1 solution - %s\n", getFirstMarkerLocation(puzzleInput, PACKET_MARKER_SIZE));
    }

    @Override
    public void part2() {
        System.out.printf("Part 2 solution - %s\n", getFirstMarkerLocation(puzzleInput, MESSAGE_MARKER_SIZE));
    }

    private int getFirstMarkerLocation(String signal, int markerSize) {
        Queue<Character> buffer = new ArrayDeque<>();
        for (int i = 0; i < signal.length(); i++) {
            buffer.add(signal.charAt(i));
            if (buffer.size() >= markerSize) {
                HashSet<Character> uniqueChars = new HashSet<>(buffer);
                if (uniqueChars.size() == markerSize) {
                    return i + 1;
                }
                buffer.remove();
            }
        }
        System.out.println("No marker found");
        return 0;
    }
}
