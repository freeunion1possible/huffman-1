package huffman;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract node of a huffman tree.
 *
 * Contains the lowest symbol in the tree and the frequency of the whole tree.
 *
 * Concrete Nodes are {@see InternalNode} and {@see Leaf}.
 */
public abstract class Node implements Comparable<Node> {
  public final int lowestSymbol;
  public final long frequency;

  Node(int lowestSymbol, long frequency) {
    this.lowestSymbol = lowestSymbol;
    this.frequency = frequency;
  }

  /**
   * {@inheritDoc}
   *
   * Compares nodes according to it's frequency.
   * If the nodes have the same frequency, then comparison is done by the lowest symbol in each node.
   */
  public int compareTo(Node other) {
    if (frequency < other.frequency)
      return -1;
    else if (frequency > other.frequency)
      return 1;
    else if (lowestSymbol < other.lowestSymbol)
      return -1;
    else if (lowestSymbol > other.lowestSymbol)
      return 1;
    else
      return 0;
  }

  public abstract void buildCodeLengths(int[] codeLengths, int depth);

  public abstract void buildCodeList(List<List<Integer>> codes, ArrayList<Integer> integers);
}
