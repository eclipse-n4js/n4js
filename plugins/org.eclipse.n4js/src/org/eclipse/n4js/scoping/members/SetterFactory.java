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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.scoping.members.ComposedMemberInfo.ComposedFParInfo;
import org.eclipse.n4js.scoping.members.MethodFactory.FParFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * The abstract {@link SetterFactory} is the base class for the child classes {@link UnionSetterFactory} and
 * {@link IntersectionSetterFactory}. It implements the method {@link #create(String)} which gets its information
 * through abstract methods implemented in the child classes mentioned before. The sub classes are instantiated in
 * {@link IntersectionMemberFactory} and {@link UnionMemberFactory} respectively.
 * <p>
 * This class also defines the class {@link StandaloneFPar} which is based upon the class {@link FParFactory} and reuses
 * its method {@link FParFactory#create()}.
 */
abstract class SetterFactory implements MemberFactory {
	final ComposedMemberInfo cma;
	final StandaloneFPar fpar;

	SetterFactory(ComposedMemberInfo cma) {
		this.cma = cma;
		String name = "arg0";
		List<TypeRef> typeRefs = new LinkedList<>();
		List<ComposedFParInfo> fpars = cma.getFParAggregates();
		if (fpars != null && !fpars.isEmpty()) {
			ComposedFParInfo firstFpar = fpars.get(0);
			name = firstFpar.getName();
			typeRefs.addAll(firstFpar.getTypeRefs());
		}
		this.fpar = new StandaloneFPar(name, typeRefs);
	}

	abstract MemberAccessModifier getAccessability();

	abstract TypeRef getReturnTypeRefComposition();

	@Override
	public TSetter create(String name) {
		TSetter setter = TypesFactory.eINSTANCE.createTSetter();
		setter.setComposed(true);
		setter.setName(name);
		setter.setDeclaredMemberAccessModifier(getAccessability());
		setter.setFpar(fpar.create());
		return setter;
	}

	/** Class to implement logic with regard to setters in {@code Intersection Types}. */
	static class IntersectionSetterFactory extends SetterFactory {
		IntersectionSetterFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		public boolean isValid() {
			return !cma.onlyReadOnlyFields();
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMax();
		}

		@Override
		TypeRef getReturnTypeRefComposition() {
			List<TypeRef> typeRefs = cma.getTypeRefsOfMemberType(MemberType.SETTER, MemberType.FIELD);
			return cma.getTypeSystem().createSimplifiedUnion(typeRefs, cma.getResource());
		}
	}

	/** Class to implement logic with regard to setters in {@code Union Types}. */
	static class UnionSetterFactory extends SetterFactory {
		UnionSetterFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		public boolean isValid() {
			return !cma.hasReadOnlyField();
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMin();
		}

		@Override
		TypeRef getReturnTypeRefComposition() {
			List<TypeRef> typeRefs = cma.getTypeRefsOfMemberType(MemberType.SETTER, MemberType.FIELD);
			return cma.getTypeSystem().createSimplifiedIntersection(typeRefs, cma.getResource());
		}

	}

	/** Class to create formal parameters of setters. */
	class StandaloneFPar extends FParFactory {
		final String name;
		final List<TypeRef> typeRefs;

		StandaloneFPar(String name, List<TypeRef> typeRefs) {
			this.name = name;
			this.typeRefs = typeRefs;
		}

		@Override
		String getName() {
			return name;
		}

		@Override
		List<TypeRef> getTypeRefs() {
			return typeRefs;
		}

		@Override
		TypeRef getFParTypeRefComposition(List<TypeRef> typeRefsToUse) {
			return getReturnTypeRefComposition();
		}
	}
}
