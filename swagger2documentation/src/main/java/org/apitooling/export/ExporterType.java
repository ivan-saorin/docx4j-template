package org.apitooling.export;

public enum ExporterType {
	LOGGER(""), ANALYSIS_LOGGER(""), MD(".md"), PDF(".pdf"), MSWORD(".docx"), MSEXCEL(".xls");
	
	private String extension;
	
	private ExporterType(String extension) {
		this.extension = extension;		
	}

	public String getExtension() {
		return extension;
	}
}
