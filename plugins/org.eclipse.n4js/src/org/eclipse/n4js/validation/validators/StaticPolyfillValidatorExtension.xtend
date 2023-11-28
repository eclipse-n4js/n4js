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
package org.eclipse.n4js.validation.validators

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.N4ClassDeclaration

import static org.eclipse.n4js.validation.IssueCodes.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Collecting special validation logic only related to static polyfill modules.
 * IDE-1735
 */
public class StaticPolyfillValidatorExtension {

   /** §143 (Restriction on static-polyfilling): §143.1 only classes in staticPolyfillModule allowed. */
	public static def internalCheckNotInStaticPolyfillModule(N4InterfaceDeclaration n4InterfaceDeclaration, N4JSInterfaceValidator host) {
		if (n4InterfaceDeclaration.isContainedInStaticPolyfillModule) {
			host.addIssue(n4InterfaceDeclaration, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
				POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES.toIssueItem());
		}
	}


   /** §143 (Restriction on static-polyfilling): §143.1 only classes in staticPolyfillModule allowed. */
	public static def internalCheckNotInStaticPolyfillModule(N4EnumDeclaration n4EnumDecl, N4JSEnumValidator host) {
		if (n4EnumDecl.isContainedInStaticPolyfillModule) {
			host.addIssue(n4EnumDecl, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
				POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES.toIssueItem());
		}
	}

   /** §143 (Restriction on static-polyfilling): §143.1 only classes in staticPolyfillModule allowed. */
	public static def internalCheckNotInStaticPolyfillModule(FunctionDeclaration functionDeclaration, N4JSFunctionValidator host) {
		// top level functionDeclarations:
		var cont = functionDeclaration.eContainer;
		while ( cont instanceof ExportDeclaration ) cont = cont.eContainer;
		if( cont instanceof Script)
		{
			if ( functionDeclaration.isContainedInStaticPolyfillModule ) {
				host.addIssue(functionDeclaration, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME,
					POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES.toIssueItem());
			}
		}
	}

 	 /** §143 (Restriction on static-polyfilling): §143.4 P must not implement any interfaces */
	public static def internalCheckPolyFilledClassWithAdditionalInterface(N4ClassDeclaration classDeclaration, N4JSClassValidator host) {
		if( classDeclaration.isStaticPolyfill ) {
			if( ! classDeclaration.implementedInterfaceRefs.isEmpty ) {
				host.addIssue(classDeclaration, N4JSPackage.Literals.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS,
					POLY_IMPLEMENTING_INTERFACE_NOT_ALLOWED.toIssueItem());
			}
		}
	}

}
