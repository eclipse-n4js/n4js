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
package org.eclipse.n4js.n4idl.validation

import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.VersionedNamedImportSpecifier
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.validation.validators.N4JSImportValidator

/**
 * A slightly modified import validator, that considers version numbers 
 * for import statement analysis.
 */
class N4IDLImportValidator extends N4JSImportValidator {

	override protected addIssueDuplicateNamedImportDeclaration(NamedImportSpecifier specifier,
		NamedImportSpecifier duplicate, ImportDeclaration duplicateImportDeclaration,
		Map<EObject, String> eObjectToIssueCode) {
			if (specifier instanceof VersionedNamedImportSpecifier &&
				duplicate instanceof VersionedNamedImportSpecifier) {
				// don't add an issue if they differ in the version they import
				if (!(specifier as VersionedNamedImportSpecifier).requestedVersion.equals(
					(duplicate as VersionedNamedImportSpecifier).requestedVersion)) {
					return;
				}
			}
			// otherwise behave as in N4JS
			super.addIssueDuplicateNamedImportDeclaration(specifier, duplicate, duplicateImportDeclaration,
				eObjectToIssueCode)
		}

		override public def computeImportSpecifierName(NamedImportSpecifier specifier) {
			if (specifier instanceof VersionedNamedImportSpecifier) {
				return specifier.importedElement.exportedName + N4IDLGlobals.VERSION_SEPARATOR +
					specifier.requestedVersion
			} else {
				return super.computeImportSpecifierName(specifier);
			}
		}

	}
	