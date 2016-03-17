package huffman;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import static huffman.Utils.checkSymbolRange;

/**
 * Collects Frequencies of bytes.
 *
 * @uthor 05014050
 * @since 15.03.2016
 */
public class FrequencyTable {

  public final static int EOF = 256;
  public final static int SYMBOL_LIMIT = EOF + 1; // Frequency of every real symbol and one for EOF

  private final long[] frequencies = new long[SYMBOL_LIMIT];

  /**
   * increment frequency count for symbol
   *
   * @param symbol
   */
  public void increment(int symbol) {
    if (frequencies[checkSymbolRange(symbol)] == Long.MAX_VALUE) {
      throw new RuntimeException("overflow");
    }
    frequencies[symbol]++;
  }

  /**
   * Resets frequency of all symbols to the given value
   *
   * @param value new frequency for all symbols
   */
  public void resetAllFrequencies(long value) {
    Arrays.fill(frequencies, value);
  }

  public CodeTree buildCodeTree() {
    Queue<Node> queue = new PriorityQueue<Node>(); // container with sorted nodes

    // add a Leaf-Tree for every symbol contained in the input stream
    for (int i = 0; i < frequencies.length; i++) {
      if (frequencies[i] > 0) {
        queue.add(new Leaf(i, frequencies[i]));
      }
    }

    // to ensure we have an InternalNode at the end, there must be at least two Leaf nodes
    int i = 0;
    while (queue.size() < 2) {
      queue.add(new Leaf(i++, 0));
    }

    // take the two least weight nodes from the queue and make an InternalNode out of it
    while (queue.size() > 1) {
      Node nf1 = queue.remove();
      Node nf2 = queue.remove();
      queue.add(new InternalNode(nf1, nf2));
    }

    // construct the CodeTree with the remaining InternalNode
    return new CodeTree((InternalNode) queue.remove(), SYMBOL_LIMIT);
  }

}
