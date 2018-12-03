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
package org.eclipse.n4js.scoping.imports;

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Aliased elements are contained in the scope with their real name, too to allow to validate an attempt to access them
 * without an alias.
 */
public class PlainAccessOfAliasedImportDescription extends AbstractDescriptionWithError {

	private final String alias;

	/**
	 * Creates a new instance that decorates the given delegate.
	 */
	protected PlainAccessOfAliasedImportDescription(IEObjectDescription delegate, String alias) {
		super(delegate);
		this.alias = alias;
	}

	/**
	 * Returns the alias.
	 */
	public String getAlias() {
		return alias;
	}

	@Override
	public String getMessage() {
		return IssueCodes.getMessageForIMP_PLAIN_ACCESS_OF_ALIASED_TYPE(getName(), alias);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.IMP_PLAIN_ACCESS_OF_ALIASED_TYPE;
	}

}
