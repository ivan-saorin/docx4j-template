package org.apitooling.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apitooling.model.ApiPath;

public class ApiUtils {

	public static Map<String, ArrayList<ApiPath>> groupByApi(ArrayList<ApiPath> paths) {
		LinkedHashMap<String, ArrayList<ApiPath>> res = new LinkedHashMap<String, ArrayList<ApiPath>>();
		for (ApiPath path : paths) {
			String basePath = calculateBasePath(path);
			if (res.containsKey(basePath)) {
				ArrayList<ApiPath> tempPaths = res.get(basePath);
				tempPaths.add(path);
			}
			else {
				ArrayList<ApiPath> tempPaths = new ArrayList<ApiPath>();
				tempPaths.add(path);
				res.put(basePath, tempPaths);
			}
		}
		return res;
	}

	private static String calculateBasePath(ApiPath path) {
		String[] tokens = path.getPath().split("/");
		if (tokens.length == 0) {
			return "Base";
		}
		
		int i = 0;
		if (tokens[0].equals("")) {
			i++;
		}
		
		if (tokens[i].startsWith("{")) {
			return "Base";
		}
		else {
			return tokens[i];
		}
	}

}
