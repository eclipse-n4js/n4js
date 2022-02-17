/**
 * Copyright (c) 2022 NumberFour AG.
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
import org.eclipse.n4js.n4JS.N4ModuleDeclaration
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.TNestedModule
import org.eclipse.n4js.ts.types.TypesFactory

class N4JSModuleDeclarationTypesBuilder {

	@Inject protected extension N4JSTypesBuilderHelper

	def package boolean relinkTNamespace(N4ModuleDeclaration n4ModuleDecl, AbstractNamespace target, boolean preLinkingPhase, int idx) {
		if (n4ModuleDecl.name === null) { // may be null due to syntax errors
			return false;
		}

		val TNestedModule module = target.nestedModules.get(idx)
		ensureEqualName(n4ModuleDecl, module.qualifiedName);

		module.astElement = n4ModuleDecl;
		n4ModuleDecl.definedModule = module;

		return true;
	}

	def protected TNestedModule createTNestedModule(N4ModuleDeclaration n4ModuleDecl, AbstractNamespace target, boolean preLinkingPhase) {
		if (n4ModuleDecl.name === null) {
			return null;
		}

		val module = TypesFactory::eINSTANCE.createTNestedModule();
		val nameInAST = n4ModuleDecl.name; // may contain more than one segment
		module.simpleName = nameInAST.substring(nameInAST.lastIndexOf(N4JSQualifiedNameConverter.DELIMITER));
		module.qualifiedName = nameInAST;

		module.astElement = n4ModuleDecl;
		n4ModuleDecl.definedModule = module;

		target.nestedModules += module;

		return module;
	}
}
