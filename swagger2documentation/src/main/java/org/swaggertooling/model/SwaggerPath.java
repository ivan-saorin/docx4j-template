package org.swaggertooling.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Path;

public class SwaggerPath extends SwaggerElement {

	private static Logger logger = LoggerFactory.getLogger(SwaggerPath.class);
	
	public int idx;
	public String path;
	public ArrayList<SwaggerOperation> operations = new ArrayList<SwaggerOperation>(); 
	
	public SwaggerPath(int idx, Path path) {
		super();
		this.idx = idx;
		describeModel(path);
	}

	private void describeModel(Path path) {
		this.path = Overlay.of(path).getPathInParent();
		System.out.printf("Path %s:\n", Overlay.of(path).getPathInParent());
		int idx = 0;
		for (Operation op : path.getOperations().values()) {
			operations.add(new SwaggerOperation(idx++, op));
		}
	}

	public int getIndex() {
		return idx;
	}

	public String getPath() {
		return path;
	}

	public ArrayList<SwaggerOperation> getOperations() {
		return operations;
	}
	
}
