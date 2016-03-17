package huffman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FrequencyTableTest {
  @Test
  public void testFrequencyTable() {
    FrequencyTable ft = new FrequencyTable();
    ft.increment(1);
    ft.increment(2);
    CodeTree ct = ft.buildCodeTree();
    int codeWithLength = numberOfEncodedSymbols(ct);

    assertEquals(2, codeWithLength);
  }

  private int numberOfEncodedSymbols(CodeTree ct) {
    int codeWithLength = 0;
    for (int l : ct.buildCodeLengths()) {
      if (l > 0) {
        codeWithLength++;
      }
    }
    return codeWithLength;
  }

}
