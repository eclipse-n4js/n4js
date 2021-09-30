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

import java.util.List;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

/**
 * The abstract {@link GetterFactory} is the base class for the child classes {@link UnionGetterFactory} and
 * {@link IntersectionGetterFactory}. It implements the method {@link #create(String)} which gets its information
 * through abstract methods implemented in the child classes mentioned before. The sub classes are instantiated in
 * {@link IntersectionMemberFactory} and {@link UnionMemberFactory} respectively.
 */
abstract class GetterFactory implements MemberFactory {
	final ComposedMemberInfo cma;

	GetterFactory(ComposedMemberInfo cma) {
		this.cma = cma;
	}

	abstract MemberAccessModifier getAccessability();

	abstract TypeRef getReturnTypeRefComposition();

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public TGetter create(String name) {
		TGetter getter = TypesFactory.eINSTANCE.createTGetter();
		getter.setComposed(true);
		TypeRef typeRef = getReturnTypeRefComposition();
		TypeUtils.setMemberTypeRef(getter, typeRef);
		getter.setName(name);
		getter.setDeclaredMemberAccessModifier(getAccessability());
		return getter;
	}

	/** Class to implement logic with regard to getters in {@code Intersection Types}. */
	static class IntersectionGetterFactory extends GetterFactory {
		IntersectionGetterFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMax();
		}

		@Override
		TypeRef getReturnTypeRefComposition() {
			List<TypeRef> typeRefs = cma.getTypeRefsOfMemberType(MemberType.GETTER, MemberType.FIELD);
			return cma.getTypeSystem().createSimplifiedIntersection(typeRefs, cma.getResource());
		}

		@Override
		public List<TMember> getConstituentMembers() {
			return cma.getConstituentMembers();
		}
	}

	/** Class to implement logic with regard to getters in {@code Union Types}. */
	static class UnionGetterFactory extends GetterFactory {
		UnionGetterFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMin();
		}

		@Override
		TypeRef getReturnTypeRefComposition() {
			List<TypeRef> typeRefs = cma.getTypeRefsOfMemberType(MemberType.GETTER, MemberType.FIELD);
			return cma.getTypeSystem().createSimplifiedUnion(typeRefs, cma.getResource());
		}

		@Override
		public List<TMember> getConstituentMembers() {
			return cma.getConstituentMembers();
		}
	}

}
