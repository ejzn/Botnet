package servicebus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArrayDataSource implements javax.activation.DataSource {
	private byte[] bytes;
	
	public void setBytes(byte[] bytes) { this.bytes = bytes; }
	
	
	public byte[] getBytes() { return bytes; }
	
	
	private String contentType;
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentType() { return contentType; }
	public InputStream getInputStream() {
		return new ByteArrayInputStream(bytes);
	}
	/** for completeness, here's how to implement the outputstream.
	this is unnecessary for what you're doing, you can just throw
	an UnsupportedOperationException. */
	public OutputStream getOutputStream() {
		final ByteArrayDataSource bads = this;
		final ByteArrayOutputStream baos =
			new ByteArrayOutputStream();
		// return an outputstream that sets my byte array
		// when it is closed.
		return new FilterOutputStream(baos) {
			public void close() throws IOException {
				baos.close();
				bads.setBytes(baos.toByteArray());
			}
		};
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
