package algorithms.search;

import java.util.List;

public interface ISearchable {
    // Returns the starting state of the problem (in a maze: the StartPosition)
    AState getStartState();

    // Returns the goal state of the problem (in a maze: the GoalPosition)
    AState getGoalState();

    // Returns a list of all states reachable from the current state.
    // This is where movement rules, diagonals, and the 'L-shape' rule are implemented.
    List<AState> getAllPossibleStates(AState s);
}
