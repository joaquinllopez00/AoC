package aoc;

import aoc.lib.Coord2D;
import aoc.lib.Map2D;
import aoc.lib.SubsetSupport;

import java.util.*;







public class Day08 extends AoCSolution {
    final private Map2D<Character> AntennaMap;
    final private HashMap<Character, Set<Coord2D>> Antennas;

    public Day08(String inputFilename) {
        super(inputFilename);
        this.AntennaMap = Map2D.fromStringInputLines(inputLines);

        HashMap<Character, Set<Coord2D>> antennas = new HashMap<>();
        for (int row = 0; row <= this.AntennaMap.MAX_ROW_INDEX; row++) {
            for (int col = 0; col <= this.AntennaMap.MAX_COLUMN_INDEX; col++) {
                Coord2D position = new Coord2D(row, col);
                Character antennaName = this.AntennaMap.getAt(position);
                if (antennaName != '.') {
                    Set<Coord2D> antennaGroup = antennas.getOrDefault(antennaName, new HashSet<Coord2D>());
                    antennaGroup.add(position);
                    antennas.put(antennaName, antennaGroup);
                }
            }
        }
        this.Antennas = antennas;
    }

    public static void main(String[] args) {
        long startTotal = System.currentTimeMillis();
        Day08 day = new Day08("src/main/resources/day8.txt");

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
        Set<Coord2D> antipodes = new HashSet<>();
        for (Set<Coord2D> antennaGroup : Antennas.values()) {
            antipodes.addAll(findAntipodes(antennaGroup));
        }
        return (long) antipodes.size();
    }

    Set<Coord2D> findAntipodes(Set<Coord2D> antennaGroup) {
        Set<Coord2D> antipodes = new HashSet<>();
        Set<Set<Coord2D>> pairs = SubsetSupport.getAllPairs(antennaGroup);

        for (Set<Coord2D> pair : pairs) {
            List<Coord2D> pairL = pair.stream().toList();
            Coord2D a1 = pairL.getFirst();
            Coord2D a2 = pairL.getLast();
            // Directed distance between two points: (a1 - a2) & (a2 - a1)
            // Antipode = a1 - (a2 - a1) = 2a1 - a2
            Coord2D antipode1 = a1.scalarMultiplication(2).minus(a2);
            if (this.AntennaMap.isWithinBounds(antipode1)) {
                antipodes.add(antipode1);
            }
            Coord2D antipode2 = a2.scalarMultiplication(2).minus(a1);
            if (this.AntennaMap.isWithinBounds(antipode2)) {
                antipodes.add(antipode2);
            }

        }
        return antipodes;
    }

    Set<Coord2D> findAllAntipodes(Set<Coord2D> antennaGroup) {
        Set<Coord2D> antipodes = new HashSet<>();
        // This actually calculates the result twice: for (a1, a2) and (a2, a1), but the final set results does the deduplication anyway
        for (Coord2D a1 : antennaGroup) {
            for (Coord2D a2 : antennaGroup) {
                if (!a1.equals(a2)) {
                    // Include the antennas themselves
                    antipodes.add(a1);
                    antipodes.add(a2);

                    Coord2D distanceRay1 = a2.minus(a1);
                    Coord2D nextAntipodeRay1 = a1.minus(distanceRay1);
                    while (this.AntennaMap.isWithinBounds(nextAntipodeRay1)) {
                        antipodes.add(nextAntipodeRay1);
                        nextAntipodeRay1 = nextAntipodeRay1.minus(distanceRay1);
                    }

                    Coord2D distanceRay2 = a1.minus(a2);
                    Coord2D nextAntipodeRay2 = a2.minus(distanceRay2);
                    while (this.AntennaMap.isWithinBounds(nextAntipodeRay2)) {
                        antipodes.add(nextAntipodeRay2);
                        nextAntipodeRay2 = nextAntipodeRay2.minus(distanceRay2);
                    }
                }
            }
        }
        return antipodes;
    }

    @Override
    public Long solvePart2() {
        Set<Coord2D> antipodes = new HashSet<>();
        for (Set<Coord2D> antennaGroup : Antennas.values()) {
            antipodes.addAll(findAllAntipodes(antennaGroup));
        }
        return (long) antipodes.size();
    }
}