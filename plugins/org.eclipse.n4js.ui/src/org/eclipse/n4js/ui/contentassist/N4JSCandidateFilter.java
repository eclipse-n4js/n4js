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
package org.eclipse.n4js.ui.contentassist;

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ui.proposals.imports.ImportsAwareReferenceProposalCreator;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;

/**
 * This predicate is passed to concrete proposal creators, such as {@link ImportsAwareReferenceProposalCreator}.
 */
public class N4JSCandidateFilter implements Predicate<IEObjectDescription> {

	@Override
	public boolean apply(IEObjectDescription candidate) {
		QualifiedName qualifiedName = candidate.getQualifiedName();
		final IEObjectDescription eObjectDescription = candidate;
		// Don't propose any erroneous descriptions.
		return !IEObjectDescriptionWithError.isErrorDescription(eObjectDescription)
				&& !N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(qualifiedName.getFirstSegment())
				&& !N4TSQualifiedNameProvider.isModulePolyfill(qualifiedName)
				&& !N4TSQualifiedNameProvider.isPolyfill(qualifiedName);
	}
}
