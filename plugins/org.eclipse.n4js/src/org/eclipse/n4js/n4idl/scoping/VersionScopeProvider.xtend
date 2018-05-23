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
package org.eclipse.n4js.n4idl.scoping

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.scoping.IScope

/**
 * A provider for version scopes.
 */
class VersionScopeProvider {

	@Inject private N4JSScopeProvider scopeProvider;
	@Inject private LocallyKnownTypesScopingHelper locallyKnownTypesScopingHelper;

	/**
	 * Returns a scope containing all versions of the given type.
	 */
	public def getVersionScope(Type type) {
		// If present, use containing module as scoping context.
		if (null !== type.containingModule) {
			// Only return locally declared types, to avoid issues with cyclic dependencies
			// via module imports.
			val script = type.containingModule.astElement as Script;
			return locallyKnownTypesScopingHelper.scopeWithLocallyDeclaredTypes(script, IScope.NULLSCOPE);
		} else {
			// For built-in types, there is no containing module and thus the
			// type itself is used as context. In this special case, this strategy is valid, 
			// since built-in types are guaranteed to not create cyclic dependencies with workspace modules.
			return scopeProvider.getN4JSScope(type,
				TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
		}

		
	}

}
