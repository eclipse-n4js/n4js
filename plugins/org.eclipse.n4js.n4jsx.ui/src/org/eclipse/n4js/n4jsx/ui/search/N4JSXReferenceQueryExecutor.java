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
package org.eclipse.n4js.n4jsx.ui.search;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage;
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription;
import org.eclipse.n4js.ui.search.N4JSReferenceQueryExecutor;
import org.eclipse.xtext.resource.IReferenceDescription;

/**
 * Filter find references result for JSX language. For instance closing elements of JSX elements are not shown in the find references result.
 */
public class N4JSXReferenceQueryExecutor extends N4JSReferenceQueryExecutor {

	/**
	 * Returns <code>true</code> if the reference should be presented to the user.
	 */
	@Override
	protected boolean isRelevantToUser(IReferenceDescription referenceDescription) {
		if (referenceDescription instanceof LabelledReferenceDescription) {
			EObject source = ((LabelledReferenceDescription)referenceDescription).getSource();
			if (source.eContainer() instanceof JSXElementName && source.eContainer().eContainingFeature() == N4JSXPackage.eINSTANCE.getJSXElement_JsxClosingName()) {
				// Do not show JSX element's closing tag
				return false;
			} else {
				return true;
			}
		} else {
			return super.isRelevantToUser(referenceDescription);
		}
	}
}
