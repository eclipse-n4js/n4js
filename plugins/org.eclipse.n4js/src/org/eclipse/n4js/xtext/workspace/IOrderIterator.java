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
package org.eclipse.n4js.xtext.workspace;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.xtext.resource.IResourceDescription;

/**
 * The {@link IOrderIterator} iterates over the sorted set of elements. During iteration, elements can be added to be
 * visited. As a default, no elements are visited.
 *
 * This interface is intended for use cases where sorting elements and computing an "is affected" information are
 * combined. The combination might be reasonable since both of these computations might rely on similar data which would
 * be cached for performance reasons. Also, the sorting is cached for later re-use.
 */
public interface IOrderIterator<T> extends Iterator<T> {

	/** Set all affected elements of the underlying collection to be visited by this iterator. */
	public IOrderIterator<T> visitAffected(List<IResourceDescription.Delta> changes);

	/** Set the given elements of the underlying collection to be visited by this iterator. */
	public IOrderIterator<T> visit(Collection<? extends T> elements);

	/** Sets all elements of the underlying collection to be visited by this iterator. */
	public IOrderIterator<T> visitAll();

}
