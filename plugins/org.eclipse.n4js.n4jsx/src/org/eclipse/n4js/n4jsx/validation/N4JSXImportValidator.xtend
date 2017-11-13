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
package org.eclipse.n4js.n4jsx.validation

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4jsx.helpers.ReactHelper
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.validators.N4JSImportValidator
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static extension org.eclipse.n4js.organize.imports.ImportSpecifiersUtil.*

/**
 * This validator validates react import statements.
 */
@Log
class N4JSXImportValidator extends N4JSImportValidator {

	@Inject
	private ReactHelper reactHelper;
	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/** Make sure the namespace to react module is React. */
	@Check
	def checkReactImport(NamespaceImportSpecifier importSpecifier) {
		val module = importSpecifier.importedModule
		if (reactHelper.isReactModule(module)) {
			if (importSpecifier.alias != ReactHelper.REACT_NAMESPACE) {
						addIssue(
							IssueCodes.getMessageForREACT_NAMESPACE_NOT_ALLOWED(),
							importSpecifier, IssueCodes.REACT_NAMESPACE_NOT_ALLOWED);
			}
		}
	}
}
