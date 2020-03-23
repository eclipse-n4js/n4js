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
import org.eclipse.xtext.nodemodel.BidiTreeIterator
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.util.ITextRegion
import org.eclipse.xtext.util.TextRegion

/**
 * Utility methods for dealing with the parse tree, in addition to those in {@link NodeModelUtils}.
 */
class NodeModelUtilsN4 {

	/**
	 * Computation of a node's length with {@link INode#getLength()} does not work properly with the
	 * automatic semicolon insertion (ASI) in N4JS:
	 * <ul>
	 * <li>if an optional ';' is given in the N4JS source code, then the length returned will only include code
	 *     up to (including) the semicolon. This is the desired behavior.
	 * <li>if the optional ';' is omitted in the N4JS source code, then the length returned will include all
	 *     following white space and comments up to (excluding) the beginning of the following statement,
	 *     declaration, etc.
	 * </ul>
	 * In contrast, this method will always return the actual length (including the semicolon, if given)
	 * without including any following white space or comments.
	 * <p>
	 * This method should always be used when computing the length or region of an AST element which
	 * may include an optional semicolon at the end (e.g. {@code ImportDeclaration}, {@code ExpressionStatement})
	 * but not for AST elements that never include a semicolon (e.g. {@code N4ClassDeclaration}).
	 */
	def public static int getNodeLengthWithASISupport(INode node) {
		val BidiTreeIterator<INode> iterator = node.getAsTreeIterable().iterator();
		var done = false;
		var end = node.endOffset;
		var foundSemi = false;
		while (!done && iterator.hasPrevious()) {
			val INode prev = iterator.previous();
			if (prev instanceof ILeafNode) {
				val hiddenOrIgnored = prev.isHidden() || prev.getText().trim().isEmpty();
				if (!hiddenOrIgnored) {
					done = true;
					end = prev.endOffset;
					foundSemi = ';'.equals(prev.text);
				}
			}
		}

		// Sometimes an actually present semicolon is not part of the node
		// returned by NodeModelUtils#findActualNodeFor(), therefore we have
		// to do some additional work:
		if (!foundSemi) {
			val next = node.nextSibling;
			if (next !== null && isHiddenTokensFollowedBySemi(next)) {
				end = next.getEndOffset();
			}
		}

		val offset = node.getOffset();
		return end - offset;
	}

	def private static boolean isHiddenTokensFollowedBySemi(INode node) {
		val BidiTreeIterator<INode> iterator = node.getAsTreeIterable().iterator();
		while (iterator.hasNext) {
			val INode next = iterator.next();
			if (next instanceof ILeafNode) {
				if (!next.isHidden()) {
					if (';'.equals(next.text)) {
						// consume remaining non-leafs
						while (iterator.hasNext) {
							if (iterator.next() instanceof ILeafNode) {
								return false;
							}
						}
						return true;
					} else {
						// non-hidden leaf other than ';'
						return false;
					}
				}
			}
		}
		// no ';' found
		return false;
	}

	/**
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

	/**
	 * This method converts a node to text.
	 *
	 * Only hidden tokens (whitespaces/comments) before and after non-hidden tokens are deleted.
	 *
	 * See {@link NodeModelUtils#getTokenText(INode node)}
	 */
	def public static String getTokenTextWithHiddenTokens(INode node) {
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
