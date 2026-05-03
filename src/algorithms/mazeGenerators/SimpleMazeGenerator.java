package algorithms.mazeGenerators;
import java.util.Random;


// This class generates a "simple" maze by randomly distributing walls throughout the grid.
// While it doesn't guarantee a complex structure, it ensures the start and end points are accessible.
public class SimpleMazeGenerator extends AMazeGenerator {

    // Generates a maze by randomly assigning 0 (path) or 1 (wall) to each cell.
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

        // Ensure that the start and end positions are always paths (0)
        // This guarantees the player doesn't start or end inside a wall
        Position start = maze.getStartPosition();
        Position end = maze.getGoalPosition();

        // Manually clearing the entrance and exit points
        maze.setCellValue(start.getRowIndex(), start.getColumnIndex(), 0);
        maze.setCellValue(end.getRowIndex(), end.getColumnIndex(), 0);

        return maze;
    }
}
