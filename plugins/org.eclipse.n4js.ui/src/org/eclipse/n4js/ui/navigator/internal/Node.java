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

import static com.google.common.collect.FluentIterable.from;

import java.io.File;
import java.util.Arrays;
import java.util.Stack;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Representation of all virtual common navigator nodes in the {@code Project Explorer} view.
 */
/* default */ interface Node extends IWorkbenchAdapter {

	/**
	 * Empty {@link Node node} array.
	 */
	Node[] EMPTY_NODES = new Node[0];

	/**
	 * Returns the image for current node or {@code null} if there is not image for the current node.
	 *
	 * @return the image or {@code null} if does not exist.
	 */
	public Image getImage();

	/**
	 * Returns the text for the node. Could return with {@code null} if not specified.
	 *
	 * @return the human readable text for the node, or {@code null} if does not exist.
	 */
	public String getText();

	/**
	 * Returns the child elements of the given node or {@code null} if there are not child elements exist for the node.
	 *
	 * @return an array of child elements or {@code null}.
	 */
	public Object[] getChildren();

	/**
	 * Returns the parent for the given element, or {@code null} indicating that the parent can't be computed
	 *
	 * @return the parent element, or {@code null} if it has none or if the parent cannot be computed
	 */
	public Object getParent();

	/**
	 * Returns whether the node has children.
	 *
	 * @return {@code true} if the given element has children, and {@code false} if it has no children.
	 */
	public boolean hasChildren();

	@Override
	default Object[] getChildren(final Object o) {
		return getChildren();
	}

	@Override
	default String getLabel(final Object o) {
		return getText();
	}

	@Override
	default Object getParent(final Object o) {
		return getParent();
	}

	@Override
	default ImageDescriptor getImageDescriptor(final Object object) {
		final Image image = getImage();
		return null == image ? null : ImageDescriptor.createFromImage(image);
	}

	/**
	 * Tries to locate a associated with the current node or with any of its descendants. Transitive descendants are
	 * considered. Returns with {@code null} if the given file does not exist or any child nodes can be associated with
	 * the given file.
	 *
	 * @param file
	 *            the file to search as a node.
	 * @return the node representing the file, or {@code null} if not found.
	 */
	default Node findChild(File file) {

		if (null == file || !file.exists()) {
			return null;
		}

		if (Node.this instanceof ResourceNode) {
			if (((ResourceNode) Node.this).getLocation().toJavaIoFile().equals(file)) {
				return Node.this;
			}
		}

		final Stack<Node> stack = new Stack<>();
		for (final Node node : from(Arrays.asList(Node.this.getChildren())).filter(Node.class)) {
			stack.push(node);
		}

		while (!stack.isEmpty()) {
			final Node currentNode = stack.pop();
			if (currentNode instanceof ResourceNode) {
				if (((ResourceNode) currentNode).getLocation().toJavaIoFile().equals(file)) {
					return currentNode;
				}
			}

			for (final Node node : from(Arrays.asList(currentNode.getChildren())).filter(Node.class)) {
				stack.push(node);
			}

		}

		return null;
	}

}
