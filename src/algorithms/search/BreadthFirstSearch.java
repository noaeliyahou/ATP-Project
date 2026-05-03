package algorithms.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth First Search (BFS) algorithm.
 * This algorithm explores the neighbor nodes first before moving to the next level neighbors.
 * It is guaranteed to find the shortest path in an unweighted graph.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm{
    // The openList stores states that are discovered but not yet evaluated.
    // Protected so that BestFirstSearch can replace it with a PriorityQueue.
    protected Queue<AState> openList;

    // Constructor that initializes the openList as a standard FIFO Queue (LinkedList)
    public BreadthFirstSearch() {
        this.openList = new LinkedList<>();
    }

    // The main search logic that explores the searchable domain to find the goal state
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

    // closedSet keeps track of visited states to ensure O(1) lookup and prevent cycles
        Set<AState> closedSet = new HashSet<>();
        AState start = domain.getStartState();

        // Adding the initial state to start the search
        openList.add(start);
        closedSet.add(start);

        // Continue searching as long as there are states in the openList
        while (!openList.isEmpty()) {
            // Retrieve the next state in line (FIFO) and increment the evaluation counter
            AState currentState = openList.poll();
            this.evaluatedNodes++;

            // Check if the current state is the goal
            if (currentState.equals(domain.getGoalState())) {
                // Return the path reconstructed from the goal state
                return new Solution(currentState); //
            }

            // Get all valid neighbors from the searchable domain
            for (AState neighbor : domain.getAllPossibleStates(currentState)) {
                // Only process neighbors that haven't been visited yet
                if (!closedSet.contains(neighbor)) {
                    closedSet.add(neighbor);// Mark as visited
                    neighbor.setCameFrom(currentState);// Set parent for path reconstruction
                    openList.add(neighbor);// Add to queue for future evaluation
                }
            }
        }
        // Return null if no path to the goal is found
        return null;
    }

    // Returns the formal name of the algorithm
    @Override
    public String getName() { return "Breadth First Search"; }

}
