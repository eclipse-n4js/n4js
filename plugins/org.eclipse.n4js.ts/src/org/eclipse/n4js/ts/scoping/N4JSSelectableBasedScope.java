/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.scoping;

import java.util.Collections;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;

/**
 * Disables default behavior of Xtext for (fully-)qualified names in all N4JS languages.
 */
public class N4JSSelectableBasedScope extends SelectableBasedScope {

	public static IScope createScope(IScope outer, ISelectable selectable, EClass type, boolean ignoreCase) {
		return createScope(outer, selectable, null, type, ignoreCase);
	}

	public static IScope createScope(IScope outer, ISelectable selectable, Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase) {
		if (selectable == null || selectable.isEmpty())
			return outer;
		return new N4JSSelectableBasedScope(outer, selectable, filter, type, ignoreCase);
	}

	/**
	 * See {@link N4JSSelectableBasedScope}.
	 */
	protected N4JSSelectableBasedScope(IScope outer, ISelectable selectable, Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase) {
		super(outer, selectable, filter, type, ignoreCase);
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(final QualifiedName name) {
		// disallow names that are actually qualified, i.e. names with more than one segment
		if (name.getSegmentCount() > 1) {
			return Collections.emptyList();
		}
		return super.getLocalElementsByName(name);
	}
}
