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
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

/**
 * The abstract {@link FieldFactory} is the base class for the child classes {@link UnionFieldFactory} and
 * {@link IntersectionFieldFactory}. It implements the method {@link #create(String)} which gets its information through
 * abstract methods implemented in the child classes mentioned before The child classes are instantiated in
 * {@link IntersectionMemberFactory} and {@link UnionMemberFactory} respectively.
 */
abstract class FieldFactory implements MemberFactory {
	final ComposedMemberInfo cma;

	FieldFactory(ComposedMemberInfo cma) {
		this.cma = cma;
	}

	abstract MemberAccessModifier getAccessability();

	abstract boolean isFinal();

	abstract TypeRef getReturnTypeRef();

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public TField create(String name) {
		TField field = TypesFactory.eINSTANCE.createTField();
		field.setComposed(true);
		TypeRef typeRef = getReturnTypeRef();
		TypeUtils.setMemberTypeRef(field, typeRef);
		field.setName(name);
		field.setDeclaredFinal(isFinal());
		field.setDeclaredMemberAccessModifier(getAccessability());
		return field;
	}

	/** Class to implement logic with regard to fields in {@code Intersection Types}. */
	static class IntersectionFieldFactory extends FieldFactory {
		IntersectionFieldFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMax();
		}

		@Override
		boolean isFinal() {
			return cma.onlyReadOnlyFields();
		}

		@Override
		TypeRef getReturnTypeRef() {
			List<TypeRef> typeRefs = cma.getTypeRefsOfMemberType(MemberType.FIELD, MemberType.GETTER,
					MemberType.SETTER);
			TypeRef typeRef = typeRefs.get(0); // since this is a field, the typeRef is never a composed type
			return typeRef;
		}

		@Override
		public List<TMember> getConstituentMembers() {
			return cma.getConstituentMembers();
		}
	}

	/** Class to implement logic with regard to fields in {@code Union Types}. */
	static class UnionFieldFactory extends FieldFactory {
		UnionFieldFactory(ComposedMemberInfo cma) {
			super(cma);
		}

		@Override
		MemberAccessModifier getAccessability() {
			return cma.getAccessabilityMin();
		}

		@Override
		boolean isFinal() {
			return cma.hasReadOnlyField();
		}

		@Override
		TypeRef getReturnTypeRef() {
			List<TypeRef> typeRefs = cma.getTypeRefsOfMemberType(MemberType.FIELD);
			TypeRef typeRef = typeRefs.get(0); // since this is a field, the typeRef is never a composed type
			return typeRef;
		}

		@Override
		public List<TMember> getConstituentMembers() {
			return cma.getConstituentMembers();
		}
	}
}
