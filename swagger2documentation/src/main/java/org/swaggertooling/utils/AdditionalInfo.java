package org.swaggertooling.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class AdditionalInfo {

	private long tableId;
	private String propertyName;
	private String type;
	private String description;
	private JsonNode node;
	private boolean isArray;
	private String minItems;
	private String maxItems;
	
	public AdditionalInfo(long tableId, String propertyName, String type, String description, String minItems, String maxItems, JsonNode node, boolean isArray) {
		this.tableId = tableId;
		this.propertyName = propertyName;
		this.type = type;
		this.node = node;
		this.description = description;
		this.minItems = minItems;
		this.maxItems = maxItems;
		this.isArray = isArray;		
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getType() {
		return type;
	}

	public JsonNode getNode() {
		return node;
	}
	
	public boolean isArray() {
		return isArray;
	}

	public String getDescription() {
		return description;
	}

	public long getTableId() {
		return tableId;
	}

	public String getMinItems() {
		return minItems;
	}

	public String getMaxItems() {
		return maxItems;
	}
}
