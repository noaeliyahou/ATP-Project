package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) return;

        // 1. Write the metadata (first 24 bytes) as is, without compression
        for (int i = 0; i < 24; i++) {
            out.write(b[i]);
        }

        // 2. Prepare the grid data for compression (from byte 24 onwards)
        if (b.length <= 24) return;

        byte[] dataToCompress = new byte[b.length - 24];
        System.arraycopy(b, 24, dataToCompress, 0, dataToCompress.length);

        // 3. Execute LZW compression algorithm
        List<Integer> compressed = lzwCompress(dataToCompress);

        // 4. Write the compressed codes to the stream.
        // Each code is written as 2 bytes (16-bit) to support a large dictionary.
        for (int code : compressed) {
            out.write((code >> 8) & 0xFF); // High byte
            out.write(code & 0xFF);        // Low byte
        }
    }

    /**
     * LZW Compression logic.
     * @param uncompressed The data to compress.
     * @return A list of dictionary codes representing the compressed data.
     */
    private List<Integer> lzwCompress(byte[] uncompressed) {
        // Initialize the dictionary with basic byte values (0-255)
        int dictSize = 256;
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) (i & 0xFF), i);
        }

        String w = "";
        List<Integer> result = new ArrayList<>();

        for (byte b : uncompressed) {
            String c = "" + (char) (b & 0xFF);
            String wc = w + c;

            if (dictionary.containsKey(wc)) {
                w = wc;
            } else {
                result.add(dictionary.get(w));
                // Add new sequence to dictionary if there is space (up to 16-bit limit)
                if (dictSize < 65535) {
                    dictionary.put(wc, dictSize++);
                }
                w = c;
            }
        }

        // Add the last code
        if (!w.equals("")) {
            result.add(dictionary.get(w));
        }
        return result;
    }

}
