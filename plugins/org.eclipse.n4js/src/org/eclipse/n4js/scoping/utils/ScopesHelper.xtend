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
package org.eclipse.n4js.scoping.utils

import com.google.common.base.Function
import com.google.common.collect.Iterables
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.scoping.smith.MeasurableMapBasedScope
import org.eclipse.n4js.scoping.smith.MeasurableMultimapBasedScope
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.SimpleAttributeResolver

/**
 * Some utility methods, similar to xtext's {@link Scopes}.
 */
public class ScopesHelper {

	@Inject
	private JavaScriptVariantHelper javaScriptVariantHelper;

	/**
	 * Creates a map based scope for the given iterable of descriptions.
	 *
	 * @param context The context of the scope
	 * @param descriptions The descriptions
	 */
	def public IScope mapBasedScopeFor(EObject context, Iterable<IEObjectDescription> descriptions) {
		return mapBasedScopeFor(context, IScope.NULLSCOPE, descriptions);
	}

	/**
	 * Creates a map based scope for the given iterable of descriptions.
	 *
	 * @param context The context of the scope
	 * @param parent The parent scope
	 * @param descriptions The descriptions
	 */
	def public IScope mapBasedScopeFor(EObject context, IScope parent, Iterable<IEObjectDescription> descriptions) {
		if (javaScriptVariantHelper.isMultiQNScope(context)) {
			return MeasurableMultimapBasedScope.createScope(parent, descriptions, false);
		} else {
			return MeasurableMapBasedScope.createScope(parent, descriptions);
		}
	}

	/**
	 * Convenience method for {@link #scopeFor(Iterable,Function,Function,IScope)}.
	 */
	def public IScope scopeFor(Iterable<? extends EObject> elements,
		Function<IEObjectDescription, ? extends IEObjectDescription> wrapper) {
		return scopeFor(elements, wrapper, IScope.NULLSCOPE);
	}

	/**
	 * Convenience method for {@link #scopeFor(Iterable,Function,Function,IScope)}.
	 */
	def public IScope scopeFor(Iterable<? extends EObject> elements,
		Function<IEObjectDescription, ? extends IEObjectDescription> wrapper, IScope outer) {
		return scopeFor(elements, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER), wrapper, outer);
	}

	/**
	 * Similar to {@link Scopes#scopeFor(Iterable,Function,IScope)} but supports custom wrapping
	 * of the IEObjectDescriptions, for example to wrap them with error message providing subclasses
	 * such as {@link WrongStaticAccessDescription}. The wrapper can return the object description
	 * unchanged, create and return a new one or may return null to remove the corresponding object
	 * from the scope.
	 */
	def public <T extends EObject> IScope scopeFor(Iterable<? extends T> elements,
		Function<T, QualifiedName> nameComputation,
		Function<IEObjectDescription, ? extends IEObjectDescription> wrapper, IScope outer) {
		return new SimpleScope(outer,
			Iterables.transform(Scopes.scopedElementsFor(elements, nameComputation), wrapper));
	}
}
