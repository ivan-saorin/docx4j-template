package org.apitooling.model;

public class ApiContact extends ApiElement {

	private String name;
	private String url;
	private String email;

	public ApiContact(v2.io.swagger.models.Contact contact) {
		super();
		describeModel(contact);
	}
	
	public ApiContact(io.swagger.v3.oas.models.info.Contact contact) {
		super();
		describeModel(contact);
	}

	private void describeModel(v2.io.swagger.models.Contact contact) {
		this.name = contact.getName();
		this.url = contact.getUrl();
		this.email = contact.getEmail();
	}

	private void describeModel(io.swagger.v3.oas.models.info.Contact contact) {
		this.name = contact.getName();
		this.url = contact.getUrl();
		this.email = contact.getEmail();
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getEmail() {
		return email;
	}
	
}
