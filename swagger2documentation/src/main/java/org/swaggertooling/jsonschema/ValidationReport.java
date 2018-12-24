package org.swaggertooling.jsonschema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;

public class ValidationReport {

	private ArrayList<ValidationMessage> nones = new ArrayList<ValidationMessage>();
	private ArrayList<ValidationMessage> debugs = new ArrayList<ValidationMessage>();
	private ArrayList<ValidationMessage> infos = new ArrayList<ValidationMessage>();
	private ArrayList<ValidationMessage> warns = new ArrayList<ValidationMessage>();
	private ArrayList<ValidationMessage> errors = new ArrayList<ValidationMessage>();
	private ArrayList<ValidationMessage> fatals = new ArrayList<ValidationMessage>();
	private ProcessingReport delegate = null;
	
	public ValidationReport(ProcessingReport report) {
		super();
		this.delegate = report;		
		for (ProcessingMessage m : delegate) {
			switch (m.getLogLevel()) {
			case NONE: nones.add(new ValidationMessage(m)); break;
			case DEBUG: debugs.add(new ValidationMessage(m)); break;
			case INFO: infos.add(new ValidationMessage(m)); break;
			case WARNING: warns.add(new ValidationMessage(m)); break;
			case ERROR: errors.add(new ValidationMessage(m)); break;
			case FATAL: fatals.add(new ValidationMessage(m)); break;
			}
		}
	}
	
	public Collection<ValidationMessage> all() {
		ArrayList<ValidationMessage> all = new ArrayList<ValidationMessage>();
		all.addAll(nones);
		all.addAll(debugs);
		all.addAll(infos);
		all.addAll(warns);
		all.addAll(errors);
		all.addAll(fatals);
		return all;
	}
	
	public List<ValidationMessage> filter(ValidationMessageType type) {
		switch (type) {
		case NONE: return nones;
		case DEBUG: return debugs;
		case INFO: return infos;
		case WARNING: return warns;
		case ERROR: return errors;
		case FATAL: return fatals;
		default:
			return fatals;
		}
	}
	
	public boolean isSuccess() {
		return (errors.isEmpty() && fatals.isEmpty());
	}
	
	public boolean hasWarning() {
		return (warns.isEmpty());
	}
	
}
