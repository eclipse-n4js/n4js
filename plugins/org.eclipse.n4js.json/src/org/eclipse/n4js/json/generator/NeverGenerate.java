package org.eclipse.n4js.json.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IShouldGenerate;
import org.eclipse.xtext.util.CancelIndicator;

/** Disables output generation from .json files */
public class NeverGenerate implements IShouldGenerate {

	@Override
	public boolean shouldGenerate(Resource resource, CancelIndicator cancelIndicator) {
		return false;
	}

}
