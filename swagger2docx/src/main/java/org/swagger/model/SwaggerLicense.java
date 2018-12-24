package org.swagger.model;

import com.reprezen.kaizen.oasparser.model3.License;

public class SwaggerLicense extends SwaggerElement {

	private String name;
	private String url;
	
	public SwaggerLicense(License license) {
		super();
		describeModel(license);
	}

	private void describeModel(License license) {
		this.name = license.getName();
		this.url = license.getUrl();
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

}
