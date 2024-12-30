package aoc;

public class Day04 {
    private static final String TARGET = "MAS";
    private char[][] grid;
    private int rows;
    private int cols;
    
    public Day04(String input) {
        String[] lines = input.trim().split("\n");
        rows = lines.length;
        cols = lines[0].length();
        grid = new char[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].trim().toCharArray();
        }
    }

    public int solvePart1() {
        int count = 0;
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                count += countXMASPatterns(row, col);
            }
        }
        return count;
    }

    public int solvePart2() {
        return solvePart1();
    }

    private int countXMASPatterns(int centerRow, int centerCol) {
        int count = 0;
        for (boolean firstLegForward : new boolean[]{true, false}) {
            for (boolean secondLegForward : new boolean[]{true, false}) {
                if (checkXPattern(centerRow, centerCol, firstLegForward, secondLegForward)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private boolean checkXPattern(int centerRow, int centerCol, 
                                boolean firstLegForward, boolean secondLegForward) {
        char[] targetFirst = firstLegForward ? TARGET.toCharArray() : new StringBuilder(TARGET).reverse().toString().toCharArray();
        char[] targetSecond = secondLegForward ? TARGET.toCharArray() : new StringBuilder(TARGET).reverse().toString().toCharArray();
        
        // Check first diagonal (top-left to bottom-right)
        if (!checkDiagonal(centerRow, centerCol, -1, -1, 1, 1, targetFirst)) {
            return false;
        }
        
        // Check second diagonal (top-right to bottom-left)
        if (!checkDiagonal(centerRow, centerCol, -1, 1, 1, -1, targetSecond)) {
            return false;
        }
        
        return true;
    }
    
    private boolean checkDiagonal(int centerRow, int centerCol, 
                                int topDirRow, int topDirCol,
                                int bottomDirRow, int bottomDirCol,
                                char[] target) {
        // Check top character
        if (grid[centerRow + topDirRow][centerCol + topDirCol] != target[0]) {
            return false;
        }
        
        // Check middle character (center position)
        if (grid[centerRow][centerCol] != target[1]) {
            return false;
        }
        
        // Check bottom character
        if (grid[centerRow + bottomDirRow][centerCol + bottomDirCol] != target[2]) {
            return false;
        }
        
        return true;
    }
}