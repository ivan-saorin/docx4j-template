package org.apitooling.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apitooling.model.stats.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiElement.class);  
	
	private static final String XIMPLEMENTATION_KEY = "x-implementation";
	private static final String CONDITION_KEY = "condition";
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
	protected HashMap<String, Object> xImplementation = new HashMap<String, Object>();  
	
	private static final String XEXAMPLES_KEY = "x-examples";
	protected HashMap<String, Object> xExamples = new HashMap<String, Object>();
	
	protected ApiModel model;
	
	public ApiElement(ApiModel parent) {
		super();
		this.model = parent;
	}

	public static String typeName(String ref) {
		String typeName = ref;
		int l = ref.lastIndexOf('/');
		if (++l > -1) {
			typeName = ref.substring(l);
		}
		return typeName;			
	}
	
	@SuppressWarnings("unchecked")
	protected void describeExtensions(Map<String, Object> extensions) {
		this.describeExtensions(null, extensions);
	}
		
	protected void describeExtensions(String name, Map<String, Object> extensions) {
		if (extensions == null) {
			return;
		}
		
		describeXImplementation(name, extensions);
		describeXExamples(name, extensions);
	}

	private void describeXImplementation(String name, Map<String, Object> extensions) {
		if (extensions.containsKey(XIMPLEMENTATION_KEY)) {
			Map<String, Object> extension = (Map<String, Object>) extensions.get(XIMPLEMENTATION_KEY);
			if (xImplementation.size() == 0) {
				xImplementation.putAll(extension);
				
				if (name != null) {
					xImplementation.put("fieldName", name);
				}
				
				if (xImplementation.containsKey("date")) {
					//"2018-12-27T00:00:00.000+0000"
					String date = xImplementation.get("date").toString();
					date = date.substring(0,10);
					try {
						xImplementation.put("date", DATEFORMAT.parse(date));
					} catch (ParseException cause) {
						//cause.printStackTrace();
					}				
				}
				if (xImplementation.containsKey(CONDITION_KEY)) {
					HashMap<String, Object> xCondition = (HashMap<String, Object>) xImplementation.get(CONDITION_KEY);
					if (xCondition.containsKey("date")) {
						//"2018-12-27T00:00:00.000+0000"
						String date = xCondition.get("date").toString();
						date = date.substring(0,10);
						try {
							xCondition.put("date", DATEFORMAT.parse(date));
						} catch (ParseException cause) {
							//cause.printStackTrace();
						}
					}
				}
			}			
		}
	}
	
	public Map<String, Object> getxImplementation() {
		return xImplementation;
	}

	@SuppressWarnings("unchecked")
	private void describeXExamples(String name, Map<String, Object> extensions) {
		if (extensions.containsKey(XEXAMPLES_KEY)) {
			if (xExamples.size() == 0) {
				List<Object> myExamples = (List<Object>) extensions.get(XEXAMPLES_KEY);
				//if (logger.isInfoEnabled()) logger.info("myexamples: {} -> {}", this.hashCode(), myExamples);
				xExamples = new HashMap<String, Object>();
				for (Object obj : myExamples) {
					Map<String, Object> map = (Map<String, Object>) obj;
					Set<String> keys = map.keySet();					
					for (String key : keys) {
						List<Object>tmp = (List<Object>) map.get(key);						
						String key2 = null;
						int i = 0;
						xExamples.put(key, parseList(tmp));
					}					
				}
				
				if (logger.isInfoEnabled()) logger.info("examples: {} -> {}", this.hashCode(), xExamples);
			}			
		}
	}
	
	private Map<String, Object> parseList(List<Object> list) {
		Map<String, Object> map = new HashMap<String, Object>();		
		String key = null;
		String summary = null;
		String description = null;
		String note = null;
		String exampleCode = null;
		int i = 0;
		for (Object obj : list) {
			if (i == 0) {
				key = obj.toString();
			}
			else if (list.size() == 2) {
				if (i == 1) {
					exampleCode = obj.toString();
				}
			}			
			else if (list.size() == 3) {
				if (i == 1) {
					description = obj.toString();
				}				
				if (i == 2) {
					exampleCode = obj.toString();
				}
			}
			else if (list.size() == 4) {
				if (i == 1) {
					description = obj.toString();
				}
				if (i == 2) {
					note = obj.toString();
				}				
				if (i == 3) {
					exampleCode = obj.toString();
				}
			}
			else if (list.size() == 5) {
				if (i == 1) {
					summary = obj.toString();
				}
				if (i == 2) {
					description = obj.toString();
				}												
				if (i == 3) {
					note = obj.toString();
				}								
				if (i == 4) {
					exampleCode = obj.toString();
				}
			}			
			else {
				exampleCode = obj.toString();
			}
			i++;
		}
		
		ApiExample example = new ApiExample(this.model, summary, description, note, exampleCode);
		
		map.put(key, example);
		
		return map;
	}

	public Map<String, Object> getxExamples() {
		return xExamples;
	}

	public ApiModel getModel() {
		if (model == null) {
			throw new RuntimeException(new NullPointerException("model is null"));
		}
		return model;
	}	

	public Stats getStats() {
		if (model == null) {
			throw new RuntimeException(new NullPointerException("model is null"));
		}			
		return model.getStats();
	}	

	
}
