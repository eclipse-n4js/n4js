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

import org.eclipse.n4js.n4idl.versioning.VersionUtils;
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xtext.scoping.FilteringScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * A custom {@link IEObjectDescriptionWithError} that indicates users, that the name binding may note be accurate since
 * the context version of the scoping context could not be computed (e.g. There may not be a context version in the
 * current context).
 */
public class FailedToInferContextVersionDescription extends AbstractDescriptionWithError {

	/**
	 * Instantiates a new {@link FailedToInferContextVersionDescription} wrapping around the given delegate description.
	 */
	protected FailedToInferContextVersionDescription(IEObjectDescription delegate) {
		super(delegate);
	}

	@Override
	public String getMessage() {
		return IssueCodes.getMessageForIDL_FAILED_TO_INFER_CONTEXT_VERSION(getDelegate().getName().toString());
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.IDL_FAILED_TO_INFER_CONTEXT_VERSION;
	}

	/**
	 * A scope that wraps all the results of the parent-scope that represent {@link TVersionable} elements in a
	 * {@link FailedToInferContextVersionDescription}.
	 */
	public static final class TVersionableWrappingScope extends FilteringScope {

		/**
		 * Instantiates a new {@link TVersionableWrappingScope} that wraps around the given scope and decorates all of
		 * its descriptions.
		 */
		public TVersionableWrappingScope(IScope parent) {
			super(parent, d -> {
				if (d.getEObjectOrProxy() instanceof TVersionable) {
					return !VersionUtils.isTVersionable(d.getEObjectOrProxy());
				} else {
					// leave all other descriptions un-decorated
					return true;
				}
			});
		}

		@Override
		protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription description) {
			return new FailedToInferContextVersionDescription(description);
		}
	}
}
