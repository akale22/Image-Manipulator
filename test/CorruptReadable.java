import java.nio.CharBuffer;

/**
 * A class to represent a readable where an IOException is thrown in its method for testing
 * purposes.
 */
public class CorruptReadable implements Readable {

  private String str;

  /**
   * Constructor which instantiates a readable string to whatever parameter is passed in.
   *
   * @param str the string that is passed in for the field.
   */
  public CorruptReadable(String str) {
    this.str = str;
  }

  /**
   * Constructor which instantiates a readable string to an empty string.
   */
  public CorruptReadable() {
    this.str = "";
  }

  @Override
  public int read(CharBuffer cb) throws IllegalStateException {
    throw new IllegalStateException();
  }
}
