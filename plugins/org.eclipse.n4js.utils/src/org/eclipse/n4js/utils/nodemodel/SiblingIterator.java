package org.eclipse.n4js.utils.nodemodel;

import java.util.Iterator;

import org.eclipse.xtext.nodemodel.INode;

/**
 * Iterates over INode passed in to constructor and all its next siblings.
 */
public class SiblingIterator implements Iterable<INode> {

	private final INode firstSibling;

	/**
	 * Creates an instance. See {@link SiblingIterator}.
	 */
	public SiblingIterator(INode firstSibling) {
		this.firstSibling = firstSibling;
	}

	@Override
	public Iterator<INode> iterator() {
		return new Iterator<INode>() {
			private INode currNode = firstSibling;

			@Override
			public boolean hasNext() {
				return currNode.hasNextSibling();
			}

			@Override
			public INode next() {
				final INode result = currNode;
				currNode = currNode.getNextSibling();
				return result;
			}
		};
	}
}
