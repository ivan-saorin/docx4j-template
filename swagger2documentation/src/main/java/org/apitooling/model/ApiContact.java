package org.apitooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiContact extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiContact.class);
	
	private String name;
	private String url;
	private String email;
	
	public ApiContact(ApiModel parent, io.swagger.v3.oas.models.info.Contact contact) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", "Open Api 3.x", contact.getClass().getName(), contact.getExtensions());
		describeModel(contact);
	}
	
	private void describeModel(io.swagger.v3.oas.models.info.Contact contact) {
		this.describeExtensions(contact.getExtensions());
		this.name = contact.getName();
		this.url = contact.getUrl();
		this.email = contact.getEmail();
	}

	public ApiContact(ApiModel parent, v2.io.swagger.models.Contact contact) {
		super(parent);		
		describeModel(contact);
	}

	private void describeModel(v2.io.swagger.models.Contact contact) {
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
