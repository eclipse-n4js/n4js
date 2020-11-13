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
import java.util.Iterator;
import java.util.List;

import org.eclipse.xtext.resource.IResourceDescription;

/**
 * This interface is intended for use cases where sorting elements and computing an "is affected" information are
 * combined. The combination might be reasonable since both of these computations might rely on similar data which would
 * be cached for performance reasons. Also, the sorting is cached for later re-use.
 *
 * Implementations of this interface return sorted {@link IOrderIterator}s. The idea is that an iterator iterates over
 * the cached and filtered sorted set of elements. The filtering is done by enabling elements to be visited during the
 * iteration. As a {@link IOrderIterator}'s default, all elements are disabled.
 */
public interface IOrderInfo<T> {

	/**
	 * The {@link IOrderIterator} iterates over the sorted set of elements of {@link IOrderInfo}. During iteration,
	 * elements can be added to be visited. As a default, no elements are visited.
	 */
	public interface IOrderIterator<T> extends Iterator<T> {

		/** Set all affected elements of the underlying collection to be visited by this iterator. */
		public IOrderIterator<T> visitAffected(List<IResourceDescription.Delta> changes);

		/** Set the given elements of the underlying collection to be visited by this iterator. */
		public IOrderIterator<T> visit(Collection<? extends T> elements);

		/** Sets all elements of the underlying collection to be visited by this iterator. */
		public IOrderIterator<T> visitAll();

	}

	/**
	 * Creates a new instance of {@link IOrderIterator}. Assumes a succeeding call to
	 * {@link IOrderIterator#visit(Collection)} method.
	 */
	public IOrderIterator<T> getIterator();

	/** Creates a new instance of {@link IOrderIterator}. The given set of projects will be visited only. */
	public IOrderIterator<T> getIterator(Collection<? extends T> elements);

}
