package org.apitooling.analysis;

import java.util.HashMap;

public class ParamName2PathName extends HashMap<String, PathParameters> {

	private static final long serialVersionUID = -6971257840275213988L;

	public PathParameters getOrNew(String name) {
		if (this.containsKey(name)) {
			return this.get(name);
		}
		else {
			PathParameters pathParams = new PathParameters();
			this.put(name, pathParams);
			return pathParams;
		}
	}

}
