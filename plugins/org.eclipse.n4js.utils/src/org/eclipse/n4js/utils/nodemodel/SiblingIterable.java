package org.eclipse.n4js.utils.nodemodel;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;

/**
 * See {@link #SiblingIterable(INode, boolean)}.
 */
public class SiblingIterable implements Iterable<INode> {

	private final INode firstSibling;
	private final boolean skipHidden;

	/**
	 * Same as {@link #SiblingIterable(INode, boolean)}, always skipping {@link HiddenLeafNode}s.
	 */
	public SiblingIterable(INode firstSibling) {
		this(firstSibling, true);
	}

	/**
	 * Iterates over the given INode and all its siblings, optionally skipping {@link HiddenLeafNode}s. If
	 * <code>null</code> is given as the first sibling, an empty iterable is created.
	 */
	public SiblingIterable(INode firstSibling, boolean skipHidden) {
		this.firstSibling = firstSibling;
		this.skipHidden = skipHidden;
	}

	@Override
	public Iterator<INode> iterator() {
		return new Iterator<>() {
			private INode currNode = skipHidden(firstSibling);

			@Override
			public boolean hasNext() {
				return currNode != null;
			}

			@Override
			public INode next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				final INode result = currNode;
				currNode = skipHidden(currNode.getNextSibling());
				return result;
			}

			private INode skipHidden(INode node) {
				if (skipHidden) {
					while (node instanceof HiddenLeafNode) {
						node = node.getNextSibling();
					}
				}
				return node;
			}
		};
	}
}
