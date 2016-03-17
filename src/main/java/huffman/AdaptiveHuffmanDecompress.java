package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class AdaptiveHuffmanDecompress {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: java AdaptiveHuffmanDecompress InputFile OutputFile");
      System.exit(1);
      return;
    }

    File inputFile = new File(args[0]);
    File outputFile = new File(args[1]);

    BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
    OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
    AdaptiveHuffmanEngine engine = new AdaptiveHuffmanEngine();
    try {
      engine.decompress(in, out);
    } finally {
      out.close();
      in.close();
    }
  }

}
