package org.apitooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiLicense extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiLicense.class);
	
	private String name;
	private String url;
	
	public ApiLicense(io.swagger.v3.oas.models.info.License license) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", "Open Api 3.x", license.getClass().getName(), license.getExtensions());
		describeModel(license);
	}

	public ApiLicense(v2.io.swagger.models.License license) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", "Swagger 2.x", license.getClass().getName(), license.getVendorExtensions());
		describeModel(license);
	}

	private void describeModel(io.swagger.v3.oas.models.info.License license) {
		this.describeExtensions(license.getExtensions());
		this.name = license.getName();
		this.url = license.getUrl();
	}

	private void describeModel(v2.io.swagger.models.License license) {
		this.describeExtensions(license.getVendorExtensions());
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
