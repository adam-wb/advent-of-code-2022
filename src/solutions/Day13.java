package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day13 extends AdventOfCodePuzzle {

    private final List<List<List<Object>>> input;

    public Day13() throws IOException {
        super(13);
        this.input = parseInput();
    }

    @Override
    public void part1() {
        List<Integer> orderedPacketIndices = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            System.out.printf("Comparing packet pair %s\n", i+1);
            if (isOrdered(input.get(i))) {
                orderedPacketIndices.add(i + 1);
            }
        }

        System.out.printf("Indices of packets in the correct order - %s\n", orderedPacketIndices);
        int orderedPacketIndicesSum = orderedPacketIndices.stream().reduce(Integer::sum).orElse(0);
        System.out.printf("Part 1 solution - %s\n", orderedPacketIndicesSum);
    }

    @Override
    public void part2() {
        List<List<Object>> flattenedPackets = new ArrayList<>(input.stream().flatMap(List::stream).toList());
        flattenedPackets.add(parsePacket("[[2]]"));
        flattenedPackets.add(parsePacket("[[6]]"));

        flattenedPackets.sort(this::orderPackets);
        System.out.println("Sorted packets");
        flattenedPackets.forEach(System.out::println);

        int dividerPacketOneIndex = IntStream.range(0, flattenedPackets.size())
                .filter(i -> flattenedPackets.get(i).toString().equals("[[2]]"))
                .findFirst()
                .orElse(-1);
        int dividerPacketTwoIndex = IntStream.range(0, flattenedPackets.size())
                .filter(i -> flattenedPackets.get(i).toString().equals("[[6]]"))
                .findFirst()
                .orElse(-1);

        int decoderKey = (dividerPacketOneIndex + 1) * (dividerPacketTwoIndex + 1);
        System.out.printf("Part 2 solution - %s", decoderKey);
    }

    private boolean isOrdered(List<List<Object>> packetPair) {
        if (packetPair.size() != 2) {
            return false;
        }
        boolean isOrdered = orderPackets(packetPair.get(0), packetPair.get(1)) == -1;
        if (isOrdered) {
            System.out.println("Inputs are in the right order");
        } else {
            System.out.println("Inputs are in the wrong order");
        }
        return isOrdered;
    }

    private int orderPackets(List<Object> left, List<Object> right) {
        System.out.printf("Left packet - %s\n", left);
        System.out.printf("Right packet - %s\n", right);
        for (int index = 0; index < left.size() && index < right.size(); index++) {
            Object leftCurrent = left.get(index);
            Object rightCurrent = right.get(index);
            int comparison = compare(leftCurrent, rightCurrent);
            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(left.size(), right.size());
    }

    private int compare(Object left, Object right) {
        System.out.printf("Comparing %s to %s\n", left, right);
        if (left instanceof Integer && right instanceof Integer) {
            return Integer.compare((int) left, (int) right);
        }
        if (left instanceof List<?> && right instanceof List<?>) {
            int leftSize = ((List<?>) left).size();
            int rightSize = ((List<?>) right).size();
            for (int i = 0; i < leftSize && i < rightSize; i++) {
                int comparison = compare(((List<?>) left).get(i), ((List<?>) right).get(i));
                if (comparison != 0) {
                    return comparison;
                }
            }
            return Integer.compare(leftSize, rightSize);
        }
        if (left instanceof Integer) {
            List<Integer> leftAsList = new ArrayList<>();
            leftAsList.add((Integer) left);
            return compare(leftAsList, right);
        }
        if (right instanceof Integer) {
            List<Integer> rightAsList = new ArrayList<>();
            rightAsList.add((Integer) right);
            return compare(left, rightAsList);
        }
        return 0;
    }

    private List<List<List<Object>>> parseInput() {
        List<String> packetPairs = List.of(puzzleInput.split("\\R\\R\\R"));
        List<List<String>> packets = packetPairs.stream().map(pair -> List.of(pair.split("\\R"))).toList();

        List<List<List<Object>>> parsedPackets = new ArrayList<>();
        for (List<String> pair : packets) {
            List<List<Object>> parsedPair = new ArrayList<>();
            parsedPair.add(parsePacket(pair.get(0)));
            parsedPair.add(parsePacket(pair.get(1)));
            parsedPackets.add(parsedPair);
        }

        return parsedPackets;
    }

    private List<Object> parsePacket(String packet) {
        String numberValues = packet
                .replace(",", " ")
                .replace("[", " ")
                .replace("]", " ");
        List<Object> result = new ArrayList<>();
        Stack<List<Object>> stack = new Stack<>();
        stack.push(result);
        for (int i = 1; i < packet.length() - 1; i++) {
            switch (packet.charAt(i)) {
                case ',' -> {}
                case ']' -> stack.pop();
                case '[' -> {
                    List<Object> newList = new ArrayList<>();
                    stack.peek().add(newList);
                    stack.push(newList);
                }
                default -> {
                    int end = numberValues.indexOf(' ', i + 1);
                    stack.peek().add(Integer.parseInt(numberValues.substring(i, end)));
                    i = end - 1;
                }
            }
        }
        return result;
    }
}
