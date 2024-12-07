package aoc;

import java.util.ArrayList;
import java.util.List;

public class Day02 {
    private final String input;

    /**
     * Constructor that takes the puzzle input
     */
    public Day02(String input) {
        this.input = input;
    }

    /**
     * Convert input string into list of integer arrays
     * Each array represents one report's levels
     */
    private List<int[]> parseInput() {
        List<int[]> reports = new ArrayList<>();

        for (String line : input.trim().split("\n")) {
            if (line.trim().isEmpty()) continue;

            // Convert line of numbers into int array
            String[] parts = line.trim().split("\\s+");
            int[] levels = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                levels[i] = Integer.parseInt(parts[i]);
            }
            reports.add(levels);
        }

        return reports;
    }

    /**
     * Check if a sequence of levels is safe according to rules:
     * 1. All increasing or all decreasing
     * 2. Adjacent levels differ by 1-3
     */
    private boolean isReportSafe(int[] levels) {
        if (levels.length < 2) return true;

        // Check first difference to determine if should be increasing or decreasing
        int firstDiff = levels[1] - levels[0];
        if (Math.abs(firstDiff) < 1 || Math.abs(firstDiff) > 3) {
            return false;
        }

        boolean shouldBeIncreasing = firstDiff > 0;

        // Check all adjacent pairs
        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i-1];

            // Check rules:
            // 1. Difference must be between 1 and 3 (inclusive)
            // 2. Must maintain same direction (increasing or decreasing)
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            if (shouldBeIncreasing && diff <= 0) {
                return false;
            }

            if (!shouldBeIncreasing && diff >= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if a report can be made safe by removing one number
     */
    private boolean canBeMadeSafe(int[] levels) {
        // Try removing each number one at a time
        for (int skipIndex = 0; skipIndex < levels.length; skipIndex++) {
            // Create new array without the number at skipIndex
            int[] newLevels = new int[levels.length - 1];
            int newIndex = 0;

            for (int i = 0; i < levels.length; i++) {
                if (i != skipIndex) {
                    newLevels[newIndex++] = levels[i];
                }
            }

            // Check if this version is safe
            if (isReportSafe(newLevels)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Solve part 1: Count how many reports are safe
     */
    public int solvePart1() {
        List<int[]> reports = parseInput();

        int safeCount = 0;
        for (int[] report : reports) {
            if (isReportSafe(report)) {
                safeCount++;
            }
        }

        return safeCount;
    }

    /**
     * Solve part 2: Count reports that are either safe or can be made safe
     * by removing one number (Problem Dampener)
     */
    public int solvePart2() {
        List<int[]> reports = parseInput();

        int safeCount = 0;
        for (int[] report : reports) {
            if (isReportSafe(report) || canBeMadeSafe(report)) {
                safeCount++;
            }
        }

        return safeCount;
    }
}