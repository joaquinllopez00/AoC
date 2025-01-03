package aoc;

import aoc.lib.CharacterMap2d;
import aoc.lib.Coord2D;

import java.util.Map;

public class Day20 extends AoCSolution {
    Coord2D StartPosition;
    Coord2D EndPosition;
    Long ShortestLegalPath = Long.MAX_VALUE;
    CharacterMap2d Maze;
    Long SavedTimeThreshold;
    Map<Coord2D, Long> DistancesFromStart;
    Map<Coord2D, Long> DistancesFromEnd;


    public Day20(String inputFilename, Long savedTimeThreshold) {
        super(inputFilename);
        Maze = CharacterMap2d.fromStringInputLines(inputLines, '#');
        StartPosition = Maze.findFirst('S').get();
        EndPosition = Maze.findFirst('E').get();
        SavedTimeThreshold = savedTimeThreshold;

        DistancesFromStart = Maze.getDistancesFrom(StartPosition);
        DistancesFromEnd = Maze.getDistancesFrom(EndPosition);
        ShortestLegalPath = DistancesFromStart.get(EndPosition);
    }

    public static void main(String[] args) {
        long startTotal = System.currentTimeMillis();
        Day20 day = new Day20("src/main/resources/day20.txt", 100L);

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

    private Long countShortcuts(Integer maxShortcutLength) {
        long shorterPathCount = 0L;

        // Iterate over all non-wall points on the grid
        for (int rowIdx = 0; rowIdx <= Maze.MAX_ROW_INDEX; rowIdx++) {
            for (int colIdx = 0; colIdx <= Maze.MAX_COLUMN_INDEX; colIdx++) {
                Coord2D currentPosition = new Coord2D(rowIdx, colIdx);
                if (!Maze.isWall(currentPosition)) {
                    // Check all the neighbors and check if the tile after the neighbor is free
                    for (Coord2D teleport : Maze.getManhattanClosedBallPoints(currentPosition, maxShortcutLength)) {
                        if (!Maze.isWall(teleport)) {
                            // Teleporting from p to q through a cheat
                            // d(S, p) + d(p, q) + d(q, E)
                            long cheatedDistance = DistancesFromStart.get(currentPosition) + currentPosition.manhattanDistanceFrom(teleport) + DistancesFromEnd.get(teleport);
                            if (ShortestLegalPath - cheatedDistance >= SavedTimeThreshold) {
                                shorterPathCount++;
                            }
                        }
                    }
                }
            }
        }
        return shorterPathCount;
    }

    @Override
    public Long solvePart1() {
        return countShortcuts(2);
    }

    @Override
    public Long solvePart2() {
        return countShortcuts(20);
    }
}