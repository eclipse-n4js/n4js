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
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.ts.versions.VersionableUtils;
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
		if (VersionableUtils.isTVersionable(element)) {
			return new N4JSEObjectDescription(qualifiedName, (TVersionable) element, userData);
		} else {
			return EObjectDescription.create(qualifiedName, element, userData);
		}
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
	private N4JSEObjectDescription(QualifiedName qualifiedName, TVersionable element,
			Map<String, String> userData) {
		super(qualifiedName, element, userData);
	}

	@Override
	public String toString() {
		EObject objectOrProxy = this.getEObjectOrProxy();
		// Since the element is from the type model, the
		// element should never be a proxy
		if (!objectOrProxy.eIsProxy()) {
			// Casting to TVersionable is safe, since the constructor doesn't allow for any other value
			// and #element is final in the superclass
			return this.getName().toString() + N4IDLGlobals.VERSION_SEPARATOR
					+ ((TVersionable) objectOrProxy).getVersion();
		}
		// fall-back to super implementation
		return super.toString();
	}
}
