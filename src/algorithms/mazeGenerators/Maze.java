package algorithms.mazeGenerators;


/**
 * Represents a Maze structure including its grid, dimensions, and start/goal positions.
 */
public class Maze {
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
}
