/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.nodemodel

import org.eclipse.xtext.Keyword
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.util.ITextRegion
import org.eclipse.xtext.util.TextRegion
import org.eclipse.xtext.nodemodel.ILeafNode

/**
 * Utility methods for dealing with the parse tree, in addition to those in {@link NodeModelUtils}.
 */
class NodeModelUtilsN4 {

	/*
	 * Finds recursively a node in the node tree which stands for a keyword (starting from the parentNode)
	 * @param parentNode The node from which the recursive search begins
	 * @param keyword Searched keyword
	 * @returns keywordNode if exists; null otherwise
	 */
	def public static INode findKeywordNode(ICompositeNode parentNode, String keyword) {
		val children = parentNode.children;
		val rs = children.findFirst[c|c.isKeyword(keyword)];
		if (rs !== null) {
			return rs;
		}
		for (INode iNode: children) {
			if (iNode instanceof ICompositeNode) {
				val retValOfRecursion = findKeywordNode(iNode, keyword);
				if (retValOfRecursion !== null) {
					return retValOfRecursion;
				}
			}
		}
		return null;
	}

	def public static ITextRegion findRegionOfKeywordWithOptionalBlock(ICompositeNode parentNode, String keyword) {
		val INode keywordNode = parentNode.children.findFirst[isKeyword(keyword)];
		if (keywordNode !== null) {
			val siblingsIter = new SiblingIterable(keywordNode.nextSibling).iterator;
			if (siblingsIter.hasNext) {
				val INode nextNode = siblingsIter.next;
				if (nextNode.isKeyword("{")) {
					val INode closingNode = siblingsIter.findFirst[isKeyword("}")];
					if (closingNode !== null) {
						return new TextRegion(keywordNode.offset,
							(closingNode.offset + closingNode.length) - keywordNode.offset);
					}
				}
			}
			return new TextRegion(keywordNode.offset, keywordNode.length);
		}
		return null;
	}

	def public static boolean isKeyword(INode node, String keyword) {
		val ge = node.grammarElement;
		return if (ge instanceof Keyword) keyword.equalsIgnoreCase(ge.value) else false;
	}

	def public static String getFullTextOfBogusType(INode node) {
		if (node instanceof ILeafNode) {
			return node.getText();
		} else {
			val builder = new StringBuilder(Math.max(node.getTotalLength(), 1));
			val tmpBuilder = new StringBuilder(Math.max(node.getTotalLength(), 1));
			var nonHiddenSeen = false;
			for (ILeafNode leaf : node.getLeafNodes()) {
				if (!leaf.isHidden()) {
					builder.append(tmpBuilder);
					tmpBuilder.delete(0, tmpBuilder.length);
					builder.append(leaf.getText());
					nonHiddenSeen = true;
				} else {
					if (nonHiddenSeen) {
						tmpBuilder.append(leaf.getText);
					}
				}
			}
			return builder.toString();
		}
	}
}
