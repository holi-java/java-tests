package test.java.nio;

import java.nio.CharBuffer;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-9.
 */
public class CharBufferTest {
  private static final int CAPACITY = 100;
  private static final int LIMIT = 50;
  private final CharBuffer buff = (CharBuffer) CharBuffer.allocate(CAPACITY).limit(LIMIT);

  @Test public void initialValues() throws Exception {
    assertThat(buff.isDirect(), is(false));
    assertThat(buff.isReadOnly(), is(false));
    assertThat(buff.arrayOffset(), equalTo(0));
    assertThat(buff.capacity(), equalTo(CAPACITY));
    assertThat(buff.limit(), equalTo(LIMIT));
    assertThat(buff.length(), equalTo(LIMIT));
    assertThat(buff.remaining(), equalTo(LIMIT));
    assertThat(buff.position(), equalTo(0));
  }

  @Test public void put() throws Exception {
    String added = "foo";
    int chars = added.length();
    buff.put(added);

    assertThat(buff.isReadOnly(), is(false));
    assertThat(buff.arrayOffset(), equalTo(0));
    assertThat(buff.capacity(), equalTo(CAPACITY));
    assertThat(buff.limit(), equalTo(LIMIT));
    assertThat(buff.length(), equalTo(LIMIT - chars));
    assertThat(buff.remaining(), equalTo(LIMIT - chars));
    assertThat(buff.position(), equalTo(chars));
  }

  @Test public void setPositionToZeroAndLimitToPositionWhenFlipCalled() throws Exception {
    String added = "foo";
    int chars = added.length();
    buff.put(added);

    buff.flip();

    assertThat(buff.position(), equalTo(0));
    assertThat(buff.remaining(), equalTo(chars));
    assertThat(buff.limit(), equalTo(chars));
    assertThat(buff.length(), equalTo(chars));
    assertThat(buff.capacity(), equalTo(CAPACITY));
  }

  @Test public void setPositionToZeroWhenRewindCalled() throws Exception {
    buff.put("foo");

    buff.rewind();

    assertThat(buff.position(), equalTo(0));
    assertThat(buff.remaining(), equalTo(LIMIT));
    assertThat(buff.limit(), equalTo(LIMIT));
    assertThat(buff.length(), equalTo(LIMIT));
    assertThat(buff.capacity(), equalTo(CAPACITY));
  }

  @Test public void resetToPreviousMarkedPosition() throws Exception {
    buff.mark();//position=0
    buff.put("foo");

    buff.reset();

    assertThat(buff.position(), equalTo(0));
    assertThat(buff.remaining(), equalTo(LIMIT));
    assertThat(buff.limit(), equalTo(LIMIT));
    assertThat(buff.length(), equalTo(LIMIT));
    assertThat(buff.capacity(), equalTo(CAPACITY));
  }

  @Test public void resetLimit() throws Exception {
    int newLimit = 3;

    buff.limit(newLimit);

    assertThat(buff.position(), equalTo(0));
    assertThat(buff.remaining(), equalTo(newLimit));
    assertThat(buff.limit(), equalTo(newLimit));
    assertThat(buff.length(), equalTo(newLimit));
    assertThat(buff.capacity(), equalTo(CAPACITY));
  }

  @Test public void resetPosition() throws Exception {
    int position = 3;

    buff.position(position);

    assertThat(buff.position(), equalTo(position));
    assertThat(buff.remaining(), equalTo(LIMIT - position));
    assertThat(buff.limit(), equalTo(LIMIT));
    assertThat(buff.length(), equalTo(LIMIT - position));
    assertThat(buff.capacity(), equalTo(CAPACITY));
  }

  @Test public void clear() throws Exception {
    buff.position(2);
    buff.clear();

    assertThat(buff.position(), equalTo(0));
    assertThat(buff.remaining(), equalTo(CAPACITY));
    assertThat(buff.limit(), equalTo(CAPACITY));
    assertThat(buff.length(), equalTo(CAPACITY));
    assertThat(buff.capacity(), equalTo(CAPACITY));
  }

  @Test public void duplicate() throws Exception {
    buff.put("foo");
    buff.flip();

    CharBuffer duplicated = buff.duplicate();

    assertThat(duplicated, is(not(sameInstance(buff))));
    assertThat(duplicated, equalTo(buff));
  }

  @Test public void compact() throws Exception {
    String added = "foo";
    int chars = added.length();
    buff.put(added);
    buff.put(chars, 'z');
    //[0..position..limit..capacity]
    // copy [position..limit] to [0..(limit-position)]
    // position(limit-position)
    // limit(capacity)
    buff.compact();

    assertThat(buff.position(), equalTo(LIMIT - chars));
    assertThat(buff.remaining(), equalTo(CAPACITY - (LIMIT - chars)));
    assertThat(buff.limit(), equalTo(CAPACITY));
    assertThat(buff.length(), equalTo(CAPACITY - (LIMIT - chars)));
    assertThat(buff.capacity(), equalTo(CAPACITY));
    assertThat(buff.get(0), equalTo('z'));
  }
}
