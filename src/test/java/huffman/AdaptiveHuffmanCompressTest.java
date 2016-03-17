package huffman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// Tests AdaptiveHuffmanCompress coupled with AdaptiveHuffmanDecompress.
public class AdaptiveHuffmanCompressTest extends HuffmanCodingTest {

  private final AdaptiveHuffmanEngine engine = new AdaptiveHuffmanEngine();

  protected byte[] compress(byte[] b) throws IOException {
    InputStream in = new ByteArrayInputStream(b);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    BitOutputStream bitOut = new BitOutputStream(out);
    engine.compress(in, bitOut);
    bitOut.close();
    return out.toByteArray();
  }

  protected byte[] decompress(byte[] b) throws IOException {
    InputStream in = new ByteArrayInputStream(b);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    engine.decompress(new BitInputStream(in), out);
    return out.toByteArray();
  }

}
