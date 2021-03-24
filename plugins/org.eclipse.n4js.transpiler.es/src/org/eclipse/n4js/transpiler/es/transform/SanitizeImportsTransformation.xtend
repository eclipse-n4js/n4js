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
package org.eclipse.n4js.transpiler.es.transform

import com.google.common.base.Joiner
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.tooling.organizeImports.ScriptDependencyResolver
import org.eclipse.n4js.transpiler.AbstractTranspiler
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.utils.TranspilerUtils
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.xtext.EcoreUtil2

/**
 * Transformation to clean up imports:
 * <ul>
 * <li>add missing imports,
 * <li>remove unused import statements.
 * </ul>
 */
class SanitizeImportsTransformation extends Transformation {

	override analyze() {
	}

	override assertPreConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			assertTrue("requires a fully resolved script", state.im.flaggedUsageMarkingFinished);
		}
	}

	override assertPostConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			// no import with flag used in code = false contained.
			val unusedImports = EcoreUtil2.getAllContentsOfType( state.im, ImportSpecifier ).filter[!isUsed].toList;
			assertTrue( "There should not be any unused import."
				+" Unused="+ Joiner.on(",").join(unusedImports)
				, unusedImports.size === 0
			);
		}
	}

	override transform() {
		addMissingImplicitImports();
		removeUnusedImports();
	}

	/**
	 * Compute imports for globally defined elements in other resources on the projects dependencies and
	 * add them iff missing.
	 */
	private def addMissingImplicitImports(){

		// Current use-case is use of Variable in global-scope.
		// The variable needs the defining module to be executed, hence a

		// collect all elements that are referenced from within the current module
		val referencedSTEs = state.im.eAllContents.filter(IdentifierRef_IM)
			.map[id_IM]
			.filter(SymbolTableEntryOriginal)
			.toSet;
		// explanation for filters in previous lines:
		// (1) reason why it is legal to filter out all ReferencingElement_IMs that aren't IdentifierRef_IM:
		// * ParameterizedTypeRef_IM => type references won't show up in target code anyway -> no import required
		// * ParameterizedPropertyAccessExpression_IM => here, the referenced element is a member -> members need not be imported
		// (2) reason why it is legal to filter out all STEs that aren't SymbolTableEntryOriginal:
		// * SymbolTableEntryInternal => low-level stuff, no imports required (at least not imports that work like N4JS imports)
		// * SymbolTableEntryIMOnly => stuff programmatically created in IM -> need not be imported (defined locally)

		// filter out those that do not require an import (e.g. built-in, provided by runtime, etc.)
		val baseModule = state.resource.module;
		val requiresImport = referencedSTEs.filter[ScriptDependencyResolver.shouldBeImported(baseModule, originalTarget)];


		// look for already given imports:
		val thingsToImportSTE = requiresImport;
		{
			// The Question is, if the variable was already imported, then
			// a) do we still see it here and b) if so, was the import flagged by usedInCode ?

			// It turns out, that current scoping as of Dec'2015 doesn't bind to imports to globally available definitions.
			// Assuming a) no + b) irrelevant for a==no
			thingsToImportSTE.forEach[ ste |
				if( ste.importSpecifier === null ) {
					val orig = ste.originalTarget;
					// for an element to be globally available, there are two preconditions:
					// (1) containing module must be annotated with @@Global (2) element must be exported
					if(N4JSLanguageUtils.isExported(orig)) {
						val module = orig.containingModule;
						if(AnnotationDefinition.GLOBAL.hasAnnotation(module)) {
							addNamedImport(ste,null);
						}
					}
				}
			]
		}
	}


	private def removeUnusedImports(){

		val unusedImports = EcoreUtil2.getAllContentsOfType( state.im, ImportSpecifier ).filter[!isUsed].toList

		for( val iter = unusedImports.iterator; iter.hasNext; ){
			val is = iter.next;

			val ImportDeclaration container = is.eContainer as ImportDeclaration
			val toBeRemoved = if( container.importSpecifiers.size === 1 && container.importSpecifiers.contains( is )) {
				// remove container if import-specifier is last child.
				container
			} else {
				// remove import-specifier
				is
			};

			remove( toBeRemoved )

		}

	}


	def private boolean isUsed(ImportSpecifier importSpec) {
		if(importSpec.flaggedUsedInCode===false) {
			// was already unused in original N4JS code (and we expect transformations to update this flag if they
			// add new usages of an existing import)
			// -> therefore simply return false (i.e. unused)
			return false;
		} else {
			// for performance reasons, we do not require the flaggedUsedInCode to be set to false if usages are removed
			// -> therefore have to check for references now
			val ste = if(importSpec instanceof NamedImportSpecifier) {
				findSymbolTableEntryForNamedImport(importSpec)
			} else if(importSpec instanceof NamespaceImportSpecifier) {
				findSymbolTableEntryForNamespaceImport(importSpec)
			};

			// note: here it is not enough to return !ste.referencingElements.empty, because for performance reasons
			// transformations are not required to remove obsolete entries from that list
			val hasReference = ste.referencingElements.exists[TranspilerUtils.isIntermediateModelElement(it)];
			if(hasReference) {
				// we have an actual reference to the imported element
				// -> whether the import is deemed "used" depends on whether that original target actually requires
				// an import (will be false in case of elements globally provided by runtime, etc.)
				val target = ste.originalTarget;
				return target!==null && ScriptDependencyResolver.shouldBeImported(state.resource.module, target);
			}
			return false;
		}
	}
}
