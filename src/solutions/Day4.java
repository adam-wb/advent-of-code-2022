package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends AdventOfCodePuzzle {

    public Day4() throws IOException {
        super(4);
    }

    @Override
    public void part1() {
        List<String[]> assignmentsPerPair = Arrays.stream(puzzleInput.split("\\R"))
                .map(assignmentPair -> assignmentPair.split(","))
                .toList();
        long fullOverlapAssignments = assignmentsPerPair.stream()
                .filter(this::isFullOverlap).count();

        System.out.printf("Part 1 solution: %s\n", fullOverlapAssignments);
    }

    @Override
    public void part2() {
        List<String[]> assignmentsPerPair = Arrays.stream(puzzleInput.split("\\R"))
                .map(assignmentPair -> assignmentPair.split(","))
                .toList();
        long someOverlapAssignments = assignmentsPerPair.stream()
                .filter(this::isAnyOverlap).count();

        System.out.printf("Part 2 solution: %s\n", someOverlapAssignments);
    }

    private boolean isFullOverlap(String[] assignment) {
        HashSet<Integer> assignment1Sections = unfoldAssignment(assignment[0]);
        HashSet<Integer> assignment2Sections = unfoldAssignment(assignment[1]);

        return assignment1Sections.containsAll(assignment2Sections)
                || assignment2Sections.containsAll(assignment1Sections);
    }

    private boolean isAnyOverlap(String [] assignment) {
        HashSet<Integer> assignment1Sections = unfoldAssignment(assignment[0]);
        HashSet<Integer> assignment2Sections = unfoldAssignment(assignment[1]);

        return assignment1Sections.stream().anyMatch(assignment2Sections::contains)
                || assignment2Sections.stream().anyMatch(assignment1Sections::contains);
    }

    private HashSet<Integer> unfoldAssignment(String assignment) {
        String[] assignmentBounds = assignment.split("-");
        int lowerBound = Integer.parseInt(assignmentBounds[0]);
        int upperBound = Integer.parseInt(assignmentBounds[1]) + 1;

        return IntStream.range(lowerBound, upperBound).boxed()
                .collect(Collectors.toCollection(HashSet::new));
    }
}
