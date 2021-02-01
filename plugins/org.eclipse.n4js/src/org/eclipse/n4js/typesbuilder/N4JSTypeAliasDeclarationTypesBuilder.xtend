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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils

class N4JSTypeAliasDeclarationTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def protected boolean relinkTypeAlias(N4TypeAliasDeclaration n4TypeAlias, TModule target, boolean preLinkingPhase, int idx) {
		if (n4TypeAlias.name === null) { // may be null due to syntax errors
			return false;
		}

		val TypeAlias typeAlias = target.topLevelTypes.get(idx) as TypeAlias

		ensureEqualName(n4TypeAlias, typeAlias);
		typeAlias.astElement = n4TypeAlias;
		n4TypeAlias.definedType = typeAlias;

		return true;
	}

	def package void createTypeAlias(N4TypeAliasDeclaration n4TypeAlias, TModule target, boolean preLinkingPhase) {
		if(n4TypeAlias.name === null) { // may be null due to syntax errors
			return;
		}

		val typeAlias = TypesFactory.eINSTANCE.createTypeAlias();
		typeAlias.name = n4TypeAlias.name;
		typeAlias.exportedName = n4TypeAlias.exportedName;

		typeAlias.setTypeAccessModifier(n4TypeAlias);
		typeAlias.addCopyOfTypeParameters(n4TypeAlias, preLinkingPhase);

		typeAlias.copyAnnotations(n4TypeAlias, preLinkingPhase);

		if (!preLinkingPhase) {
			typeAlias.typeRef = TypeUtils.copyWithProxies(n4TypeAlias.declaredTypeRefInAST);
		}

		typeAlias.astElement = n4TypeAlias;
		n4TypeAlias.definedType = typeAlias;

		target.topLevelTypes += typeAlias;
	}
}
