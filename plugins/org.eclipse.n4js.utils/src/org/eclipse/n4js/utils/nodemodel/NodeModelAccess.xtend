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
package org.eclipse.n4js.utils.nodemodel

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator

// Taken from https://raw.githubusercontent.com/eclipse/xtext/v2.7_Maintenance/plugins/org.eclipse.xtext.xbase/src/org/eclipse/xtext/xbase/formatting/NodeModelAccess.xtend
// to drop dependency to "org.eclipse.xtext.xbase"
/**
 */
class NodeModelAccess {

	def INode nodeForEObject(EObject obj) {
		NodeModelUtils.findActualNodeFor(obj)
	}

	def ILeafNode nodeForKeyword(EObject obj, String kw) {
		val node = NodeModelUtils.findActualNodeFor(obj)
		node.asTreeIterable.findFirst[semanticElement == obj && grammarElement instanceof Keyword && text == kw] as ILeafNode
	}

	def Iterable<ILeafNode> nodesForKeyword(EObject obj, String kw) {
		val node = NodeModelUtils.findActualNodeFor(obj)
		node.asTreeIterable.filter(ILeafNode).filter [
			semanticElement == obj && grammarElement instanceof Keyword && text == kw
		]
	}

	def INode nodeForFeature(EObject obj, EStructuralFeature feature) {
		NodeModelUtils.findNodesForFeature(obj, feature).head
	}

	def Iterable<INode> nodesForFeature(EObject obj, EStructuralFeature feature) {
		NodeModelUtils.findNodesForFeature(obj, feature)
	}

	def ILeafNode immediatelyFollowingKeyword(EObject obj, String kw) {
		obj.nodeForEObject.immediatelyFollowingKeyword(kw)
	}

	def ILeafNode immediatelyFollowingKeyword(INode node, String kw) {
		var current = node
		while (current instanceof ICompositeNode)
			current = current.lastChild
		val current1 = current
		val result = current1.findNextLeaf[current1 != it && grammarElement instanceof Keyword]
		if (result !== null && result.text == kw) result
	}

	def ILeafNode findNextLeaf(INode node, (ILeafNode)=>boolean matches) {
		if (node !== null) {
			if (node instanceof ILeafNode && matches.apply(node as ILeafNode))
				return node as ILeafNode
			val ni = new NodeIterator(node)
			while (ni.hasNext) {
				val next = ni.next
				if (next instanceof ILeafNode && matches.apply(next as ILeafNode))
					return next as ILeafNode
			}
		}
	}
}
