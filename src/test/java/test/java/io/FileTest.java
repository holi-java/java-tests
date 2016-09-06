package test.java.io;

import java.io.File;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-7.
 */
public class FileTest {
  private final File virtual = new File(".");
  private final File real = new File(System.getProperty("user.dir"));

  @Test public void returnNullIfGetParentFileFromVirtualFile() throws Exception {
    assertThat(virtual.getParentFile(), is(nullValue()));
    assertThat(real.getParentFile(), is(notNullValue()));
  }

  @Test public void returnRealFileWhenGetCanonicalFile() throws Exception {
    File file1 = virtual.getCanonicalFile();
    File file2 = real.getCanonicalFile();

    assertThat(file1, equalTo(real));
    assertThat(file2, equalTo(real));
  }
}
