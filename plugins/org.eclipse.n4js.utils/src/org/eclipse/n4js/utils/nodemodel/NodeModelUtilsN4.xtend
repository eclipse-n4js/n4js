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

/**
 * Utility methods for dealing with the parse tree, in addition to those in {@link NodeModelUtils}.
 */
class NodeModelUtilsN4 {

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
}
