/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.refactoring;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.types.util.TypeModelUtils;
import org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator;

/**
 * Custom DependentElementsCalculator
 *
 */
@SuppressWarnings("restriction")
public class N4JSDependentElementsCalculator extends DefaultDependentElementsCalculator {

	@Override
	public List<URI> getDependentElementURIs(EObject baseElement, IProgressMonitor monitor) {
		List<EObject> realTargets = TypeModelUtils.getRealElements(baseElement);
		List<URI> elementURIs = super.getDependentElementURIs(baseElement, monitor);
		for (EObject realTarget : realTargets) {
			elementURIs.add(EcoreUtil.getURI(realTarget));
		}
		return elementURIs;
	}
}
