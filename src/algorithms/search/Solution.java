package algorithms.search;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ArrayList;

public class Solution {
    // A list that stores the path from start to goal in order
    private ArrayList<AState> solutionPath;

    // Constructor that reconstructs the path starting from the goal state.
    public Solution(AState goalState) {
        solutionPath = new ArrayList<>();

        AState temp = goalState;
        // Loop until we reach the start state (where 'cameFrom' is null)
        while (temp != null) {
            solutionPath.add(temp);
            temp = temp.getCameFrom(); // הולכים לאבא
        }

        Collections.reverse(solutionPath);
    }

    //Returns the reconstructed path as an ArrayList.
    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }
}