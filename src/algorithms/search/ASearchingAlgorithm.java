package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    // Counter for the number of states developed.
    // Protected so that subclasses (BFS, DFS) can update it.
    protected int evaluatedNodes;

    public ASearchingAlgorithm() {
        // Initialize the counter to 0 for every new algorithm instance
        this.evaluatedNodes = 0;
    }

    // Returns the total count of nodes that were dequeued and evaluated by the algorithm
    @Override
    public int getNumberOfNodesEvaluated() {
        // Return the counter that was updated during the solve process
        return evaluatedNodes;
    }

    // Abstract method to return the specific name of the searching algorithm
    @Override
    public abstract String getName();

    // Abstract method that defines the core logic for solving a searchable problem
    @Override
    public abstract Solution solve(ISearchable domain);
}
