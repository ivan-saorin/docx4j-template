package org.apifever.jsonschema;

import java.io.IOException;

import org.apifever.exceptions.WebApiRuntimeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.processors.syntax.SyntaxValidator;

public class JsonSchemaValidator {

	public static final String JSON_V4_SCHEMA_IDENTIFIER = "http://json-schema.org/draft-04/schema#";
	public static final String JSON_SCHEMA_IDENTIFIER_ELEMENT = "$schema";
	
	public ValidationReport validateSchema(JsonNode schema) {
		SyntaxValidator syntaxValidator = JsonSchemaFactory.byDefault().getSyntaxValidator();
		ProcessingReport validatorResult = syntaxValidator.validateSchema(schema);
		return new ValidationReport(validatorResult);
	}
	
	public ValidationReport validateJson(String schemaText, String jsonText) throws ProcessingException {
		final JsonNode schemaNode = getJsonNode(schemaText);
		ValidationReport schemaValidationResult = validateSchema(schemaNode);
		if (!schemaValidationResult.isSuccess()) {
			return schemaValidationResult;
		}
		final JsonSchema schema = getSchemaNode(schemaNode);
		final JsonNode jsonNode = getJsonNode(jsonText);
		return validate(schema, jsonNode);
	}
	
	public ValidationReport validateJson(JsonNode schemaNode, String jsonText) throws ProcessingException {
		final JsonSchema schema = getSchemaNode(schemaNode);
		final JsonNode jsonNode = getJsonNode(jsonText);
		return validate(schema, jsonNode);
	}

	public ValidationReport validateJson(JsonNode schemaNode, JsonNode jsonNode) throws ProcessingException {
		final JsonSchema schema = getSchemaNode(schemaNode);
		return validate(schema, jsonNode);
	}
	
	private JsonNode getJsonNode(String jsonText) {
		try {
			return JsonLoader.fromString(jsonText);
		} catch (IOException cause) {
			throw new WebApiRuntimeException(cause);
		}
	}

	/*
	private JsonSchema getSchemaNodeFromText(String schemaText) {
		try {
			final JsonNode schemaNode =  getJsonNode(schemaText);
			return getSchemaNode(schemaNode);
		} catch (ProcessingException cause) {
			throw new WebApiRuntimeException(cause);
		}
	}
	*/

	private JsonSchema getSchemaNode(JsonNode jsonNode) throws ProcessingException {
		final JsonNode schemaIdentifier = jsonNode.get(JSON_SCHEMA_IDENTIFIER_ELEMENT);
		if (null == schemaIdentifier) {
			((ObjectNode) jsonNode).put(JSON_SCHEMA_IDENTIFIER_ELEMENT, JSON_V4_SCHEMA_IDENTIFIER);
		}
		final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		return factory.getJsonSchema(jsonNode);
	}

	private ValidationReport validate(JsonSchema jsonSchemaNode, JsonNode jsonNode) throws ProcessingException {
		ProcessingReport report = jsonSchemaNode.validate(jsonNode);
		return new ValidationReport(report);
	}
	
	/*
	private ValidationReport processReport(ProcessingReport report) {
		ValidationReport result = new ValidationReport(report);		
		System.err.printf("report %s %n", report);
		if (report.isSuccess()) {
			return result;
		}

		List<String> messageList = new ArrayList<>();
		for (ProcessingMessage processingMessage : report) {
			//System.err.printf(" >>> %s %n    %s %n", processingMessage.asJson(), processingMessage.getMessage());
			String field = "";
		    JsonNode j = processingMessage.asJson();
		    
		    if (!isErrorLevel(j)) {
		    	//System.err.printf(" NOT ERROR   %s %n", j);
		    	continue;
		    }
		    JsonNode keywordNode = j.get("keyword");
		    if (keywordNode == null) {
		    	continue;
		    }
		    Type type = (keywordNode == null ? Type.UNKNOWN : getErrorType(keywordNode.asText()));
		    Object expected = null;
		    JsonNode expectedNode = j.get("expected");
		    if (expectedNode instanceof ArrayNode) {
		    	List<String> expectedList = new ArrayList<String>();
		    	for (JsonNode ex : expectedNode) {
		    		expectedList.add(ex.asText());
				}
		    	expected = expectedList;
		    }
		    JsonNode actualWrongValue = j.get("found");
			//System.err.printf(" >>> type=%s expectedNode=%s expected=%s %n", type, expectedNode, expected);
		    if (keywordNode != null && "required".equals(keywordNode.asText())) {
		    	JsonNode missing = j.get("missing");
		    	//System.err.printf(" >>> REQUIRED MISSING %s %s %n", missing.getClass().getName(), missing);
		    	if (missing instanceof ArrayNode) {
		    		for (JsonNode f : missing) {
		    			//System.err.printf("     MISSING field %s %s %n", f.getClass().getName(), f);
		    			field = f.asText();
		    			JsonSchemaValidationError error = new JsonSchemaValidationError(field, type);
		    		    error.setActualWrongValue(actualWrongValue);
		    			result.addError(error);
					}
		    	}
		    } else {
		    	JsonNode schema = j.get("schema");
		    	//System.err.printf("#### schema %s %n", schema);
		    	JsonNode pointer = schema.get("pointer");
		    	if (pointer != null) {
			    	field = (pointer.asText().startsWith("/properties/")) ? pointer.asText().substring("/properties/".length()) : pointer.asText();
		    	}
    			JsonSchemaValidationError error = new JsonSchemaValidationError(field, type);
    			error.setExpected(expected);
    			error.setActualWrongValue(actualWrongValue);
    			result.addError(error);
		    }
		    String fieldJsonSchemaPath = j.get("schema").get("pointer").toString();
		    String validationMessage = j.get("message").toString();
		    messageList.add(String.format("field: %s , validationMessage: %s",fieldJsonSchemaPath, validationMessage));
		    //System.err.printf(" >>> field: %s , validationMessage: %s %n",fieldJsonSchemaPath, validationMessage);
		}
		return result;
	}
	*/
}
