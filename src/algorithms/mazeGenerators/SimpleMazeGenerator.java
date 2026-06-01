package algorithms.mazeGenerators;
import java.util.Random;


/**
 * This class generates a "simple" maze by randomly distributing walls throughout the grid.
 * While it doesn't guarantee a complex structure, it ensures the start and end points are accessible.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze by randomly assigning 0 (path) or 1 (wall) to each cell.
     * Ensures that start and goal positions are always set to 0.
     * @param rows the number of rows in the maze.
     * @param columns the number of columns in the maze.
     * @return a Maze object with randomly distributed walls.
     */
    @Override
    public Maze generate(int rows, int columns) {
        // Initialize a new maze with the given dimensions
        Maze maze = new Maze(rows, columns);
        Random random = new Random();

        // Initialize a new maze with the given dimensions
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Randomly assign a value: 0 (path) or 1 (wall)
                // Using random.nextInt(2) gives a 50/50 chance
                int val = random.nextInt(2);
                maze.setCellValue(i, j, val);
            }
        }
        int currRow = maze.getStartPosition().getRowIndex();
        int currCol = maze.getStartPosition().getColumnIndex();
        int goalRow = maze.getGoalPosition().getRowIndex();
        int goalCol = maze.getGoalPosition().getColumnIndex();

        while (currRow != goalRow) {
            maze.setCellValue(currRow, currCol, 0);
            if (currRow < goalRow) currRow++;
            else currRow--;
        }
        while (currCol != goalCol) {
            maze.setCellValue(currRow, currCol, 0);
            if (currCol < goalCol) currCol++;
            else currCol--;
        }

        maze.setCellValue(goalRow, goalCol, 0);
        // Ensure that the start and end positions are always paths (0)
        // This guarantees the player doesn't start or end inside a wall
        return maze;
    }
}
