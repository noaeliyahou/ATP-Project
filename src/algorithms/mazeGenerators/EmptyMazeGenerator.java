package algorithms.mazeGenerators;

/**
 * This class generates an "empty" maze, meaning a maze with no walls where all cells are paths.
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze with the given dimensions that contains only paths (0).
     * @param rows the number of rows in the maze.
     * @param columns the number of columns in the maze.
     * @return a new Maze object initialized with zeros.
     */
    @Override
    public Maze generate(int rows, int columns) {
        // Return a new Maze object; by default, the grid is initialized with zeros.
        return new Maze(rows, columns);
    }
}
