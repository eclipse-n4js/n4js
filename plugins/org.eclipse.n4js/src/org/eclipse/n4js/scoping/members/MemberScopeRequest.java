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
package org.eclipse.n4js.scoping.members;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * Immutable value object containing information related to member scoping.
 */
public class MemberScopeRequest {

	/** Receiver of original scoping request. */
	public final TypeRef originalReceiverTypeRef;
	/** Context of original scoping request. */
	public final EObject context;
	/** Flag indicating whether visibility is to be checked. */
	public final boolean checkVisibility;
	/** Flag whether static or instance members are to be accessed. */
	public final boolean staticAccess;

	/**
	 * Creates a new memeber scope request with given values, these values are directly accessibly via fields.
	 */
	public MemberScopeRequest(TypeRef originalReceiverTypeRef, EObject context, boolean checkVisibility,
			boolean staticAccess) {
		this.originalReceiverTypeRef = originalReceiverTypeRef;
		this.context = context;
		this.checkVisibility = checkVisibility;
		this.staticAccess = staticAccess;
	}

	/**
	 * Returns a request similar to the current one, enforcing the static access flag to be true.
	 */
	public MemberScopeRequest enforceStatic() {
		if (staticAccess) {
			return this;
		}
		return new MemberScopeRequest(originalReceiverTypeRef, context, checkVisibility, true);
	}

	/**
	 * Returns a request similar to the current one, enforcing the static access flag to be false.
	 */
	public MemberScopeRequest enforceInstance() {
		if (!staticAccess) {
			return this;
		}
		return new MemberScopeRequest(originalReceiverTypeRef, context, checkVisibility, false);
	}

}
