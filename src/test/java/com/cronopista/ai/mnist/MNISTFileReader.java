package com.cronopista.ai.mnist;

import java.io.IOException;
import java.io.InputStream;

public class MNISTFileReader {

	private InputStream in;
	
	private byte[] buffer;
	
	public MNISTFileReader(InputStream in, int offset, int size) throws IOException {
		this.in=in;
		in.read(new byte[offset]);
		this.buffer = new byte[size];
	}
	
	
	public byte[] readNext() throws IOException {
		in.read(buffer);
		
		return buffer;
	} 
	
}
