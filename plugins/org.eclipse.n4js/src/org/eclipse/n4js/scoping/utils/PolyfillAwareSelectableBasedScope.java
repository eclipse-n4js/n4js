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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;

/**
 * Polyfill aware scope disabling shadowing of polyfills.
 */
public class PolyfillAwareSelectableBasedScope extends SelectableBasedScope {

	/**
	 * Creates this scope if container is present, otherwise parent is returned.
	 */
	public static IScope createPolyfillAwareScope(IScope parent, IContainer container,
			Predicate<IEObjectDescription> filter, EClass type, boolean ignoreCase) {
		if (container == null || container.isEmpty())
			return parent;
		return new PolyfillAwareSelectableBasedScope(parent, container, filter, type, ignoreCase);
	}

	/**
	 * Delegates to super constructor.
	 */
	protected PolyfillAwareSelectableBasedScope(IScope outer, ISelectable selectable,
			Predicate<IEObjectDescription> filter, EClass type, boolean ignoreCase) {
		super(outer, selectable, filter, type, ignoreCase);
	}

	/**
	 * {@inheritDoc} Always returns false for polyfills, otherwise super method is called.
	 */
	@Override
	protected boolean isShadowed(IEObjectDescription fromParent) {
		if (fromParent != null && PolyfillUtils.isPolyfill(fromParent.getName())) {
			return false;
		}
		return super.isShadowed(fromParent);
	}

}
