package huffman;

import static huffman.FrequencyTable.EOF;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Huffman encode and decode engine.
 */
public class HuffmanEngine {

  public void writeCodeAndCompress(CanonicalCode canonCode, CodeTree code, InputStream in, BitOutputStream out) throws IOException {
    try {
      writeCode(out, canonCode);
      compress(code, in, out);
    } finally {
      out.close();
      in.close();
    }
  }

  public void readCodeAndDecompress(BitInputStream in, OutputStream out) throws IOException {
    try {
      CanonicalCode canonCode = readCode(in);
      CodeTree code = canonCode.toCodeTree();
      decompress(code, in, out);
    } finally {
      out.close();
      in.close();
    }
  }

  private void writeCode(BitOutputStream out, CanonicalCode canonCode) throws IOException {
    for (int i = 0; i < canonCode.getSymbolLimit(); i++) {
      int val = canonCode.getCodeLength(i);
      // we only support codes up to 255 bits long
      if (val >= EOF) {
        throw new RuntimeException("The code for a symbol is too long");
      }

      // 8 bits in big endian
      for (int j = 7; j >= 0; j--)
        out.write((val >>> j) & 1);
    }
  }

  private CanonicalCode readCode(BitInputStream in) throws IOException {
    int[] codeLengths = new int[257];
    for (int i = 0; i < codeLengths.length; i++) {
      // 8 bits in big endian
      int val = 0;
      for (int j = 0; j < 8; j++)
        val = val << 1 | in.readNoEof();
      codeLengths[i] = val;
    }
    return new CanonicalCode(codeLengths);
  }

  private void compress(CodeTree code, InputStream in, BitOutputStream out) throws IOException {
    HuffmanEncoder enc = new HuffmanEncoder(out, code);
    while (true) {
      int b = in.read();
      if (b == -1)
        break;
      enc.write(b);
    }
    enc.write(EOF);
  }

  private void decompress(CodeTree code, BitInputStream in, OutputStream out) throws IOException {
    HuffmanDecoder dec = new HuffmanDecoder(in, code);
    while (true) {
      int symbol = dec.read();
      if (symbol == EOF) {
        break;
      }
      out.write(symbol);
    }
  }
}
