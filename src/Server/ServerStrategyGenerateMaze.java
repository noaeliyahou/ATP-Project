package Server;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;


public class
ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outFromClient) {
        try {
            // 1. Setup Input and Output streams to talk with the client
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outFromClient);

            // 2. Read the maze dimensions from the client
            // The client is expected to send an int array: [rows, columns]
            int[] mazeDimensions = (int[]) fromClient.readObject();
            if (mazeDimensions != null && mazeDimensions.length == 2) {
                int rows = mazeDimensions[0];
                int cols = mazeDimensions[1];

                // 3. Generate the maze using MyMazeGenerator
                // Get the algorithm name from the configurations file
                String genAlgorithm = Configurations.getInstance().getMazeGeneratingAlgorithm();
                IMazeGenerator generator;

                // Dynamically choose which generator to instantiate based on the config file
                if ("SimpleMazeGenerator".equals(genAlgorithm)) {
                    generator = new algorithms.mazeGenerators.SimpleMazeGenerator();
                } else if ("EmptyMazeGenerator".equals(genAlgorithm)) {
                    generator = new algorithms.mazeGenerators.EmptyMazeGenerator();
                } else {
                    generator = new MyMazeGenerator(); // Default fallback
                }                Maze maze = generator.generate(rows, cols);

                // 4. Compress the maze and send it back to the client
                // We use our MyCompressorOutputStream (decorator) over a temporary byte stream
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteOut);

                // Convert maze to byte array and write to the compressor
                compressor.write(maze.toByteArray());
                compressor.flush();
                compressor.close();

                // 5. Send the compressed byte array back to the client
                toClient.writeObject(byteOut.toByteArray());
                toClient.flush();
            }

            fromClient.close();
            toClient.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
