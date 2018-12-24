package org.swaggertooling.utils.walker;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swaggertooling.exceptions.WebApiException;

public abstract class DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(DirectoryWalker.class);
	
	private File directory;
	private boolean recurse = true;
	
	public DirectoryWalker(File directory, boolean recurse) {
		super();
		this.directory = directory;
		this.recurse = recurse;
	}
	
	public void walk() throws WebApiException {
		if (directory.isDirectory()) {
			walk(directory);
		}
		else {
			process(directory);
		}
	}
	
	public abstract void process(File file) throws WebApiException;
	
	public void internalProcess(File file) throws WebApiException {
		logger.info("processing: " + file.getName());
		process(file);
	}
	
	private void walk(File directory) throws WebApiException {
		logger.info("current directory: " + directory.getPath());
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files == null || files.length < 1) {
				System.out.println("Empty directory [" + directory.getAbsolutePath() + "]");
				return;
			}
			for (File f : files) {
				if (f != null && f.isDirectory()) {
					if (recurse) {
						walk(f);
					}
					continue;
				}
				process(f);
			}
		} else {
			System.out.println("Not a directory.... skipping [" + directory.getAbsolutePath() + "]");
		}
		
	}
}
