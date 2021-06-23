/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.utils;

import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;

/**
 * Some utilities for .d.ts export.
 */
public class DtsUtils {

	/**
	 * Returns the textual reference that can be used in the local file to refer to the given type, or <code>null</code>
	 * if the type cannot be referred to without adding new imports, etc.
	 * <p>
	 * The returned string is usually simply the local name of the given type, but includes, if required, also the name
	 * of a namespace and "." as separator.
	 */
	public static String getReferenceToTypeIfLocallyAvailable(Type type, TranspilerState state) {
		if (N4Scheme.isFromResourceWithN4Scheme(type)) {
			// simple case: the type reference points to a built-in type
			// -> can simply use its name in output code, because they are global and available everywhere
			if (type == RuleEnvironmentExtensions.intType(state.G)) {
				type = RuleEnvironmentExtensions.numberType(state.G);
			} else if (type == RuleEnvironmentExtensions.iteratorEntryType(state.G)) {
				return "IteratorReturnResult";
			}
			return type.getName();
		}
		SymbolTableEntryOriginal ste = state.steCache.mapOriginal.get(type);
		if (ste != null) {
			// the type reference points to a type contained in or already imported into the current module
			ImportSpecifier importSpec = ste.getImportSpecifier();
			if (importSpec instanceof NamespaceImportSpecifier) {
				String namespaceName = ((NamespaceImportSpecifier) importSpec).getAlias();
				return namespaceName + "." + ste.getName();
			}
			return ste.getName();
		}
		return null;
	}
}
