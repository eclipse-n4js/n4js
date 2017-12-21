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
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.VersionedNamedImportSpecifier
import org.eclipse.n4js.n4idl.versioning.VersionHelper
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.xtext.scoping.IScope

/**
 * Adapts {@link N4JSScopeProvider} by wrapping the created scopes inside an instance of {@link N4IDLVersionAwareScope}.
 */
class N4IDLVersionAwareScopeProvider extends N4JSScopeProvider implements VersionScopeProvider {

	@Inject
	private VersionHelper versionHelper;

	override getVersionScope(TClassifier classifier) {
		return getN4JSScope(classifier.containingModule,
			TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
	}

	override getScope(EObject context, EReference reference) {
		val IScope scope = getN4JSScope(context, reference);

		if (reference === TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE ||
			reference === N4JSPackage.Literals.IDENTIFIER_REF__ID
		) {
			val int contextVersion = versionHelper.computeMaximumVersion(context);

			// do not filter by version, if context version is infinity
//			if (contextVersion == Integer.MAX_VALUE) {
//				return scope;
//			}

			return new N4IDLVersionAwareScope(scope, contextVersion);
		}

		return scope;
	}

	override protected scope_ImportedElement(NamedImportSpecifier specifier, EReference reference) {
		if (specifier instanceof VersionedNamedImportSpecifier) {
			return new N4IDLVersionAwareScope(super.scope_ImportedElement(specifier, reference), specifier.requestedVersion.intValue);
		} else {
			super.scope_ImportedElement(specifier, reference)
		}
	}

}
