/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.resource;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * An {@link IEObjectDescription} that includes N4JS-specific information in its string-representation.
 */
public class N4JSEObjectDescription extends EObjectDescription {

	/**
	 * Static factory method for {@link N4JSEObjectDescription}.
	 *
	 * Falls back to {@link EObjectDescription#create} if the given name and element do not require any special
	 * {@link IEObjectDescription} implementation.
	 */
	public static IEObjectDescription create(String simpleName, EObject element) {
		return N4JSEObjectDescription.create(QualifiedName.create(simpleName), element);
	}

	/**
	 * Static factory method for {@link N4JSEObjectDescription}.
	 *
	 * Falls back to {@link EObjectDescription#create} if the given name and element do not require any special
	 * {@link IEObjectDescription} implementation.
	 */
	public static IEObjectDescription create(QualifiedName qualifiedName, EObject element) {
		return N4JSEObjectDescription.create(qualifiedName, element, Collections.emptyMap());
	}

	/**
	 * Static factory method for {@link N4JSEObjectDescription}.
	 *
	 * Falls back to {@link EObjectDescription#create} if the given element is not versionable.
	 */
	public static IEObjectDescription create(QualifiedName qualifiedName, EObject element,
			Map<String, String> userData) {
		return EObjectDescription.create(qualifiedName, element, userData);
	}

	/**
	 * Instantiates a new {@link N4JSEObjectDescription} with the given versionable element and qualified name.
	 *
	 * @param qualifiedName
	 *            The qualified name of the object
	 * @param element
	 *            The versionable element
	 * @param userData
	 *            The description user data
	 */
	private N4JSEObjectDescription(QualifiedName qualifiedName, Type element, Map<String, String> userData) {
		super(qualifiedName, element, userData);
	}

	@Override
	public String toString() {
		EObject objectOrProxy = this.getEObjectOrProxy();
		// Since the element is from the type model, the
		// element should never be a proxy
		if (!objectOrProxy.eIsProxy()) {
			return this.getName().toString();
		}
		// fall-back to super implementation
		return super.toString();
	}
}
