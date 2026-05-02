package algorithms.mazeGenerators;

public class Maze {
    private int[][] grid;
    private Position startPosition;
    private Position goalPosition;
    private int rows;
    private int cols;

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

    // Getter for rows
    public int getRows() {
        return rows;
    }

    // Getter for columns
    public int getCols() {
        return cols;
    }

    // Returns the start position of the maze
    public Position getStartPosition() {
        return startPosition;
    }

    // Returns the goal (end) position of the maze
    public Position getGoalPosition() {
        return goalPosition;
    }

    // Sets a value (0 or 1) at a specific cell
    public void setCellValue(int row, int col, int value) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            grid[row][col] = value;
        }
    }

    // Gets the value at a specific cell
    public int getCellValue(int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
            return grid[row][col];
        }
        return -1; // Indicates out of bounds
    }

    // Optional: Method to print the maze to the console for debugging
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
