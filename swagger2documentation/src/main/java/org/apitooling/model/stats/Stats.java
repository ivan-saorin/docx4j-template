package org.apitooling.model.stats;

public class Stats {

	private int paths = 0;
	private int operations = 0;
	private int getOperations = 0;
	private int postOperations = 0;
	private int putOperations = 0;
	private int deleteOperations = 0;
	private int patchOperations = 0;
	private int totPathParams = 0;
	private int totHeaderAttrs = 0;
	private int totQueryParams = 0;
	private int totExamples= 0;
	
	public Stats() {
		// TODO Auto-generated constructor stub
	}

	public int getPaths() {
		return paths;
	}

	public void incPaths() {
		this.paths++;
	}

	public int getOperations() {
		return operations;
	}

	public void incOperations(String method) {
		this.operations++;
		if ("GET".equalsIgnoreCase(method)) {
			this.getOperations++;
		} if ("POST".equalsIgnoreCase(method)) {
			this.postOperations++;
		} if ("PUT".equalsIgnoreCase(method)) {
			this.putOperations++;
		} if ("DELETE".equalsIgnoreCase(method)) {
			this.deleteOperations++;
		} if ("PATCH".equalsIgnoreCase(method)) {
			this.patchOperations++;
		}
	}

	public int getHttpGetOperations() {
		return getOperations;
	}

	public int getHttpPostOperations() {
		return postOperations;
	}

	public int getHttpPutOperations() {
		return putOperations;
	}

	public int getHttpDeleteOperations() {
		return deleteOperations;
	}

	public int getHttpPatchOperations() {
		return patchOperations;
	}

	public double getAvgPathParamsPerOperation() {
		return totPathParams / operations;
	}

	public void incPathParams() {
		this.totPathParams++;
	}

	public double getAvgHeaderAttrsPerOperation() {
		return totHeaderAttrs / operations;
	}

	public void incHeaderAttrs() {
		this.totHeaderAttrs++;
	}

	public double getAvgQueryParamsPerOperation() {
		return totQueryParams / operations;
	}

	public void incQueryParams() {
		this.totQueryParams++;
	}

	public double getAvgExamplesPerOperation() {
		return totExamples / operations;
	}

	public void incExamples() {
		this.totExamples++;
	}

	public void incExamples(int size) {
		this.totExamples += size;
	}

}
