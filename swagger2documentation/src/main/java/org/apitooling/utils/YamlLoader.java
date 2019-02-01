package org.apitooling.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class YamlLoader {
    public static ObjectNode loadString(String content) throws IOException {
        // here doesn't use jackson-dataformat-yaml so that snakeyaml calls Resolver
        // and Composer. See also YamlTagResolver.
        Yaml yaml = new Yaml(new SafeConstructor(), new Representer(), new DumperOptions(), new YamlTagResolver());
        ObjectNode object = normalizeValidateObjectNode(yaml.load(content));
        return object;
    }

    private static ObjectNode normalizeValidateObjectNode(Object object) throws IOException {
    	final ObjectMapper treeObjectMapper = new ObjectMapper();
        JsonNode node = treeObjectMapper.readTree(treeObjectMapper.writeValueAsString(object));
        if (!node.isObject()) {
            throw new RuntimeJsonMappingException("Expected object to load Config but got "+node);
        }
        return (ObjectNode) node;
    }

	public static JsonNode fromFile(File file) throws IOException {
		// TODO Auto-generated method stub
		return YamlLoader.loadString(FileUtils.readFileToString(file));
	}
}