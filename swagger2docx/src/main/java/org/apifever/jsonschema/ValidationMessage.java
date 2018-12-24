package org.apifever.jsonschema;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.util.AsJson;

public class ValidationMessage  {

	public enum ErrorKind {
		UNKNOWN, GENERIC, REQUIRED, DATATYPE, FORMAT, MIN_MAX_LENGTH, ADDITIONAL_PROPERTIES
	}
	
	private ProcessingMessage delegate;
	private ValidationMessageType type = ValidationMessageType.NONE;
	
	public ValidationMessage(ProcessingMessage message) {
		this.delegate = message;
		switch (delegate.getLogLevel()) {
		case NONE: type = ValidationMessageType.NONE; break;
		case DEBUG: type = ValidationMessageType.DEBUG; break;
		case ERROR: type = ValidationMessageType.ERROR; break;
		case FATAL: type = ValidationMessageType.FATAL; break;
		case INFO: type = ValidationMessageType.INFO; break;
		case WARNING: type = ValidationMessageType.WARNING; break;
		}
		
	}
	
	public ValidationMessageType getType() {
		return type;
	}

	public ErrorKind getErrorKind() {
		JsonNode keywordNode = delegate.asJson().get("keyword");
		String keyword = (keywordNode != null) ? keywordNode.asText().trim().toLowerCase() : "";
		switch (keyword) {
		case "required":
			return ErrorKind.REQUIRED;
		case "type":
			return ErrorKind.DATATYPE;
		case "format":
			return ErrorKind.FORMAT;
		case "minlength":
		case "maxlength":
			return ErrorKind.MIN_MAX_LENGTH;
		case "additionalProperties":
			return ErrorKind.ADDITIONAL_PROPERTIES;
		default:
			return ErrorKind.UNKNOWN;
		}
	}

	@Override
	public String toString() {
		return getFieldAsString("message");
	}
	
	public String getField() {
		String pointer = getFieldAsString("schema.pointer");
		String properties = "/properties/";
	    return pointer.startsWith(properties) ? pointer.substring(properties.length()) : pointer;
	}

	public List<String> getMissing() {
		return getFieldAsList("missing");
	}
	
	public List<String> getExpected() {
		return getFieldAsList("expected");
	}

	public String getFound() {
		return getFieldAsString("found");
	}

	private String getFieldAsString(String fieldname) {
		JsonNode node = getField(fieldname);		
		String text = (node != null) ? node.asText() : "";
		return text;
	}

	private JsonNode getField(String fieldname) {
		JsonNode node = delegate.asJson();
		String[] fieldnames = fieldname.split("\\.");
		for (String fn : fieldnames) {
			if ("".equals(fn)) {
				continue;
			}
			node = node.get(fn);
		}
		return node;
	}

	private List<String> getFieldAsList(String fieldname) {
		List<String> result = new ArrayList<>();
		JsonNode node = getField(fieldname);
		if (node == null) {
			return result;
		}
	    if (node instanceof ArrayNode) {	    	
	    	for (JsonNode item : node) {
	    		result.add(item.asText());
			}
	    }
	    else if (node instanceof TextNode) {
	    	result.add(node.asText());
	    }
		return result;
	}

	
	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof ValidationMessage) {
			return delegate.equals(((ValidationMessage)obj).delegate);
		}
		else {
			return delegate.equals(obj);
		}
	}

	public ProcessingMessage setMessage(String message) {
		return delegate.setMessage(message);
	}

	public ProcessingMessage put(String key, JsonNode value) {
		return delegate.put(key, value);
	}

	public ProcessingMessage putArgument(String key, JsonNode value) {
		return delegate.putArgument(key, value);
	}

	public ProcessingMessage put(String key, AsJson asJson) {
		return delegate.put(key, asJson);
	}

	public ProcessingMessage putArgument(String key, AsJson asJson) {
		return delegate.putArgument(key, asJson);
	}

	public ProcessingMessage put(String key, String value) {
		return delegate.put(key, value);
	}

	public ProcessingMessage put(String key, int value) {
		return delegate.put(key, value);
	}

	public ProcessingMessage putArgument(String key, int value) {
		return delegate.putArgument(key, value);
	}

	public <T> ProcessingMessage put(String key, T value) {
		return delegate.put(key, value);
	}

	public <T> ProcessingMessage putArgument(String key, T value) {
		return delegate.putArgument(key, value);
	}

	public <T> ProcessingMessage put(String key, Iterable<T> values) {
		return delegate.put(key, values);
	}

	public <T> ProcessingMessage putArgument(String key, Iterable<T> values) {
		return delegate.putArgument(key, values);
	}

	public JsonNode asJson() {
		return delegate.asJson();
	}

	public ProcessingException asException() {
		return delegate.asException();
	}

}
