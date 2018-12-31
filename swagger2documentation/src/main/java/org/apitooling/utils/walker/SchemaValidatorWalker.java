package org.apitooling.utils.walker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apitooling.exceptions.ValidationException;
import org.apitooling.exceptions.WebApiException;
import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.jsonschema.JsonSchemaValidator;
import org.apitooling.jsonschema.ValidationMessage;
import org.apitooling.jsonschema.ValidationMessageType;
import org.apitooling.jsonschema.ValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;

public class SchemaValidatorWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(SchemaValidatorWalker.class);
	
	private JsonSchemaValidator schemaValidator = new JsonSchemaValidator();
	
	public SchemaValidatorWalker(File directory) {
		super(directory, true);
	}

	@Override
	public void process(File file) throws WebApiException {
		
		if (!file.getName().startsWith(".") && file.getName().endsWith(".json")) {
			try {
				logger.info("validating: " + file.getName());
				final JsonNode fstabSchema = JsonLoader.fromFile(file);
				// https://github.com/fge/json-schema-validator/blob/fd781ef26318ce4b7bde294307dd32d6c2e8da03/src/main/java/com/github/fge/jsonschema/examples/Example10.java				
				//final JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(fstabSchema);
				ValidationReport validationResult = schemaValidator.validateSchema(fstabSchema);
				if (validationResult.isSuccess()) {
					return;
				}
				
				List<ValidationMessage> errors = validationResult.filter(ValidationMessageType.FATAL);
				errors.addAll(validationResult.filter(ValidationMessageType.ERROR));
				throw new ValidationException(errors);
			} catch (IOException e) {
				throw new WebApiRuntimeException(file.getName() + " > " + e.getMessage(), e);
			}
		}		
	}

}
