package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

public class Monkey {

    public List<Long> items;
    private final LongFunction<Long> operation;
    private final LongFunction<Integer> test;

    private int inspections;

    public Monkey(List<Long> startingItems, LongFunction<Long> operation, LongFunction<Integer> test) {
        this.items = new ArrayList<>(startingItems);
        this.operation = operation;
        this.test = test;
        this.inspections = 0;
    }

    public long updateWorry(long item) {
       item = operation.apply(item);
       return item / 3;
    }

    public long updateWorryPart2(long item, int commonMultiple) {
        item = operation.apply(item);
        return item % commonMultiple;
    }

    public int getMonkeyToThrowTo(long item) {
        return test.apply(item);
    }

    public int inspect(long item) {
        item = updateWorry(item);
        inspections++;
        return getMonkeyToThrowTo(item);
    }

    public int inspectPart2(long item, int commonMultiple) {
        item = updateWorryPart2(item, commonMultiple);
        inspections++;
        return getMonkeyToThrowTo(item);
    }

    public int getInspections() {
        return inspections;
    }
}
