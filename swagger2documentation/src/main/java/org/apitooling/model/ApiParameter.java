package org.apitooling.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.parameters.Parameter;
import v2.io.swagger.models.ModelImpl;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.properties.Property;
import v2.io.swagger.models.properties.RefProperty;

public class ApiParameter extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiParameter.class);
	
	public int idx;
	private String ref;
	private String name;
	private ApiParameterType type;
	private ApiField dataType;
	private String description;
	private String pattern;
	private String defaultValue;
	private ArrayList<String> enumValues;
	private ApiContent content; 
	private String example;
	private HashMap<String, ApiExample> examples = new HashMap<String, ApiExample>();	
	private boolean required = false;
	private boolean readOnly = false;
	private boolean allowEmptyValues = false;
	private boolean allowReserved = false;
	private boolean deprecated = false;
	
	public ApiParameter(ApiModel parent, int idx, ApiType modelVersion, OpenAPI model, ApiParameterType type, Parameter param) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, param.getClass().getName(), param.getExtensions());
		this.idx = idx;
		this.type = type;
		describeModel(modelVersion, model, param);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, Parameter param) {		
		this.describeExtensions(param.getExtensions());
		if (param.get$ref() != null) {
			this.ref = param.get$ref();
		}

		this.name = param.getName();
		if (param.getSchema() != null) {
			this.dataType = new ApiField(this.getModel(), modelVersion, model, this.name, param.getSchema());
		}
		this.description = param.getDescription();
		this.required = param.getRequired();

		this.readOnly = false;

		if (param.getExample() != null) {
			this.example = param.getExample().toString();
		}
		
		if (param.getExamples() != null) {
			Set<String> keys = param.getExamples().keySet();
			for (String key : keys) {
				this.getStats().incExamples();
				this.examples.put(key, new ApiExample(this.getModel(), modelVersion, model, key, param.getExamples().get(key)));
			}
		}
		
		if (param.getAllowEmptyValue() != null) {
			this.allowEmptyValues = param.getAllowEmptyValue();
		}

		if (param.getAllowReserved() != null) {
			this.allowReserved = param.getAllowReserved();
		}

		if (param.getDeprecated() != null) {
			this.deprecated = param.getDeprecated();
		}

		if (param.getContent() != null) {
			this.content = new ApiContent(this.getModel(), modelVersion, model, this.name, param.getContent());
		}

	}

	public ApiParameter(ApiModel parent, int idx, ApiType modelVersion, Swagger model, ApiParameterType type, v2.io.swagger.models.parameters.Parameter param) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, param.getClass().getName(), param.getVendorExtensions());
		this.idx = idx;
		this.type = type;
		describeModel(modelVersion, model, param);
	}

	private void describeModel(ApiType modelVersion, Swagger model, v2.io.swagger.models.parameters.Parameter param) {
		
		this.name = param.getName();
		this.description = param.getDescription();
		this.describeExtensions(param.getVendorExtensions());
		
		this.required = param.getRequired();

		if (param instanceof v2.io.swagger.models.parameters.PathParameter) {
			v2.io.swagger.models.parameters.PathParameter p = (v2.io.swagger.models.parameters.PathParameter) param;			
			this.dataType = new ApiField(this.getModel(), modelVersion, model, this.name, p, param.getRequired());
		}

		if (param instanceof v2.io.swagger.models.parameters.HeaderParameter) {
			v2.io.swagger.models.parameters.HeaderParameter p = (v2.io.swagger.models.parameters.HeaderParameter) param;
			this.dataType = new ApiField(this.getModel(), modelVersion, model, this.name, p, param.getRequired());
		}

		if (param instanceof v2.io.swagger.models.parameters.QueryParameter) {
			v2.io.swagger.models.parameters.QueryParameter p = (v2.io.swagger.models.parameters.QueryParameter) param;
			this.dataType = new ApiField(this.getModel(), modelVersion, model, this.name, p, param.getRequired());
		}

		if (param.isReadOnly() != null) {
			this.readOnly = param.isReadOnly();
		}

		if (param.getAllowEmptyValue() != null) {
			this.allowEmptyValues = param.getAllowEmptyValue();
		}
		
		this.allowReserved = false;
		this.deprecated = false;
		
	}

	public ApiParameter(ApiModel parent, int i, ApiType modelVersion, OpenAPI model, String key, ApiParameterType type, Header header) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, header.getClass().getName(), header.getExtensions());
		describeModel(modelVersion, model, key, type, header);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, String key, ApiParameterType type, Header param) {
		this.name = key;		

		if (param.get$ref() != null) {
			this.ref = param.get$ref();
		}
		if (param.getSchema() != null) {
			this.dataType = new ApiField(this.getModel(), modelVersion, model, this.name, param.getSchema());
		}
		if (param.getDescription() != null) {
			this.description = param.getDescription();
		}
		
		this.describeExtensions(param.getExtensions());
		
		if (param.getRequired() != null) {
			this.required = param.getRequired();
		}

		this.readOnly = false;

		if (param.getExample() != null) {
			this.example = param.getExample().toString();
		}
		
		if (param.getExamples() != null) {
			Set<String> keys = param.getExamples().keySet();
			for (String ekey : keys) {
				this.getStats().incExamples();
				this.examples.put(key, new ApiExample(this.getModel(), modelVersion, model, ekey, param.getExamples().get(ekey)));
			}
		}
		
		if (param.getDeprecated() != null) {
			this.deprecated = param.getDeprecated();
		}

		if (param.getContent() != null) {
			this.content = new ApiContent(this.getModel(), modelVersion, model, this.name, param.getContent());
		}

		//if (logger.isInfoEnabled()) logger.info("    {} {}[{}]: {} - {}", (this.deprecated) ? "deprecated" : "", this.name, this.type, this.dataType, this.description);
	}
	
	public ApiParameter(ApiModel parent, int i, ApiType modelVersion, Swagger model, String key, ApiParameterType type, Property header) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, header.getClass().getName(), header.getVendorExtensions());
		describeModel(modelVersion, model, key, type, header);
	}
	
	private void describeModel(ApiType modelVersion, Swagger model, String key, ApiParameterType type, Property header) {

		if (header.getName() != null) {
			this.name = header.getName();
		}
		else {
			this.name = key;
		}
		
		if (header instanceof RefProperty) {
			RefProperty ref = (RefProperty) header;
			String reference = ref.get$ref();
			String simpleType = ref.getSimpleRef();
			v2.io.swagger.models.Model referred = getReferredProperty(model, type, ref.get$ref());
			describeModel(modelVersion, model, key, type, (ModelImpl) referred);			
		}
		else {
			this.dataType = new ApiField(this.getModel(), modelVersion, model, key, header, this.required);			
		}

		if (header.getDescription() != null) {
			this.description = header.getDescription();
		}
		
		this.describeExtensions(header.getVendorExtensions());
		
		this.required = header.getRequired();
		
		if (header.getPosition() != null) {
			this.idx = header.getPosition();
		}
		if (header.getReadOnly() != null) {
			this.readOnly = header.getReadOnly();
		}
		this.required = header.getRequired();
		
		if (header.getAllowEmptyValue() != null) {
			this.allowEmptyValues = header.getAllowEmptyValue();
		}
	}


	private v2.io.swagger.models.Model getReferredProperty(Swagger model, ApiParameterType type, String ref) {
		ref = ApiField.typeName(ref);
		Map<String, v2.io.swagger.models.Model> definitions = model.getDefinitions();
		v2.io.swagger.models.Model m = null;
		if (definitions.containsKey(ref)) {
			m = definitions.get(ref);
		}
		return m;
	}

	private void describeModel(ApiType modelVersion, Swagger model, String key, ApiParameterType type,
			v2.io.swagger.models.ModelImpl referred) {
		if (referred.getName() != null) {
			this.name = referred.getName();
		}
		else {
			this.name = key;
		}
			
		this.describeExtensions(referred.getVendorExtensions());
		
		this.dataType = new ApiField(this.getModel(), modelVersion, model, key, referred);
		
		if (referred.getAllowEmptyValue() != null) {
			this.allowEmptyValues = referred.getAllowEmptyValue();
		}
		if (referred.getDescription() != null) {
			this.description = referred.getDescription();
		}
	}	
	public int getIndex() {
		return idx;
	}
	
	public String getName() {
		return name;
	}

	public ApiParameterType getType() {
		return type;
	}

	public ApiField getDataType() {
		return dataType;
	}

	public String getDescription() {
		return description;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getPattern() {
		return pattern;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public List<String> getEnumValues() {
		return enumValues;
	}

	public ApiContent getContent() {
		return content;
	}

	public String getExample() {
		return example;
	}

	public HashMap<String, ApiExample> getExamples() {
		return examples;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public boolean isAllowEmptyValues() {
		return allowEmptyValues;
	}

	public boolean isAllowReserved() {
		return allowReserved;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allowEmptyValues ? 1231 : 1237);
		result = prime * result + (allowReserved ? 1231 : 1237);
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + (deprecated ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((enumValues == null) ? 0 : enumValues.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		result = prime * result + (readOnly ? 1231 : 1237);
		result = prime * result + (required ? 1231 : 1237);
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ApiParameter other = (ApiParameter) obj;
		if (allowEmptyValues != other.allowEmptyValues) {
			return false;
		}
		if (allowReserved != other.allowReserved) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (dataType == null) {
			if (other.dataType != null) {
				return false;
			}
		} else if (!dataType.toString().equals(other.dataType.toString())) {
			return false;
		}
		if (defaultValue == null) {
			if (other.defaultValue != null) {
				return false;
			}
		} else if (!defaultValue.equals(other.defaultValue)) {
			return false;
		}
		if (deprecated != other.deprecated) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (enumValues == null) {
			if (other.enumValues != null) {
				return false;
			}
		} else if (!enumValues.equals(other.enumValues)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (pattern == null) {
			if (other.pattern != null) {
				return false;
			}
		} else if (!pattern.equals(other.pattern)) {
			return false;
		}
		if (readOnly != other.readOnly) {
			return false;
		}
		if (required != other.required) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
	
}
