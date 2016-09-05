package test.java.lang;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-5.
 */
public class StringTest {

  @Test public void replaceStringWithMatchedStringAppendSomethingElse() throws Exception {
    String string = "foo";

    String result = string.replaceAll("^(\\w)", "$1<append>");

    assertThat(result, equalTo("f<append>oo"));
  }
}
