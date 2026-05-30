package IO;

import java.io.OutputStream;
import java.io.IOException;


public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses the byte array and writes it to the decorated OutputStream.
     * @param b the byte array representing the Maze.
     */
    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) return;

        // 1. Write the metadata (first 24 bytes) without compression.
        // These are essential for the Maze constructor (rows, cols, etc.)
        for (int i = 0; i < 24; i++) {
            out.write(b[i]);
        }

        // 2. Compress the grid values (from byte 24 onwards)
        if (b.length > 24) {
            byte lastByte = b[24];
            int count = 0;

            for (int i = 24; i < b.length; i++) {
                // If the current byte is the same as the previous one, and count < 255
                if (b[i] == lastByte && count < 255) {
                    count++;
                } else {
                    // Write the counter and the byte value
                    out.write((byte) count);
                    out.write(lastByte);

                    // Reset for the next sequence
                    lastByte = b[i];
                    count = 1;
                }
            }
            // Write the final sequence
            out.write((byte) count);
            out.write(lastByte);
        }
    }
}
