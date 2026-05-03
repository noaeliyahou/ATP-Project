package algorithms.search;

/**
 * Interface for searching algorithms.
 * Any class implementing this interface must provide a way to solve
 * a searchable problem and track the performance (nodes evaluated).
 */
public interface ISearchingAlgorithm {
    // The main function that receives a searchable problem and returns a solution path
    Solution solve(ISearchable searchable);

    // Returns the number of nodes developed by the algorithm - a metric for efficiency
    int getNumberOfNodesEvaluated();

    // Returns the name of the algorithm
    String getName();
}
