package algorithms.mazeGenerators;

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

    @Override
    public String toString() {
        return "{" + rowIndex + "," + columnIndex + "}";
    }
}