package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.*;

public class Day1 extends AdventOfCodePuzzle {
    public Day1() throws IOException {
        super(1);
    }

    @Override
    public void part1() {
        List<List<String>> elfInventories = getElfInventories();
        List<Integer> elfCalories = elfInventories.stream().map(this::countElfCalories).toList();
        int maxCalories = Collections.max(elfCalories);
        System.out.printf("Part 1 solution: %s\n", maxCalories);
    }

    @Override
    public void part2() {
        List<List<String>> elfInventories = getElfInventories();
        List<Integer> elfCalories = elfInventories.stream().map(this::countElfCalories).toList();
        Optional<Integer> totalCalories = findTop3Calories(elfCalories).stream().reduce(Integer::sum);
        System.out.printf("Part 2 solution: %s\n", totalCalories.orElse(0));
    }

    private List<List<String>> getElfInventories() {
        String[] calorieEntries = puzzleInput.split("\\R");
        List<List<String>> elfInventories = new ArrayList<>();
        List<String> elfCalories = new ArrayList<>();

        for (String entry: calorieEntries) {
            if (entry == null || entry.equals("")) {
                elfInventories.add(elfCalories);
                elfCalories = new ArrayList<>();
                continue;
            }
            elfCalories.add(entry);
        }
        return elfInventories;
    }

    private int countElfCalories(List<String> elfInventory) {
        int totalCalories = 0;
        for (String calorieEntry : elfInventory) {
            totalCalories += Integer.parseInt(calorieEntry);
        }
        return totalCalories;
    }

    private List<Integer> findTop3Calories(List<Integer> elfCalories) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();

        elfCalories.forEach(number -> {
            maxHeap.add(number);
            if (maxHeap.size() > 3) {
                maxHeap.poll();
            }
        });

        List<Integer> top3List = new ArrayList<>(maxHeap);
        Collections.reverse(top3List);
        return top3List;
    }
}
