package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day2 extends AdventOfCodePuzzle {
    public Day2() throws IOException {
        super(2);
    }

    @Override
    public void part1() {
        List<String> games = Arrays.stream(puzzleInput.split("\\R")).toList();
        Optional<Integer> totalScore = games.stream().map(Day2::calculateScore).reduce(Integer::sum);
        System.out.printf("Part 1 solution: %s\n", totalScore.orElse(0));
    }

    @Override
    public void part2() {
        List<String> games = Arrays.stream(puzzleInput.split("\\R")).toList();
        Optional<Integer> totalScore = games.stream().map(Day2::calculateScorePart2).reduce(Integer::sum);
        System.out.printf("Part 2 solution: %s\n", totalScore.orElse(0));
    }

    private static int calculateScore(String game) {
        // First char is opponent choice A (Rock), B (Paper), C (Scissors)
        // Third char is your choice X (Rock), Y (Paper), Z (Scissors)
        char opponentChoice = game.charAt(0);
        char yourChoice = game.charAt(2);
        return calculateResultScorePart1(opponentChoice, yourChoice) + calculateChoiceScorePart1(yourChoice);
    }

    private static int calculateScorePart2(String game) {
        // First char is opponent choice A (Rock), B (Paper), C (Scissors)
        // Third char is desired result X (Lose), Y (Draw), Z (Win)
        char opponentChoice = game.charAt(0);
        char yourChoice = game.charAt(2);
        return calculateResultScorePart2(yourChoice) + calculateChoiceScorePart2(opponentChoice, yourChoice);
    }

    private static int calculateResultScorePart1(char opponent, char you) {
        // Win - 6, Draw - 3, Lose - 0
        return switch (opponent) {
            case 'A' -> you == 'Y' ? 6 : you == 'X' ? 3 : 0;
            case 'B' -> you == 'Z' ? 6 : you == 'Y' ? 3 : 0;
            case 'C' -> you == 'X' ? 6 : you == 'Z' ? 3 : 0;
            default -> 0;
        };
    }

    private static int calculateResultScorePart2(char you) {
        // Win - 6, Draw - 3, Lose - 0
        return switch (you) {
            case 'X' -> 0;
            case 'Y' -> 3;
            case 'Z' -> 6;
            default -> 0;
        };
    }

    private static int calculateChoiceScorePart1(char choice) {
        // Rock - 1, Paper - 2, Scissors - 3
        return switch (choice) {
            case 'X' -> 1;
            case 'Y' -> 2;
            case 'Z' -> 3;
            default -> 0;
        };
    }

    private static int calculateChoiceScorePart2(char opponent, char you) {
        // Rock - 1, Paper - 2, Scissors - 3
        return switch (opponent) {
            case 'A' -> you == 'X' ? 3 : you == 'Y' ? 1 : 2;
            case 'B' -> you == 'X' ? 1 : you == 'Y' ? 2 : 3;
            case 'C' -> you == 'X' ? 2 : you == 'Y' ? 3 : 1;
            default -> 0;
        };
    }
}
