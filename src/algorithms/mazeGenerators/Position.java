package algorithms.mazeGenerators;


// This class represents a specific location (coordinates) within a 2D maze grid.
// It holds the row and column indices and provides a string representation for debugging and display.
public class Position {
    private int rowIndex;
    private int columnIndex;



public Position(int rowIndex, int columnIndex) {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;

}

    // Getter for the row index
    public int getRowIndex() {
        return rowIndex;
    }

    // Getter for the column index
    public int getColumnIndex() {
        return columnIndex;
    }

    // Returns the position in the format {row,column}
    @Override
    public String toString() {
        return "{" + rowIndex + "," + columnIndex + "}";
    }
}