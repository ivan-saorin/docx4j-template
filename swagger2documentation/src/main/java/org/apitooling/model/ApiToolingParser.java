package org.apitooling.model;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.parser.SwaggerParser;

public class ApiToolingParser {

	private static Logger logger = LoggerFactory.getLogger(ApiToolingParser.class);
	
	public static ApiModel load(File file) throws Exception {
		try {
			OpenAPI openApi = new OpenAPIV3Parser().read(file.getAbsolutePath());
			return new ApiModel(openApi);
		} catch (NullPointerException cause) {
			logger.error("Fatal error:", cause);
			return null;
		} catch (Throwable cause) {
			Swagger swagger = new SwaggerParser().read(file.getAbsolutePath());
			return new ApiModel(swagger);
		}
	}
	
}
