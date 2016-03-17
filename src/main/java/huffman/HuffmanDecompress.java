package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Decompresses an input file that was compressed with HuffmanCompress, to an output file.
 */
public final class HuffmanDecompress {
  public static void main(String[] args) throws IOException {
    // Show what command line arguments to use
    if (args.length == 0) {
      System.err.println("Usage: java HuffmanDecompress InputFile OutputFile");
      System.exit(1);
      return;
    }

    File inputFile = new File(args[0]);
    File outputFile = new File(args[1]);

    HuffmanEngine engine = new HuffmanEngine();
    BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
    OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
    engine.readCodeAndDecompress(in, out);
  }
}
