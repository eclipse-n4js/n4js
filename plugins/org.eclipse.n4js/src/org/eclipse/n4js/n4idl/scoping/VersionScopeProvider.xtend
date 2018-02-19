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

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.Type
import com.google.inject.Inject

/**
 * A provider for version scopes.
 */
class VersionScopeProvider {

	@Inject private N4JSScopeProvider scopeProvider;

	/**
	 * Returns a scope containing all versions of the given type.
	 */
	public def getVersionScope(Type classifier) {
		var EObject context = classifier;

		// If present, use containing module as scoping context.
		// For built-in types however, there is no containing module and thus the
		// classifier itself is used.
		if (null !== classifier.containingModule) {
			context = classifier.containingModule;
		}

		return scopeProvider.getN4JSScope(context,
			TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
	}

}
