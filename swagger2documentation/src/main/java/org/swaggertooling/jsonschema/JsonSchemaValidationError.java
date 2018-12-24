package org.swaggertooling.jsonschema;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonSchemaValidationError {

	public enum Type {
		REQUIRED, DATATYPE, FORMAT, MIN_MAX_LENGTH, UNKNOWN, ADDITIONAL_PROPERTIES
	}
	
	private final Type type;
	private final String field;
	private Object expected;
	private JsonNode actual; 
	
	public JsonSchemaValidationError(String field, Type type) {
		this.field = field;
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	public String getField() {
		return field;
	}
	public void setExpected(Object expected) {
		this.expected = expected;
	}
	public Object getExpected() {
		return expected;
	}
	public String humanReadable() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("The field [")
			.append(field)
			.append("] is ")
			.append(type);			
		if (expected != null) {
			sb.append("; Expected: ").append(expected);
		}
		if (actual != null) {
			sb.append("; Actual: ").append(actual);
		}
		sb.append(".");
		return sb.toString();
	}

	@Override
	public String toString() {
		return "JsonSchemaValidationError [type=" + type + ", field=" + field + ", expected=" + expected + "]";
	}
	
	public JsonNode getActualWrongValue() {
		return actual;
	}

	public void setActualWrongValue(JsonNode actual) {
		this.actual = actual;
	}
	
}
