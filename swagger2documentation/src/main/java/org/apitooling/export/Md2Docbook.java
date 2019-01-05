package org.apitooling.export;

import java.util.List;

import org.apitooling.utils.Markdown;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class Md2Docbook implements TemplateMethodModelEx {

	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() == 1) {
			return Markdown.md2docbook(args.get(0));
		}
		else {
			return "";
		}
	}

}
