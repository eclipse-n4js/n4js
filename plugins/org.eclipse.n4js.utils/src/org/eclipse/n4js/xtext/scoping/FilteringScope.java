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
package org.eclipse.n4js.xtext.scoping;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Predicate;

/**
 * Variant of {@link FilterWithErrorMarkerScope} which completely removes the filtered out elements. In general it is
 * recommended to <b>not</b> filter out elements completely and instead create an erroneous description, since this
 * helps the user to fix problems.
 */
public class FilteringScope extends FilterWithErrorMarkerScope {

	/**
	 * The predicate which must return true to let a description pass, set in constructor
	 */
	protected final Predicate<? super IEObjectDescription> filterPredicate;

	/**
	 * Creates this filtering scope, the predicate which must return true to let a description pass.
	 */
	public FilteringScope(IScope parent, Predicate<? super IEObjectDescription> filterPredicate) {
		super(parent);
		this.filterPredicate = filterPredicate;
	}

	/**
	 * Filtered elements are removed, that is this method always returns null.
	 */
	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription originalDescr) {
		return null;
	}

	@Override
	protected boolean isAccepted(IEObjectDescription originalDescr) {
		return filterPredicate.apply(originalDescr);
	}

}
