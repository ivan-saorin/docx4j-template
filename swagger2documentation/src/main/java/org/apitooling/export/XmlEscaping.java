package org.apitooling.export;

import java.util.List;

import org.apitooling.utils.XmlUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class XmlEscaping implements TemplateMethodModelEx {

	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() == 1) {
			return XmlUtils.xmlEscaping(args.get(0));
		}
		else {
			return "";
		}
	}

}
