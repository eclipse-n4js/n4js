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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * This description wraps an invisible member.
 */
public class RestrictedUsageDescription extends AbstractDescriptionWithError {

	private final String jsVariant;

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 * @param jsVariant
	 *            Variant in which the restriction appeared.
	 */
	public RestrictedUsageDescription(IEObjectDescription delegate, String jsVariant) {
		super(delegate);
		this.jsVariant = jsVariant;

	}

	@Override
	public String getMessage() {
		String memberName = getName().getLastSegment();
		return IssueCodes.VIS_RESTRITCTED_USAGE.getMessage(memberName, jsVariant);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.VIS_RESTRITCTED_USAGE.name();
	}
}
