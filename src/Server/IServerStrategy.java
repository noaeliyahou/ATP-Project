package Server;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface represents a strategy for handling client requests.
 * Any class that implements this interface will define a specific logic
 * for what the server should do when a client connects.
 */

public interface IServerStrategy {
    /**
     * This method defines the interaction between the server and the client.
     * @param inFromClient - The input stream to read data from the client.
     * @param outFromClient - The output stream to send data to the client.
     */
    void serverStrategy(InputStream inFromClient, OutputStream outFromClient);
}

