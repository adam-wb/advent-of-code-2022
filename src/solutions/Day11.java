package solutions;

import utils.AdventOfCodePuzzle;
import utils.Monkey;

import java.io.IOException;
import java.util.*;

public class Day11 extends AdventOfCodePuzzle {

    private final List<Monkey> monkeys = new ArrayList<>();

    public Day11() throws IOException {
        super(11);
        initialiseMonkeys();
    }

    @Override
    public void part1() {
        final int NO_OF_ROUNDS = 20;

        for (int i = 0; i < NO_OF_ROUNDS; i++) {
            for (Monkey monkey : monkeys) {
                Iterator<Long> it = monkey.items.iterator();
                while (it.hasNext()) {
                    long item = it.next();
                    System.out.printf("Monkey inspecting item with worry %s\n", item);
                    int throwToMonkey = monkey.inspect(item);
                    System.out.printf("Throwing item with worry %s to monkey %s\n", monkey.updateWorry(item), throwToMonkey);
                    monkeys.get(throwToMonkey).items.add(monkey.updateWorry(item));
                    it.remove();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("End of round %s\n", i + 1));
            monkeys.forEach((monkey) -> sb.append(String.format("%s\n", monkey.items)));
            System.out.println(sb);
        }

        List<Integer> inspections = monkeys.stream().map(Monkey::getInspections).toList();
        System.out.println(inspections);
        long monkeyBusiness = findTop2(inspections).stream().reduce((a, b) -> a * b).orElse(0L);
        System.out.printf("Part 1 solution - %s\n", monkeyBusiness);
    }

    @Override
    public void part2() {
        final int NO_OF_ROUNDS = 10000;
        final int COMMON_MULTIPLE = 7 * 3 * 2 * 11 * 17 * 5 * 13 * 19;

        for (int i = 0; i < NO_OF_ROUNDS; i++) {
            for (Monkey monkey : monkeys) {
                Iterator<Long> it = monkey.items.iterator();
                while (it.hasNext()) {
                    long item = it.next();
                    int throwToMonkey = monkey.inspectPart2(item, COMMON_MULTIPLE);
                    monkeys.get(throwToMonkey).items.add(monkey.updateWorryPart2(item, COMMON_MULTIPLE));
                    it.remove();
                }
            }
            if ((i+1) % 500 == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("End of round %s\n", i + 1));
                monkeys.forEach((monkey) -> sb.append(String.format("%s\n", monkey.items)));
                System.out.println(sb);
            }
        }

        List<Integer> inspections = monkeys.stream().map(Monkey::getInspections).toList();
        System.out.println(inspections);
        long monkeyBusiness = findTop2(inspections).stream().reduce((a, b) -> a * b).orElse(0L);
        System.out.printf("Part 2 solution - %s\n", monkeyBusiness);
    }

    private List<Long> findTop2(List<Integer> inspections) {
        PriorityQueue<Long> maxHeap = new PriorityQueue<>();

        inspections.forEach(number -> {
            maxHeap.add(Long.valueOf(number));
            if (maxHeap.size() > 2) {
                maxHeap.poll();
            }
        });

        List<Long> top2 = new ArrayList<>(maxHeap);
        Collections.reverse(top2);
        return top2;
    }

    private void initialiseMonkeys() {
        // Puzzle input
        List<Long> monkey0StartingItems = Arrays.asList(91L, 58L, 52L, 69L, 95L, 54L);
        Monkey monkey0 = new Monkey(monkey0StartingItems, ((worry) -> worry * 13), ((worry) -> worry % 7 == 0 ? 1 : 5));
        monkeys.add(monkey0);

        List<Long> monkey1StartingItems = Arrays.asList(80L, 80L, 97L, 84L);
        Monkey monkey1 = new Monkey(monkey1StartingItems, ((worry) -> worry * worry), ((worry) -> worry % 3 == 0 ? 3 : 5));
        monkeys.add(monkey1);

        List<Long> monkey2StartingItems = Arrays.asList(86L, 92L, 71L);
        Monkey monkey2 = new Monkey(monkey2StartingItems, ((worry) -> worry + 7), ((worry) -> worry % 2 == 0 ? 0 : 4));
        monkeys.add(monkey2);

        List<Long> monkey3StartingItems = Arrays.asList(96L, 90L, 99L, 76L, 79L, 85L, 98L, 61L);
        Monkey monkey3 = new Monkey(monkey3StartingItems, ((worry) -> worry + 4), ((worry) -> worry % 11 == 0 ? 7 : 6));
        monkeys.add(monkey3);

        List<Long> monkey4StartingItems = Arrays.asList(60L, 83L, 68L, 64L, 73L);
        Monkey monkey4 = new Monkey(monkey4StartingItems, ((worry) -> worry * 19), ((worry) -> worry % 17 == 0 ? 1 : 0));
        monkeys.add(monkey4);

        List<Long> monkey5StartingItems = Arrays.asList(96L, 52L, 52L, 94L, 76L, 51L, 57L);
        Monkey monkey5 = new Monkey(monkey5StartingItems, ((worry) -> worry + 3), ((worry) -> worry % 5 == 0 ? 7 : 3));
        monkeys.add(monkey5);

        List<Long> monkey6StartingItems = List.of(75L);
        Monkey monkey6 = new Monkey(monkey6StartingItems, ((worry) -> worry + 5), ((worry) -> worry % 13 == 0 ? 4 : 2));
        monkeys.add(monkey6);

        List<Long> monkey7StartingItems = Arrays.asList(83L, 75L);
        Monkey monkey7 = new Monkey(monkey7StartingItems, ((worry) -> worry + 1), ((worry) -> worry % 19 == 0 ? 2 : 6));
        monkeys.add(monkey7);
    }
}
