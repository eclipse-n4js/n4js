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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.tooling.react.ReactHelper.REACT_JSX_RUNTIME_NAME;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toSet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.tooling.organizeImports.ScriptDependencyResolver;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Transformation to clean up imports:
 * <ul>
 * <li>add missing imports,
 * <li>remove unused import statements.
 * </ul>
 */
public class SanitizeImportsTransformation extends Transformation {

	@Override
	public void analyze() {
		// empty
	}

	@Override
	public void assertPreConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			assertTrue("requires a fully resolved script", getState().im.isFlaggedUsageMarkingFinished());
		}
	}

	@Override
	public void assertPostConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			// no import with flag used in code = false contained.
			Iterable<ImportSpecifier> unusedImports = filter(
					EcoreUtil2.getAllContentsOfType(getState().im, ImportSpecifier.class), is -> !isUsed(is));
			assertTrue("There should not be any unused import."
					+ " Unused=" + Strings.join(",", unusedImports), !unusedImports.iterator().hasNext());
		}
	}

	@Override
	public void transform() {
		addMissingImplicitImports();
		removeUnusedImports();
	}

	/**
	 * Compute imports for globally defined elements in other resources on the projects dependencies and add them iff
	 * missing.
	 */
	private void addMissingImplicitImports() {

		// Current use-case is use of Variable in global-scope.
		// The variable needs the defining module to be executed, hence a

		// collect all elements that are referenced from within the current module
		Set<SymbolTableEntryOriginal> referencedSTEs = toSet(filter(map(filter(
				getState().im.eAllContents(), IdentifierRef_IM.class), idRef -> idRef.getId_IM()),
				SymbolTableEntryOriginal.class));

		// explanation for filters in previous lines:
		// (1) reason why it is legal to filter out all ReferencingElement_IMs that aren't IdentifierRef_IM:
		// * ParameterizedTypeRef_IM => type references won't show up in target code anyway -> no import required
		// * ParameterizedPropertyAccessExpression_IM => here, the referenced element is a member -> members need not be
		// imported
		// (2) reason why it is legal to filter out all STEs that aren't SymbolTableEntryOriginal:
		// * SymbolTableEntryInternal => low-level stuff, no imports required (at least not imports that work like N4JS
		// imports)
		// * SymbolTableEntryIMOnly => stuff programmatically created in IM -> need not be imported (defined locally)

		// filter out those that do not require an import (e.g. built-in, provided by runtime, etc.)
		TModule baseModule = getState().resource.getModule();
		Iterable<SymbolTableEntryOriginal> requiresImport = filter(referencedSTEs,
				ste -> ScriptDependencyResolver.shouldBeImported(baseModule, ste.getOriginalTarget()));

		// look for already given imports:
		Iterable<SymbolTableEntryOriginal> thingsToImportSTE = requiresImport;
		{
			// The Question is, if the variable was already imported, then
			// a) do we still see it here and b) if so, was the import flagged by usedInCode ?

			// It turns out, that current scoping as of Dec'2015 doesn't bind to imports to globally available
			// definitions.
			// Assuming a) no + b) irrelevant for a==no
			for (SymbolTableEntryOriginal ste : thingsToImportSTE) {
				if (ste.getImportSpecifier() == null) {
					IdentifiableElement orig = ste.getOriginalTarget();
					// for an element to be globally available, there are two preconditions:
					// (1) containing module must be annotated with @@Global (2) element must be directly exported
					if (N4JSLanguageUtils.isDirectlyExported(orig)) {
						TModule module = orig.getContainingModule();
						if (AnnotationDefinition.GLOBAL.hasAnnotation(module)) {
							addNamedImport(ste, null);
						}
					}
				}
			}
		}
	}

	private void removeUnusedImports() {
		List<ImportSpecifier> unusedImports = toList(
				filter(EcoreUtil2.getAllContentsOfType(getState().im, ImportSpecifier.class), is -> !isUsed(is)));

		for (Iterator<ImportSpecifier> iter = unusedImports.iterator(); iter.hasNext();) {
			ImportSpecifier is = iter.next();

			ImportDeclaration container = (ImportDeclaration) is.eContainer();
			EObject toBeRemoved = (container.getImportSpecifiers().size() == 1
					&& container.getImportSpecifiers().contains(is))
							?
							// remove container if import-specifier is last child.
							container
							:
							// remove import-specifier
							is;

			remove(toBeRemoved);
		}
	}

	private boolean isUsed(ImportSpecifier importSpec) {
		if (importSpec.isFlaggedUsedInCode() == false) {
			// was already unused in original N4JS code (and we expect transformations to update this flag if they
			// add new usages of an existing import)
			// -> therefore simply return false (i.e. unused)
			return false;
		} else if (importSpec.eContainer() instanceof ImportDeclaration
				&& REACT_JSX_RUNTIME_NAME
						.equals(((ImportDeclaration) importSpec.eContainer()).getModuleSpecifierAsText())) {
			// special case for import that is added in JSXTransformation
			return true;
		} else {
			// for performance reasons, we do not require the flaggedUsedInCode to be set to false if usages are removed
			// -> therefore have to check for references now
			SymbolTableEntryOriginal ste = null;

			if (importSpec instanceof NamedImportSpecifier) {
				ste = findSymbolTableEntryForNamedImport((NamedImportSpecifier) importSpec);
			} else if (importSpec instanceof NamespaceImportSpecifier) {
				ste = findSymbolTableEntryForNamespaceImport((NamespaceImportSpecifier) importSpec);
			}

			if (ste == null) {
				// this may happen in case of several NamedImportSpecifiers importing the same IdentifiableElement
				// (which is a validation error in most cases, but the validation misses some corner cases)
				return false;
			}

			// note: here it is not enough to return !ste.referencingElements.empty, because for performance reasons
			// transformations are not required to remove obsolete entries from that list
			boolean hasReference = exists(ste.getReferencingElements(),
					refElem -> TranspilerUtils.isIntermediateModelElement(refElem));
			if (hasReference) {
				// we have an actual reference to the imported element
				// -> whether the import is deemed "used" depends on whether that original target actually requires
				// an import (will be false in case of elements globally provided by runtime, etc.)
				IdentifiableElement target = ste.getOriginalTarget();
				return target != null
						&& ScriptDependencyResolver.shouldBeImported(getState().resource.getModule(), target);
			}
			return false;
		}
	}
}
