/*
 * ============================================================
 * Part A -- Upgraded Sanity Check & Stress Test
 * ============================================================
 */

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PartA_SanityCheck {

    // ---- counters ----
    private static int totalTests  = 0;
    private static int passedTests = 0;

    // ---- output collection ----
    private static final StringBuilder output = new StringBuilder();

    // ================================================================
    //  Main
    // ================================================================
    public static void main(String[] args) {
        log("========================================");
        log("  Part A  --  Upgraded Sanity Check");
        log("========================================");
        log("");

        // --- 1. Maze generator instantiation ---
        testGeneratorInstantiation();

        // --- 2. Maze generation ---
        testMazeGeneration();

        // --- 3. Maze properties (positions & bounds) ---
        testMazeProperties();

        // --- 4. Time measurement ---
        testTimeMeasurement();

        // --- 5. SearchableMaze implements ISearchable ---
        testSearchableMaze();

        // --- 6. Search algorithm instantiation ---
        testSearchAlgorithmInstantiation();

        // --- 7. Solve maze with each algorithm ---
        testSolveMaze();

        // --- 8. Search algorithm API (getName, getNumberOfNodesEvaluated) ---
        testSearchAlgorithmAPI();

        // --- 9. מבחן עומס ומבוכים גדולים (Stress Test) ---
        testBigMazesAndStressTest();

        // ---- summary ----
        log("");
        log("========================================");
        log(String.format("  Results: %d/%d passed", passedTests, totalTests));
        log("========================================");

        // ---- write to file ----
        if (args.length > 0) {
            writeToFile(args[0]);
        } else {
            System.out.println("\nTip: pass an output file path as argument to save results:");
            System.out.println("  java PartA_SanityCheck ./my_results.txt");
        }
    }

    // ================================================================
    //  1. Generator instantiation
    // ================================================================
    private static void testGeneratorInstantiation() {
        try {
            IMazeGenerator gen = new EmptyMazeGenerator();
            check(gen instanceof IMazeGenerator, "EmptyMazeGenerator is IMazeGenerator");
        } catch (Exception e) {
            fail("EmptyMazeGenerator is IMazeGenerator", e.getMessage());
        }

        try {
            IMazeGenerator gen = new SimpleMazeGenerator();
            check(gen instanceof IMazeGenerator, "SimpleMazeGenerator is IMazeGenerator");
        } catch (Exception e) {
            fail("SimpleMazeGenerator is IMazeGenerator", e.getMessage());
        }

        try {
            IMazeGenerator gen = new MyMazeGenerator();
            check(gen instanceof IMazeGenerator, "MyMazeGenerator is IMazeGenerator");
        } catch (Exception e) {
            fail("MyMazeGenerator is IMazeGenerator", e.getMessage());
        }
    }

    // ================================================================
    //  2. Maze generation (non-null return)
    // ================================================================
    private static void testMazeGeneration() {
        String[] names = {"EmptyMazeGenerator", "SimpleMazeGenerator", "MyMazeGenerator"};
        IMazeGenerator[] generators = null;
        try {
            generators = new IMazeGenerator[]{
                    new EmptyMazeGenerator(),
                    new SimpleMazeGenerator(),
                    new MyMazeGenerator()
            };
        } catch (Exception e) {
            fail("Instantiate generators for generation test", e.getMessage());
            return;
        }

        for (int i = 0; i < generators.length; i++) {
            try {
                Maze maze = generators[i].generate(10, 10);
                check(maze != null, names[i] + ".generate(10,10) returns non-null Maze");
            } catch (Exception e) {
                fail(names[i] + ".generate(10,10) returns non-null Maze", e.getMessage());
            }
        }
    }

    // ================================================================
    //  3. Maze properties -- positions within bounds
    // ================================================================
    private static void testMazeProperties() {
        try {
            IMazeGenerator gen = new SimpleMazeGenerator();
            int rows = 10, cols = 10;
            Maze maze = gen.generate(rows, cols);

            Position start = maze.getStartPosition();
            Position goal  = maze.getGoalPosition();

            check(start != null, "getStartPosition() is non-null");
            check(goal  != null, "getGoalPosition() is non-null");

            boolean startInBounds = start.getRowIndex() >= 0 && start.getRowIndex() < rows
                    && start.getColumnIndex() >= 0 && start.getColumnIndex() < cols;
            check(startInBounds, "Start position within maze bounds");

            boolean goalInBounds = goal.getRowIndex() >= 0 && goal.getRowIndex() < rows
                    && goal.getColumnIndex() >= 0 && goal.getColumnIndex() < cols;
            check(goalInBounds, "Goal position within maze bounds");
        } catch (Exception e) {
            fail("Maze properties (positions & bounds)", e.getMessage());
        }
    }

    // ================================================================
    //  4. Time measurement
    // ================================================================
    private static void testTimeMeasurement() {
        try {
            IMazeGenerator gen = new SimpleMazeGenerator();
            long time = gen.measureAlgorithmTimeMillis(10, 10);
            check(time >= 0, "measureAlgorithmTimeMillis returns non-negative value (" + time + " ms)");
        } catch (Exception e) {
            fail("measureAlgorithmTimeMillis returns non-negative value", e.getMessage());
        }
    }

    // ================================================================
    //  5. SearchableMaze implements ISearchable
    // ================================================================
    private static void testSearchableMaze() {
        try {
            IMazeGenerator gen = new SimpleMazeGenerator();
            Maze maze = gen.generate(10, 10);
            SearchableMaze searchable = new SearchableMaze(maze);
            check(searchable instanceof ISearchable, "SearchableMaze is ISearchable");
        } catch (Exception e) {
            fail("SearchableMaze is ISearchable", e.getMessage());
        }
    }

    // ================================================================
    //  6. Search algorithm instantiation
    // ================================================================
    private static void testSearchAlgorithmInstantiation() {
        try {
            ISearchingAlgorithm alg = new BreadthFirstSearch();
            check(alg instanceof ISearchingAlgorithm, "BreadthFirstSearch is ISearchingAlgorithm");
        } catch (Exception e) {
            fail("BreadthFirstSearch is ISearchingAlgorithm", e.getMessage());
        }

        try {
            ISearchingAlgorithm alg = new DepthFirstSearch();
            check(alg instanceof ISearchingAlgorithm, "DepthFirstSearch is ISearchingAlgorithm");
        } catch (Exception e) {
            fail("DepthFirstSearch is ISearchingAlgorithm", e.getMessage());
        }

        try {
            ISearchingAlgorithm alg = new BestFirstSearch();
            check(alg instanceof ISearchingAlgorithm, "BestFirstSearch is ISearchingAlgorithm");
        } catch (Exception e) {
            fail("BestFirstSearch is ISearchingAlgorithm", e.getMessage());
        }
    }

    // ================================================================
    //  7. Solve maze with each algorithm
    // ================================================================
    private static void testSolveMaze() {
        IMazeGenerator gen;
        Maze maze;
        SearchableMaze searchable;
        try {
            gen = new MyMazeGenerator();
            maze = gen.generate(30, 30);
            searchable = new SearchableMaze(maze);
        } catch (Exception e) {
            fail("Prepare 30x30 maze for solving", e.getMessage());
            return;
        }

        String[] names = {"BreadthFirstSearch", "DepthFirstSearch", "BestFirstSearch"};
        ISearchingAlgorithm[] algorithms;
        try {
            algorithms = new ISearchingAlgorithm[]{
                    new BreadthFirstSearch(),
                    new DepthFirstSearch(),
                    new BestFirstSearch()
            };
        } catch (Exception e) {
            fail("Instantiate search algorithms for solving", e.getMessage());
            return;
        }

        for (int i = 0; i < algorithms.length; i++) {
            try {
                SearchableMaze fresh = new SearchableMaze(maze);
                Solution solution = algorithms[i].solve(fresh);
                check(solution != null, names[i] + " returns non-null Solution");

                if (solution != null) {
                    ArrayList<AState> path = solution.getSolutionPath();
                    check(path != null && !path.isEmpty(),
                            names[i] + " solution path is non-empty");
                }
            } catch (Exception e) {
                fail(names[i] + " solve", e.getMessage());
            }
        }
    }

    // ================================================================
    //  8. Search algorithm API -- getName, getNumberOfNodesEvaluated
    // ================================================================
    private static void testSearchAlgorithmAPI() {
        IMazeGenerator gen;
        Maze maze;
        try {
            gen = new MyMazeGenerator();
            maze = gen.generate(30, 30);
        } catch (Exception e) {
            fail("Prepare maze for algorithm API test", e.getMessage());
            return;
        }

        String[] names = {"BreadthFirstSearch", "DepthFirstSearch", "BestFirstSearch"};
        ISearchingAlgorithm[] algorithms;
        try {
            algorithms = new ISearchingAlgorithm[]{
                    new BreadthFirstSearch(),
                    new DepthFirstSearch(),
                    new BestFirstSearch()
            };
        } catch (Exception e) {
            fail("Instantiate search algorithms for API test", e.getMessage());
            return;
        }

        for (int i = 0; i < algorithms.length; i++) {
            try {
                String name = algorithms[i].getName();
                check(name != null, names[i] + ".getName() is non-null");

                SearchableMaze searchable = new SearchableMaze(maze);
                algorithms[i].solve(searchable);

                int nodesEvaluated = algorithms[i].getNumberOfNodesEvaluated();
                check(nodesEvaluated > 0,
                        names[i] + ".getNumberOfNodesEvaluated() > 0 (got " + nodesEvaluated + ")");
            } catch (Exception e) {
                fail(names[i] + " API check", e.getMessage());
            }
        }
    }

    // ================================================================
    //  9. מבחן עומס ומבוכים גדולים (Stress Test)
    // ================================================================
    private static void testBigMazesAndStressTest() {
        log("");
        log("----------------------------------------");
        log(" Running Stress Test on Big Mazes (50x20 and 1000x1000)...");
        log("----------------------------------------");

        IMazeGenerator gen = new MyMazeGenerator();
        ISearchingAlgorithm bfs = new BreadthFirstSearch();
        ISearchingAlgorithm best = new BestFirstSearch();

        // א. בדיקה על מבוך 50x20
        try {
            Maze maze50x20 = gen.generate(50, 20);

            Solution bfsSol = bfs.solve(new SearchableMaze(maze50x20));
            check(bfsSol != null, "BFS solve 50x20: returns non-null Solution");
            if(bfsSol != null) log("    -> BFS 50x20 Evaluated Nodes: " + bfs.getNumberOfNodesEvaluated());

            Solution bestSol = best.solve(new SearchableMaze(maze50x20));
            check(bestSol != null, "BestFirstSearch solve 50x20: returns non-null Solution");
            if(bestSol != null) log("    -> Best 50x20 Evaluated Nodes: " + best.getNumberOfNodesEvaluated());

        } catch (Exception e) {
            fail("Stress Test 50x20 failed with exception", e.getMessage());
        }

        // ב. מבחן עומס ענק על מבוך 1000x1000
        try {
            log(" Generating 1000x1000 Maze (this might take a second)...");
            long startGen = System.currentTimeMillis();
            Maze maze1000 = gen.generate(1000, 1000);
            log(String.format("   Maze generated in %d ms.", (System.currentTimeMillis() - startGen)));

            log(" Testing BFS on 1000x1000...");
            long startBfs = System.currentTimeMillis();
            Solution bfsSol1000 = bfs.solve(new SearchableMaze(maze1000));
            long endBfs = System.currentTimeMillis();

            check(bfsSol1000 != null, "BreadthFirstSearch solve 1000x1000: returns non-null Solution");
            if(bfsSol1000 != null) {
                log(String.format("    -> BFS 1000x1000 succeeded in %d ms. Evaluated nodes: %d",
                        (endBfs - startBfs), bfs.getNumberOfNodesEvaluated()));
            }

            log(" Testing BestFirstSearch on 1000x1000...");
            long startBest = System.currentTimeMillis();
            Solution bestSol1000 = best.solve(new SearchableMaze(maze1000));
            long endBest = System.currentTimeMillis();

            check(bestSol1000 != null, "BestFirstSearch solve 1000x1000: returns non-null Solution");
            if(bestSol1000 != null) {
                log(String.format("    -> Best 1000x1000 succeeded in %d ms. Evaluated nodes: %d",
                        (endBest - startBest), best.getNumberOfNodesEvaluated()));
            }

        } catch (OutOfMemoryError oom) {
            fail("Best/BFS 1000x1000", "OutOfMemoryError! Too many redundant objects generated.");
        } catch (Exception e) {
            fail("Best/BFS 1000x1000 failed with exception", e.getMessage());
        }
    }

    // ================================================================
    //  Helpers
    // ================================================================

    private static void log(String message) {
        System.out.println(message);
        output.append(message).append(System.lineSeparator());
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            pass(testName);
        } else {
            fail(testName, "condition was false (Returned null or path is empty)");
        }
    }

    private static void pass(String testName) {
        totalTests++;
        passedTests++;
        log("  [PASS] " + testName);
    }

    private static void fail(String testName, String reason) {
        totalTests++;
        log("  [FAIL] " + testName + "  --  " + reason);
    }

    private static void writeToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(output.toString());
            System.out.println("\nResults saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("\nError writing results to file: " + e.getMessage());
        }
    }
}