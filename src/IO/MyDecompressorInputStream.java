package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyDecompressorInputStream implements LZW decompression.
 * It reconstructs the dictionary dynamically while reading the compressed codes.
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        // 1. Read the metadata (first 24 bytes) as is
        for (int i = 0; i < 24; i++) {
            b[i] = (byte) in.read();
        }

        if (b.length <= 24) return 0;

        // 2. Read all compressed codes from the stream
        List<Integer> compressedCodes = new ArrayList<>();
        int highByte, lowByte;
        while ((highByte = in.read()) != -1 && (lowByte = in.read()) != -1) {
            int code = ((highByte & 0xFF) << 8) | (lowByte & 0xFF);
            compressedCodes.add(code);
        }

        // 3. Decompress LZW codes
        byte[] decompressedData = lzwDecompress(compressedCodes);

        // 4. Copy decompressed data into the provided byte array b
        System.arraycopy(decompressedData, 0, b, 24, Math.min(decompressedData.length, b.length - 24));

        return 0;
    }

    private byte[] lzwDecompress(List<Integer> compressed) {
        // Initialize dictionary with single byte values (0-255)
        int dictSize = 256;
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

        if (compressed.isEmpty()) return new byte[0];

        String w = "" + (char) (int) compressed.remove(0);
        StringBuilder result = new StringBuilder(w);

        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k)) {
                entry = dictionary.get(k);
            } else if (k == dictSize) {
                entry = w + w.charAt(0);
            } else {
                throw new IllegalArgumentException("Bad compressed k: " + k);
            }

            result.append(entry);

            // Add new sequence to dictionary
            if (dictSize < 65535) {
                dictionary.put(dictSize++, w + entry.charAt(0));
            }

            w = entry;
        }

        // Convert the StringBuilder results back to byte array
        byte[] output = new byte[result.length()];
        for (int i = 0; i < result.length(); i++) {
            output[i] = (byte) result.charAt(i);
        }
        return output;
    }
}