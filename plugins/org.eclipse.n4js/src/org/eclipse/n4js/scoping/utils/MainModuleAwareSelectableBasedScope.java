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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;

/**
 * MainModule aware scope adjusting shadowing of main modules.
 *
 *
 * Note: since this allows duplicates it is possible it will return multiple {@link IEObjectDescription} for one
 * {@link EObject} (e.g. TModule). This can happen when different scopes in parent chain process the same EObjects.
 * Clients need to handle those duplicates on their own.
 */
public class MainModuleAwareSelectableBasedScope extends SelectableBasedScope {

	/**
	 * Creates new scope with given ISelectable.
	 */
	public static IScope createMainModuleAwareScope(IScope parent, ISelectable selectable,
			EClass type) {
		return new MainModuleAwareSelectableBasedScope(parent, selectable, null, type, true);
	}

	/**
	 * Delegates to super constructor.
	 */
	protected MainModuleAwareSelectableBasedScope(IScope outer, ISelectable selectable,
			Predicate<IEObjectDescription> filter, EClass type, boolean ignoreCase) {
		super(outer, selectable, filter, type, ignoreCase);
	}

	/**
	 * {@inheritDoc} Always returns false for polyfills, otherwise super method is called.
	 */
	@Override
	protected boolean isShadowed(IEObjectDescription fromParent) {
		boolean describesMainModule = N4JSResourceDescriptionStrategy.getMainModule(fromParent);
		if (describesMainModule) {
			return false;// main modules are never shadowed
		}
		return super.isShadowed(fromParent);
	}

}
