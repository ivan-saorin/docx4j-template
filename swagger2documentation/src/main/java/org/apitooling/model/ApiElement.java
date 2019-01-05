package org.apitooling.model;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ApiElement {

	private static final String XIMPLEMENTATION_KEY = "x-implementation";	
	protected LinkedHashMap<String, Object> xImplementation = new LinkedHashMap<String, Object>();  
	
	public ApiElement() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void describeExtensions(Map<String, Object> extensions) {
		if (extensions == null) {
			return;
		}
		
		if (extensions.containsKey(XIMPLEMENTATION_KEY)) {
			Map<String, Object> extension = (Map<String, Object>) extensions.get(XIMPLEMENTATION_KEY);
			xImplementation.putAll(extension);
		}		
	}
	
	public Map<String, Object> getxImplementation() {
		return xImplementation;
	}	
	
}
