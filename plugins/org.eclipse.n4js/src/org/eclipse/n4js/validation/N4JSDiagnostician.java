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
package org.eclipse.n4js.validation;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.inject.Inject;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.validation.PrePostDiagnostician;

/**
 *
 */
public class N4JSDiagnostician extends PrePostDiagnostician {

	/** Copied from super class. */
	@Inject
	public N4JSDiagnostician(Registry registry) {
		super(registry);
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// do not validate eObject if it is contained via one of the AST_CONTAINMENT_REFERENCES_WITHOUT_VALIDATION
		if (eObject != null) {
			final EStructuralFeature containingFeature = eObject.eContainingFeature();
			for (int i = 0; i < N4JSGlobals.AST_CONTAINMENT_REFERENCES_WITHOUT_VALIDATION.length; i++) {
				if (containingFeature == N4JSGlobals.AST_CONTAINMENT_REFERENCES_WITHOUT_VALIDATION[i]) {
					return true;
				}
			}
		}
		// default behavior
		return super.validate(eClass, eObject, diagnostics, context);
	}
}
