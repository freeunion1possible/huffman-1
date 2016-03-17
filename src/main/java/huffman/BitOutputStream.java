package huffman;

import static huffman.Utils.checkRange;
import static huffman.Utils.notNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A stream where bits can be written to.
 * <p/>
 * Because they are written to an underlying byte stream, the end of the stream is padded with 0's up to a multiple of 8 bits.
 * The bits are written in big endian.
 */
public final class BitOutputStream {
  private OutputStream output;
  private int currentByte;
  private int numBitsInCurrentByte;

  public BitOutputStream(OutputStream out) {
    output = notNull(out, "out");
    currentByte = 0;
    numBitsInCurrentByte = 0;
  }

  /**
   * Writes a bit to the stream.
   *
   * @param b the bit to write (must be 0 or 1).
   */
  public void write(int b) throws IOException {
    currentByte = currentByte << 1 | checkRange(b, 0, 1, "b");
    numBitsInCurrentByte++;
    if (numBitsInCurrentByte == 8) {
      output.write(currentByte);
      numBitsInCurrentByte = 0;
    }
  }

  /**
   * Closes this stream and the underlying OutputStream.
   * <p/>
   * If called when this bit stream is not at a byte boundary, then the minimum number of "0" bits (between 0 and 7 of them)
   * are written as padding to reach the next byte boundary.
   */
  public void close() throws IOException {
    while (numBitsInCurrentByte != 0)
      write(0);
    output.close();
  }

}
