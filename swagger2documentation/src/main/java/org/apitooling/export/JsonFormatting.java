package org.apitooling.export;

import java.util.List;

import org.apitooling.utils.JsonUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class JsonFormatting implements TemplateMethodModelEx {

	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() == 1) {
			return JsonUtils.ex2json2(args.get(0));
		}
		else {
			return "";
		}
	}

}
