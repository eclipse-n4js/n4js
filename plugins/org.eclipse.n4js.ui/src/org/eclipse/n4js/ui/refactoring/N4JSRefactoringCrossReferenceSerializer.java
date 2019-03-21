/**
 * Copyright (c) 2019 NumberFour AG. All rights reserved. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.refactoring;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringCrossReferenceSerializer;
import org.eclipse.xtext.ui.refactoring.impl.StatusWrapper;
import org.eclipse.xtext.util.ITextRegion;

/**
 * Custom Refactoring cross reference serializer because its default implementation {@code getCrossRefText} has problems
 * with finding the updated name via scope. May be the same issues as PERSISTED/LIVE scope??
 */
@SuppressWarnings("restriction")
public class N4JSRefactoringCrossReferenceSerializer extends RefactoringCrossReferenceSerializer {

	/**
	 * If the target has a name field, simply return it as it is already the changed name. The default implementation
	 * {@code getCrossRefText} has a problem: it asks the scope but the scope seems to not have the updated state.
	 */
	@Override
	public String getCrossRefText(EObject owner, CrossReference crossref, EObject target,
			RefTextEvaluator refTextEvaluator, ITextRegion linkTextRegion, StatusWrapper status) {
		if (target instanceof IdentifiableElement) {
			return ((IdentifiableElement) target).getName();
		} else {
			return super.getCrossRefText(owner, crossref, target, refTextEvaluator, linkTextRegion, status);
		}
	}

}
