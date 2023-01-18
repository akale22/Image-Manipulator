import java.io.IOException;

/**
 * A class to represent an appendable where an IOException is thrown in every method for testing
 * purposes.
 */
public class CorruptAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}

