package huffman;

import static huffman.Utils.checkSymbolRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Leaf node of a Huffman tree.
 *
 * Contains the symbol and it's frequency.
 */
public class Leaf extends Node {
  public final int symbol;

  public Leaf(int symbol, long frequency) {
    super(checkSymbolRange(symbol), frequency);
    this.symbol = symbol;
  }

  @Override
  public void buildCodeLengths(int[] codeLengths, int depth) {
    if (codeLengths[symbol] != 0)
      throw new AssertionError("Symbol has more than one code");  // Because CodeTree has a checked constraint that disallows a symbol in multiple leaves
    if (symbol >= codeLengths.length)
      throw new IllegalArgumentException("Symbol exceeds symbol limit");
    codeLengths[symbol] = depth;
  }

  @Override
  public void buildCodeList(List<List<Integer>> codes, ArrayList<Integer> prefix) {
    if (symbol >= codes.size())
      throw new IllegalArgumentException("Symbol exceeds symbol limit");
    if (codes.get(symbol) != null)
      throw new IllegalArgumentException("Symbol has more than one code");
    codes.set(symbol, new ArrayList<Integer>(prefix));
  }

}
