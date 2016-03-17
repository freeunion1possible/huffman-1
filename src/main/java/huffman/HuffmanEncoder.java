package huffman;

import static huffman.Utils.notNull;

import java.io.IOException;
import java.util.List;

public final class HuffmanEncoder {
  private BitOutputStream output;
  private CodeTree codeTree;

  public HuffmanEncoder(BitOutputStream out, CodeTree ct) {
    output = notNull(out, "out");
    codeTree = notNull(ct, "ct");
  }

  public void setCodeTree(CodeTree ct) {
    codeTree = ct;
  }

  public void write(int symbol) throws IOException {
    List<Integer> bits = codeTree.getCode(symbol);
    for (int b : bits)
      output.write(b);
  }
}
