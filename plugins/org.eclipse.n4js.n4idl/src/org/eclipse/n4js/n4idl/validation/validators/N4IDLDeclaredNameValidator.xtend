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
package org.eclipse.n4js.n4idl.validation.validators

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.N4IDLClassDeclaration
import org.eclipse.n4js.n4JS.N4IDLEnumDeclaration
import org.eclipse.n4js.n4JS.N4IDLInterfaceDeclaration
import org.eclipse.n4js.validation.validators.N4JSDeclaredNameValidator
import org.eclipse.xtext.validation.EValidatorRegistrar

/**
 * Adaptation of N4JSDeclaredNameValidator to avoid errors due to duplicate names with multiple versions of the same element.
 * Names of versionable elements now include the version number.
 */
class N4IDLDeclaredNameValidator extends N4JSDeclaredNameValidator {

	/**
	 * If removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Declared name of N4IDL class declaration contains version as well.
	 */
	override def String getDeclaredName(EObject eo)  {
		switch (eo) {
			N4IDLClassDeclaration    : eo.name + "#" + eo.declaredVersion
			N4IDLInterfaceDeclaration: eo.name + "#" + eo.declaredVersion
			N4IDLEnumDeclaration	 : eo.name + "#" + eo.declaredVersion
			default                  : super.getDeclaredName(eo)
		}
	}

	override String getDeclaredNameForGlobalScopeComparision(EObject eo) {
		switch (eo) {
			N4IDLClassDeclaration    : eo.name
			N4IDLInterfaceDeclaration: eo.name
			N4IDLEnumDeclaration	 : eo.name
			default                  : super.getDeclaredName(eo)
		}
	}
}
