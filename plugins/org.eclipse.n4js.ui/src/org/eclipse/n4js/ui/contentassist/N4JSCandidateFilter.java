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

import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ui.proposals.imports.ImportsAwareReferenceProposalCreator;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;

/**
 * This predicate is passed to concrete proposal creators, such as {@link ImportsAwareReferenceProposalCreator}.
 */
public class N4JSCandidateFilter implements Predicate<IEObjectDescription> {

	@Override
	public boolean apply(IEObjectDescription candidate) {
		// Don't propose any erroneous descriptions.
		return !AbstractDescriptionWithError.isErrorDescription_XTEND_MVN_BUG_HACK(candidate)
				&& !N4TSQualifiedNameProvider.isModulePolyfill(candidate.getQualifiedName()) // IDE-1735 filter "!MPOLY"
																								// entries.
				&& !N4TSQualifiedNameProvider.isPolyfill(candidate.getQualifiedName());
	}
}
