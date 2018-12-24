package org.swagger.model;

import java.io.File;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;

public class SwaggerParser {

	public static SwaggerModel load(File file) throws Exception {
		return new SwaggerModel(new OpenApi3Parser().parse(file, false));
	}
	
}
