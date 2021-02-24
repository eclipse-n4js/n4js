/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Utility for finding EObjects
 */
public class XtResourceUtil {

	/** @return an {@link EObject} of the given resource at the given offset */
	static public EObject findEObject(XtextResource resource, int offset, int length) {
		boolean haveRegion = length > 0;
		int endOffset = offset + length;
		EObject semanticObject = null;

		IParseResult parseResult = resource.getParseResult();
		INode node = NodeModelUtils.findLeafNodeAtOffset(parseResult.getRootNode(), offset);
		while (node != null) {
			EObject actualObject = NodeModelUtils.findActualSemanticObjectFor(node);
			if (actualObject != null) {
				if (haveRegion) {
					int nodeEndOffset = node.getEndOffset();
					if (nodeEndOffset <= endOffset || semanticObject == null) {
						semanticObject = actualObject;
					}
					if (nodeEndOffset >= endOffset) {
						break;
					}
				} else { // no region given, just a matched offset
					if (semanticObject == null) {
						semanticObject = actualObject;
						break;
					}
				}
			}
			node = node.getParent();
		}
		return semanticObject;
	}

	/** @return {@link EStructuralFeature} of the given resource at the given offset */
	static public EStructuralFeature findStructuralFeature(XtextResource resource, int offset) {
		INode leaf = NodeModelUtils.findLeafNodeAtOffset(resource.getParseResult().getRootNode(), offset);
		NodeIterator ni = null;
		while (ni == null || ni.hasNext()) {
			INode next = ni == null ? leaf : ni.next();
			if (ni == null)
				ni = new NodeIterator(leaf);
			EObject object = NodeModelUtils.findActualSemanticObjectFor(next);
			INode current = next;
			do {
				Assignment ass = GrammarUtil.containingAssignment(current.getGrammarElement());
				if (ass != null) {
					EStructuralFeature feat = object.eClass().getEStructuralFeature(ass.getFeature());
					if (feat != null)
						return feat;
				}
				current = current.getParent();
			} while (current != null && object == NodeModelUtils.findActualSemanticObjectFor(current));
		}
		throw new RuntimeException("No EStructuralFeature found at offset " + offset);
	}

}
