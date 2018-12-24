package org.swaggertooling.model;

import com.reprezen.kaizen.oasparser.model3.Contact;

public class SwaggerContact extends SwaggerElement {

	private String name;
	private String url;
	private String email;
	
	public SwaggerContact(Contact contact) {
		super();
		describeModel(contact);
	}

	private void describeModel(Contact contact) {
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
