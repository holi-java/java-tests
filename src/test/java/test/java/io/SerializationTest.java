package test.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by selonj on 16-9-15.
 */
public class SerializationTest {
  private final SerializingObject original = new SerializingObject("foo");

  @Test public void callsInternalWriteObjectIfSerializingObjectDefinedWriteObjectMethodWhenSerializing() throws Exception {
    serializing(original);

    original.hasCalledInternalWriteObjectMethod();
  }

  @Test public void callsInternalReadObjectIfSerializingObjectDefinedReadObjectMethodWhenDeserializing() throws Exception {
    byte[] binary = serializing(original);

    SerializingObject result = deserializing(binary);

    result.hasCalledInternalReadObjectMethod();
    result.assertStateThatIs("foo");
  }

  private SerializingObject deserializing(byte[] binary) throws IOException, ClassNotFoundException {
    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(binary));
    return SerializingObject.class.cast(in.readObject());
  }

  private byte[] serializing(Serializable serializingObject) throws IOException {
    ByteArrayOutputStream dest = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(dest);
    out.writeObject(serializingObject);
    return dest.toByteArray();
  }

  private static class SerializingObject implements Serializable {
    private boolean written = false;
    private boolean read = false;
    private String state;

    public SerializingObject(String state) {
      this.state = state;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
      written = true;
      out.writeUTF(state);
    }

    private void readObject(ObjectInputStream in) throws IOException {
      state = in.readUTF();
      read = true;
    }

    public void hasCalledInternalWriteObjectMethod() {
      assertTrue("write method has not been called", written);
    }

    public void hasCalledInternalReadObjectMethod() {
      assertTrue("read method has not been called", read);
    }

    private void assertStateThatIs(String expectedState) {
      assertThat(state, equalTo(expectedState));
    }
  }
}
