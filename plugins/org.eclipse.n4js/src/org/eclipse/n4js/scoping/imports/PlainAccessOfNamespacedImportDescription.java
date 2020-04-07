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
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Namespaced elements are contained in the scope with their exported name to allow validation of an attempt to access
 * them without an namespace.
 */
public class PlainAccessOfNamespacedImportDescription extends AbstractDescriptionWithError {

	private final QualifiedName namespacedName;

	/**
	 * Creates a new instance that decorates the given delegate.
	 */
	public PlainAccessOfNamespacedImportDescription(IEObjectDescription delegate, QualifiedName namespacedName) {
		super(delegate);
		this.namespacedName = namespacedName;
	}

	@Override
	public String getMessage() {
		return IssueCodes.getMessageForIMP_PLAIN_ACCESS_OF_NAMESPACED_TYPE(getName(), namespacedName);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.IMP_PLAIN_ACCESS_OF_NAMESPACED_TYPE;
	}

	/**
	 * @return return namespaced version of the wrapped object name
	 */
	public QualifiedName getNamespacedName() {
		return this.namespacedName;
	}

}
