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
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

/**
 * The abstract {@link MethodFactory} is the base class for the child classes {@link UnionMethodFactory} and
 * {@link IntersectionMethodFactory}. It implements the method {@link #create(String)} which gets its information
 * through abstract methods implemented in the child classes mentioned before The child classes are instantiated in
 * {@link IntersectionMemberFactory} and {@link UnionMemberFactory} respectively.
 * <p>
 * Additionally, the base class {@link FParFactory} for formal parameters including its method
 * {@link FParFactory#create()} is defined here. This class is used here to eventually create formal parameters of
 * methods, and moreover it is used in {@link SetterFactory} to create the formal parameter of setters.
 */
abstract class MethodFactory implements MemberFactory {
	final ComposedMemberInfo cma;
	final List<MethodFParFactory> fpas = new LinkedList<>();

	MethodFactory(ComposedMemberInfo cma) {
		this.cma = cma;
		List<ComposedFParInfo> fpars = cma.getFParAggregates();
		for (int fparIdx = 0; fparIdx < fpars.size(); fparIdx++) {
			final ComposedFParInfo curr = fpars.get(fparIdx);
			fpas.add(getFParFactory(curr));
		}
	}

	abstract MemberAccessModifier getAccessability();

	abstract MethodFParFactory getFParFactory(ComposedFParInfo fpa);

	abstract TypeRef getReturnTypeRefComposition();

	abstract TypeRef getFParTypeRefComposition(List<TypeRef> typeRefsToUse);

