/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4idl.transpiler.utils.N4IDLTranspilerUtils;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM;
import org.eclipse.xtext.EcoreUtil2;

/**
 * This transformations transforms implicitly versioned imports into multiple regular named imports which each import
 * one of the type versions in use.
 *
 * Example:
 *
 * <code>
 * import {A} from "A" //implicitly versioned import of type A
 *
 * var a : A#1
 * var a : A#2
 * </code>
 *
 * becomes
 *
 * <code>
 * import {A#1} from "A"
 * import {A#2} from "A"
 *
 * var a : A#1
 * var a : A#2
 * </code>
 */
public class N4IDLVersionedImportsTransformation extends Transformation {

	@Override
	public void analyze() {
		// nothing to analyze
	}

	@Override
	public void assertPostConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			assertTrue("There should not be any more implicitly versioned imports in the IM.",
					EcoreUtil2.getAllContentsOfType(getState().im, VersionedNamedImportSpecifier_IM.class)
							.stream().noneMatch(VersionedNamedImportSpecifier_IM::isVersionedTypeImport));
		}
	}

	@Override
	public void assertPreConditions() {
		// nothing to assert
	}

	@Override
	public void transform() {
		EcoreUtil2.getAllContentsOfType(getState().im, ImportDeclaration.class)
				.forEach(this::transformVersionedImportSpecifiers);
	}

	private void transformVersionedImportSpecifiers(ImportDeclaration impDecl) {
		for (ImportSpecifier impSpec : new ArrayList<>(impDecl.getImportSpecifiers())) {
			if (impSpec instanceof VersionedNamedImportSpecifier_IM) {
				VersionedNamedImportSpecifier_IM impSpecCasted = (VersionedNamedImportSpecifier_IM) impSpec;
				if (impSpecCasted.isVersionedTypeImport()) {
					transformVersionedImportSpecifier(impSpecCasted);
				}
			}
		}
		if (impDecl.getImportSpecifiers().isEmpty()) {
			remove(impDecl);
		}
	}

	/**
	 * Replaces the given versioned import specifier, by multiple named imports.
	 *
	 * Only adds new imports for type versions that are actually used in the module.
	 */
	private void transformVersionedImportSpecifier(VersionedNamedImportSpecifier_IM importSpecifier) {
		// determine the type versions that are actually used in this module
		Collection<SymbolTableEntryOriginal> typeVersionSTEs = findSymbolTableEntriesForVersionedTypeImport(
				importSpecifier);

		// Add a new named import for each imported type version
		typeVersionSTEs.forEach(ste -> {
			// remove specifier reference from entry (as we have just removed it from the IM)
			ste.setImportSpecifier(null);
			// add new import for this specific version
			addNamedImport(ste, null);
			// obtain newly created specifier via STE
			NamedImportSpecifier createdSpecifier = (NamedImportSpecifier) ste.getImportSpecifier();
			// set the name of the imported element including the version number, e.g. C$2
			createdSpecifier.setImportedElementAsText(
					N4IDLTranspilerUtils.getVersionedInternalName(ste.getOriginalTarget()));
			// make sure to retain trace of the original IM specifier
			getState().tracer.copyTrace(importSpecifier, createdSpecifier);
		});

		// remove implicitly versioned import from IM
		remove(importSpecifier);
	}

}
