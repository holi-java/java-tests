package test.java.nio.file;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-7.
 */
public class PathTest {
  private final Path virtual = Paths.get(".");
  private final Path real = Paths.get(System.getProperty("user.dir"));

  @Test public void returnNullIfGetParentFromVirtualPath() throws Exception {
    assertThat(virtual.getParent(), is(nullValue()));
    assertThat(real.getParent(), is(notNullValue()));
  }

  @Test public void realPath() throws Exception {
    Path path1 = virtual.toRealPath();
    Path path2 = real.toRealPath();

    assertThat(path1, equalTo(real));
    assertThat(path2, equalTo(real));
  }

  @Test public void diffTypePathUrisAreDifferent() throws Exception {
    URI uri = real.toUri();
    URI uri1 = virtual.toUri();
    URI uri2 = virtual.toRealPath().toUri();

    assertThat(uri1, is(notNullValue()));
    assertThat(uri1, not(equalTo(uri)));
    assertThat(uri2, equalTo(uri));
  }
}
