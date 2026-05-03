package algorithms.search;

import java.util.Objects;

/**
 * Represents a specific state within a maze, defined by its row and column coordinates.
 */
public class MazeState extends AState {
    private int row;
    private int col;

    /**
     * Constructor to initialize a maze state with its coordinates.
     * @param row row index.
     * @param col column index.
     */
    public MazeState(int row, int col) {
        super(row + "," + col); //using abstract constructor that defines how to print
        //initializing MazeState fields
        this.row = row;
        this.col = col;
    }
    /**
     * @return the row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column index.
     */
    public int getCol() {
        return col;
    }

    /**
     * Provides a clean string representation of the state for printing.
     * @return formatted string as {row,col}.
     */
    @Override
    public String toString() {
        return String.format("{%d,%d}", row, col);
    }

    /**
     * Compares this MazeState to another object based on coordinates.
     * @param obj the object to compare.
     * @return true if row and column are the same.
     */
    @Override
    public boolean equals(Object obj){
        if(obj==null||getClass()!=obj.getClass()) return false;
        MazeState m = (MazeState) obj;
        return row == m.row && col == m.col;
    }

    /**
     * Generates a hash code for the state based on its row and column.
     * @return unique hash code for the coordinate.
     */    @Override
    public int hashCode() {
        // Uses the Objects.hash helper to generate a consistent hash value
        return Objects.hash(row, col);
    }
}
