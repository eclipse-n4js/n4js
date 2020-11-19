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
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.scoping.IScopeProvider;

/**
 * Immutable value object containing information related to member scoping.
 */
public class MemberScopeRequest {

	/** Receiver of original scoping request. */
	public final TypeRef originalReceiverTypeRef;
	/** Context of original scoping request. */
	public final EObject context;
	/**
	 * Iff true, the scoping is requested to provide TMembers that are guaranteed to be contained in a resource. In this
	 * case, {@link #context} is guaranteed to be of type {@link MemberAccess}.
	 */
	public final boolean provideContainedMembers;
	/** Flag indicating whether visibility is to be checked. */
	public final boolean checkVisibility;
	/** Flag whether static or instance members are to be accessed. */
	public final boolean staticAccess;
	/**
	 * Flag indicating access of a member of a structural field initializer type, see
	 * {@link AbstractMemberScope#structFieldInitMode}.
	 */
	public final boolean structFieldInitMode;
	/** Flag whether the TypeRef is dynamic. */
	public final boolean isDynamicType;
	/** Flag whether the kind of access (read/write) should be respected. */
	public final boolean suppressAccessKind;

	/**
	 * Creates a new member scope request with given values, these values are directly accessible via fields.
	 *
	 * @param context
	 *            the context as defined by {@link IScopeProvider#getScope(EObject, org.eclipse.emf.ecore.EReference)}.
	 *            If <code>provideContainedMembers</code> this must be of type {@link MemberAccess}.
	 * @param provideContainedMembers
	 *            see {@link #provideContainedMembers}.
	 */
	public MemberScopeRequest(TypeRef originalReceiverTypeRef, EObject context, boolean provideContainedMembers,
			boolean checkVisibility, boolean staticAccess, boolean structFieldInitMode, boolean isDynamicType,
			boolean suppressAccessKind) {

		if (provideContainedMembers && !(context instanceof MemberAccess)) {
			throw new IllegalStateException(
					"member scoping can only guarantee to provide contained members if given a context of type "
							+ MemberAccess.class.getSimpleName());
		}
		this.originalReceiverTypeRef = originalReceiverTypeRef;
		this.context = context;
		this.provideContainedMembers = provideContainedMembers;
		this.checkVisibility = checkVisibility;
		this.staticAccess = staticAccess;
		this.structFieldInitMode = structFieldInitMode;
		this.isDynamicType = isDynamicType;
		this.suppressAccessKind = suppressAccessKind;
	}

	/**
	 * Returns a request similar to the current one, enforcing the static access flag to be true.
	 */
	public MemberScopeRequest enforceStatic() {
		if (staticAccess) {
			return this;
		}
		return new MemberScopeRequest(originalReceiverTypeRef, context, provideContainedMembers, checkVisibility,
				true, structFieldInitMode, isDynamicType, suppressAccessKind);
	}

	/**
	 * Returns a request similar to the current one, enforcing the static access flag to be false.
	 */
	public MemberScopeRequest enforceInstance() {
		if (!staticAccess) {
			return this;
		}
		return new MemberScopeRequest(originalReceiverTypeRef, context, provideContainedMembers, checkVisibility,
				false, structFieldInitMode, isDynamicType, suppressAccessKind);
	}

	/**
	 * Returns a request similar to the receiving one, but with the given {@link #structFieldInitMode structural field
	 * init mode}.
	 */
	public MemberScopeRequest setStructFieldInitMode(boolean structFieldInitMode) {
		if (this.structFieldInitMode == structFieldInitMode) {
			return this;
		}
		return new MemberScopeRequest(originalReceiverTypeRef, context, provideContainedMembers, checkVisibility,
				staticAccess, structFieldInitMode, isDynamicType, suppressAccessKind);
	}
}
