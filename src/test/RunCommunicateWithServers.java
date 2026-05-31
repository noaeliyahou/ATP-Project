package test;

import IO.MyDecompressorInputStream;
import Server.*;
import Client.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.Solution;
import algorithms.search.AState;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RunCommunicateWithServers {
    public static void main(String[] args) {
        // Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());

        // Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();

        // Communicating with servers
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();

        //Test_LargeMaze();
        //Test_CachingEfficiency();
        //Test_ParallelClients();

        //try {
            //Thread.sleep(2000); //ניתן ללקוחות המקבילים זמן לסיים
        //} catch (InterruptedException e) { e.printStackTrace(); }

        // Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();


    }

    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        int[] mazeDimensions = new int[]{50, 50};
                        toServer.writeObject(mazeDimensions); // send maze dimensions to server
                        toServer.flush();

                        byte[] compressedMaze = (byte[]) fromServer.readObject(); // read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));

                        // Allocating byte[] for the decompressed maze - 10000 is safe for 50x50
                        byte[] decompressedMaze = new byte[10000];
                        is.read(decompressedMaze); // Fill decompressedMaze with bytes

                        Maze maze = new Maze(decompressedMaze);
                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(50, 50);
                        maze.print();

                        toServer.writeObject(maze); // send maze to server
                        toServer.flush();

                        Solution mazeSolution = (Solution) fromServer.readObject(); // read solution from server

                        // Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void Test_LargeMaze() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        int[] mazeDimensions = new int[]{250, 250}; // מבוך גדול מאוד
                        System.out.println("Requesting 250x250 maze...");
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();

                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        System.out.println("Large maze received! Size: " + compressedMaze.length + " bytes.");
                    } catch (Exception e) { e.printStackTrace(); }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) { e.printStackTrace(); }
    }

    private static void Test_ParallelClients() {
        for (int i = 0; i < 5; i++) {
            final int clientID = i;
            new Thread(() -> {
                try {
                    Client client = new Client(InetAddress.getLocalHost(), 5400, (in, out) -> {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(out);
                            ObjectInputStream fromServer = new ObjectInputStream(in);
                            toServer.flush();

                            // שליחת בקשה למבוך קטן כדי לבדוק שהשרת מגיב
                            toServer.writeObject(new int[]{10, 10});
                            toServer.flush();

                            fromServer.readObject(); // קריאת המבוך מהשרת
                            System.out.println("Client " + clientID + " finished successfully!");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    client.communicateWithServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void Test_CachingEfficiency() {
        try {
            MyMazeGenerator mg = new MyMazeGenerator();
            Maze maze = mg.generate(80, 80);

            IClientStrategy solveStrategy = (in, out) -> {
                try {
                    ObjectOutputStream toServer = new ObjectOutputStream(out);
                    ObjectInputStream fromServer = new ObjectInputStream(in);
                    toServer.flush();
                    toServer.writeObject(maze);
                    toServer.flush();
                    fromServer.readObject(); // קריאת הפתרון
                } catch (Exception e) { e.printStackTrace(); }
            };

            // פעם ראשונה - פתרון מלא
            long startTime = System.currentTimeMillis();
            new Client(InetAddress.getLocalHost(), 5401, solveStrategy).communicateWithServer();
            long firstTime = System.currentTimeMillis() - startTime;

            // פעם שנייה - אמור להישלף מהקובץ
            startTime = System.currentTimeMillis();
            new Client(InetAddress.getLocalHost(), 5401, solveStrategy).communicateWithServer();
            long secondTime = System.currentTimeMillis() - startTime;

            System.out.println("First solve time: " + firstTime + "ms");
            System.out.println("Second solve time (should be faster): " + secondTime + "ms");
        } catch (Exception e) { e.printStackTrace(); }
    }

}