package common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource {
	private String contentType;
	private byte[] data;
	private String name;
	public ByteArrayDataSource(String name, byte[] data, String contentType) {
	this.name = name;
	this.data = data;
	this.contentType = contentType;
	}
	public String getContentType( ) {

	return contentType;
	}
	public InputStream getInputStream( ) throws IOException {
	return new ByteArrayInputStream(data);
	}
	public String getName( ) {
	return name;
	}
	public OutputStream getOutputStream( ) throws IOException {
	throw new IOException(
	"ByteArrayDataSource cannot support getOutputStream( )");
	}
	}
