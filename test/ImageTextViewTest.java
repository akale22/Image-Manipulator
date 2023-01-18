import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.text.ImageTextViewImpl;
import view.text.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for {@link ImageTextViewImpl}s.
 */
public class ImageTextViewTest {

  private Appendable out;

  private ImageTextView view;

  @Before
  public void init() {
    out = new StringBuilder();
    view = new ImageTextViewImpl(out);
  }

  @Test
  public void testValidConstruction() throws IOException {
    view.renderMessage("hi");
    assertEquals("hi", out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction() {
    ImageTextView view = new ImageTextViewImpl(null);
  }

  @Test
  public void testRenderMessage1() throws IOException {
    view.renderMessage("");
    assertEquals("", out.toString());
  }

  @Test
  public void testRenderMessage2() throws IOException {
    view.renderMessage("anshul");
    assertEquals("anshul", out.toString());
  }

  @Test
  public void testRenderMessage3() throws IOException {
    view.renderMessage("celtics in 6.");
    assertEquals("celtics in 6.", out.toString());
  }

  @Test(expected = IOException.class)
  public void testMessageRenderFail() throws IOException {
    Appendable badAppendable = new CorruptAppendable();
    ImageTextView badView = new ImageTextViewImpl(badAppendable);

    badView.renderMessage("hello");
  }
}