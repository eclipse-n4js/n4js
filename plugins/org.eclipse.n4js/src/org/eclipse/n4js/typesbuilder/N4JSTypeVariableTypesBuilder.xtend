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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.ts.types.GenericType
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils

package class N4JSTypeVariableTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def package void relinkTypeParameters(GenericDeclaration decl, GenericType target) {
		val n4TypeVars = decl.typeVars;
		val typeVars = target.typeVars;
		val len = Math.min(n4TypeVars.size, typeVars.size);
		for (var i = 0; i < len; i++) {
			relinkTypeParameter(n4TypeVars.get(i), typeVars.get(i));
		}
	}

	def private void relinkTypeParameter(N4TypeVariable n4TypeVar, TypeVariable typeVar) {
		if (n4TypeVar === null || typeVar === null) {
			return;
		}
		ensureEqualName(n4TypeVar, typeVar);
		n4TypeVar.definedTypeVariable = typeVar;
	}

	def package void addTypeParameters(GenericType target, GenericDeclaration decl, boolean preLinkingPhase) {
		target.typeVars.clear();
		target.typeVars += decl.typeVars.map[createTypeVariable(it, preLinkingPhase)];
	}

	def private TypeVariable createTypeVariable(N4TypeVariable n4TypeVar, boolean preLinkingPhase) {
		val typeVar = TypesFactory.eINSTANCE.createTypeVariable();
		typeVar.name = n4TypeVar.name;
		typeVar.declaredCovariant = n4TypeVar.declaredCovariant;
		typeVar.declaredContravariant = n4TypeVar.declaredContravariant;
		if (!preLinkingPhase) {
			typeVar.declaredUpperBound = TypeUtils.copyWithProxies(n4TypeVar.declaredUpperBoundNode?.typeRefInAST);
		}

		n4TypeVar.definedTypeVariable = typeVar;

		return typeVar;
	}
}
