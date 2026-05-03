package algorithms.search;

import java.util.PriorityQueue;

/**
* Best First Search algorithm class.
* This algorithm is a variation of BFS that prioritizes states based on their cost.
*/
public class BestFirstSearch extends BreadthFirstSearch{

    /**
     * Constructor for BestFirstSearch.
     * Initializes the 'openList' with a PriorityQueue using a cost-based comparator.
     */
    public BestFirstSearch() {
        // Instead of a simple FIFO queue, we use a PriorityQueue (Min-Heap).
        // This ensures that the state with the lowest cumulative cost is always polled first.
        this.openList = new PriorityQueue<>((s1, s2) -> Double.compare(s1.getCost(), s2.getCost()));
    }

    /**
     * Returns the formal name of the algorithm.
     * @return "Best First Search".
     */
    @Override
    public String getName() { return "Best First Search"; }
}
