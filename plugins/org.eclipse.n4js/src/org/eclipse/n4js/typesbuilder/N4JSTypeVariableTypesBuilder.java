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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.GenericType;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;

import com.google.inject.Inject;

class N4JSTypeVariableTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	void relinkTypeParameters(GenericDeclaration decl, GenericType target) {
		EList<N4TypeVariable> n4TypeVars = decl.getTypeVars();
		EList<TypeVariable> typeVars = target.getTypeVars();
		int len = Math.min(n4TypeVars.size(), typeVars.size());
		for (var i = 0; i < len; i++) {
			relinkTypeParameter(n4TypeVars.get(i), typeVars.get(i));
		}
	}

	private void relinkTypeParameter(N4TypeVariable n4TypeVar, TypeVariable typeVar) {
		if (n4TypeVar == null || typeVar == null) {
			return;
		}
		_n4JSTypesBuilderHelper.ensureEqualName(n4TypeVar, typeVar);
		n4TypeVar.setDefinedTypeVariable(typeVar);
	}

	void addTypeParameters(GenericType target, GenericDeclaration decl, boolean preLinkingPhase) {
		target.getTypeVars().clear();
		target.getTypeVars().addAll(toList(map(decl.getTypeVars(), tv -> createTypeVariable(tv, preLinkingPhase))));
	}

	private TypeVariable createTypeVariable(N4TypeVariable n4TypeVar, boolean preLinkingPhase) {
		TypeVariable typeVar = TypesFactory.eINSTANCE.createTypeVariable();
		typeVar.setName(n4TypeVar.getName());
		typeVar.setDeclaredCovariant(n4TypeVar.isDeclaredCovariant());
		typeVar.setDeclaredContravariant(n4TypeVar.isDeclaredContravariant());
		if (!preLinkingPhase) {
			TypeReferenceNode<TypeRef> bound = n4TypeVar.getDeclaredUpperBoundNode();
			typeVar.setDeclaredUpperBound(TypeUtils.copyWithProxies(bound == null ? null : bound.getTypeRefInAST()));
			if (n4TypeVar.isOptional()) {
				TypeReferenceNode<TypeRef> argumentNode = n4TypeVar.getDeclaredDefaultArgumentNode();
				TypeRef typeRef = argumentNode == null ? null : argumentNode.getTypeRefInAST();
				if (typeRef == null) {
					if (typeVar.getDeclaredUpperBound() == null) {
						RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(n4TypeVar);
						typeRef = RuleEnvironmentExtensions.anyTypeRef(G);
					} else {
						typeRef = typeVar.getDeclaredUpperBound();
					}
				}
				typeVar.setDefaultArgument(TypeUtils.copyWithProxies(typeRef));
			}
		}

		n4TypeVar.setDefinedTypeVariable(typeVar);

		return typeVar;
	}
}
