package huffman;

import static huffman.Utils.notNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @uthor 05014050
 * @since 15.03.2016
 */
public class CodeTree {
  private final InternalNode root;

  private List<List<Integer>> codes = new ArrayList<List<Integer>>();
  ;

  public CodeTree(InternalNode root, int symbolLimit) {
    this.root = notNull(root, "root");
    for (int i = 0; i < symbolLimit; i++)
      codes.add(null);
    root.buildCodeList(codes, new ArrayList<Integer>());
  }

  public List<Integer> getCode(int symbol) {
    if (symbol < 0)
      throw new IllegalArgumentException("Illegal symbol");
    else if (codes.get(symbol) == null)
      throw new IllegalArgumentException("No code for given symbol");
    else
      return codes.get(symbol);
  }

  public InternalNode getRoot() {
    return root;
  }

  public int[] buildCodeLengths() {
    int[] codeLengths = new int[257];
    root.buildCodeLengths(codeLengths, 0);
    return codeLengths;
  }
}
