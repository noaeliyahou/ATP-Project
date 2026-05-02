package algorithms.search;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private List<AState> solutionPath;

    public Solution(AState goalState) {
        solutionPath = new ArrayList<>();

        // שחזור המסלול מהסוף להתחלה
        AState temp = goalState;
        while (temp != null) {
            solutionPath.add(temp);
            temp = temp.getCameFrom(); // הולכים לאבא
        }

        // הופכים את הרשימה כדי שתהיה מההתחלה לסוף
        Collections.reverse(solutionPath);
    }

    public List<AState> getSolutionPath() {
        return solutionPath;
    }
}