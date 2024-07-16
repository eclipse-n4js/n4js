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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillModule;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isStaticPolyfill;
import static org.eclipse.n4js.validation.IssueCodes.POLY_IMPLEMENTING_INTERFACE_NOT_ALLOWED;
import static org.eclipse.n4js.validation.IssueCodes.POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;

/**
 * Collecting special validation logic only related to static polyfill modules. IDE-1735
 */
public class StaticPolyfillValidatorExtension {

	/** §143 (Restriction on static-polyfilling): §143.1 only classes in staticPolyfillModule allowed. */
	public static void internalCheckNotInStaticPolyfillModule(N4InterfaceDeclaration n4InterfaceDeclaration,
			N4JSInterfaceValidator host) {
		if (isContainedInStaticPolyfillModule(n4InterfaceDeclaration)) {
			host.addIssue(n4InterfaceDeclaration, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
					POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES.toIssueItem());
		}
	}

	/** §143 (Restriction on static-polyfilling): §143.1 only classes in staticPolyfillModule allowed. */
	public static void internalCheckNotInStaticPolyfillModule(N4EnumDeclaration n4EnumDecl, N4JSEnumValidator host) {
		if (isContainedInStaticPolyfillModule(n4EnumDecl)) {
			host.addIssue(n4EnumDecl, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
					POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES.toIssueItem());
		}
	}

	/** §143 (Restriction on static-polyfilling): §143.1 only classes in staticPolyfillModule allowed. */
	public static void internalCheckNotInStaticPolyfillModule(FunctionDeclaration functionDeclaration,
			N4JSFunctionValidator host) {
		// top level functionDeclarations:
		EObject cont = functionDeclaration.eContainer();
		while (cont instanceof ExportDeclaration) {
			cont = cont.eContainer();
		}
		if (cont instanceof Script) {
			if (isContainedInStaticPolyfillModule(functionDeclaration)) {
				host.addIssue(functionDeclaration, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME,
						POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES.toIssueItem());
			}
		}
	}

	/** §143 (Restriction on static-polyfilling): §143.4 P must not implement any interfaces */
	public static void internalCheckPolyFilledClassWithAdditionalInterface(N4ClassDeclaration classDeclaration,
			N4JSClassValidator host) {
		if (isStaticPolyfill(classDeclaration)) {
			if (!classDeclaration.getImplementedInterfaceRefs().isEmpty()) {
				host.addIssue(classDeclaration, N4JSPackage.Literals.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS,
						POLY_IMPLEMENTING_INTERFACE_NOT_ALLOWED.toIssueItem());
			}
		}
	}

}
