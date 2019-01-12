package org.apitooling.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Schema;
import v2.io.swagger.models.ArrayModel;
import v2.io.swagger.models.ComposedModel;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.ModelImpl;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.properties.ObjectProperty;
import v2.io.swagger.models.properties.Property;

public class ApiSchema extends ApiElement {
	
	private static Logger logger = LoggerFactory.getLogger(ApiSchema.class);
	
	private Random rnd = new Random();
	private ApiComponents parent;
	private String name;
	private String description;
	private ArrayList<String> requireds = new ArrayList<String>();
	private HashMap<String, ApiField> properties = new HashMap<String, ApiField>();
	
	public ApiSchema(ApiModel parentModel, int index, ApiType modelVersion, OpenAPI model, ApiComponents parent, String key, Schema<?> schema) {
		super(parentModel);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, schema.getClass().getName(), schema.getExtensions());
		this.parent = parent;
		this.name = key;
		if (schema.getRequired() != null) {
			requireds.addAll(schema.getRequired());
		}
		if (schema.getDescription() != null) {
			this.description = schema.getDescription();
		}
		if (schema instanceof ComposedSchema) {
			ComposedSchema cs = (ComposedSchema) schema;
			if (cs.getAllOf() != null) {
				this.name = key + " (allOf)";
				describeComposedSchema(modelVersion, model, cs.getAllOf(), "+");
			}
			else if (cs.getAnyOf() != null) {
				this.name = key + " (anyOf)";
				describeComposedSchema(modelVersion, model, cs.getAnyOf(), "or");
			}
			else if (cs.getOneOf() != null) {
				this.name = key + " (oneOf)";
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
				this.properties.put("Type", new ApiField(this.getModel(), modelVersion, model, s.get$ref(), s));
			}
			else if ((s.getProperties() != null) && (s.getProperties().size() > 0)) {
				put(modelVersion, model, title, s);
			}
		}
	}

	private void put(ApiType modelVersion, OpenAPI model, String key, Schema<?> s) {
		this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, s));
		if (s.getProperties() != null) {
			Set<String> keys = s.getProperties().keySet();
			for (String skey : keys) {
				Schema<?> s1 = s.getProperties().get(skey);
				put(modelVersion, model, skey, s1);
			}
		}
	}

	public ApiSchema(ApiModel parentModel, int index, ApiType modelVersion, Swagger model, ApiComponents parent, String key, Model schema) {
		super(parentModel);
		this.parent = parent;
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, schema.getClass().getName(), schema.getVendorExtensions());
		describeModel(index, modelVersion, model, key, schema);
	}

	private void describeModel(int index, ApiType modelVersion, Swagger model, String key, Model schema) {
		this.name = key;
		if (schema.getDescription() != null) {
			this.description = schema.getDescription();
		}
		this.describeExtensions(schema.getVendorExtensions());
		put(modelVersion, model, key, schema);
	}

	public ApiSchema(ApiModel parent, int index, ApiType modelVersion, Swagger model, String key, Property schema) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, schema.getClass().getName(), schema.getVendorExtensions());
		describeModel(index, modelVersion, model, key, schema);
	}

	private void describeModel(int index, ApiType modelVersion, Swagger model, String key, Property schema) {
		this.name = key;
		if (schema.getDescription() != null) {
			this.description = schema.getDescription();
		}		
		this.describeExtensions(schema.getVendorExtensions());
		put(modelVersion, model, key, schema, false);
	}

	private void put(ApiType modelVersion, Swagger model, String key, Model schema) {
		this.name = key;
		if (schema.getDescription() != null) {
			this.description = schema.getDescription();
		}				
		Map<String, Property> properties = schema.getProperties();
		if (schema instanceof RefModel) {
			this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, (RefModel) schema));
		}
		else if (schema instanceof ComposedModel) {
			ComposedModel cm = (ComposedModel) schema;
			if (cm.getAllOf() != null) {
				this.name = key + " (allOf)";
				describeComposedSchema(modelVersion, model, key, cm.getAllOf(), "+");
			}
		}
		else if (schema instanceof ArrayModel) {
			ArrayModel array = (ArrayModel) schema;
			Property items = array.getItems();
			if (items.getType().equals("object")) {
				logger.debug("breakpoint");
				String nkey = getNewTypeName();
				while (parent.getSchemas().containsKey(nkey)) {
					nkey = getNewTypeName();
				}
				parent.getSchemas().put(nkey, new ApiSchema(this.getModel(), 0, modelVersion, model, nkey, items));
				this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, array, nkey));
			}
			else {
				this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, array));
			}
		}
		else if (schema instanceof ModelImpl) {
			ModelImpl s = (ModelImpl) schema;
			if (s.getRequired() != null) {
				requireds.addAll(s.getRequired());
			}		

			if (properties == null) {
				this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, (ModelImpl) schema));
			}
		}
		
		if (properties != null) {
			Set<String> keys = properties.keySet();
			for (String skey : keys) {
				Property s1 = properties.get(skey);
				put(modelVersion, model, skey, s1, requireds.contains(skey));
			}
		}
		
	}
	private String getNewTypeName() {
		return "Type" + rnd.nextInt(1000);
	}

	private void describeComposedSchema(ApiType modelVersion, Swagger model, String key, List<Model> list, String title) {
		for (Model m : list) {
			if (m instanceof RefModel) {
				this.properties.put("Type", new ApiField(this.getModel(), modelVersion, model, ((RefModel) m).getSimpleRef(), (RefModel) m));
				this.properties.put(title, new ApiField(this.getModel()));
			}
			else if ((m.getProperties() != null) && (m.getProperties().size() > 0)) {
				Set<String> keys = m.getProperties().keySet();
				for (String pkey : keys) {					
					this.properties.put(pkey, new ApiField(this.getModel(), modelVersion, model, pkey, m.getProperties().get(pkey)));
				}
				this.properties.put(title, new ApiField(this.getModel()));
			}
		}
	}

	private void put(ApiType modelVersion, Swagger model, String key, Property schema, boolean required) {
		if (schema.getType().equalsIgnoreCase("object")) {
			if (schema.getName() != null) {
				this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, schema, required));
			}
			else if (schema instanceof ObjectProperty) {
				ObjectProperty obj = (ObjectProperty) schema;				
				Set<String> keys = obj.getProperties().keySet();
				for (String pkey : keys) {
					ArrayList<String> requireds = new ArrayList<String>();
					if (obj.getRequiredProperties() != null) {
						requireds.addAll(obj.getRequiredProperties());
					}
					//if (logger.isInfoEnabled()) logger.info("{}> requireds: {}, contains: {}", pkey, requireds, requireds.contains(pkey));
					this.properties.put(pkey, new ApiField(this.getModel(), modelVersion, model, pkey, obj.getProperties().get(pkey), requireds.contains(pkey)));
				}
			}
			else {
				throw new IllegalStateException();
			}
		}
		else {
			this.properties.put(key, new ApiField(this.getModel(), modelVersion, model, key, schema, required));
		}		
	}

	public String getName() {
		return name;
	}

	public HashMap<String, ApiField> getProperties() {
		return properties;
	}
	
	
	
}
