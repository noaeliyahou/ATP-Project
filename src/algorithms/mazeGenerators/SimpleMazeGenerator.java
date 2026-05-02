package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        // Initialize a new maze with the given dimensions
        Maze maze = new Maze(rows, columns);
        Random random = new Random();

        // Iterate through each cell in the maze grid
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

        maze.setCellValue(start.getRowIndex(), start.getColumnIndex(), 0);
        maze.setCellValue(end.getRowIndex(), end.getColumnIndex(), 0);

        return maze;
    }
}
