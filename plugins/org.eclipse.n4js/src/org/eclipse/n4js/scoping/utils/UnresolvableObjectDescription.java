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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;

/**
 * This description is created if a reference cannot be resolved, but no error should be created because the receiver is
 * marked as dynamic.
 */
public class UnresolvableObjectDescription extends EObjectDescription {

	/**
	 * @param qualifiedName
	 *            the EObject description to wrap
	 */
	public UnresolvableObjectDescription(QualifiedName qualifiedName) {
		super(qualifiedName, null, null);
	}

	@Override
	public URI getEObjectURI() {
		return null;
	}

	@Override
	public EClass getEClass() {
		return null;
	}
}
