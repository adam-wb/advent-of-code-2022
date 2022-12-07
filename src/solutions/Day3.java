package solutions;

import utils.AdventOfCodePuzzle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day3 extends AdventOfCodePuzzle {

    private static final int UPPER_CASE_OFFSET = 38;
    private static final int LOWER_CASE_OFFSET = 96;
    private static final int ELF_GROUP_SIZE = 3;

    public Day3() throws IOException {
        super(3);
    }

    @Override
    public void part1() {
        List<List<String>> rucksackContents = getRucksackContentsByCompartment();
        List<List<Character>> itemsInBoth = getItemsInBothCompartments(rucksackContents);
        int totalRucksackCommonItemPriorities = sumRucksackCommonItemPriorities(itemsInBoth);

        System.out.printf("Part 1 solution: %s", totalRucksackCommonItemPriorities);
    }

    @Override
    public void part2() {
        List<Character> elfBadges = getElfGroupBadges();
        int totalGroupItemPriorities = getRucksackCommonItemPriorities(elfBadges);

        System.out.printf("Part 2 solution: %s", totalGroupItemPriorities);
    }

    private List<List<String>> getRucksackContentsByCompartment() {
        List<String> rucksackContents = List.of(puzzleInput.split("\\R"));
        return rucksackContents.stream().map(rucksack -> {
            String firstCompartmentItems = rucksack.substring(0, rucksack.length() / 2);
            String secondCompartmentItems = rucksack.substring(rucksack.length() / 2);
            List<String> rucksackByCompartment = new ArrayList<>();
            rucksackByCompartment.add(firstCompartmentItems);
            rucksackByCompartment.add(secondCompartmentItems);
            return rucksackByCompartment;
        }).toList();
    }

    private List<List<Character>> getItemsInBothCompartments(List<List<String>> rucksackContents) {
        return rucksackContents.stream().map(rucksack -> {
            String firstCompartmentItems = rucksack.get(0);
            String secondCompartmentItems = rucksack.get(1);
            return getCommonChars(firstCompartmentItems, secondCompartmentItems);
        }).toList();
    }

    private List<Character> getCommonChars(String s1, String s2) {
        HashSet<Character> h1 = new HashSet<>();
        HashSet<Character> h2 = new HashSet<>();

        for (int i = 0; i < s1.length(); i++) {
            h1.add(s1.charAt(i));
            h2.add(s2.charAt(i));
        }

        h1.retainAll(h2);
        return new ArrayList<>(h1);
    }

    private int sumRucksackCommonItemPriorities(List<List<Character>> rucksacks) {
        return rucksacks.stream().map(this::getRucksackCommonItemPriorities).reduce(Integer::sum).orElse(0);
    }

    private int getRucksackCommonItemPriorities(List<Character> items) {
        return items.stream().map(this::getItemPriority).reduce(Integer::sum).orElse(0);
    }

    private int getItemPriority(Character item) {
        boolean isUpperCase = Character.isUpperCase(item);
        int charValue = (int) item;
        System.out.printf("Value of char %s is %d\n", item, charValue);
        return isUpperCase ? charValue - UPPER_CASE_OFFSET : charValue - LOWER_CASE_OFFSET;
    }

    private List<Character> getElfGroupBadges() {
        List<String> rucksackContents = List.of(puzzleInput.split("\\R"));
        List<Character> groupItems = new ArrayList<>();
        for (int i = 0; i < rucksackContents.size(); i += ELF_GROUP_SIZE) {
            groupItems.add(getGroupItem(rucksackContents.get(i), rucksackContents.get(i + 1), rucksackContents.get(i + 2)));
        }
        return groupItems;
    }

    private Character getGroupItem(String s1, String s2, String s3) {
        HashSet<Character> h1 = new HashSet<>();
        HashSet<Character> h2 = new HashSet<>();
        HashSet<Character> h3 = new HashSet<>();

        for (int i = 0; i < s1.length(); i++) {
            h1.add(s1.charAt(i));
        }
        for (int i = 0; i < s2.length(); i++) {
            h2.add(s2.charAt(i));
        }
        for (int i = 0; i < s3.length(); i++) {
            h3.add(s3.charAt(i));
        }

        h1.retainAll(h2);
        h1.retainAll(h3);
        List<Character> commonItems = new ArrayList<>(h1);
        if (commonItems.size() != 1) {
            System.out.println("More than 1 group item found");
        }
        return commonItems.get(0);
    }
}
