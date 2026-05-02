package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        // no walls maze
        return new Maze(rows, columns);
    }
}
