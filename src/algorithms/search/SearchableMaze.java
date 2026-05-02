package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable {
    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return new MazeState(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
    }

    @Override
    public List<AState> getAllPossibleStates(AState s) {
        if (s == null) return null;

        List<AState> neighbors = new ArrayList<>();
        MazeState mState = (MazeState) s;
        int row = mState.getRow();
        int col = mState.getCol();

        // 1. קודם כל בודקים את 4 הכיוונים הישרים ושומרים את התוצאה (בלי להוסיף לרשימה עדיין)
        boolean up = isValid(row - 1, col);
        boolean down = isValid(row + 1, col);
        boolean right = isValid(row, col + 1);
        boolean left = isValid(row, col - 1);

        // 1: Up
        if (up) addState(neighbors, row - 1, col, s, 10);

        // 2: Up-Right (Diagonal)
        if ((up || right) && isValid(row - 1, col + 1))
            addState(neighbors, row - 1, col + 1, s, 15);

        // 3: Right
        if (right) addState(neighbors, row, col + 1, s, 10);

        // 4: Down-Right (Diagonal)
        if ((down || right) && isValid(row + 1, col + 1))
            addState(neighbors, row + 1, col + 1, s, 15);

        // 5: Down
        if (down) addState(neighbors, row + 1, col, s, 10);

        // 6: Down-Left (Diagonal)
        if ((down || left) && isValid(row + 1, col - 1))
            addState(neighbors, row + 1, col - 1, s, 15);

        // 7: Left
        if (left) addState(neighbors, row, col - 1, s, 10);

        // 8: Up-Left (Diagonal)
        if ((up || left) && isValid(row - 1, col - 1))
            addState(neighbors, row - 1, col - 1, s, 15);

        return neighbors;
    }

    /**
     * פונקציית עזר שבודקת אם תא חוקי ואם כן מוסיפה אותו לרשימת השכנים
     * @return true אם התא פנוי (0), כדי שנוכל להשתמש בזה לבדיקת אלכסונים
     */
    private boolean isValid(int r, int c) {
        return (r >= 0 && r < maze.getRows() &&
                c >= 0 && c < maze.getCols() &&
                maze.getCellValue(r, c) == 0);
    }

    // פונקציית עזר להוספה לרשימה
    private void addState(List<AState> neighbors, int r, int c, AState parent, double weight) {
        MazeState newState = new MazeState(r, c);
        newState.setCost(parent.getCost() + weight);
        newState.setCameFrom(parent);
        neighbors.add(newState);
    }
}