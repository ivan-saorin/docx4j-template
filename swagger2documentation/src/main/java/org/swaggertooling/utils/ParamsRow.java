package org.swaggertooling.utils;

import static org.swaggertooling.utils.Constants.EOL;

import com.fasterxml.jackson.databind.JsonNode;

public class ParamsRow extends SchemaRow {

	public enum ParamType {
		TITLE, HEADER, URIPARAMETER, QUERYPARAMETER
	}
	
	private ParamType kind;

	public ParamsRow(ParamType kind, boolean highlight, String id, JsonNode title, JsonNode type, JsonNode format, 
			JsonNode pattern, JsonNode example, JsonNode description, JsonNode defaultNode, JsonNode enumNode, JsonNode minLength,
			JsonNode maxLength) {
		super(highlight, id, title, type, format, pattern, example, description, defaultNode, enumNode, minLength, maxLength);
		this.kind = kind;

	}

	public ParamsRow(String id, String title, String type, String format, String pattern, String example, String description) {
		super(id, title, type, format, pattern, example, description);
		this.kind = ParamType.TITLE;
	}

	public ParamType getKind() {
		return kind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ParamsRow)) {
			return false;
		}
		ParamsRow other = (ParamsRow) obj;
		if (kind != other.kind) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
			.append("*")
			.append(getId())
			.append("*");

		if (getExample() != null) {
			builder.append(" +").append(EOL);
			if (isEg()) {
				builder.append("E.g.: ");
			}
			builder.append("*").append(getExample()).append("*");
		}
		builder.append(" | ");
		
		if (isEg()) {
			builder.append(kind);			
		}
		else {			
			builder.append("Kind");
		}		
		builder.append(" | ");

		builder.append(getType());
		if (getFormat() != null) {
			builder.append(" (").append(getFormat()).append(")");
		}
		if (getPattern() != null) {
			builder.append(" +").append(EOL).append("_").append(getPattern()).append("_");
		}
		builder.append(" | ");
		if (getTitle() != null) {
			builder.append("_").append(getTitle()).append("_").append(" +").append(EOL);
		}
		builder.append(getDescription());
		if (getEnumValue() != null) {
			builder.append(getEnumValue());
		}
		if (getDefaultValue() != null) {
			builder.append(getDefaultValue());
		}
		
		return builder.toString();
	}
	
	public String toHeaderRow() {
		return "|"+toString();
	}

	public String toRow() {
		return "|"+toString();
	}
	
}
