package algorithms.mazeGenerators;


/**
 * This abstract class provides a base implementation for all maze generators.
 * It implements the IMazeGenerator interface and provides a common method to measure performance.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Abstract method to be implemented by specific maze generation algorithms.
     * @param rows the number of rows in the maze.
     * @param columns the number of columns in the maze.
     * @return a Maze object of the specified dimensions.
     */
    @Override
    public abstract Maze generate(int rows, int columns);

    /**
     * Measures the time it takes to generate a maze in milliseconds.
     * @param rows the number of rows in the maze.
     * @param columns the number of columns in the maze.
     * @return the time elapsed in milliseconds.
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        // Record the system time before starting the generation
        long startTime = System.currentTimeMillis();

        // Execute the specific generation algorithm
        generate(rows, columns);

        // Record the system time after the generation is complete
        long endTime = System.currentTimeMillis();

        // Return the elapsed time in milliseconds
        return endTime - startTime;
    }
}
