package algorithms.search;

import java.util.Objects;

public class MazeState extends AState {
    private int row;
    private int col;

    public MazeState(int row, int col) {
        super(row + "," + col); //using abstract constructor that defines how to print
        //initializing MazeState fields
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    @Override
    public boolean equals(Object obj){
        if(obj==null||getClass()!=obj.getClass()) return false;
        MazeState m = (MazeState) obj;
        return row == m.row && col == m.col;
    }

    @Override
    public int hashCode() {
        // הפונקציה הזו לוקחת את השורה והעמודה ומערבבת אותן למספר int אחד.
        // זה מה שמאפשר ל-HashSet למצוא את המשבצת ב-O(1).
        return Objects.hash(row, col); //using a hash function from Objects
    }
}
