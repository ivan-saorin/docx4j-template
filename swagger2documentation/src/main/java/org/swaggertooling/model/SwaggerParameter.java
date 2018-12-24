package org.swaggertooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reprezen.kaizen.oasparser.model3.Parameter;
import com.reprezen.kaizen.oasparser.model3.Schema;

public class SwaggerParameter extends SwaggerElement {

	private static Logger logger = LoggerFactory.getLogger(SwaggerParameter.class);
	
	public int idx;
	private String name;
	private SwggerParameterType type;
	private String dataType;
	private String description;
	
	public SwaggerParameter(int idx, SwggerParameterType type, Parameter param) {
		super();
		this.idx = idx;
		this.type = type;
		describeModel(param);
	}

	private void describeModel(Parameter param) {
		this.name = param.getName();
		this.dataType = getParameterType(param);
		this.description = this.md2html(param.getDescription());
		System.out.printf("    %s[%s]:, %s - %s\n", param.getName(), param.getIn(), getParameterType(param),
				param.getDescription());
	}
	
	private String getParameterType(Parameter param) {
		Schema schema = param.getSchema();
		return schema != null ? schema.getType() : "unknown";
	}

	public int getIndex() {
		return idx;
	}
	
	public String getName() {
		return name;
	}

	public SwggerParameterType getType() {
		return type;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDescription() {
		return description;
	}
	
}
