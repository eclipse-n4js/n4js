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
package org.eclipse.n4js.ide.xtext.server.index;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * Interface for resource descriptions that can be updated.
 */
public interface MutableResourceDescriptions extends IResourceDescriptions {

	/**
	 * Add the given description this this index instance and return the previously known description with the same URI.
	 *
	 *
	 * @throws UnsupportedOperationException
	 *             if adding individual descriptions is not supported.
	 */
	IResourceDescription add(IResourceDescription newDescription);

	/**
	 * Remove the description with the given {@link URI} from this index instance.
	 *
	 * @throws UnsupportedOperationException
	 *             if removing individual descriptions is not supported.
	 */
	IResourceDescription remove(URI uri);

	/**
	 * Remove all entries from this index instance.
	 */
	void clear();

	/**
	 * Create an immutable snapshot from this index state.
	 */
	ImmutableResourceDescriptions snapshot();

	/**
	 * Put a new resource description into the index, or remove one if the delta has no new description. A delta for a
	 * particular URI may be registered more than once; overwriting any earlier registration.
	 *
	 * @throws UnsupportedOperationException
	 *             if changing individual descriptions is not supported.
	 */
	default void register(Delta delta) {
		final IResourceDescription newDesc = delta.getNew();
		if (newDesc == null) {
			remove(delta.getUri());
		} else {
			add(newDesc);
		}
	}
}
