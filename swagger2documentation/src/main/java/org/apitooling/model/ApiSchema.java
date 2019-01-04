package org.apitooling.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Schema;
import v2.io.swagger.models.ComposedModel;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.properties.Property;

public class ApiSchema extends LinkedHashMap<String, ApiField> {
	
	private static final long serialVersionUID = 6146468998693758762L;
	
	private String typeName;
	
	public ApiSchema(int index, ApiType modelVersion, OpenAPI model, String key, Schema<?> schema) {
		this.typeName = key;
		if (schema instanceof ComposedSchema) {
			ComposedSchema cs = (ComposedSchema) schema;
			if (cs.getAllOf() != null) {
				this.typeName = key + " (allOf)";
				describeComposedSchema(modelVersion, model, cs.getAllOf(), "+");
			}
			else if (cs.getAnyOf() != null) {
				this.typeName = key + " (anyOf)";
				describeComposedSchema(modelVersion, model, cs.getAnyOf(), "or");
			}
			else if (cs.getOneOf() != null) {
				this.typeName = key + " (oneOf)";
				describeComposedSchema(modelVersion, model, cs.getOneOf(), "and");
			}
		}
		
		if (schema.getProperties() != null) {
			Set<String> keys = schema.getProperties().keySet();
			for (String skey : keys) {
				Schema<?> s = schema.getProperties().get(skey);
				put(modelVersion, model, skey, s);
			}
		}
	}

	private void describeComposedSchema(ApiType modelVersion, OpenAPI model, List<Schema> list, String title) {
		for (Schema<?> s : list) {
			if (s.get$ref() != null) {				
				this.put("Type", new ApiField(modelVersion, model, s.get$ref(), s));
			}
			else if ((s.getProperties() != null) && (s.getProperties().size() > 0)) {
				put(modelVersion, model, title, s);
			}
		}
	}

	private void put(ApiType modelVersion, OpenAPI model, String key, Schema<?> s) {
		this.put(key, new ApiField(modelVersion, model, key, s));
		if (s.getProperties() != null) {
			Set<String> keys = s.getProperties().keySet();
			for (String skey : keys) {
				Schema<?> s1 = s.getProperties().get(skey);
				put(modelVersion, model, skey, s1);
			}
		}
	}

	public ApiSchema(int index, ApiType modelVersion, Swagger model, String key, Model schema) {
		super();
		describeModel(index, modelVersion, model, key, schema);
	}

	private void describeModel(int index, ApiType modelVersion, Swagger model, String key, Model schema) {
		this.typeName = key;
		put(modelVersion, model, key, schema);
	}
	
	
	private void put(ApiType modelVersion, Swagger model, String key, Model schema) {
		this.typeName = key;
		if (schema instanceof RefModel) {
			this.put(key, new ApiField(modelVersion, model, key, (RefModel) schema));
		}
		else if (schema instanceof ComposedModel) {
			ComposedModel cm = (ComposedModel) schema;
			if (cm.getAllOf() != null) {
				this.typeName = key + " (allOf)";
				describeComposedSchema(modelVersion, model, key, cm.getAllOf(), "+");
			}
		}
		if (schema.getProperties() != null) {
			Set<String> keys = schema.getProperties().keySet();
			for (String skey : keys) {
				Property s1 = schema.getProperties().get(skey);
				put(modelVersion, model, skey, s1);
			}
		}
		
	}

	private void describeComposedSchema(ApiType modelVersion, Swagger model, String key, List<Model> list, String title) {
		for (Model m : list) {
			if (m instanceof RefModel) {
				this.put("Type", new ApiField(modelVersion, model, ((RefModel) m).getSimpleRef(), (RefModel) m));
				this.put(title, new ApiField());
			}
			else if ((m.getProperties() != null) && (m.getProperties().size() > 0)) {
				Set<String> keys = m.getProperties().keySet();
				for (String pkey : keys) {					
					this.put(pkey, new ApiField(modelVersion, model, m.getProperties().get(pkey)));
				}
				this.put(title, new ApiField());
			}
		}
	}

	private void put(ApiType modelVersion, Swagger model, String key, Property schema) {
		this.put(key, new ApiField(modelVersion, model, schema));
	}

	public String getTypeName() {
		return typeName;
	}
	
}
