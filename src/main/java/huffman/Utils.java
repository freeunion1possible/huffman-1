package huffman;

/**
 * Description.
 *
 * @uthor 05014050
 * @since 15.03.2016
 */
public class Utils {
  public final static int MIN_SYMBOL_VALUE = 0;
  public final static int MAX_SYMBOL_VALUE = 256;

  public static int checkSymbolRange(int symbol) {
    return checkRange(symbol, MIN_SYMBOL_VALUE, MAX_SYMBOL_VALUE, "symbol");
  }

  public static int checkRange(int value, int minValue, int maxValue, String paramName) {
    if (value < MIN_SYMBOL_VALUE || value > MAX_SYMBOL_VALUE) {
      throw new IllegalArgumentException(paramName + " value " + value + " not within range (" + minValue + ".." + maxValue + ")");
    }
    return value;
  }

  public static <T> T notNull(T o, String paramName) {
    if (o == null) {
      throw new NullPointerException(paramName + " value must not be null");
    }
    return o;
  }
}
