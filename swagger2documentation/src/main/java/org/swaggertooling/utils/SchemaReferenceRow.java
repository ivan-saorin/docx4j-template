package org.swaggertooling.utils;

public class SchemaReferenceRow extends SchemaRow {

	private String id;
	private String type;
	private String description;
	private long tableId;
	private String minItems;
	private String maxItems;
	//private boolean isArray = false;

	public SchemaReferenceRow(AdditionalInfo info) {
		super();
		this.tableId = info.getTableId();
		this.id = info.getPropertyName();
		this.type = info.getType();
		this.description = info.getDescription();
		this.minItems = info.getMinItems();
		this.maxItems = info.getMaxItems();
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
			.append("*")
			.append(id)
			.append("*");

		builder.append(" | ");
		
		builder.append("<<");
		builder.append(tableId);
		builder.append(',');
		builder.append(type);
		boolean min = (minItems != null) && (minItems.length() > 0);
		boolean max = (maxItems != null) && (maxItems.length() > 0);
		if (min || max) {
			builder.append(" [");
			if (min) {
				builder.append(minItems);
				if (max) {
					builder.append("..");
				}
			}
			if (max) {
				builder.append(maxItems);
			}
			builder.append("]");
		}
		
		builder.append(">>");
		builder.append(" | ");
		
		builder.append(description);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SchemaReferenceRow)) {
			return false;
		}
		SchemaReferenceRow other = (SchemaReferenceRow) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	
}
