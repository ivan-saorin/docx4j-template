package org.apitooling.model;

import java.util.LinkedHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.parameters.Parameter;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.properties.Property;

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
	private String enumValue;
	private ApiContent content; 
	private String example;
	private LinkedHashMap<String, ApiExample> examples = new LinkedHashMap<String, ApiExample>();	
	private boolean required = false;
	private boolean readOnly = false;
	private boolean allowEmptyValues = false;
	private boolean allowReserved = false;
	private boolean deprecated = false;
	
	public ApiParameter(int idx, ApiType modelVersion, OpenAPI model, ApiParameterType type, Parameter param) {
		super();
		this.idx = idx;
		this.type = type;
		describeModel(modelVersion, model, param);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, Parameter param) {
		if (param.get$ref() != null) {
			this.ref = param.get$ref();
		}

		this.name = param.getName();
		if (param.getSchema() != null) {
			this.dataType = new ApiField(modelVersion, model, this.name, param.getSchema());
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
				this.examples.put(key, new ApiExample(modelVersion, model, param.getExamples().get(key)));
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
			this.content = new ApiContent(modelVersion, model, this.name, param.getContent());
		}

	}

	public ApiParameter(int idx, ApiType modelVersion, Swagger model, ApiParameterType type, v2.io.swagger.models.parameters.Parameter param) {
		super();
		this.idx = idx;
		this.type = type;
		describeModel(modelVersion, model, param);
	}

	private void describeModel(ApiType modelVersion, Swagger model, v2.io.swagger.models.parameters.Parameter param) {
		
		this.name = param.getName();
		this.description = param.getDescription();
		this.required = param.getRequired();

		if (param instanceof v2.io.swagger.models.parameters.PathParameter) {
			v2.io.swagger.models.parameters.PathParameter p = (v2.io.swagger.models.parameters.PathParameter) param;			
			this.dataType = new ApiField(modelVersion, model, this.name, p);
		}

		if (param instanceof v2.io.swagger.models.parameters.HeaderParameter) {
			v2.io.swagger.models.parameters.HeaderParameter p = (v2.io.swagger.models.parameters.HeaderParameter) param;
			this.dataType = new ApiField(modelVersion, model, this.name, p);
		}

		if (param instanceof v2.io.swagger.models.parameters.QueryParameter) {
			v2.io.swagger.models.parameters.QueryParameter p = (v2.io.swagger.models.parameters.QueryParameter) param;
			this.dataType = new ApiField(modelVersion, model, this.name, p);
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

	public ApiParameter(int i, ApiType modelVersion, OpenAPI model, String key, ApiParameterType type, Header header) {
		super();
		describeModel(modelVersion, model, key, type, header);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, String hkey, ApiParameterType type, Header param) {
		if (param.get$ref() != null) {
			this.ref = param.get$ref();
		}
		if (param.getSchema() != null) {
			this.dataType = new ApiField(modelVersion, model, this.name, param.getSchema());
		}
		if (param.getDescription() != null) {
			this.description = param.getDescription();
		}
		if (param.getRequired() != null) {
			this.required = param.getRequired();
		}

		this.readOnly = false;

		if (param.getExample() != null) {
			this.example = param.getExample().toString();
		}
		
		if (param.getExamples() != null) {
			Set<String> keys = param.getExamples().keySet();
			for (String key : keys) {
				this.examples.put(key, new ApiExample(modelVersion, model, param.getExamples().get(key)));
			}
		}
		
		if (param.getDeprecated() != null) {
			this.deprecated = param.getDeprecated();
		}

		if (param.getContent() != null) {
			this.content = new ApiContent(modelVersion, model, this.name, param.getContent());
		}

		if (logger.isInfoEnabled()) logger.info("    {} {}[{}]: {} - {}", (this.deprecated) ? "deprecated" : "", this.name, this.type, this.dataType, this.description);
	}
	
	public ApiParameter(int i, ApiType modelVersion, Swagger model, String key, ApiParameterType type, Property header) {
		super();
		describeModel(modelVersion, model, key, type, header);
	}
	
	private void describeModel(ApiType modelVersion, Swagger model, String key, ApiParameterType type, Property header) {

		if (header.getName() != null) {
			this.name = header.getName();
		}
		
		this.dataType = new ApiField(modelVersion, model, header);
		
		if (header.getPosition() != null) {
			this.idx = header.getPosition();
		}
		if (header.getReadOnly() != null) {
			this.readOnly = header.getReadOnly();
		}
		this.required = header.getRequired();
		
		//if (header.getAccess() != null) {
		//}
		if (header.getAllowEmptyValue() != null) {
			this.allowEmptyValues = header.getAllowEmptyValue();
		}
		if (header.getDescription() != null) {
			this.description = header.getDescription();
		}
		//if (header.getXml() != null) {			
		//}
		
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

	public String getEnumValue() {
		return enumValue;
	}

	public ApiContent getContent() {
		return content;
	}

	public String getExample() {
		return example;
	}

	public LinkedHashMap<String, ApiExample> getExamples() {
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
	
}
