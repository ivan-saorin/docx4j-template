package org.apitooling.export;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class ExporterOuptput {
	
	private File file;
	private OutputStream stream;	
	
	public ExporterOuptput(OutputStream stream) {
		super();
		this.stream = stream;
	}
	
	public ExporterOuptput(File file) {
		super();
		this.file = file;
	}

	public ExporterOuptput() {
		super();
	}

	public File asFile() {
		return null;
	}
	
	public InputStream asInputStream() {
		return null;
	}
	
	public Reader asReader() {
		return null;
	} 
}
