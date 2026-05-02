package algorithms.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    protected Queue<AState> openList;

    public BreadthFirstSearch() {
        this.openList = new LinkedList<>();
    }

    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        Set<AState> closedSet = new HashSet<>();
        AState start = domain.getStartState();

        openList.add(start);
        closedSet.add(start);

        while (!openList.isEmpty()) {
            AState currentState = openList.poll();
            this.evaluatedNodes++;

            if (currentState.equals(domain.getGoalState())) {
                return new Solution(currentState); //
            }

            for (AState neighbor : domain.getAllPossibleStates(currentState)) {
                if (!closedSet.contains(neighbor)) {
                    closedSet.add(neighbor);
                    neighbor.setCameFrom(currentState);
                    openList.add(neighbor);
                }
            }
        }
        return null;
    }

    @Override
    public String getName() { return "Breadth First Search"; }

}
