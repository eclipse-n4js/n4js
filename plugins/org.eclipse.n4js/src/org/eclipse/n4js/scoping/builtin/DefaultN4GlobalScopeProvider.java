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
package org.eclipse.n4js.scoping.builtin;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.utils.PolyfillAwareSelectableBasedScope;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;

import com.google.common.base.Predicate;

/**
 * This N4 specific global scope provider assumes a different shadowing semantics for types: Polyfills are handled
 * differently, i.e., they are not shadowed at all.
 */
public abstract class DefaultN4GlobalScopeProvider extends DefaultGlobalScopeProvider {

	/**
	 * If the type is a {@link Type} a new {@link BuiltInTypeScope} is created.
	 */
	@Override
	protected IScope getScope(Resource context, boolean ignoreCase, EClass type,
			Predicate<IEObjectDescription> filter) {
		if (isSubtypeOfType(type)) {
			return getScope(getBuiltInTypeScope(context), context, ignoreCase, type, filter);
		}
		// do not call super but directly redirect with nullscope, using IScopeExt.NULLSCOPE instead of IScope.NULLSCOPE
		return getScope(IScope.NULLSCOPE, context, ignoreCase, type, filter);
	}

	/**
	 * Finds the built in type scope for the given resource and its applicable context.
	 *
	 * @param resource
	 *            the resource that is currently linked
	 * @return an instance of the {@link BuiltInTypeScope}
	 */
	protected BuiltInTypeScope getBuiltInTypeScope(Resource resource) {
		ResourceSet resourceSet = resource.getResourceSet();
		return BuiltInTypeScope.get(resourceSet);
	}

	/**
	 * Returns <code>true</code> if the given {@code type} is a subtype of {@link Type}.
	 */
	protected boolean isSubtypeOfType(EClass type) {
		return type == TypesPackage.Literals.TYPE || type.getEPackage() == TypesPackage.eINSTANCE
				&& TypesPackage.Literals.TYPE.isSuperTypeOf(type);
	}

	@Override
	protected IScope createContainerScope(IScope parent, IContainer container, Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase) {
		return PolyfillAwareSelectableBasedScope.createPolyfillAwareScope(parent, container, filter, type, ignoreCase);
	}

}
