package org.apitooling.utils;

import static org.apitooling.utils.Constants.EOL;

import com.fasterxml.jackson.databind.JsonNode;

public class ReqParamsRow {

	private String id;
	private String dataType;
	private String position;
	private String description;
	private String example;
	private boolean eg = true;

	public ReqParamsRow(String id, String dataType, String position, JsonNode example, String description) {
		this.id = id;
		this.dataType = dataType;
		this.position = position;
		this.description = description;
		if (example != null) {
			this.example = example.asText();
		}
	}

	public ReqParamsRow(String id, String dataType, String position, String example, String description) {
		this.id = id;
		this.dataType = dataType;
		this.position = position;
		this.description = description;
		this.example = example;
		this.eg = false;
	}
	
	public String getId() {
		return id;
	}

	public String getDataType() {
		return dataType;
	}

	public String getPosition() {
		return position;
	}

	public String getDescription() {
		return description;
	}

	public String getExample() {
		return example;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
			.append("*")
			.append(id)
			.append("*");
		
		if (example != null) {
			builder.append(" +").append(EOL);
			if (eg) {
				builder.append("E.g.: ");
			}
			builder.append("_").append(example).append("_");
		}
		
		builder
			.append(" | ")
			.append(dataType)
			.append(" | ")
			.append(position)
			.append(" | ")
			.append(description);
		return builder.toString();
	}

}
