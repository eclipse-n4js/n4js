/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesbuilder;

import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;

class N4JSTypeAliasDeclarationTypesBuilder {

	@Inject
	N4JSTypeVariableTypesBuilder _n4JSTypeVariableTypesBuilder;
	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	protected boolean relinkTypeAlias(N4TypeAliasDeclaration n4TypeAlias, AbstractNamespace target,
			@SuppressWarnings("unused") boolean preLinkingPhase, int idx) {

		if (n4TypeAlias.getName() == null) { // may be null due to syntax errors
			return false;
		}

		TypeAlias typeAlias = (TypeAlias) target.getTypes().get(idx);

		_n4JSTypesBuilderHelper.ensureEqualName(n4TypeAlias, typeAlias);
		typeAlias.setAstElement(n4TypeAlias);
		n4TypeAlias.setDefinedType(typeAlias);

		return true;
	}

	void createTypeAlias(N4TypeAliasDeclaration n4TypeAlias, AbstractNamespace target, boolean preLinkingPhase) {
		if (n4TypeAlias.getName() == null) { // may be null due to syntax errors
			return;
		}

		TypeAlias typeAlias = TypesFactory.eINSTANCE.createTypeAlias();
		typeAlias.setName(n4TypeAlias.getName());

		_n4JSTypesBuilderHelper.setTypeAccessModifier(typeAlias, n4TypeAlias);
		_n4JSTypeVariableTypesBuilder.addTypeParameters(typeAlias, n4TypeAlias, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(typeAlias, n4TypeAlias, preLinkingPhase);

		if (!preLinkingPhase) {
			typeAlias.setTypeRef(TypeUtils.copyWithProxies(n4TypeAlias.getDeclaredTypeRefInAST()));
		}

		typeAlias.setAstElement(n4TypeAlias);
		n4TypeAlias.setDefinedType(typeAlias);

		target.getTypes().add(typeAlias);
	}
}
