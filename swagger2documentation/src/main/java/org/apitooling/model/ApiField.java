package org.apitooling.model;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.parameters.HeaderParameter;
import v2.io.swagger.models.parameters.PathParameter;
import v2.io.swagger.models.parameters.QueryParameter;
import v2.io.swagger.models.properties.ArrayProperty;
import v2.io.swagger.models.properties.Property;
import v2.io.swagger.models.properties.RefProperty;

public class ApiField extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiField.class);
	
	private String name;
	private String type;
	private String format;
	private String pattern;
	private String example;
	private HashMap<String, Example> examples = new HashMap<String, Example>(); 
	private int minLength = -1;
	private int maxLength = -1;
	private int minItems = -1;
	private int maxItems = -1;
	private BigDecimal minimum;
	private BigDecimal maximum;
	private Number multipleOf;
	private boolean array;
	private String itemsType;
	private String itemsFormat;
	private int itemsMinLength = -1;
	private int itemsMaxLength = -1;
	private BigDecimal itemsMinimum;
	private BigDecimal itemsMaximum;
	private Number itemsMultipleOf;
	private boolean itemsReference;
	private String itemsRef;
	
	private boolean reference;
	private String ref;
	
	public ApiField(ApiType modelVersion, Swagger model, String name, PathParameter param) {
		super();
		describeModel(model, name, param);
	}

	private void describeModel(Swagger model, String name, PathParameter param) {
		/*
		if (logger.isInfoEnabled()) logger.info("        {}> param.getType: {}", name, param.getType());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getFormat: {}", name, param.getFormat());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getPattern: {}", name, param.getPattern());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getExample: {}", name, param.getExample());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinLength: {}", name, param.getMinLength());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaxLength: {}", name, param.getMaxLength());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinItems: {}", name, param.getMinItems());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaxItems: {}", name, param.getMaxItems());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinimum: {}", name, param.getMinimum());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaximum: {}", name, param.getMaximum());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMultipleOf: {}", name, param.getMultipleOf());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getItems: {}", name, param.getItems());
		*/		
		this.describeModel(model, name, 
				param.getType(), param.getFormat(), param.getPattern(), param.getExample(), 
				(param.getMinLength() != null) ? param.getMinLength() : -1, 
				(param.getMaxLength() != null) ? param.getMaxLength() : -1,
				(param.getMinItems() != null) ? param.getMinItems() : -1,
				(param.getMaxItems() != null) ? param.getMaxItems() : -1,
				(param.getMinimum() != null) ? param.getMinimum() : new BigDecimal(0),
				(param.getMaximum() != null) ? param.getMaximum() : new BigDecimal(0),
				(param.getMultipleOf() != null) ? param.getMultipleOf() : new Float(0.0),
				param.getItems());
	}

	public ApiField(ApiType modelVersion, Swagger model, String name, HeaderParameter param) {
		super();
		describeModel(model, name, param);
	}

	private void describeModel(Swagger model, String name, HeaderParameter param) {
		/*
		if (logger.isInfoEnabled()) logger.info("        {}> param.getType: {}", name, param.getType());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getFormat: {}", name, param.getFormat());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getPattern: {}", name, param.getPattern());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getExample: {}", name, param.getExample());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinLength: {}", name, param.getMinLength());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaxLength: {}", name, param.getMaxLength());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinItems: {}", name, param.getMinItems());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaxItems: {}", name, param.getMaxItems());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinimum: {}", name, param.getMinimum());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaximum: {}", name, param.getMaximum());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMultipleOf: {}", name, param.getMultipleOf());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getItems: {}", name, param.getItems());
		*/		
		this.describeModel(model, name, 
				param.getType(), param.getFormat(), param.getPattern(), param.getExample(), 
				(param.getMinLength() != null) ? param.getMinLength() : -1, 
				(param.getMaxLength() != null) ? param.getMaxLength() : -1,
				(param.getMinItems() != null) ? param.getMinItems() : -1,
				(param.getMaxItems() != null) ? param.getMaxItems() : -1,
				(param.getMinimum() != null) ? param.getMinimum() : new BigDecimal(0),
				(param.getMaximum() != null) ? param.getMaximum() : new BigDecimal(0),
				(param.getMultipleOf() != null) ? param.getMultipleOf() : new Float(0.0),
				param.getItems());
	}
	
	public ApiField(ApiType modelVersion, Swagger model, String name, QueryParameter param) {
		super();
		describeModel(model, name, param);
	}

	private void describeModel(Swagger model, String name, QueryParameter param) {
		/*
		if (logger.isInfoEnabled()) logger.info("        {}> param.getType: {}", name, param.getType());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getFormat: {}", name, param.getFormat());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getPattern: {}", name, param.getPattern());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getExample: {}", name, param.getExample());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinLength: {}", name, param.getMinLength());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaxLength: {}", name, param.getMaxLength());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinItems: {}", name, param.getMinItems());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaxItems: {}", name, param.getMaxItems());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMinimum: {}", name, param.getMinimum());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMaximum: {}", name, param.getMaximum());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getMultipleOf: {}", name, param.getMultipleOf());
		if (logger.isInfoEnabled()) logger.info("        {}> param.getItems: {}", name, param.getItems());
		*/
		this.describeModel(model, name, 
				param.getType(), param.getFormat(), param.getPattern(), param.getExample(), 
				(param.getMinLength() != null) ? param.getMinLength() : -1, 
				(param.getMaxLength() != null) ? param.getMaxLength() : -1,
				(param.getMinItems() != null) ? param.getMinItems() : -1,
				(param.getMaxItems() != null) ? param.getMaxItems() : -1,
				(param.getMinimum() != null) ? param.getMinimum() : new BigDecimal(0),
				(param.getMaximum() != null) ? param.getMaximum() : new BigDecimal(0),
				(param.getMultipleOf() != null) ? param.getMultipleOf() : new Float(0.0),
				param.getItems());
	}
	
	private void describeModel(Swagger model, String name, String type, String format, String pattern, Object example, 
			int minLength, int maxLength, int minItems, int maxItems, BigDecimal minimum, BigDecimal maximum, Number multipleOf, 
			Property items) {
		this.name = name;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.minItems = minItems;
		this.maxItems = maxItems;
		this.minimum = minimum;
		this.maximum  = maximum;
		this.multipleOf = multipleOf;
		this.array = false;		
		if (type != null) {
			this.type = type;
			if (type.equalsIgnoreCase("array") && (items != null)) {
				this.array = true;
				this.itemsType = items.getType();
				this.itemsFormat = items.getFormat();
			}				
		}
		if (format != null) {
			this.format = format;
		}
		if (pattern != null) {
			this.pattern = pattern;
		}			
		if (example != null) {
			this.example = example.toString();
			if (logger.isInfoEnabled()) logger.info("        example {}", this.example);
		}			
		
		//if (logger.isInfoEnabled()) logger.info("        {}> type: {}, format: {}", this.name, this.type, this.format);
	}

	public ApiField(ApiType modelVersion, OpenAPI model, String name, Schema<?> schema) {
		super();
		describeSchemaField(modelVersion, model, name, schema);
	}
	
	private void describeSchemaField(ApiType modelVersion, OpenAPI model, String name, Schema<?> schema) {
		this.name = name;
		this.array = false;
		if (schema != null) {
			if (schema.getType() != null) {
				this.type = schema.getType();
				if (type.equalsIgnoreCase("array") && (schema instanceof ArraySchema)) {
					this.array = true;
					ArraySchema array = (ArraySchema) schema;
					Schema<?> items = array.getItems();
					this.itemsType = items.getType();
					if ((this.itemsType != null) && (this.itemsType.equalsIgnoreCase("ref"))) {
						this.itemsType = items.get$ref();
					}
					else {
						if (items.getMinLength() != null) {
							this.itemsMinLength = items.getMinLength();
						}
						if (items.getMaxLength() != null) {
							this.itemsMaxLength = items.getMaxLength();
						}
						if (items.getMinimum() != null) {
							this.itemsMinimum = items.getMinimum();
						}
						if (items.getMaximum() != null) {
							this.itemsMaximum  = items.getMaximum();
						}
						if (items.getMultipleOf() != null) {
							this.itemsMultipleOf = items.getMultipleOf();
						}					
						if (items.getFormat() != null) {
							this.itemsFormat = items.getFormat();
						}
						if (items.get$ref() != null) {
							this.itemsRef = items.get$ref();
						}			
					}
				}				
			}
			if (schema.getMinLength() != null) {
				this.minLength = schema.getMinLength();
			}
			if (schema.getMaxLength() != null) {
				this.maxLength = schema.getMaxLength();
			}
			if (schema.getMinItems() != null) {
				this.minItems = schema.getMinItems();
			}
			if (schema.getMaxItems() != null) {
				this.maxItems = schema.getMaxItems();
			}
			if (schema.getMinimum() != null) {
				this.minimum = schema.getMinimum();
			}
			if (schema.getMaximum() != null) {
				this.maximum  = schema.getMaximum();
			}
			if (schema.getMultipleOf() != null) {
				this.multipleOf = schema.getMultipleOf();
			}
			if (example != null) {
				this.example = example.toString();
			}
			if (examples != null) {
				this.examples.putAll(examples);
			}			
			if (schema.getFormat() != null) {
				this.format = schema.getFormat();
			}
			if (schema.get$ref() != null) {
				this.ref = schema.get$ref();
			}			
			
		}
	}

	public ApiField(ApiType modelVersion, Swagger model, Property property) {
		super();
		describeModel(modelVersion, model, property);
	}


	private void describeModel(ApiType modelVersion, Swagger model, Property p) {
		String pRefType = null;
		String iRefType = null;
		
		if (p instanceof RefProperty) {
			RefProperty rp = (RefProperty) p;
			pRefType = rp.getSimpleRef();
		}
		
		ArrayProperty ap = null;
		if (p instanceof ArrayProperty) {
			this.array = true;		
			ap = (ArrayProperty) p;
			Property i = ap.getItems();
			if (i instanceof RefProperty) {
				RefProperty irp = (RefProperty) i;
				iRefType = irp.getSimpleRef();
			}			
		}
		
		if (p.getName() != null) {
			this.name = p.getName();
		}
		if ((p.getType() != null) || (pRefType != null)) {
			this.type = (pRefType != null) ? pRefType : p.getType();
		}
		if (p.getFormat() != null) {
			this.format = p.getFormat();
		}
		if (p.getExample() != null) {
			this.example = p.getExample().toString();
		}
		if (ap != null) {
			if (((ap.getItems() != null) && (ap.getItems().getType() != null)) || (iRefType != null)) {
				this.itemsType =  (iRefType != null) ? iRefType : ap.getItems().getType();
			}
			this.itemsMinimum = (ap.getMinItems() != null) ? new BigDecimal(ap.getMinItems()) : new BigDecimal(-1);
			this.itemsMaximum = (ap.getMaxItems() != null) ? new BigDecimal(ap.getMaxItems()) : new BigDecimal(-1);
		}
		
	}

	public ApiField(ApiType modelVersion, Swagger model, String name, RefModel schema) {
		super();
		describeModel(modelVersion, model, name, schema);		
	}

	private void describeModel(ApiType modelVersion, Swagger model, String name, RefModel schema) {		
		if (schema.get$ref() != null) {
			this.ref = schema.get$ref();
		}			
	}

	public ApiField() {
		super();
	}

	public String getName() {
		return name;
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

	public HashMap<String, Example> getExamples() {
		return examples;
	}

	public int getMinLength() {
		return minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public int getMinItems() {
		return minItems;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public BigDecimal getMinimum() {
		return minimum;
	}

	public BigDecimal getMaximum() {
		return maximum;
	}

	public Number getMultipleOf() {
		return multipleOf;
	}

	public boolean isArray() {
		return array;
	}

	public String getItemsType() {
		return itemsType;
	}

	public String getItemsFormat() {
		return itemsFormat;
	}

	public int getItemsMinLength() {
		return itemsMinLength;
	}

	public int getItemsMaxLength() {
		return itemsMaxLength;
	}

	public BigDecimal getItemsMinimum() {
		return itemsMinimum;
	}

	public BigDecimal getItemsMaximum() {
		return itemsMaximum;
	}

	public Number getItemsMultipleOf() {
		return itemsMultipleOf;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.type != null) {
			sb.append(this.type);
			if (this.array) {
				if (this.itemsRef != null) {
					sb.append("[");
					sb.append(ApiField.typeName(this.itemsRef));					
					sb.append("]");
				}
				else {
					sb.append("[").append(this.itemsType);
					if (this.itemsFormat != null) {
						sb.append("<").append(this.itemsFormat).append(">");
					}
					insertMinMax(sb, this.itemsMinLength, this.itemsMaxLength);
					sb.append("]");
					insertMinMax(sb, this.minItems, this.maxItems);
				}
			} else {			
				if (this.format != null) {		
					sb.append("<").append(this.format).append(">");
				}
				if (this.pattern != null) {		
					sb.append("(").append(this.pattern).append(")");
				}
				insertMinMax(sb, this.minLength, this.maxLength);
			}
		}
		else if (this.ref != null) {
			sb.append(ApiField.typeName(this.ref));
		}
		return sb.toString();
	}

	private void insertMinMax(StringBuilder sb, int min, int max) {
		if ((min > -1) || (max > -1)) {
			sb.append("{");
			if (min > -1) {
				sb.append(min);
			}
			sb.append(",");
			if (max > -1) {
				sb.append(max);
			}				
			sb.append("}");
		}
	}
	
	public static String typeName(String ref) {
		String typeName = ref;
		int l = ref.lastIndexOf('/');
		if (++l > -1) {
			typeName = ref.substring(l);
		}
		return typeName;			
	}
}
