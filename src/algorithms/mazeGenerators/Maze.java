package algorithms.mazeGenerators;

import java.io.Serializable;
/**
 * Represents a Maze structure including its grid, dimensions, and start/goal positions.
 */
public class Maze implements Serializable{
    private int[][] grid;
    private Position startPosition;
    private Position goalPosition;
    private int rows;
    private int cols;


    /**
     * Constructor to initialize a maze with the given dimensions and default positions.
     * @param rows number of rows in the maze.
     * @param columns number of columns in the maze.
     */
    public  Maze(int rows, int columns){
        this.rows = rows;
        this.cols = columns;
        // Initialize the grid with the given dimensions
        grid = new int[rows][columns];

        // Default start position is the top-left corner
        startPosition = new Position(0, 0);

        // Default goal position is the bottom-right corner
        goalPosition = new Position(rows - 1, columns - 1);
    }

    /**
     * Constructor that builds a maze from a byte array.
     */
    public Maze(byte[] b){
        // Extract metadata using the same order
        this.rows = extractIntFromByteArray(b, 0);
        this.cols = extractIntFromByteArray(b, 4);
        this.startPosition = new Position(extractIntFromByteArray(b, 8), extractIntFromByteArray(b, 12));
        this.goalPosition = new Position(extractIntFromByteArray(b, 16), extractIntFromByteArray(b, 20));

        // Initialize grid and fill with values
        this.grid = new int[rows][cols];
        int index = 24;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.grid[i][j] = b[index++];
            }
        }
    }

    /**
     * @return the number of rows in the maze.
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the number of columns in the maze.
     */
    public int getCols() {
        return cols;
    }

    /**
     * @return the starting Position of the maze.
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * @return the goal Position of the maze.
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Sets a specific value at the given coordinates within the maze grid.
     * @param row row index.
     * @param col column index.
     * @param value the value to set (typically 0 for path, 1 for wall).
     */
    public void setCellValue(int row, int col, int value) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            grid[row][col] = value;
        }
    }

    /**
     * Retrieves the value at the specified coordinates.
     * @param row row index.
     * @param col column index.
     * @return the value at the cell, or -1 if the coordinates are out of bounds.
     */
    public int getCellValue(int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            return grid[row][col];
        }
        return -1; // Indicates out of bounds
    }

    /**
     * Prints the maze structure to the console for debugging purposes.
     * Marks start position as 'S' and goal position as 'E'.
     */
    public void print() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()) {
                    System.out.print("S "); // Start
                } else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()) {
                    System.out.print("E "); // End
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Converts the maze into a byte array.
     * Structure:
     * [0-3] Rows, [4-7] Cols,
     * [8-11] StartRow, [12-15] StartCol,
     * [16-19] GoalRow, [20-23] GoalCol,
     * [24...] Grid values
     */
    public byte[] toByteArray(){
// 24 bytes for metadata (6 integers * 4 bytes each) + the grid itself
        int metadataSize = 24;
        byte[] result = new byte[metadataSize + (rows * cols)];

        // Helper to put integers into the byte array
        insertIntToByteArray(rows, result, 0);
        insertIntToByteArray(cols, result, 4);
        insertIntToByteArray(startPosition.getRowIndex(), result, 8);
        insertIntToByteArray(startPosition.getColumnIndex(), result, 12);
        insertIntToByteArray(goalPosition.getRowIndex(), result, 16);
        insertIntToByteArray(goalPosition.getColumnIndex(), result, 20);

        // Fill the grid values
        int index = metadataSize;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[index++] = (byte) grid[i][j];
            }
        }
        return result;
    }

    // --- Helper Methods for Integer/Byte conversion ---

    private void insertIntToByteArray(int val, byte[] b, int offset) {
        b[offset] = (byte) (val >> 24);
        b[offset + 1] = (byte) (val >> 16);
        b[offset + 2] = (byte) (val >> 8);
        b[offset + 3] = (byte) val;
    }

    private int extractIntFromByteArray(byte[] b, int offset) {
        return ((b[offset] & 0xFF) << 24) |
                ((b[offset + 1] & 0xFF) << 16) |
                ((b[offset + 2] & 0xFF) << 8) |
                (b[offset + 3] & 0xFF);
    }
}
