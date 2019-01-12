package org.apitooling.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.ServerVariable;

public class ApiServerVariable extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiServerVariable.class);
	
	private String defaultValue;
	private String description;
	private ArrayList<String> enumValue = new ArrayList<String>(); 
	
	public ApiServerVariable(ApiModel parent, int index, ApiType modelVersion, OpenAPI model, String key, ServerVariable serverVariable) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, serverVariable.getClass().getName(), serverVariable.getExtensions());
		describeModel(index, modelVersion, model, key, serverVariable);
	}

	private void describeModel(int index, ApiType modelVersion, OpenAPI model, String key, ServerVariable serverVariable) {
		if (serverVariable.getDefault() != null)  {
			this.defaultValue = serverVariable.getDefault();
		}
		
		if (serverVariable.getDescription() != null)  {
			this.description = serverVariable.getDescription();
		}
		
		this.describeExtensions(serverVariable.getExtensions());
		
		if (serverVariable.getEnum() != null)  {
			this.enumValue.addAll(serverVariable.getEnum());
		}		
	}

	public String getDefault() {
		return defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<String> getEnum() {
		return enumValue;
	}

	
}
