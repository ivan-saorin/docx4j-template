package org.apitooling.analysis;

import java.util.ArrayList;
import java.util.HashMap;

import org.apitooling.model.ApiParameter;
import org.apitooling.model.ApiParameterType;

public class PathParameters extends HashMap<String, ArrayList<ApiParameter>> {
	
	private static final long serialVersionUID = 1377237600400271533L;

	public ArrayList<ApiParameter> getOrNew(String key, ApiParameterType type) {
		String primary = type.toString() + " " + key;
		if (this.containsKey(primary)) {
			return this.get(primary);
		}
		else {
			ArrayList<ApiParameter> params = new ArrayList<ApiParameter>();
			this.put(primary, params);
			return params;
		}
	}
	
}
