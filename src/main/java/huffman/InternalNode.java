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
public class InternalNode extends Node {
  public final Node leftChild;
  public final Node rightChild;

  public InternalNode(Node leftChild, Node rightChild) {
    super(Math.min(notNull(leftChild, "leftChild").lowestSymbol, notNull(rightChild, "rightChild").lowestSymbol),
          notNull(leftChild, "leftChild").frequency + notNull(rightChild, "rightChild").frequency);
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  @Override
  public void buildCodeLengths(int[] codeLengths, int depth) {
    leftChild.buildCodeLengths(codeLengths, depth + 1);
    rightChild.buildCodeLengths(codeLengths, depth + 1);
  }

  @Override
  public void buildCodeList(List<List<Integer>> codes, ArrayList<Integer> prefix) {
    prefix.add(0);
    leftChild.buildCodeList(codes, prefix);
    prefix.remove(prefix.size() - 1);

    prefix.add(1);
    rightChild.buildCodeList(codes, prefix);
    prefix.remove(prefix.size() - 1);
  }

}
