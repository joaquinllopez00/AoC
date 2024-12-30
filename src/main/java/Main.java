import aoc.Day05;
// I stopped using this after day5, day classes now have their own main methods
public class Main {
    public static void main(String[] args) {
        // Your puzzle input as a string
        // Example input from the problem
        String input = """
        xxx
               """;
        Day05 day = new Day05(input);
        int part1Result = day.solvePart1();
        System.out.println(part1Result);

        // int part2Result = day.solvePart2();
        // System.out.println(part2Result);
    }
}
