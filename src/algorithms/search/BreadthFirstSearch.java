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

    /**
     * Constructor that initializes the openList as a standard FIFO Queue (LinkedList).
     */
    public BreadthFirstSearch() {
        this.openList = new LinkedList<>();
    }

    protected void initOpenList() {
        this.openList = new LinkedList<>();
    }

    /**
     * The main search logic that explores the searchable domain to find the goal state.
     * @param domain the searchable problem (the maze).
     * @return a Solution object representing the path, or null if no path is found.
     */
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null) return null;

        this.evaluatedNodes = 0;
        initOpenList();
        // closedSet keeps track of visited states to ensure O(1) lookup and prevent cycles
        Set<AState> closedSet = new HashSet<>();
        AState start = domain.getStartState();
        AState goal = domain.getGoalState();

        // Adding the initial state to start the search
        start.setCost(0);
        openList.add(start);
        //closedSet.add(start);

        // Continue searching as long as there are states in the openList
        while (!openList.isEmpty()) {
            // Retrieve the next state in line (FIFO) and increment the evaluation counter
            AState currentState = openList.poll();

            if (closedSet.contains(currentState)) {
                continue;
            }
            closedSet.add(currentState);
            this.evaluatedNodes++;

            // Check if the current state is the goal
            if (currentState.equals(goal)) {
                // Return the path reconstructed from the goal state
                return new Solution(currentState); //
            }

            // Get all valid neighbors from the searchable domain
            for (AState neighbor : domain.getAllPossibleStates(currentState)) {
                if (!closedSet.contains(neighbor)) {
                    if (openList.contains(neighbor)) {
                        for (AState stateInList : openList) {
                            if (stateInList.equals(neighbor) && neighbor.getCost() < stateInList.getCost()) {
                                openList.remove(stateInList);
                                openList.add(neighbor);
                                break;
                            }
                        }
                    } else {
                        openList.add(neighbor);
                    }
                }
            }
        }
        // Return null if no path to the goal is found
        return null;
    }

    /**
     * Returns the formal name of the algorithm.
     * @return "Breadth First Search".
     */
    @Override
    public String getName() { return "Breadth First Search"; }

}
