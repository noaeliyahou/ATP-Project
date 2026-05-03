package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
/**
 * Depth First Search (DFS) algorithm.
 * This algorithm explores as far as possible along each branch before backtracking.
 * It uses a Stack (LIFO) to manage the exploration order.
 */
public class DepthFirstSearch extends ASearchingAlgorithm{

    // The main search logic that explores the searchable domain using a Stack
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        Stack<AState> stack = new Stack<>();
        // visited set ensures we don't process the same state multiple times
        Set<AState> visited = new HashSet<>();

        // Start the search by pushing the initial state onto the stack
        stack.push(domain.getStartState());

        while (!stack.isEmpty()) {
            // Pop the most recent state added to the stack
            AState currentState = stack.pop();

            if (visited.contains(currentState)) continue;

            // Mark the current state as visited and increment the evaluation counter
            visited.add(currentState);
            this.evaluatedNodes++;

            // If the goal state is reached, reconstruct and return the solution
            if (currentState.equals(domain.getGoalState())) {
                return new Solution(currentState);
            }

            // Retrieve all possible neighbors for the current state
            List<AState> neighbors = domain.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                // If the neighbor hasn't been visited, set its parent and push to stack
                if (!visited.contains(neighbor)) {
                    neighbor.setCameFrom(currentState);
                    stack.push(neighbor);
                }
            }
        }
        // Return null if no path to the goal is found
        return null;
    }
    // Returns the formal name of the algorithm
    @Override
    public String getName() { return "Depth First Search"; }
}
