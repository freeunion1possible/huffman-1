package huffman;

import static huffman.Utils.checkSymbolRange;
import static huffman.Utils.notNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static huffman.FrequencyTable.SYMBOL_LIMIT;

/**
 * A canonical Huffman code.
 * <p/>
 * Immutable. Code length 0 means no code.
 * <p/>
 * A canonical Huffman code only describes the code length of each symbol.
 * The codes can be reconstructed from this information. In this implementation, symbols with lower code lengths,
 * breaking ties by lower symbols, are assigned lexicographically lower codes.
 * <p/>
 * Example:
 * Code lengths (canonical code):
 * Symbol A: 1
 * Symbol B: 3
 * Symbol C: 0 (no code)
 * Symbol D: 2
 * Symbol E: 3
 * Huffman codes (generated from canonical code):
 * Symbol A: 0
 * Symbol B: 110
 * Symbol C: None
 * Symbol D: 10
 * Symbol E: 111
 */
public final class CanonicalCode {
  private int[] codeLengths = new int[SYMBOL_LIMIT];

  /**
   * The constructor does not check that the array of code lengths results in a complete Huffman tree,
   * being neither underfilled nor overfilled.
   */
  public CanonicalCode(int[] codeLengths) {
    if (codeLengths.length != SYMBOL_LIMIT) {
      throw new IllegalArgumentException("codeLengths size must be " + SYMBOL_LIMIT + " but is " + codeLengths.length);
    }
    for (int x : notNull(codeLengths, "codeLengths")) {
      if (x < 0) {
        throw new IllegalArgumentException("Illegal code length");
      }
    }
    for (int i = 0; i < SYMBOL_LIMIT; i++) {
      this.codeLengths[i] = codeLengths[i];
    }
  }

  /**
   * Builds a canonical code from the given code tree.
   */
  public CanonicalCode(CodeTree tree) {
    codeLengths = tree.buildCodeLengths();
  }

  public int getSymbolLimit() {
    return codeLengths.length;
  }

  /**
   * @param symbol
   *
   * @return code length fot the given symbol
   */
  public int getCodeLength(int symbol) {
    return codeLengths[checkSymbolRange(symbol)];
  }

  /**
   * @return CodeTree for this canonical Code
   */
  public CodeTree toCodeTree() {
    List<Node> nodes = new ArrayList<Node>();
    for (int len = max(codeLengths); len >= 1; len--) {
      List<Node> newNodes = new ArrayList<Node>();

      // Add leaves for symbols with code length i
      for (int symbol = 0; symbol < SYMBOL_LIMIT; symbol++) {
        if (codeLengths[symbol] == len) {
          newNodes.add(new Leaf(symbol, len));
        }
      }

      // Merge nodes from the previous deeper layer
      for (int j = 0; j < nodes.size(); j += 2) {
        newNodes.add(new InternalNode(nodes.get(j), nodes.get(j + 1)));
      }

      nodes = newNodes;

      if (nodes.size() % 2 != 0) {
        throw new IllegalStateException("This canonical code does not represent a Huffman code tree");
      }
    }

    if (nodes.size() != 2) {
      throw new IllegalStateException("This canonical code does not represent a Huffman code tree");
    }

    return new CodeTree(new InternalNode(nodes.get(0), nodes.get(1)), codeLengths.length);
  }

  private static int max(int[] array) {
    int result = array[0];
    for (int x : array)
      result = Math.max(x, result);
    return result;
  }

}
