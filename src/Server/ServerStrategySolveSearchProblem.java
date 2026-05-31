package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    // A directory to store solved maze solutions
    private String tempDirectoryPath = System.getProperty("java.io.tmpdir");

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outFromClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outFromClient);

            // 1. Get the maze from the client
            Maze maze = (Maze) fromClient.readObject();

            // 2. Generate a unique identifier for this maze to check in cache
            String mazeId = String.valueOf(Arrays.hashCode(maze.toByteArray()));
            String solutionFilePath = tempDirectoryPath + File.separator + mazeId;

            File solutionFile = new File(solutionFilePath);
            Solution solution;

            // 3. Check if we already solved this maze
            if (solutionFile.exists()) {
                // If yes, load the solution from the file
                ObjectInputStream fromFile = new ObjectInputStream(new FileInputStream(solutionFile));
                solution = (Solution) fromFile.readObject();
                fromFile.close();
            } else {
                // If no, solve it using our algorithms from Part A
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                // You can choose any searching algorithm (e.g., BestFirstSearch)
                ISearchingAlgorithm solver = new BestFirstSearch();
                solution = solver.solve(searchableMaze);

                // Save the solution to a file for future use
                ObjectOutputStream toFile = new ObjectOutputStream(new FileOutputStream(solutionFile));
                toFile.writeObject(solution);
                toFile.flush();
                toFile.close();
            }

            // 4. Send the solution back to the client
            toClient.writeObject(solution);
            toClient.flush();

            fromClient.close();
            toClient.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}