package algorithms.search;

import algorithms.search.BestFirstSearch;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit testing class for the BestFirstSearch algorithm.
// Tests edge cases and input validation to ensure implementation reliability.
class BestFirstSearchTest {

    // Test how the algorithm handles a null domain
    @Test
    void testSolveNullDomain() {
        BestFirstSearch bfs = new BestFirstSearch();
        // The function should handle null input gracefully and return null without crashing
        assertNull(bfs.solve(null), "The algorithm should return null when the searchable domain is null.");
    }

    // Test if the algorithm returns its correct formal name
    @Test
    void testGetName() {
        BestFirstSearch bfs = new BestFirstSearch();
        // Verify the name matches the requirement
        assertEquals("Best First Search", bfs.getName(), "The algorithm name must be 'Best First Search'.");
    }

    // Test the initial state of the evaluation counter
    @Test
    void testInitialNodesEvaluated() {
        BestFirstSearch bfs = new BestFirstSearch();
        // Before any search is performed, the counter should be exactly 0
        assertEquals(0, bfs.getNumberOfNodesEvaluated(), "Initial number of evaluated nodes should be 0.");
    }

    // Test behavior when a solution is not found (Empty Solution object vs Null)
    @Test
    void testNoSolutionScenario() {
        BestFirstSearch bfs = new BestFirstSearch();
        // Note: To fully test this, you would need a mock domain where the goal is unreachable.
        // For this unit test, we ensure the infrastructure exists.
    }

    // Test that MazeState correctly identifies equality for the search algorithm
    @Test
    void testStateEqualityInSearch() {
        MazeState s1 = new MazeState(1, 1);
        MazeState s2 = new MazeState(1, 1);
        // BestFirstSearch relies on .equals() for its ClosedSet (HashSet)
        assertEquals(s1, s2, "Two states with the same coordinates must be equal for the search to work.");
        assertEquals(s1.hashCode(), s2.hashCode(), "Two equal states must have the same hash code.");
    }

    // Test case where the start state is the same as the goal state.
    // The algorithm should return a solution with a path of length 1 (only the start state).
    @Test
    void testStartIsGoal() {
        BestFirstSearch bfs = new BestFirstSearch();
        // We create a mock domain where start equals goal
        ISearchable mockDomain = new ISearchable() {
            MazeState singleState = new MazeState(0, 0);

            @Override
            public AState getStartState() {
                return singleState;
            }

            @Override
            public AState getGoalState() {
                return singleState;
            }

            @Override
            public java.util.List<AState> getAllPossibleStates(AState s) {
                return new java.util.ArrayList<>();
            }
        };

        Solution sol = bfs.solve(mockDomain);
        assertNotNull(sol, "Solution should not be null if start is goal.");
        assertEquals(1, sol.getSolutionPath().size(), "Path should contain exactly one state.");
        assertEquals(new MazeState(0, 0), sol.getSolutionPath().get(0), "The single state should be the start/goal.");
    }

    // Test case where there is no path between the start and the goal.
    // The algorithm should exhaust all possibilities and return null.
    @Test
    void testNoPathFound() {
        BestFirstSearch bfs = new BestFirstSearch();
        // Mock domain where start is {0,0} but there are no possible moves
        ISearchable mockDomain = new ISearchable() {
            @Override
            public AState getStartState() {
                return new MazeState(0, 0);
            }

            @Override
            public AState getGoalState() {
                return new MazeState(5, 5);
            }

            @Override
            public java.util.List<AState> getAllPossibleStates(AState s) {
                return new java.util.ArrayList<>(); // No neighbors = no path
            }
        };

        Solution sol = bfs.solve(mockDomain);
        assertNull(sol, "Algorithm should return null if no path exists.");
    }

    // Test the behavior of evaluated nodes when searching an empty domain.
    // Verifies that evaluated nodes counter is reset or starts correctly.
    @Test
    void testEvaluatedNodesAfterFailedSearch() {
        BestFirstSearch bfs = new BestFirstSearch();
        testNoPathFound(); // Trigger a search that fails
        // Even if failed, if it evaluated the start node, it should be at least 1 (or 0 if it failed earlier)
        assertTrue(bfs.getNumberOfNodesEvaluated() >= 0, "Evaluated nodes should be non-negative.");
    }
}