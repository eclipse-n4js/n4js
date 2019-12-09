/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

/**
 * A tuple of deleted and modified resources.
 */
public class ResourceChangeSet {

	private final Set<URI> changed = Sets.newHashSet();
	private final Set<URI> deleted = Sets.newHashSet();

	/**
	 * Return the deleted uris.
	 */
	public Set<URI> getDeleted() {
		return deleted;
	}

	/**
	 * Return the modified uris.
	 */
	public Set<URI> getModified() {
		return changed;
	}

	@Override
	public String toString() {
		Joiner joiner = Joiner.on("\n  ");
		StringBuilder result = new StringBuilder();
		result.append("CHANGED:\n  ");
		joiner.appendTo(result, changed);
		result.append("\nDELETED:\n  ");
		joiner.appendTo(result, deleted);
		return result.toString();
	}

}
