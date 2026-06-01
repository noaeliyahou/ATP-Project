package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port; // The port the server listens on
    private int listeningIntervalMS; // How often to check for "stop" signal
    private IServerStrategy strategy; // What the server actually does
    private volatile boolean stop; // Flag to stop the server
    private ExecutorService threadPool; // The pool of worker threads

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
        // Pull the thread pool size dynamically from the configurations
        int poolSize = Configurations.getInstance().getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }
    // This method starts the server in a new thread so it doesn't block the main program
    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS); // Set timeout for accept()

            while (!stop) {
                try {
                    // Waiting for a client to connect
                    Socket clientSocket = serverSocket.accept();

                    // When a client connects, we DON'T handle them here.
                    // Instead, we give the task to the Thread Pool.
                    threadPool.submit(() -> {
                        handleClient(clientSocket);
                    });

                } catch (SocketTimeoutException e) {
                    // No client connected within the interval, just loop again and check 'stop'
                }
            }
            serverSocket.close();
            threadPool.shutdown(); // Stop accepting new tasks in the pool
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This is the logic performed by the worker thread
    private void handleClient(Socket clientSocket) {
        try {
            // Log for debugging
            System.out.println("Client connected: " + clientSocket.toString());

            // Execute the strategy on the client's streams
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());

            // Cleanup
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}
