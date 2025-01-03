package aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day11 extends AoCSolution {
    public Day11(String inputFilename) {
        super(inputFilename);
    }

    public static void main(String[] args) {
        long startTotal = System.currentTimeMillis();
        Day11 day = new Day11("src/main/resources/day11.txt");

        long startPart1 = System.currentTimeMillis();
        Long part1Solution = day.solvePart1();
        long endPart1 = System.currentTimeMillis();
        System.out.println("Part 1: " + part1Solution + " (Time: " + (endPart1 - startPart1) + " ms)");

        long startPart2 = System.currentTimeMillis();
        Long part2Solution = day.solvePart2();
        long endPart2 = System.currentTimeMillis();
        System.out.println("Part 2: " + part2Solution + " (Time: " + (endPart2 - startPart2) + " ms)");

        long endTotal = System.currentTimeMillis();
        System.out.println("Total time: " + (endTotal - startTotal) + " ms");
    }

    @Override
    public Long solvePart1() {
        StoneCollection collection = StoneCollection.from(inputLines[0]);
        for (int i = 1; i <= 25; i++) {
            collection = collection.blink();
        }

        return collection.countAll();
    }

    @Override
    public Long solvePart2() {
        StoneCollection collection = StoneCollection.from(inputLines[0]);
        for (int i = 1; i <= 75; i++) {
            collection = collection.blink();
        }

        return collection.countAll();
    }
}

/**
 * Simple enough for part 1, but not efficient enough for part 2;
 */
record Stone(Long engravedNote) {
    public List<Stone> blink() {
        if (engravedNote == 0L) {
            return List.of(new Stone(1L));
        }
        String engravedNoteStr = engravedNote.toString();
        if (engravedNoteStr.length() % 2 == 0) {
            int middlePoint = engravedNoteStr.length() / 2;
            Stone left = new Stone(Long.parseLong(engravedNoteStr.substring(0, middlePoint)));
            Stone right = new Stone(Long.parseLong(engravedNoteStr.substring(middlePoint)));
            return List.of(left, right);
        }
        return List.of(new Stone(engravedNote * 2024L));
    }

    public Long sizeAfterBlinks(Integer numberOfBlinks) {
        Stream<Stone> stones = Stream.of(this);
        for (int i = 1; i <= numberOfBlinks; i++) {
            stones = stones.flatMap(stone -> stone.blink().stream());
        }
        return stones.count();
    }
}

class StoneCollection {
    private final Map<Long, Long> StoneCounts = new HashMap<>();

    public static StoneCollection from(String input) {
        StoneCollection collection = new StoneCollection();
        Arrays.stream(input.split(" "))
                .forEach(note ->
                        collection.updateCount(Long.parseLong(note), 1L)
                );
        return collection;
    }

    public void updateCount(Long note, Long addition) {
        this.StoneCounts.merge(note, addition, Long::sum);
    }

    public StoneCollection blink() {
        StoneCollection updatedCollection = new StoneCollection();
        for (var stoneEntry : StoneCounts.entrySet()) {
            Long note = stoneEntry.getKey();
            Long count = stoneEntry.getValue();

            if (note == 0L) {
                updatedCollection.updateCount(1L, count);
            } else {
                String noteStr = note.toString();
                if (noteStr.length() % 2 == 0) {
                    int middlePoint = noteStr.length() / 2;
                    long left = Long.parseLong(noteStr.substring(0, middlePoint));
                    long right = Long.parseLong(noteStr.substring(middlePoint));
                    updatedCollection.updateCount(left, count);
                    updatedCollection.updateCount(right, count);
                } else {
                    long multipliedNote = note * 2024;
                    updatedCollection.updateCount(multipliedNote, count);
                }
            }
        }
        return updatedCollection;
    }

    public Long countAll() {
        return this.StoneCounts.values().stream().mapToLong(i -> i).sum();
    }
}