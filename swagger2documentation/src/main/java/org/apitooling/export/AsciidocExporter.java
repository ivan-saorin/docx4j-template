package org.apitooling.export;

import org.apitooling.model.ApiModel;

import io.github.swagger2markup.markup.builder.MarkupDocBuilder;
import io.github.swagger2markup.markup.builder.MarkupDocBuilders;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

public class AsciidocExporter implements Exporter {

	private ApiModel model;
	private MarkupDocBuilder builder;
	
	protected AsciidocExporter() {		
		builder = MarkupDocBuilders.documentBuilder(MarkupLanguage.ASCIIDOC);
	}	
	
	public AsciidocExporter(ApiModel model) {
		super();
		this.model = model;
	}

	@Override
	public ExporterOuptput getOuptput() {
		// TODO Auto-generated method stub
		return null;
	}

}
