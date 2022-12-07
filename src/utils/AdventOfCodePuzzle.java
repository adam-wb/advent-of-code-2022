package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AdventOfCodePuzzle {

    private static final Path PUZZLE_INPUT_DIR = Path.of("./\\input");

    protected final String puzzleInput;

    /**
     *
     * @param day The day of advent of code this class solves. Determines puzzle input the class tries to fetch.
     * @throws IOException where some puzzle input cannot be found in the input folder with the name "day{day}.txt"
     */
    protected AdventOfCodePuzzle(int day) throws IOException {
        this.puzzleInput = getPuzzleInput(day);
    }

    /**
     * Solves part 1 of the day's puzzle
     */
    public abstract void part1();

    /**
     * Solves part 2 of the day's puzzle
     */
    public abstract void part2();

    private String getPuzzleInput(int day) throws IOException {
        return Files.readString(PUZZLE_INPUT_DIR.resolve(String.format("day%s.txt", day)));
    }
}
