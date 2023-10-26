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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;

import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;

// Taken from https://raw.githubusercontent.com/eclipse/xtext/v2.7_Maintenance/plugins/org.eclipse.xtext.xbase/src/org/eclipse/xtext/xbase/formatting/NodeModelAccess.xtend
// to drop dependency to "org.eclipse.xtext.xbase"
public class NodeModelAccess {

	public INode nodeForEObject(EObject obj) {
		return NodeModelUtils.findActualNodeFor(obj);
	}

	public ILeafNode nodeForKeyword(EObject obj, String kw) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(obj);
		return (ILeafNode) findFirst(node.getAsTreeIterable(), n -> n.getSemanticElement() == obj
				&& n.getGrammarElement() instanceof Keyword && Objects.equals(n.getText(), kw));
	}

	public Iterable<ILeafNode> nodesForKeyword(EObject obj, String kw) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(obj);
		return filter(filter(node.getAsTreeIterable(), ILeafNode.class),
				n -> n.getSemanticElement() == obj && n.getGrammarElement() instanceof Keyword
						&& Objects.equals(n.getText(), kw));
	}

	public INode nodeForFeature(EObject obj, EStructuralFeature feature) {
		return head(NodeModelUtils.findNodesForFeature(obj, feature));
	}

	public Iterable<INode> nodesForFeature(EObject obj, EStructuralFeature feature) {
		return NodeModelUtils.findNodesForFeature(obj, feature);
	}

	public ILeafNode immediatelyFollowingKeyword(EObject obj, String kw) {
		return immediatelyFollowingKeyword(nodeForEObject(obj), kw);
	}

	public ILeafNode immediatelyFollowingKeyword(INode node, String kw) {
		INode current = node;
		while (current instanceof ICompositeNode) {
			current = ((ICompositeNode) current).getLastChild();
		}
		INode current1 = current;
		ILeafNode result = findNextLeaf(current1, n -> current1 != n && n.getGrammarElement() instanceof Keyword);
		if (result != null && Objects.equals(result.getText(), kw)) {
			return result;
		}
		return null;
	}

	public ILeafNode findNextLeaf(INode node, Predicate<ILeafNode> matches) {
		if (node != null) {
			if (node instanceof ILeafNode && matches.test((ILeafNode) node)) {
				return (ILeafNode) node;
			}
			NodeIterator ni = new NodeIterator(node);
			while (ni.hasNext()) {
				INode next = ni.next();
				if (next instanceof ILeafNode && matches.test((ILeafNode) next)) {
					return (ILeafNode) next;
				}
			}
		}
		return null;
	}
}
