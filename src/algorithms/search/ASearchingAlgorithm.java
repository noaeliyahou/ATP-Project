package algorithms.search;


/**
 * An abstract base class for searching algorithms.
 * Provides basic functionality like counting the number of evaluated nodes.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    // Counter for the number of states developed.
    // Protected so that subclasses (BFS, DFS) can update it.
    protected int evaluatedNodes;

    /**
     * Default constructor for ASearchingAlgorithm.
     * Initializes the evaluated nodes counter to 0.
     */
    public ASearchingAlgorithm() {
        // Initialize the counter to 0 for every new algorithm instance
        this.evaluatedNodes = 0;
    }

    /**
     * Returns the total count of nodes that were dequeued and evaluated by the algorithm.
     * @return the number of evaluated nodes.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        // Return the counter that was updated during the solve process
        return evaluatedNodes;
    }

    /**
     * Abstract method to return the specific name of the searching algorithm.
     * @return the name of the algorithm.
     */
    @Override
    public abstract String getName();

    /**
     * Abstract method that defines the core logic for solving a searchable problem.
     * @param domain the problem/domain to be searched.
     * @return a Solution object representing the path from start to goal.
     */
    @Override
    public abstract Solution solve(ISearchable domain);
}