	@Override
	public boolean isValid() {
		if (cma.isEmpty())
			return false;

		for (MethodFParFactory fpa : fpas) {
			if (!fpa.isValid()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public TMethod create(String name) {
		TMethod method = TypesFactory.eINSTANCE.createTMethod();
		method.setComposed(true);
		method.setDeclaredMemberAccessModifier(getAccessability());
		method.setName(name);
		TypeUtils.setMemberTypeRef(method, getReturnTypeRefComposition());
		if (!fpas.isEmpty()) {
			boolean variFparNecessary = cma.isVariadicButLastFParIsDifferent();
			if (variFparNecessary) {
				List<ComposedFParInfo> fpAggrs = cma.getFParAggregates();
				ComposedFParInfo lastFPAggr = fpAggrs.get(cma.getFParAggregates().size() - 1);
				List<TypeRef> variadicTypeRefs = lastFPAggr.getTypeRefsVariadicAccumulated();
				MethodFParFactory varpar = new NewLastVariadicFPar(variadicTypeRefs);
				fpas.add(varpar);
			}
		}
		for (MethodFParFactory currFparDesc : fpas) {
			TFormalParameter tFPar = currFparDesc.create();
			method.getFpars().add(tFPar);
		}
		return method;
	}

	/** Base class for any formal parameter. It implements the {@link #create()} method. */
	static abstract class FParFactory {

		abstract String getName();

		abstract List<TypeRef> getTypeRefs();

		abstract TypeRef getFParTypeRefComposition(List<TypeRef> typeRefsToUse);

		boolean isOptional() {
			return false;
		}

		boolean isValid() {
			return true;
		}

		boolean isVariadic() {
			return false;
		}

		TFormalParameter create() {
			final TFormalParameter fpar = TypesFactory.eINSTANCE.createTFormalParameter();
			fpar.setName(getName());
			List<TypeRef> typeRefsToUse = getTypeRefs();
			TypeRef paramCompTR1 = getFParTypeRefComposition(typeRefsToUse);
			TypeRef paramCompTR = paramCompTR1;
			fpar.setTypeRef(paramCompTR);
			fpar.setVariadic(isVariadic());
			fpar.setHasInitializerAssignment(isOptional());
			return fpar;
		}
	}

	/** Base class for formal parameters of composed methods. */
	abstract class MethodFParFactory extends FParFactory {
		final ComposedFParInfo fpa;
		final int index;

		MethodFParFactory(ComposedFParInfo fpa) {
			this.fpa = fpa;
			this.index = cma.getFParAggregates().indexOf(fpa);
		}

		@Override
		abstract boolean isOptional();

		@Override
		TypeRef getFParTypeRefComposition(List<TypeRef> typeRefsToUse) {
			return MethodFactory.this.getFParTypeRefComposition(typeRefsToUse);
		}

		@Override
		String getName() {
			return fpa.getName();
		}

		@Override
		List<TypeRef> getTypeRefs() {
			List<TypeRef> typeRefsToUse = new LinkedList<>();
			typeRefsToUse.addAll(fpa.getTypeRefsVariadicAccumulated());
			// remove those refs that will be added by: typeRefsToUse.addAll(fpa.getTypeRefs())
			for (int i = 0; i < fpa.getTypeRefsVariadic().size(); i++) {
				typeRefsToUse.remove(typeRefsToUse.size() - 1);
			}
			typeRefsToUse.addAll(fpa.getTypeRefs());
			return typeRefsToUse;
		}

		@Override
		boolean isValid() {
			if (fpa == null)
				return false;
			// if (!fpa.getTypeRefsVariadic().isEmpty() && getNext() != null)
			if (fpa.hasValidationProblem())
				return false;
			return true;
		}

		MethodFParFactory getPrev() {
			if (index <= 0)
				return null;
			return fpas.get(index - 1);
		}

		MethodFParFactory getNext() {
			if (index + 1 >= fpas.size())
				return null;
			return fpas.get(index + 1);
		}

		boolean isLast() {
			if (index == fpas.size() - 1)
				return true;
			return false;
		}

		@Override
		boolean isVariadic() {
			if (isLast() && !fpa.getTypeRefsVariadicAccumulated().isEmpty())
				return true;
			return false;
		}
	}

	/** Class to instantiate an additional formal parameter which does not exist in any of the siblings. */
	class NewLastVariadicFPar extends MethodFParFactory {
		final private String name = "vari";
		final private List<TypeRef> typeRefs;

		NewLastVariadicFPar(List<TypeRef> typeRefs) {
			super(null);
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
		boolean isOptional() {
			return false;
		}

		@Override
		public boolean isValid() {
			return true;
		}

		@Override
		boolean isVariadic() {
			return true;
		}
	}

	/** Class to implement logic with regard to methods in {@code Intersection Types}. */
	static class IntersectionMethodFactory extends MethodFactory {
		IntersectionMethodFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMax();
		}

		@Override
		MethodFParFactory getFParFactory(ComposedFParInfo fpa) {
			return new IntersectionFPar(fpa);
		}

		@Override
		TypeRef getReturnTypeRefComposition() {
			List<TypeRef> typeRefs = cma.getMethodTypeRefsNonVoid();
			if (typeRefs.isEmpty()) {
				typeRefs.addAll(cma.getMethodTypeRefsVoid());
			}
			return cma.getTypeSystem().createSimplifiedIntersection(typeRefs, cma.getResource());
		}

		@Override
		protected TypeRef getFParTypeRefComposition(List<TypeRef> typeRefsToUse) {
			return cma.getTypeSystem().createSimplifiedUnion(typeRefsToUse, cma.getResource());
		}

		/** Class to implement logic with regard to formal parameter in {@code Intersection Type} methods. */
		class IntersectionFPar extends MethodFParFactory {
			IntersectionFPar(ComposedFParInfo fpa) {
				super(fpa);
			}

			@Override
			protected boolean isOptional() {
				if (isVariadic())
					return false;
				if (!fpa.allNonOptional())
					return true;

				MethodFParFactory prevFpar = getPrev();
				if (prevFpar != null)
					return prevFpar.isOptional();

				return false;
			}
		}

		@Override
		public List<TMember> getConstituentMembers() {
			return cma.getConstituentMembers();
		}
	}

	/** Class to implement logic with regard to methods in {@code Union Types}. */
	static class UnionMethodFactory extends MethodFactory {
		UnionMethodFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMin();
		}

		@Override
		MethodFParFactory getFParFactory(ComposedFParInfo fpa) {
			return new UnionFPar(fpa);
		}

		@Override
		TypeRef getReturnTypeRefComposition() {
			List<TypeRef> typeRefs = cma.getMethodTypeRefsVoid();
			if (typeRefs.isEmpty()) {
				typeRefs.addAll(cma.getMethodTypeRefsNonVoid());
			}
			return cma.getTypeSystem().createSimplifiedUnion(typeRefs, cma.getResource());
		}

		@Override
		protected TypeRef getFParTypeRefComposition(List<TypeRef> typeRefsToUse) {
			return cma.getTypeSystem().createSimplifiedIntersection(typeRefsToUse, cma.getResource());
		}

		/** Class to implement logic with regard to formal parameter in {@code Union Type} methods. */
		class UnionFPar extends MethodFParFactory {
			UnionFPar(ComposedFParInfo fpa) {
				super(fpa);
			}

			@Override
			protected boolean isOptional() {
				if (isVariadic())
					return false;
				if (fpa.allOptional())
					return true;

				MethodFParFactory prevFpar = getPrev();
				if (prevFpar != null)
					return prevFpar.isOptional();

				return false;
			}
		}

		@Override
		public List<TMember> getConstituentMembers() {
			return cma.getConstituentMembers();
		}
	}

}
