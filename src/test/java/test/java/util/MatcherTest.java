package test.java.util;

import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by selonj on 16-9-13.
 */
public class MatcherTest {
  @Test public void matchesWithCharBuffer() throws Exception {
    CharBuffer input = CharBuffer.allocate(20);
    input.append("jdk: oraclejdk8");
    input.flip();
    Matcher matcher = Pattern.compile("\\w+\\d+").matcher(input);

    matcher.region(input.position(), input.limit());

    assertTrue(matcher.find());
    assertThat(matcher.group(), equalTo("oraclejdk8"));
    assertTrue(!matcher.find());
  }
}
