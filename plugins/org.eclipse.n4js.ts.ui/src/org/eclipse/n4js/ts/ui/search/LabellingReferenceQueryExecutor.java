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
package org.eclipse.n4js.ts.ui.search;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceQueryExecutor;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * Customized calculation of target URIs for the reference finder to point to the declared types resp. the TVariable.
 */
@SuppressWarnings("restriction")
public class LabellingReferenceQueryExecutor extends ReferenceQueryExecutor {

	@Inject
	private ReferenceFinderLabelProvider labelProvider;

	@Override
	protected String getLabelPrefix() {
		return "References to ";
	}

	@Override
	protected String getElementName(EObject primaryTarget) {
		return labelProvider.getText(primaryTarget);
	}

	@Override
	protected String getResourceName(EObject primaryTarget) {
		Resource resource = primaryTarget.eResource();
		if (N4Scheme.isResourceWithN4Scheme(resource)) {
			return resource.getURI().lastSegment();
		}
		return super.getResourceName(primaryTarget);
	}

	/**
	 * Filter references that should not be revealed in the UI.
	 */
	@Override
	protected Predicate<IReferenceDescription> getFilter(EObject primaryTarget) {
		return new Predicate<IReferenceDescription>() {

			@Override
			public boolean apply(IReferenceDescription input) {
				return isRelevantToUser(input);
			}

		};
	}

	/**
	 * Returns <code>true</code> if the reference should be presented to the user.
	 */
	protected boolean isRelevantToUser(IReferenceDescription input) {
		EReference reference = input.getEReference();
		boolean result = isRelevantToUser(reference);
		return result;
	}

	/**
	 * Returns <code>true</code> if the reference should be presented to the user.
	 */
	protected boolean isRelevantToUser(EReference reference) {
		return TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT != reference &&
				TypesPackage.Literals.TSTRUCT_MEMBER__DEFINED_MEMBER != reference;
	}
}
