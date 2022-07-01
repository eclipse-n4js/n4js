/**
 * Copyright (c) 2022 NumberFour AG.
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
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;

/**
 * A {@link SelectableBasedScope} that will never {@link #isShadowed(IEObjectDescription) shadow} descriptions.
 */
public class NonShadowingSelectableBasedScope extends SelectableBasedScope {

	public static IScope createScope(IScope outer, ISelectable selectable, Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase) {
		if (selectable == null || selectable.isEmpty())
			return outer;
		return new NonShadowingSelectableBasedScope(outer, selectable, filter, type, ignoreCase);
	}

	/** Creates a new {@link NonShadowingSelectableBasedScope}. */
	protected NonShadowingSelectableBasedScope(IScope outer, ISelectable selectable,
			Predicate<IEObjectDescription> filter, EClass type, boolean ignoreCase) {
		super(outer, selectable, filter, type, ignoreCase);
	}

	@Override
	protected boolean isShadowed(IEObjectDescription input) {
		return false;
	}
}
