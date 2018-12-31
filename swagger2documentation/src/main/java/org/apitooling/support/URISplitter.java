package org.apitooling.support;

import com.google.common.base.Preconditions;

public class URISplitter {

	private String uri;
	private String path;

	public URISplitter(String complexUri) {
		super();
		String safe = Preconditions.checkNotNull(complexUri, "URISplitter: URI cannot be null");
		int idx = safe.indexOf('#');
		path = null;
		if (idx > -1) {
			String[] s = safe.split("#");
			uri = s[0];
			if (s.length > 1) {
				path = s[1];
			}
		} else {
			uri = safe;
		}
	}

	public String getUri() {
		return uri;
	}

	public String getPath() {
		return path;
	}

	public boolean hasPath() {
		return (path != null);
	}

}
