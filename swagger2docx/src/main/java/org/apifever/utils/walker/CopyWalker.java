package org.apifever.utils.walker;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apifever.exceptions.WebApiException;
import org.apifever.exceptions.WebApiRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(CopyWalker.class);
	
	private final File dst;
	
	public CopyWalker(File directory, String destination) {
		super(directory, true);
		this.dst = new File(destination);
	}

	@Override
	public void process(File file) throws WebApiException {
		// ensure destination dir exists
		dst.mkdirs();
		if (!dst.exists()) {
			throw new WebApiRuntimeException("destination dir not found: " + dst.getAbsolutePath());
		}
		if (file.getName().endsWith(".adoc")) {
			logger.info("processing adoc: " + file.getName() + " [" + file.getAbsolutePath() + "]");
			
			copyFile(file.toPath(), new File(dst, file.getName()).toPath(), true);
		}
		
	}
	
	/**
     * Copy source file to target location. The {@code preserve}
     * parameter determines if file attributes should be copied/preserved.
     */
    private void copyFile(Path source, Path target, boolean preserve) {
        CopyOption[] options = (preserve) ?
            new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING } :
            new CopyOption[] { REPLACE_EXISTING };
        //if (Files.notExists(target)) {
            try {
                Files.copy(source, target, options);
            } catch (IOException e) {
                throw new WebApiRuntimeException(e);
            }
        //}
    }
 
}