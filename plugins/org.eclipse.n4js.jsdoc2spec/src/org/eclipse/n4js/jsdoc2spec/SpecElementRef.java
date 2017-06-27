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
package org.eclipse.n4js.jsdoc2spec;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;

/**
 */
public class SpecElementRef {

	/**
	 * Key used in specinfo map for requirement ids (since there is no FQN)
	 */
	public static String reqidKey(String reqid) {
		return "@reqid:" + reqid;
	}

	/**
	 * The (top level) element to be specified, either a Type (incl. TFunction) of a TVariable.
	 */
	public final IdentifiableElement identifiableElement;

	/**
	 * In case of types, it may happen that the original element is filled with a poly fill.
	 */
	public Type polyfill = null;

	/**
	 * The requirement ID.
	 */
	public final String requirementID;

	/**
	 * Creates a specified element reference for a source code element.
	 */
	public SpecElementRef(IdentifiableElement identifiableElement) {
		this.identifiableElement = identifiableElement;
		this.requirementID = null;
	}

	/**
	 * Returns the identifiable element casted to type if it is a type, otherwise null.
	 */
	public Type getElementAsType() {
		if (identifiableElement instanceof Type) {
			return (Type) identifiableElement;
		}
		return null;
	}

	/**
	 * Returns the referenced types, which may be multiple in case of polyfills. The first one is the filled type.
	 */
	public List<Type> getTypes() {
		ArrayList<Type> types = new ArrayList<>(2);
		if (identifiableElement instanceof Type) {
			types.add((Type) identifiableElement);
		}
		if (polyfill != null) {
			types.add(polyfill);
		}
		return types;
	}

	/**
	 * Creates a specified element reference for a requirement id.
	 */
	public SpecElementRef(String requirementID) {
		this.identifiableElement = null;
		this.requirementID = requirementID;
	}

	/**
	 * Returns true if referenced element is a code element, i.e. {@link IdentifiableElement} is set.
	 */
	public boolean isCodeRef() {
		return identifiableElement != null;
	}

	/**
	 * Returns true if referenced element is a requirement, i.e. {@link #requirementID} is set.
	 */
	public boolean isReqRef() {
		return requirementID != null;
	}

	@Override
	public String toString() {
		if (requirementID != null) {
			return reqidKey(requirementID);
		}
		return identifiableElement.getName();
	}

}
