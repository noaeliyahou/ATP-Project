package algorithms.search;

import java.util.Objects;

// Represents a specific state within a maze, defined by its row and column coordinates.
public class MazeState extends AState {
    private int row;
    private int col;

    // Constructor to initialize a maze state with its coordinates
    public MazeState(int row, int col) {
        super(row + "," + col); //using abstract constructor that defines how to print
        //initializing MazeState fields
        this.row = row;
        this.col = col;
    }
    //Getters
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    // Provides a clean string representation of the state for printing
    @Override
    public String toString() {
        return String.format("{%d,%d}", row, col);
    }

     //Compares this MazeState to another object. Two MazeStates are considered equal if they share the same row and column, regardless of their memory address.
    @Override
    public boolean equals(Object obj){
        if(obj==null||getClass()!=obj.getClass()) return false;
        MazeState m = (MazeState) obj;
        return row == m.row && col == m.col;
    }

    //Generates a hash code for the state based on its row and column.
    @Override
    public int hashCode() {
        // Uses the Objects.hash helper to generate a consistent hash value
        return Objects.hash(row, col);
    }
}
