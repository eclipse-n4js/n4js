/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collection;
import java.util.List;

import org.eclipse.xtext.resource.IResourceDescription;

/**
 * This interface is intended for use cases where sorting elements and computing an "is affected" information are
 * combined. The combination might be reasonable since both of these computations might rely on similar data which would
 * be cached for performance reasons.
 *
 * Implementations of this interface are sorted {@link Iterable}s. The idea is to iterate over a sorted set of elements
 * where each element in the set can be enabled to be iterated over. As a default, all elements are disabled.
 *
 * Mind that the sort order of all elements is fixed and must not change by calls to this {@link Iterable} (esp. to
 * {@link #visitAffected(List)}).
 */
public interface IOrderInfo<T> extends Iterable<T> {

	/** Set all affected elements of the underlying collection to be visited by this iterator. */
	public void visitAffected(List<IResourceDescription.Delta> changes);

	/** Set the given elements of the underlying collection to be visited by this iterator. */
	public void visit(Collection<T> projectDescriptions);

	/** Sets all elements of the underlying collection to be visited by this iterator. */
	public void visitAll();

}
