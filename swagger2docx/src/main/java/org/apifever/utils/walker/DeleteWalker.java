package org.apifever.utils.walker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apifever.exceptions.WebApiException;
import org.apifever.exceptions.WebApiRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(DeleteWalker.class);
	
	public DeleteWalker(File directory) {
		super(directory, true);
	}

	@Override
	public void process(File file) throws WebApiException {
		
		if (file.getName().endsWith(".adoc")) {
			logger.info("deleting adoc: " + file.getName() + " [" + file.getAbsolutePath() + "]");
			
            try {
            	Files.delete(file.toPath());
            } catch (IOException e) {
                throw new WebApiRuntimeException(e);
            }
		}
		
	}
 
}
