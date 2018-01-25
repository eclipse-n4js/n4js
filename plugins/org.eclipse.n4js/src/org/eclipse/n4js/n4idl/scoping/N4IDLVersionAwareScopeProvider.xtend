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
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.naming.QualifiedNameComputer
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.scoping.IScope

/**
 * Adapts {@link N4JSScopeProvider} by wrapping the created scopes inside an instance of {@link N4IDLVersionAwareScope}.
 */
class N4IDLVersionAwareScopeProvider extends N4JSScopeProvider implements VersionScopeProvider {

	@Inject
	private VersionHelper versionHelper;

	@Inject
	private QualifiedNameComputer qualifiedNameComputer;

	override getVersionScope(Type classifier) {
		var EObject context = classifier;

		// If present, use containing module as scoping context.
		// For built-in types however, there is no containing module and thus the
		// classifier itself is used.
		if (null !== classifier.containingModule) {
			context = classifier.containingModule;
		}

		return getN4JSScope(context,
			TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
	}

	override getScope(EObject context, EReference reference) {
		val IScope scope = getN4JSScope(context, reference);

		// If the N4JS scope is a NULLSCOPE there
		// is nothing to filter for a context version.
		if (scope == IScope.NULLSCOPE) {
			return scope;
		}

		if (reference === TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE ||
			reference === N4JSPackage.Literals.IDENTIFIER_REF__ID
		) {
			val int contextVersion = versionHelper.computeMaximumVersion(context);

			return new N4IDLVersionAwareScope(scope, contextVersion, qualifiedNameComputer);
		}

		return scope;
	}

}
