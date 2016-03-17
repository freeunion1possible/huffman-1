package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class AdaptiveHuffmanCompress {

  public static void main(String[] args) throws IOException {
    // Show what command line arguments to use
    if (args.length == 0) {
      System.err.println("Usage: java AdaptiveHuffmanCompress InputFile OutputFile");
      System.exit(1);
      return;
    }

    File inputFile = new File(args[0]);
    File outputFile = new File(args[1]);

    InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
    BitOutputStream out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
    AdaptiveHuffmanEngine engine = new AdaptiveHuffmanEngine();
    try {
      engine.compress(in, out);
    } finally {
      out.close();
      in.close();
    }
  }

}
