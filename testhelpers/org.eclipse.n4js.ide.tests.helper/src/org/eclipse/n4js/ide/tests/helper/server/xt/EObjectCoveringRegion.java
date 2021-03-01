/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.resource.XtextResource;

/** Default implementation for {@link IEObjectCoveringRegion} */
public class EObjectCoveringRegion implements IEObjectCoveringRegion {
	final XtextResource resource;
	final EObject eObj;
	final EStructuralFeature structuralFeature;
	int offset;

	/** Constructor */
	public EObjectCoveringRegion(XtextResource resource, EObject eObj, int offset,
			EStructuralFeature structuralFeature) {

		this.resource = resource;
		this.eObj = eObj;
		this.structuralFeature = structuralFeature;
		this.offset = offset;
	}

	@Override
	public EObject getEObject() {
		return eObj;
	}

	@Override
	public XtextResource getXtextResource() {
		return resource;
	}

	@Override
	public EStructuralFeature getEStructuralFeature() {
		return structuralFeature;
	}

	@Override
	public int getOffset() {
		return offset;
	}
}