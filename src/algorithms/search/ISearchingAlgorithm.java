package algorithms.search;
import java.util.List;

public interface ISearchingAlgorithm {
    Solution solve(ISearchable searchable);
    int getNumberOfNodesEvaluated();
    String getName();
}
