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
package org.eclipse.n4js.n4idl.scoping

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.VersionedNamedImportSpecifier
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.imports.IEODesc2ISpec
import org.eclipse.n4js.scoping.imports.ImportedElementsMap
import org.eclipse.n4js.scoping.imports.ImportedElementsScopingHelper
import org.eclipse.n4js.ts.typeRefs.Versionable
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.MultimapBasedScope
import org.eclipse.xtext.util.IResourceScopeCache

/**
 * Helper for {@link N4JSScopeProvider N4JSScopeProvider}.
 */
public class N4IDLImportedElementsScopingHelper extends ImportedElementsScopingHelper {
	
	override protected buildMapBasedScope(IScope parent, Iterable<IEObjectDescription> elements) {
		return MultimapBasedScope.createScope(parent, elements, false);
	}
	
	override protected processNamedImportSpecifier(NamedImportSpecifier specifier, ImportDeclaration imp, Resource contextResource, IEODesc2ISpec originatorMap, ImportedElementsMap validImports, ImportedElementsMap invalidImports, boolean importVariables) {
		// TODO only mark as used if the scope actual accesses it
		// -> this method should actually be private
		if (specifier instanceof VersionedNamedImportSpecifier) {
			markAsUsed(specifier);
		}
		
		super.processNamedImportSpecifier(specifier, imp, contextResource, originatorMap, validImports, invalidImports, importVariables)
	}
	
	override protected isAmbiguous(IEObjectDescription existing, IdentifiableElement element) {
		// make sure ambiguity is only detected in case of the same imported version of a name
		if (existing.getEObjectOrProxy instanceof Versionable && element instanceof Versionable) {
			return (existing.getEObjectOrProxy as Versionable).version == (element as Versionable).version;
		} else {
			return super.isAmbiguous(existing, element);
		}
	}
	
	/**
	 * Mark the import specifier as used without throwing an event. This preserves the cached data in the
	 * {@link IResourceScopeCache}.
	 *
	 * @param origin
	 *            the import specific to be marked as used. May not be null.
	 */
	private def void markAsUsed(ImportSpecifier origin) {
		val wasDeliver = origin.eDeliver();
		try {
			origin.eSetDeliver(false);
			origin.setFlaggedUsedInCode(true);
		} finally {
			origin.eSetDeliver(wasDeliver);
		}
	}
	
}
