/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.scoping;

import org.eclipse.n4js.scoping.utils.IssueCodeBasedEObjectDescription;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.ts.versions.VersionableUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xtext.scoping.FilteringScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * A scope that wraps all the results of the parent-scope that represent {@link TVersionable} elements in an
 * {@link IssueCodeBasedEObjectDescription} with issue code {@link IssueCodes#IDL_FAILED_TO_INFER_CONTEXT_VERSION}.
 */
public final class FailedToInferContextVersionWrappingScope extends FilteringScope {

	/**
	 * Instantiates a new {@link FailedToInferContextVersionWrappingScope} that wraps around the given scope and
	 * decorates all of its descriptions.
	 */
	public FailedToInferContextVersionWrappingScope(IScope parent) {
		super(parent, d -> {
			if (d.getEObjectOrProxy() instanceof TVersionable) {
				return !VersionableUtils.isTVersionable(d.getEObjectOrProxy());
			} else {
				// leave all other descriptions un-decorated
				return true;
			}
		});
	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription description) {
		return new IssueCodeBasedEObjectDescription(description,
				IssueCodes.getMessageForIDL_FAILED_TO_INFER_CONTEXT_VERSION(description.getName().toString()),
				IssueCodes.IDL_FAILED_TO_INFER_CONTEXT_VERSION);
	}
}
