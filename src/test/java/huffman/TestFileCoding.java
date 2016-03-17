package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class TestFileCoding {
  private final AdaptiveHuffmanEngine engine = new AdaptiveHuffmanEngine();

  @Test
  public void comressDecompressText() throws Exception {
    compressText();
    decompressFile();
  }

  private void compressText() throws IOException {
    File sourceText = new File("Maerchen.txt");
    File compressedFile = new File("Maerchen.txt.hc");

    InputStream in = new BufferedInputStream(new FileInputStream(sourceText));
    BitOutputStream out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(compressedFile)));

    engine.compress(in, out);
  }

  private void decompressFile() throws IOException {
    File inputFile = new File("Maerchen.txt.hc");
    File outputFile = new File("Maerchen2.txt");

    BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
    OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

    engine.decompress(in, out);
  }
}
