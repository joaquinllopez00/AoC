package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    private final String input;

    /**
     * Constructor that takes the puzzle input
     */
    public Day03(String input) {
        this.input = input;
    }

    /**
     * Parse the input string and extract instructions
     * Includes handling for mul(X,Y), do(), and don't()
     * @return A list of parsed instructions
     */
    private List<String> parseInput() {
        List<String> instructions = new ArrayList<>();

        // Match all mul(X,Y), do(), and don't() instructions
        String regex = "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Collect all valid instructions
        while (matcher.find()) {
            instructions.add(matcher.group());
        }

        return instructions;
    }

    /**
     * Solve part 2: Sum the results of enabled mul(X,Y) instructions
     */
    public int solvePart2() {
        List<String> instructions = parseInput();
        boolean isEnabled = true; // Mul instructions start as enabled
        int totalSum = 0;

        for (String instruction : instructions) {
            if (instruction.equals("do()")) {
                isEnabled = true;
            } else if (instruction.equals("don't()")) {
                isEnabled = false;
            } else if (instruction.startsWith("mul(") && isEnabled) {
                // Extract numbers from the mul(X,Y) instruction
                Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
                Matcher mulMatcher = mulPattern.matcher(instruction);

                if (mulMatcher.find()) {
                    int x = Integer.parseInt(mulMatcher.group(1));
                    int y = Integer.parseInt(mulMatcher.group(2));
                    totalSum += x * y;
                }
            }
        }

        return totalSum;
    }
}