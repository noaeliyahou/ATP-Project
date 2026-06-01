package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MyMazeGenerator implements a maze generation algorithm based on Randomized Prim's Algorithm.
 * This algorithm creates a "spanning tree" structure, ensuring there is a single path between any two points.
 */
public class MyMazeGenerator extends AMazeGenerator{

    /**
     * Generates a maze using a randomized version of Prim's algorithm.
     * @param rows the number of rows in the maze.
     * @param columns the number of columns in the maze.
     * @return a generated Maze object with a complex path structure.
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);

        // 1. Fill the maze with walls (1)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                maze.setCellValue(i, j, 1);
            }
        }

        Random random = new Random();

        // 2. Starting point: Pick a random cell to start the path (value 0)

        int startRow = 0;
        int startCol = 0;
        maze.setCellValue(startRow, startCol, 0);

        // 3. Create a list to store potential frontier cells
        // A frontier cell is a wall that is 2 steps away from a path
        List<int[]> frontiers = new ArrayList<>();
        addFrontiers(startRow, startCol, maze, frontiers);

        while (!frontiers.isEmpty()) {
            // 4. Random selection: Pick a frontier cell from the list at random to ensure non-linearity
            int index = random.nextInt(frontiers.size());
            int[] cell = frontiers.remove(index);
            int fRow = cell[0];
            int fCol = cell[1];

            // Double-check the cell is still a wall (might have been processed already)
            if (maze.getCellValue(fRow, fCol) == 1) {
                // 5. Find a neighbor path cell that is 2 steps away
                List<int[]> neighbors = getNeighbors(fRow, fCol, maze);
                if (!neighbors.isEmpty()) {
                    int[] neighbor = neighbors.get(random.nextInt(neighbors.size()));

                    // 6. Connect the frontier to the neighbor by removing the wall between them
                    maze.setCellValue(fRow, fCol, 0); // The frontier itself
                    maze.setCellValue((fRow + neighbor[0]) / 2, (fCol + neighbor[1]) / 2, 0); // The wall between

                    // 7. dd new potential frontier cells from the newly opened path cell
                    addFrontiers(fRow, fCol, maze, frontiers);
                }
            }
        }
        // Finalize: Explicitly ensure the defined Start and Goal positions are open paths
//        maze.setCellValue(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex(), 0);
//        maze.setCellValue(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex(), 0);
        int goalR = rows - 1;
        int goalC = columns - 1;
        maze.setCellValue(goalR, goalC, 0);

        if ((goalR - 1 < 0 || maze.getCellValue(goalR - 1, goalC) == 1) &&
                (goalC - 1 < 0 || maze.getCellValue(goalR, goalC - 1) == 1)) {

            if (goalR - 1 >= 0) {
                maze.setCellValue(goalR - 1, goalC, 0);
            } else if (goalC - 1 >= 0) {
                maze.setCellValue(goalR, goalC - 1, 0);
            }
        }
        return maze;
    }

    /**
     * Scans for neighboring walls at a distance of 2 and adds them to the frontier list.
     * @param row current row coordinate.
     * @param col current column coordinate.
     * @param maze the maze object being processed.
     * @param frontiers the list of potential cells to be added to the path.
     */
    private void addFrontiers(int row, int col, Maze maze, List<int[]> frontiers) {
        // Step size of 2 is crucial to maintain walls between parallel paths
        int[][] dirs = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
        for (int[] d : dirs) {
            int nr = row + d[0];
            int nc = col + d[1];

            // Check boundaries and ensure the candidate is a wall
            if (nr >= 0 && nr < maze.getRows() && nc >= 0 && nc < maze.getCols() && maze.getCellValue(nr, nc) == 1) {
                frontiers.add(new int[]{nr, nc});
            }
        }
    }

    /**
     * Finds existing path cells (0) at a distance of 2 from the given coordinate.
     * @param row current row coordinate.
     * @param col current column coordinate.
     * @param maze the maze object being processed.
     * @return a list of coordinates representing neighbor path cells.
     */
    private List<int[]> getNeighbors(int row, int col, Maze maze) {
        List<int[]> neighbors = new ArrayList<>();
        int[][] dirs = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
        for (int[] d : dirs) {
            int nr = row + d[0];
            int nc = col + d[1];
            // Check boundaries and identify neighbors that are already "path" (0)
            if (nr >= 0 && nr < maze.getRows() && nc >= 0 && nc < maze.getCols() && maze.getCellValue(nr, nc) == 0) {
                neighbors.add(new int[]{nr, nc});
            }
        }
        return neighbors;
    }
}

