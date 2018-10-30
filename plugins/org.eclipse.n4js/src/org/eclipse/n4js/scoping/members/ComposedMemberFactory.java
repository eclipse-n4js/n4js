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
package org.eclipse.n4js.scoping.members;

import static org.eclipse.n4js.ts.types.MemberType.FIELD;
import static org.eclipse.n4js.ts.types.MemberType.GETTER;
import static org.eclipse.n4js.ts.types.MemberType.METHOD;
import static org.eclipse.n4js.ts.types.MemberType.SETTER;

import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

import org.eclipse.n4js.typesystem.utils.RuleEnvironment;

/**
 * Abstract class for creating composed members.
 */
abstract public class ComposedMemberFactory implements MemberFactory {
	/** The data holder of aggregated siblings on which the new {@link TMember} is based upon */
	final protected ComposedMemberInfo cmi;
	/** The {@link MemberFactory} to create the new {@link TMember} */
	final protected MemberFactory specialMemberFactory;
	/** The new {@link MemberType} */
	final protected MemberType memberType;

	ComposedMemberFactory(ComposedMemberInfo cmi) {
		this.cmi = cmi;
		this.memberType = getNewMemberType();
		this.specialMemberFactory = (memberType != null) ? getMemberFactory() : null;
	}

	/** Returns the {@link MemberType} of the new composed {@link TMember}. */
	abstract protected MemberType getNewMemberType();

	/** Returns the {@link MemberFactory} which creates the {@link TMember}. */
	abstract protected MemberFactory getMemberFactory();

	/** Returns true iff there exist one or more siblings on which a new composed {@link TMember} is based upon. */
	public boolean isEmpty() {
		return cmi.isEmpty();
	}

	@Override
	public TMember create(String name) {
		if (specialMemberFactory == null)
			return null;
		TMember composedMember = specialMemberFactory.create(name);
		composedMember.getConstituentMembers().addAll(getConstituentMembers());
		return composedMember;
	}

	/**
	 * Compares the return types of the new {@link TMember}.
	 * <p>
	 * Returns true iff no new composition type is created. That is, all return {@link TypeRef}s of the siblings are
	 * equal.
	 */
	protected boolean allTypeRefAreEqual() {
		N4JSTypeSystem ts = cmi.getTypeSystem();
		List<TypeRef> allTypeRefs = cmi.getTypeRefsOfMemberType(METHOD, FIELD, GETTER, SETTER);
		TypeRef siTypeRef = ts.createSimplifiedIntersection(allTypeRefs, cmi.getResource());

		Iterator<TypeRef> typeRefIt = allTypeRefs.iterator();
		while (typeRefIt.hasNext()) {
			TypeRef firstNonNullTypeRef = typeRefIt.next();
			if (firstNonNullTypeRef != null) {
				RuleEnvironment G = cmi.getRuleEnvironmentForTypeRef(firstNonNullTypeRef);
				boolean equalTypeRefs = ts.equaltypeSucceeded(G, firstNonNullTypeRef, siTypeRef);
				return equalTypeRefs;
			}
		}
		return true;
	}
}
