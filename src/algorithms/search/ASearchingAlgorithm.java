package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected int evaluatedNodes; // The amount of nodes we searched
    public ASearchingAlgorithm() {
        this.evaluatedNodes = 0;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return evaluatedNodes;
    }

    @Override
    public abstract String getName();

    @Override
    public abstract Solution solve(ISearchable domain);
}
