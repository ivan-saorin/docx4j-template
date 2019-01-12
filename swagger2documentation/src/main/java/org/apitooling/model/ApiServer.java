package org.apitooling.model;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import v2.io.swagger.models.Swagger;

public class ApiServer extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiServer.class);
	
	private String description;
	private String url;
	private HashMap<String, ApiServerVariable> variables = new HashMap<String, ApiServerVariable>();
	
	public ApiServer(ApiType modelVersion, OpenAPI model, Server server) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, server.getClass().getName(), server.getExtensions());
		describeModel(modelVersion, model, server);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, Server server) {
		if (server.getDescription() != null) {
			this.description = server.getDescription();
		}
		
		this.describeExtensions(server.getExtensions());
		
		if (server.getUrl() != null) {
			this.url = server.getUrl();
		}
		
		if (server.getVariables() != null) {
			Set<String> keys = server.getVariables().keySet();
			int idx = 0;
			for (String key : keys) {
				variables.put(key, new ApiServerVariable(idx++, modelVersion, model, key, server.getVariables().get(key)));
			}						
		}
		
	}

	public ApiServer(ApiType modelVersion, Swagger model, String url) {
		super();
		describeModel(modelVersion, model, url);
	}

	private void describeModel(ApiType modelVersion, Swagger model, String url) {
		if (url != null) {
			this.url = url;
		}
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public HashMap<String, ApiServerVariable> getVariables() {
		return variables;
	}
	
}
