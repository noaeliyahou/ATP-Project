package algorithms.search;

public class MazeState extends AState {
    private int row;
    private int col;

    public MazeState(int row, int col, double cost, AState cameFrom) {
        super(row + "," + col); //using abstract constructor that defines how to print
        //initializing MazeState fields
        this.row = row;
        this.col = col;
        //set abstract fields
        this.setCost(cost);
        this.setCameFrom(cameFrom);
    }
    @Override
    public boolean equals(Object obj){
        if(obj==null||getClass()!=obj.getClass()) return false;
        MazeState m = (MazeState) obj;
        return row == m.row && col == m.col;
    }
}
