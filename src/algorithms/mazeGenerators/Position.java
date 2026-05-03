package algorithms.mazeGenerators;

/**
 * This class represents a specific location (coordinates) within a 2D maze grid.
 * It holds the row and column indices and provides a string representation for debugging and display.
 */
public class Position {
    private int rowIndex;
    private int columnIndex;


    /**
     * Constructor to initialize a Position with specific row and column indices.
     * @param rowIndex the index of the row.
     * @param columnIndex the index of the column.
     */
    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;

    }

    /**
     * @return the row index of the position.
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * @return the column index of the position.
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Returns the position formatted as a string.
     * @return a string in the format "{row,column}".
     */
    @Override
    public String toString() {
        return "{" + rowIndex + "," + columnIndex + "}";
    }
}