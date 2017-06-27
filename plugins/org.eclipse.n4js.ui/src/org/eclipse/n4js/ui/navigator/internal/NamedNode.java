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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;

import java.util.Arrays;

import org.eclipse.swt.graphics.Image;

import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Simple named node implementation with optional, children and image. As this is a named node, the human readable label
 * is mandatory.
 */
/* default */ class NamedNode extends NodeAdapter {

	private Object[] children = new Object[0];
	private final String label;
	private final Image image;

	/* default */ NamedNode(final Object parent, final String label) {
		this(parent, label, null);
	}

	/* default */ NamedNode(final Object parent, final String label, final Image image) {
		super(parent);
		this.label = checkNotNull(label, "label");
		this.image = image;
	}

	@Override
	public Object[] getChildren() {
		return children;
	}

	@Override
	public String getText() {
		return label;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(children);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NamedNode)) {
			return false;
		}
		final NamedNode other = (NamedNode) obj;
		if (!Arrays.equals(children, other.children)) {
			return false;
		}
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	/**
	 * Appends the given child node.
	 *
	 * <p>
	 * This is quite expensive. Call it once with multiple child elements.
	 *
	 * @param child
	 *            the child node to append.
	 */
	/* default */ void addChild(Object... child) {
		children = Arrays2.add(children, child);
	}

	/**
	 * Appends the given iterable of child nodes.
	 *
	 * @param children
	 *            the child node to append.
	 */
	/* default */ void addChild(@SuppressWarnings("hiding") Iterable<Object> children) {
		addChild(from(children).toArray(Object.class));
	}

}
