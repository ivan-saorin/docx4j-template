package org.apitooling.model;

public class ApiLicense extends ApiElement {

	private String name;
	private String url;
	
	public ApiLicense(io.swagger.v3.oas.models.info.License license) {
		super();
		describeModel(license);
	}

	public ApiLicense(v2.io.swagger.models.License license) {
		super();
		describeModel(license);
	}

	private void describeModel(io.swagger.v3.oas.models.info.License license) {
		this.name = license.getName();
		this.url = license.getUrl();
	}

	private void describeModel(v2.io.swagger.models.License license) {
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
