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
package org.eclipse.n4js.utils.nodemodel;

import static com.google.common.collect.Lists.reverse;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.fold;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;

import com.google.common.base.Strings;
import com.google.inject.Inject;

// taken from https://github.com/eclipse/xtext/blob/v2.7_Maintenance/plugins/org.eclipse.xtext.xbase/src/org/eclipse/xtext/xbase/formatting/HiddenLeafAccess.xtend
// to drop dependency to "org.eclipse.xtext.xbase"
public class HiddenLeafAccess {

	@Inject
	NodeModelAccess nmAccress;

	public HiddenLeafs getHiddenLeafsBefore(INode node) {
		ILeafNode start = nmAccress.findNextLeaf(node, n -> !n.isHidden());
		List<ILeafNode> nodes = findPreviousHiddenLeafs(start);
		if (start != null) {
			return newHiddenLeafs((nodes.isEmpty()) ? start.getOffset() : nodes.get(0).getOffset(), nodes);
		} else {
			return new HiddenLeafs(/* node?.offset */((node == null) ? 0 : node.getOffset()));
		}
	}

	protected HiddenLeafs newHiddenLeafs(int offset, List<ILeafNode> nodes) {
		HiddenLeafs result = new HiddenLeafs(offset);
		boolean trailing = true;
		for (ILeafNode node : nodes) {
			boolean comment = false;
			int newLines = 0;
			if (!Strings.isNullOrEmpty(node.getText())) {
				for (int i = 0; i < node.getText().length(); i++) {
					char c = node.getText().charAt(i);
					if (c == '\n') {
						newLines = newLines + 1;
					} else if (!Character.isWhitespace(c)) {
						comment = true;
					}
				}
			}
			if (comment) {
				if (!(last(result.leafs) instanceof WhitespaceInfo)) {
					result.leafs.add(new WhitespaceInfo(result, null, 0, node.getOffset()));
				}
				result.leafs.add(new CommentInfo(result, node, newLines, trailing));
			} else
				result.leafs.add(new WhitespaceInfo(result, node, newLines, node.getOffset()));
			if (newLines > 0) {
				trailing = false;
			}
		}
		if (!(last(result.leafs) instanceof WhitespaceInfo)) {
			result.leafs.add(new WhitespaceInfo(result, null, 0,
					(result.leafs.isEmpty()) ? offset : last(result.leafs).node.getEndOffset()));
		}
		return result;
	}

	HiddenLeafs getHiddenLeafsAfter(INode node) {
		ILeafNode start = findPreviousLeaf(node, n -> !n.isHidden());
		if (start != null) {
			return newHiddenLeafs(start.getEndOffset(), findNextHiddenLeafs(start));
		} else {
			return new HiddenLeafs(/* node?.offset */((node == null) ? 0 : node.getOffset()));
		}
	}

	protected List<ILeafNode> findNextHiddenLeafs(INode node) {
		List<ILeafNode> result = new ArrayList<>();
		NodeIterator ni = new NodeIterator(node);
		while (ni.hasNext()) {
			INode next = ni.next();
			if (next instanceof ILeafNode) {
				if (((ILeafNode) next).isHidden()) {
					result.add((ILeafNode) next);
				} else // if(!result.empty)
				{
					return result;
				}
			}
		}
		return result;
	}

	protected ILeafNode findPreviousLeaf(INode node, Predicate<ILeafNode> matches) {
		INode current = node;
		while (current instanceof ICompositeNode) {
			current = ((ICompositeNode) current).getLastChild();
		}
		if (current instanceof ILeafNode && matches.test((ILeafNode) current))
			return (ILeafNode) current;
		if (current != null) {
			NodeIterator ni = new NodeIterator(current);
			while (ni.hasPrevious()) {
				INode previous = ni.previous();
				if (previous instanceof ILeafNode && matches.test((ILeafNode) previous))
					return (ILeafNode) previous;
			}
		}
		return null;
	}

	protected List<ILeafNode> findPreviousHiddenLeafs(INode node) {
		INode current = node;
		while (current instanceof ICompositeNode) {
			current = ((ICompositeNode) current).getLastChild();
		}
		List<ILeafNode> result = new ArrayList<>();
		if (current != null) {
			NodeIterator ni = new NodeIterator(current);
			while (ni.hasPrevious()) {
				INode previous = ni.previous();
				if (previous != current && previous instanceof ILeafNode) {
					if (((ILeafNode) previous).isHidden()) {
						result.add((ILeafNode) previous);
					} else // if(!result.empty)
					{

						return reverse(result);
					}
				}
			}
		}
		return reverse(result);
	}

	public class HiddenLeafs {
		int offset;
		List<LeafInfo> leafs = new ArrayList<>();

		public int getOffset() {
			return offset;
		}

		public List<LeafInfo> getLeafs() {
			return leafs;
		}

		public HiddenLeafs(final int offset) {
			this.offset = offset;
		}

		public boolean isSingleWhitespace() {
			return leafs.isEmpty() || (leafs.size() == 1 && leafs.get(0) instanceof WhitespaceInfo);
		}

		public int getLenght() {
			return fold(leafs, 0, (x, i) -> x + /* i.node?.length */(((i.node == null) ? 0 : i.node.getLength())));
		}

		public int getNewLines() {
			return fold(leafs, 0, (x, i) -> x + i.newLines);
		}

		public int getNewLinesInComments() {
			return fold(filter(leafs, CommentInfo.class), 0, (x, i) -> x + i.newLines);
		}

		public boolean containsComment() {
			return size(filter(leafs, CommentInfo.class)) > 0;
		}
	}

	public abstract class LeafInfo {
		HiddenLeafs container;
		ILeafNode node;
		int newLines;

		public LeafInfo(final HiddenLeafs container, final ILeafNode node, final int newLines) {
			this.container = container;
			this.node = node;
			this.newLines = newLines;
		}

		public HiddenLeafs getContainer() {
			return container;
		}

		public ILeafNode getNode() {
			return node;
		}

		public int getNewLines() {
			return newLines;
		}

	}

	public class WhitespaceInfo extends LeafInfo {
		int offset;

		public WhitespaceInfo(final HiddenLeafs container, final ILeafNode node, final int newLines, final int offset) {
			super(container, node, newLines);
			this.offset = offset;
		}

		public int getLength() {
			// node?.length // suppress warning by replacing with:
			return ((node == null) ? 0 : node.getLength());
		}

		public CommentInfo leadingComment() {
			int i = container.leafs.indexOf(this) - 1;
			if (i >= 0) {
				return (CommentInfo) container.leafs.get(i);
			}
			return null;
		}

		public CommentInfo trailingComment() {
			int i = container.leafs.indexOf(this) + 1;
			if (i < container.leafs.size()) {
				return (CommentInfo) container.leafs.get(i);
			}
			return null;
		}

		@Override
		public String toString() {
			return """
					WS: "%s"
					""".formatted(node == null ? "" : node.getText());
		}
	}

	public class CommentInfo extends LeafInfo {
		boolean trailing; // true if this comment is in the first line of its HiddenLeafs

		public CommentInfo(final HiddenLeafs container, final ILeafNode node, final int newLines,
				final boolean trailing) {
			super(container, node, newLines);
			this.trailing = trailing;
		}

		public boolean endsWithNewLine() {
			return node.getText().endsWith("\n");
		}

		public boolean isMultiline() {
			return !endsWithNewLine() && node.getText().contains("\n");
		}

		@Override
		public String toString() {
			return """
					Comment: "%s"
					""".formatted(node.getText());
		}
	}
}