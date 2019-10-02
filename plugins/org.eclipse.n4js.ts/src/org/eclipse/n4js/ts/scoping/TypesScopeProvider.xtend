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
package org.eclipse.n4js.ts.scoping

import com.google.inject.Inject
import com.google.inject.name.Named
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractScopeProvider
import org.eclipse.xtext.scoping.impl.IDelegatingScopeProvider
import org.eclipse.xtext.scoping.impl.MapBasedScope
import org.eclipse.xtext.util.IResourceScopeCache

/**
 * This class contains custom scoping description.
 *
 * see : http://www.eclipse.org/Xtext/documentation.html#scoping
 * on how and when to use it
 */
class TypesScopeProvider extends AbstractScopeProvider implements IDelegatingScopeProvider {

	public final static String NAMED_DELEGATE = "org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.delegate";

	@Inject
	private IResourceScopeCache cache

	@Inject
	@Named(NAMED_DELEGATE)
	private IScopeProvider delegate;

	protected def IScope delegateGetScope(EObject context, EReference reference) {
		return delegate.getScope(context, reference)
	}

	def void setDelegate(IScopeProvider delegate) {
		this.delegate = delegate;
	}

	override IScopeProvider getDelegate() {
		return delegate;
	}

	new() {
	}

	override getScope(EObject context, EReference reference) {
		if (TypesPackage.Literals.TYPE.isSuperTypeOf(reference.EReferenceType)
		) {
			return getTypeScope(context, reference);
		}
		return delegateGetScope(context, reference)
	}

	private def IScope getTypeScope(EObject context, EReference reference) {
		switch context {
			TModule: { // although TModule is a Types related element, it is only used in N4JS files!
				// that is, there is a reference in an N4JS file point to a types model type (in a N4TS file).
				return delegateGetScope(context, reference);
			}
			TypeDefs: {
				return getLocallyKnownTypes(context, reference)
			}
			Type: {
				val IScope scope = context.eContainer.getTypeScope(reference)
				val declaredTypeVars = context.typeVars
				if (!declaredTypeVars.empty) {
					return Scopes.scopeFor(declaredTypeVars, scope)
				}
				return scope
			}
			default: {
				return context.eContainer.getTypeScope(reference)
			}
		}
	}

	private def getLocallyKnownTypes(TypeDefs typeDefs, EReference reference) {
		return cache.get(typeDefs -> 'locallyKnownTypes', typeDefs.eResource) [ |
			var parent = delegateGetScope(typeDefs, reference)
			return MapBasedScope.createScope(parent,
				typeDefs.types.filter[name !== null].map[EObjectDescription.create(name, it)])
		]
	}

}
