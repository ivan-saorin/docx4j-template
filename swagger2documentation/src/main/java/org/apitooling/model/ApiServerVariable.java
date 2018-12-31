package org.apitooling.model;

import java.util.ArrayList;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.ServerVariable;

public class ApiServerVariable {

	private String defaultValue;
	private String description;
	private ArrayList<String> enumValue = new ArrayList<String>(); 
	
	public ApiServerVariable(int index, ApiType modelVersion, OpenAPI model, String key, ServerVariable serverVariable) {
		super();
		describeModel(index, modelVersion, model, key, serverVariable);
	}

	private void describeModel(int index, ApiType modelVersion, OpenAPI model, String key, ServerVariable serverVariable) {
		if (serverVariable.getDefault() != null)  {
			this.defaultValue = serverVariable.getDefault();
		}
		
		if (serverVariable.getDescription() != null)  {
			this.description = serverVariable.getDescription();
		}
		
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
