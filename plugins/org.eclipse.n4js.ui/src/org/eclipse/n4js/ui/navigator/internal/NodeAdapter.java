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
package org.eclipse.n4js.ui.navigator.internal;

import org.eclipse.swt.graphics.Image;

import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Base {@link Node navigator node} implementation.
 */
/* default */ class NodeAdapter implements Node {

	/** Shared empty array. */
	protected static final Object[] EMPTY_ARRAY = new Object[0];

	protected Object parent;

	/**
	 * Creates a new node instance with {@code null} parent.
	 */
	/* default */ NodeAdapter() {
		this(null);
	}

	/* default */ NodeAdapter(final Object parent) {
		this.parent = parent;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return !Arrays2.isEmpty(getChildren());
	}

	@Override
	public Object[] getChildren() {
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return getText();
	}

}
