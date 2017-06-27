/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.findReferences;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;

import com.google.inject.Inject;

import org.eclipse.n4js.resource.InferredElements;

/**
 * Collector of target URIs when find referencs is triggered. Collects the URI of the given object and of all objects
 * that have been inferred from that.
 */
@SuppressWarnings("restriction")
public class InferredElementsTargetURICollector extends TargetURICollector {

	@Inject
	private InferredElements inferredElements;

	@Override
	protected void doAdd(EObject primaryTarget, TargetURIs targetURIs) {
		EcoreUtil.resolveAll(primaryTarget.eResource());
		super.doAdd(primaryTarget, targetURIs);
		inferredElements.collectInferredElements(primaryTarget, (object) -> {
			if (object != null) {
				super.doAdd(object, targetURIs);
			}
		});
	}

}
