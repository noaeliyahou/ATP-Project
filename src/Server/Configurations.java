package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    // Singleton instance
    private static Configurations instance = null;
    private Properties properties;

    // Private constructor to prevent instantiation from outside (Singleton Pattern)
    private Configurations() {
        properties = new Properties();
        // Load the properties file safely from the resources directory using the ClassLoader
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Warning: Unable to find config.properties, using default values.");
                // Fallback default values in case the file is missing or cannot be loaded
                properties.setProperty("threadPoolSize", "5");
                properties.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
                properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Global access point to get the single instance of this class
    public static synchronized Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    // --- Getter Methods for Server and Strategies ---

    /**
     * Retrieves the thread pool size as an integer.
     * @return the number of threads allowed in the Thread Pool.
     */
    public int getThreadPoolSize() {
        String size = properties.getProperty("threadPoolSize", "5");
        return Integer.parseInt(size);
    }

    /**
     * Retrieves the name of the maze generating algorithm.
     * @return the maze generation algorithm name as a String.
     */
    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }

    /**
     * Retrieves the name of the maze searching algorithm.
     * @return the maze solving/searching algorithm name as a String.
     */
    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }
}