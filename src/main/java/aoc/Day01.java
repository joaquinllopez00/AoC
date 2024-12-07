package aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 {
    private final String input;

    /**
     * Constructor that takes the puzzle input
     */
    public Day01(String input) {
        this.input = input;
    }

    /**
     * Splits input string into two lists of numbers
     * Example input: "40885   43247\n14780   86274"
     */
    private List<List<Integer>> separateInputIntoTwoLists() {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        for (String line : input.trim().split("\n")) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.trim().split("\\s+");
            leftList.add(Integer.parseInt(parts[0]));
            rightList.add(Integer.parseInt(parts[1]));
        }

        return List.of(leftList, rightList);
    }

    /**
     * Part 1: Calculate total distance between sorted lists
     */
    public long solvePart1() {
        List<List<Integer>> lists = separateInputIntoTwoLists();
        List<Integer> leftList = new ArrayList<>(lists.get(0));
        List<Integer> rightList = new ArrayList<>(lists.get(1));

        Collections.sort(leftList);
        Collections.sort(rightList);

        long totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }

        return totalDistance;
    }

    /**
     * Creates a map of how many times each number appears
     */
    private Map<Integer, Integer> countOccurrences(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : list) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        return map;
    }

    /**
     * Part 2: Calculate similarity score
     */
    public long solvePart2() {
        List<List<Integer>> lists = separateInputIntoTwoLists();
        List<Integer> leftList = lists.get(0);
        List<Integer> rightList = lists.get(1);

        Map<Integer, Integer> rightCount = countOccurrences(rightList);

        long totalScore = 0;
        for (int leftNum : leftList) {
            int countInRight = rightCount.getOrDefault(leftNum, 0);
            totalScore += (long) leftNum * countInRight;
        }

        return totalScore;
    }
}