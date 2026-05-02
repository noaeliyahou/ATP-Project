package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm{
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        Stack<AState> stack = new Stack<>();
        Set<AState> visited = new HashSet<>();

        stack.push(domain.getStartState());

        while (!stack.isEmpty()) {
            AState currentState = stack.pop();

            if (visited.contains(currentState)) continue;

            visited.add(currentState);
            this.evaluatedNodes++;

            if (currentState.equals(domain.getGoalState())) {
                return new Solution(currentState);
            }

            List<AState> neighbors = domain.getAllPossibleStates(currentState);
            // כדי לשמור על סדר פיתוח מסוים, נהוג להפוך את השכנים כשמכניסים למחסנית
            for (AState neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    neighbor.setCameFrom(currentState);
                    stack.push(neighbor);
                }
            }
        }
        return null;
    }

    @Override
    public String getName() { return "Depth First Search"; }
}
