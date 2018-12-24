package org.swaggertooling.utils;

import static org.swaggertooling.utils.Constants.EOL;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;

public class SchemaRow {

	private String id;
	private String title;
	private String type;
	private String format;
	private String pattern;
	private String example;
	private String description;
	private String defaultValue;
	private String enumValue;
	private String minLength;
	private String maxLength;
	
	private boolean eg = true;

	public SchemaRow(boolean highlight, String childName, JsonNode title, JsonNode type, JsonNode format, 
			JsonNode pattern, JsonNode example, JsonNode description, JsonNode defaultNode, JsonNode enumNode, JsonNode minLength,
			JsonNode maxLength) {
		super();
		this.id = childName;

		if (title != null) {
			this.title = "";
			if (highlight)
				this.title += "*";
			this.title += title.asText();
			if (highlight)
				this.title += "*";			
		}

		if (type != null) {
			this.type = type.asText();
		}

		if (format != null) {
			this.format = format.asText();
		}

		if (pattern != null) {
			this.pattern = pattern.asText();
		}

		if (example != null) {
			this.example = example.asText();
		}
		
		if (description != null) {
			this.description = description.asText();
		} else {
			this.description = "";
		}
		
		if (defaultNode != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(" + ").append(EOL).append("*Default value:* ");
			sb.append("_\"").append(defaultNode.asText()).append("\"_ +").append(EOL);
			this.defaultValue = sb.toString();
		}

		if (enumNode != null) {
			if (type == null) {
				this.type = "enum";
			}
			
			if (enumNode.isArray()) {
				StringBuffer sb = new StringBuffer();
				if (this.description.length() > 0) {
					sb.append(" + ").append(EOL).append(" +");
				}
				sb.append(" + ").append(EOL).append("*Enumeration:* + ").append(EOL);
				int m = Math.min(enumNode.size(), 20);
				for (int i = 0; i < m; i++) {
					sb.append("_").append(enumNode.get(i)).append("_ + ").append(EOL);
				}
				if (enumNode.size() > 20) {
					sb.append("_").append("...").append("_ + ").append(EOL);
				}
				//sb.deleteCharAt(sb.length()-1);
				this.enumValue = sb.toString();
			}
			
		}

		if (minLength != null) {
			this.minLength = minLength.asText();
		}

		if (maxLength != null) {
			this.maxLength = maxLength.asText();
		}
		
	}

	public SchemaRow(String childName, String title, String type, String format, String pattern, String example, String description/*, String defaultValue, String enumValue, String minValue, String maxValue*/) {
		super();
		this.id = childName;
		this.title = title;
		this.type = type;
		this.format = format;
		this.pattern = pattern;
		this.example = example;
		this.description = description;
		/*
		this.defaultValue = defaultValue;
		this.enumValue = enumValue;
		this.minLength = minLength;
		this.maxLength = maxLength;
		*/
		this.eg = false;
	}

	protected SchemaRow() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public String getFormat() {
		return format;
	}

	public String getPattern() {
		return pattern;
	}

	public String getExample() {
		return example;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public String getEnumValue() {
		return enumValue;
	}

	public String getMinLength() {
		return minLength;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public boolean isEg() {
		return eg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
			.append("*")
			.append(id)
			.append("*");

		if (!Strings.isNullOrEmpty(example)) {
			builder.append(" +").append(EOL);
			if (eg) {
				builder.append("E.g.: ");
			}
			builder.append("*").append(example).append("*");
		}
		builder.append(" | ");

		builder.append(type);
		boolean min = (minLength != null) && (minLength.length() > 0);
		boolean max = (maxLength != null) && (maxLength.length() > 0);
		if (min || max) {
			builder.append(" [");
			if (min) {
				builder.append(minLength);
				if (max) {
					builder.append("..");
				}
			}
			if (max) {
				builder.append(maxLength);
			}
			builder.append("]");
		}
		if (format != null) {
			builder.append(" (").append(format).append(")");
		}
		if (pattern != null) {
			builder.append(" +").append(EOL).append("_").append(pattern).append("_");
		}
		builder.append(" | ");
		if (title != null) {
			builder.append("_").append(title).append("_");
			if (description != null) {
				builder.append(" + ");
			}
			builder.append(EOL);
			builder.append(description);
		}
		else {
			builder.append(description);
		}
		return builder.toString();
	}

	public String toHeaderRow() {
		return "|"+toString();
	}

	public String toRow() {
		return "|"+toString();
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
		if (!(obj instanceof SchemaRow)) {
			return false;
		}
		SchemaRow other = (SchemaRow) obj;
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
