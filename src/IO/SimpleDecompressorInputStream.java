package IO;
import java.io.IOException;
import java.io.InputStream;

/**
 * SimpleDecompressorInputStream is a decorator for InputStream.
 * It decompresses data that was compressed using Run-Length Encoding (RLE).
 */
public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads compressed data and inflates it back to the original byte array.
     * @param b The byte array to fill with decompressed maze data.
     * @return 0 on success.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public int read(byte[] b) throws IOException {
        // 1. Read the metadata (first 24 bytes) as is
        for (int i = 0; i < 24; i++) {
            b[i] = (byte) in.read();
        }

        // 2. Decompress the grid data
        int index = 24;
        while (index < b.length) {
            int count = in.read(); // Read the quantity
            int value = in.read(); // Read the value (0 or 1)

            if (count == -1 || value == -1) break; // End of stream

            // Cast count to unsigned int (since byte is -128 to 127)
            int actualCount = count & 0xFF;

            // Fill the array 'actualCount' times with 'value'
            for (int i = 0; i < actualCount && index < b.length; i++) {
                b[index++] = (byte) value;
            }
        }
        return 0;
    }
}

