package org.apitooling.model;

import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import v2.io.swagger.models.Scheme;
import v2.io.swagger.models.Swagger;

public class ApiModel extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiModel.class);
	private ApiType modelVersion;
	private String version;
	private String apiVersion;	
	private ApiContact contact;
	private ApiLicense license;	
	private String termOfService;
	private String title;
	private String description;
	private ApiExternalDocs externalDocs;
	private ArrayList<ApiServer> servers = new ArrayList<ApiServer>();
	private ArrayList<ApiSecurity> security = new ArrayList<ApiSecurity>();
	private ArrayList<ApiPath> paths = new ArrayList<ApiPath>();
	private ArrayList<ApiTag> tags = new ArrayList<ApiTag>();
	private ApiComponents components;
	
	
	public ApiModel(OpenAPI openApi) {
		super();
		describeModel(openApi);
	}

	public ApiModel(Swagger swagger) {
		super();
		describeModel(swagger);
	}

	private void describeModel(OpenAPI model) {
		if (model == null) {
			return;
		}
		this.modelVersion = ApiType.OPENAPI_30;
		this.version = model.getOpenapi();

		this.apiVersion = model.getInfo().getVersion();		
		this.title = model.getInfo().getTitle();
		if (model.getInfo().getDescription() != null) {
			this.description = model.getInfo().getDescription();
		}
		if (model.getInfo().getTermsOfService() != null) {
			this.termOfService = model.getInfo().getTermsOfService();
		}
		if (model.getInfo().getContact() != null) {
			this.contact = new ApiContact(model.getInfo().getContact());
		}
		if (model.getInfo().getLicense() != null) {
			this.license = new ApiLicense(model.getInfo().getLicense());
		}
		
		if (model.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(modelVersion, model, model.getExternalDocs());
		}
		
		if (model.getTags() != null) {
			for (Tag tag : model.getTags()) {
				this.tags.add(new ApiTag(modelVersion, model, tag));
			}
		}

		if (model.getServers() != null) {
			for (Server server : model.getServers()) {
				this.servers.add(new ApiServer(modelVersion, model, server));
			}
		}

		if (model.getSecurity() != null) {
			for (SecurityRequirement security : model.getSecurity()) {
				this.security.add(new ApiSecurity(modelVersion, model, security));
			}
		}

		int idx = 0;
		Set<String> keys = model.getPaths().keySet();
		for (String key : keys) {
			paths.add(new ApiPath(idx++, getModelVersion(), model, key, model.getPaths().get(key)));
		}
		
		if (model.getComponents() != null) {
			this.components = new ApiComponents(modelVersion, model, model.getComponents());
		}
	}

	private void describeModel(Swagger model) {
		if (model == null) {
			return;
		}		
		this.modelVersion = ApiType.SWAGGER_20;
		this.version = model.getSwagger();
		
		this.apiVersion = model.getInfo().getVersion();		
		this.title = model.getInfo().getTitle();
		if (model.getInfo().getDescription() != null) {
			this.description = model.getInfo().getDescription();
		}
		if (model.getInfo().getTermsOfService() != null) {
			this.termOfService = model.getInfo().getTermsOfService();
		}
		if (model.getInfo().getContact() != null) {
			this.contact = new ApiContact(model.getInfo().getContact());
		}
		if (model.getInfo().getLicense() != null) {
			this.license = new ApiLicense(model.getInfo().getLicense());
		}
		
		if (model.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(modelVersion, model, model.getExternalDocs());
		}
		
		if (model.getTags() != null) {
			for (v2.io.swagger.models.Tag tag : model.getTags()) {
				this.tags.add(new ApiTag(modelVersion, model, tag));
			}
		}
		
		String url = model.getHost();
		if (url != null) {
			if ((model.getSchemes() != null) && (model.getSchemes().size() > 0)) {
				int i = url.indexOf(':');
				if (i < -1) {
					url = url.substring(i);
				}
				else {
					url = "://" + url;
				}
					
			}
			if ((model.getSchemes() != null) && (model.getSchemes().size() > 0)) {
				String nurl;
				for (Scheme scheme : model.getSchemes()) {
					nurl = scheme.toString().toLowerCase() + url;					
					if (model.getBasePath() != null) {
						nurl += model.getBasePath();
					}					
					this.servers.add(new ApiServer(modelVersion, model, nurl));
				}
			}
			else {
				this.servers.add(new ApiServer(modelVersion, model, url));
			}
		}
		
		int idx = 0;
		Set<String> keys = model.getPaths().keySet();
		for (String key : keys) {			
			paths.add(new ApiPath(idx++, getModelVersion(), model, key, model.getPaths().get(key)));
		}
		
		if (model.getDefinitions() != null) {
			this.components = new ApiComponents(modelVersion, model, model.getDefinitions());
		}
	}
	
	public ApiType getModelVersion() {
		return this.modelVersion;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getVersion() {
		return version;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public String getDescription() {
		return description;
	}

	public String getTermOfService() {
		return termOfService;
	}

	public ApiContact getContact() {
		return contact;
	}

	public ApiLicense getLicense() {
		return license;
	}

	public ApiExternalDocs getExternalDocs() {
		return externalDocs;
	}

	
	public ArrayList<ApiTag> getTags() {
		return tags;
	}

	public ApiComponents getComponents() {
		return components;
	}

	public ArrayList<ApiServer> getServers() {
		return servers;
	}

	public ArrayList<ApiSecurity> getSecurity() {
		return security;
	}

	public ArrayList<ApiPath> getPaths() {
		return paths;
	}

}
