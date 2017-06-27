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
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.SimpleAttributeResolver

/**
 * Some utility methods, similar to xtext's {@link Scopes}.
 */
public class ScopeUtils {

	/**
	 * Convenience method for {@link #scopeFor(Iterable,Function,Function,IScope)}.
	 */
	def public static IScope scopeFor(Iterable<? extends EObject> elements, Function<IEObjectDescription,? extends IEObjectDescription> wrapper) {
		return scopeFor(elements, wrapper, IScope.NULLSCOPE);
	}
	/**
	 * Convenience method for {@link #scopeFor(Iterable,Function,Function,IScope)}.
	 */
	def public static IScope scopeFor(Iterable<? extends EObject> elements, Function<IEObjectDescription,? extends IEObjectDescription> wrapper, IScope outer) {
		return scopeFor(elements, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER), wrapper, outer);
	}
	/**
	 * Similar to {@link Scopes#scopeFor(Iterable,Function,IScope)} but supports custom wrapping
	 * of the IEObjectDescriptions, for example to wrap them with error message providing subclasses
	 * such as {@link WrongStaticAccessDescription}. The wrapper can return the object description
	 * unchanged, create and return a new one or may return null to remove the corresponding object
	 * from the scope.
	 */
	def public static <T extends EObject> IScope scopeFor(
			Iterable<? extends T> elements,
			Function<T, QualifiedName> nameComputation,
			Function<IEObjectDescription,? extends IEObjectDescription> wrapper,
			IScope outer) {
		return new SimpleScope(outer,Iterables.transform(Scopes.scopedElementsFor(elements, nameComputation),wrapper));
	}


}
