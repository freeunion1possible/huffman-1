package huffman;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static huffman.FrequencyTable.EOF;

/**
 * Adaptive huffman compression and decompression code.
 */
public class AdaptiveHuffmanEngine {
  public static final int CHUNK_SIZE = 1 << 18; // 2^18 = 262144

  public void compress(InputStream in, BitOutputStream out) throws IOException {
    FrequencyTable freqTable = new FrequencyTable();
    freqTable.resetAllFrequencies(1);
    CodeTree ct = freqTable.buildCodeTree();
    HuffmanEncoder enc = new HuffmanEncoder(out, ct);
    long count = 0;
    while (true) {
      int b = in.read();
      if (b == -1) {
        break;
      }
      enc.write(b);

      freqTable.increment(b);
      count++;
      if (timeToRebuildCodeTree(count)) {
        enc.setCodeTree(freqTable.buildCodeTree());
      }
      if (timeToResetFrequencies(count)) {
        freqTable.resetAllFrequencies(1);
      }
    }
    enc.write(EOF);
    out.close();
  }

  public void decompress(BitInputStream in, OutputStream out) throws IOException {
    FrequencyTable freqTable = new FrequencyTable();
    freqTable.resetAllFrequencies(1);
    HuffmanDecoder dec = new HuffmanDecoder(in, freqTable.buildCodeTree());
    long count = 0;
    while (true) {
      int symbol = 0;
      try {
        symbol = dec.read();
      } catch (EOFException e) {
        symbol = EOF;
      }
      if (symbol == EOF) {
        break;
      }

      out.write(symbol);

      freqTable.increment(symbol);
      count++;
      if (timeToRebuildCodeTree(count)) {
        dec.codeTree = freqTable.buildCodeTree();
      }
      if (timeToResetFrequencies(count)) {
        freqTable.resetAllFrequencies(1);
      }
    }
    out.close();
  }

  private boolean timeToRebuildCodeTree(long count) {
    return count < CHUNK_SIZE && isPowerOf2(count) || count % CHUNK_SIZE == 0;
  }

  private boolean timeToResetFrequencies(long count) {
    return count % CHUNK_SIZE == 0;
  }

  private boolean isPowerOf2(long x) {
    return x > 0 && (x & -x) == x;
  }
}
