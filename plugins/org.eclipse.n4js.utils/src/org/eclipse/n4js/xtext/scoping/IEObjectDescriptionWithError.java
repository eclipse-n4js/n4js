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
package org.eclipse.n4js.xtext.scoping;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;

/**
 * Object description used during scoping indicating a wrong binding. That is, the reference can be resolved although
 * the binding will be incorrect, e.g., in case of invisible targets.
 * <p>
 * Do not check for instances of this classifier directly (and do not cast), instead use utility methods
 * {@link #isErrorDescription(IEObjectDescription)} and {@link #getDescriptionWithError(IEObjectDescription)}
 * respectively.
 * <p>
 * This mechanism is used in <code>org.eclipse.n4js.resource.ErrorAwareLinkingService#addError</code>
 */
public interface IEObjectDescriptionWithError extends IEObjectDescription {

	/**
	 * @return the error message as string.
	 */
	public abstract String getMessage();

	/**
	 * @return the issue code.
	 */
	public abstract String getIssueCode();

	/**
	 * Helper method to be used instead of instanceof-checks, as it also covers {@link AliasedEObjectDescription}s.
	 *
	 * @return true if given description is an (aliased) IEObjectDescriptionWithError
	 */
	public static boolean isErrorDescription(final IEObjectDescription eObjectDescription) {
		if (eObjectDescription instanceof IEObjectDescriptionWithError) {
			return true;
		}
		if (eObjectDescription instanceof AliasedEObjectDescription) {
			return isErrorDescription(((AliasedEObjectDescription) eObjectDescription).getAliasedEObjectDescription());
		}
		return false;
	}

	/**
	 * Helper method to be used casting to IEObjectDescriptionWithError as it also covers
	 * {@link AliasedEObjectDescription}s.
	 *
	 * @return the casted (or delegated) IEObjectDescriptionWithError, or null if description does not contain an error.
	 */
	public static IEObjectDescriptionWithError getDescriptionWithError(final IEObjectDescription eObjectDescription) {
		if (eObjectDescription instanceof IEObjectDescriptionWithError) {
			return (IEObjectDescriptionWithError) eObjectDescription;
		}
		if (eObjectDescription instanceof AliasedEObjectDescription) {
			return getDescriptionWithError(((AliasedEObjectDescription) eObjectDescription)
					.getAliasedEObjectDescription());
		}
		return null;
	}
}
