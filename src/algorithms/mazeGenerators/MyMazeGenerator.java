package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator{

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
        // 2. Choose a random starting point and make it a path (0)
        int startRow = random.nextInt(rows);
        int startCol = random.nextInt(columns);
        maze.setCellValue(startRow, startCol, 0);

        // 3. Create a list to store potential frontier cells
        // A frontier cell is a wall that is 2 steps away from a path
        List<int[]> frontiers = new ArrayList<>();
        addFrontiers(startRow, startCol, maze, frontiers);

        while (!frontiers.isEmpty()) {
            // 4. Pick a random frontier cell
            int index = random.nextInt(frontiers.size());
            int[] cell = frontiers.remove(index);
            int fRow = cell[0];
            int fCol = cell[1];

            if (maze.getCellValue(fRow, fCol) == 1) {
                // 5. Find a neighbor path cell that is 2 steps away
                List<int[]> neighbors = getNeighbors(fRow, fCol, maze);
                if (!neighbors.isEmpty()) {
                    int[] neighbor = neighbors.get(random.nextInt(neighbors.size()));

                    // 6. Connect the frontier to the neighbor by removing the wall between them
                    maze.setCellValue(fRow, fCol, 0); // The frontier itself
                    maze.setCellValue((fRow + neighbor[0]) / 2, (fCol + neighbor[1]) / 2, 0); // The wall between

                    // 7. Add new frontiers from the current cell
                    addFrontiers(fRow, fCol, maze, frontiers);
                }
            }
        }

        // Ensure start and goal are 0 (path)
        maze.setCellValue(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex(), 0);
        maze.setCellValue(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex(), 0);

        return maze;
    }

    private void addFrontiers(int row, int col, Maze maze, List<int[]> frontiers) {
        // Neighbors at distance 2
        int[][] dirs = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
        for (int[] d : dirs) {
            int nr = row + d[0];
            int nc = col + d[1];
            if (nr >= 0 && nr < maze.getRows() && nc >= 0 && nc < maze.getCols() && maze.getCellValue(nr, nc) == 1) {
                frontiers.add(new int[]{nr, nc});
            }
        }
    }

    private List<int[]> getNeighbors(int row, int col, Maze maze) {
        List<int[]> neighbors = new ArrayList<>();
        int[][] dirs = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
        for (int[] d : dirs) {
            int nr = row + d[0];
            int nc = col + d[1];
            if (nr >= 0 && nr < maze.getRows() && nc >= 0 && nc < maze.getCols() && maze.getCellValue(nr, nc) == 0) {
                neighbors.add(new int[]{nr, nc});
            }
        }
        return neighbors;
    }
}

