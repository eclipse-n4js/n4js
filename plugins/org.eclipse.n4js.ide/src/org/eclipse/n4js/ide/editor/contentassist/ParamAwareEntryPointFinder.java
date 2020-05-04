/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist;

import java.util.Iterator;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.contentassist.antlr.EntryPointFinder;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;

/**
 * Specialized to be able to work with parameterized rules (which are excluded by the default impl).
 */
public class ParamAwareEntryPointFinder extends EntryPointFinder {

	@Override
	protected ICompositeNode findEntryPoint(ICompositeNode node, int offset) {
		ICompositeNode result = node;
		result = getApplicableNode(result);
		while (result != null) {
			int remainingLookAhead = result.getLookAhead();
			Iterator<ILeafNode> leafNodes = result.getLeafNodes().iterator();
			while (leafNodes.hasNext()) {
				ILeafNode leaf = leafNodes.next();
				if (leaf.getTotalOffset() >= offset) {
					break;
				}
				if (!leaf.isHidden()) {
					if (remainingLookAhead > 0) {
						remainingLookAhead--;
					}
					if (remainingLookAhead == 0) {
						if (!shouldUseParent(result, offset, leaf)) {
							return result;
						} else {
							break;
						}
					}
				}
			}
			result = getApplicableNode(result.getParent());
		}
		return result;
	}

	@Override
	protected boolean shouldUseParent(ICompositeNode result, int offset, ILeafNode leaf) {
		if (leaf.getTotalEndOffset() >= offset) {
			return true;
		}
		if (result.getGrammarElement() instanceof RuleCall) {
			RuleCall rc = (RuleCall) result.getGrammarElement();
			if (isMultipleCardinality(rc)) {
				return true;
			}
		}
		return false;
	}

	private boolean isMultipleCardinality(AbstractElement e) {
		if (GrammarUtil.isMultipleCardinality(e)) {
			return true;
		}
		if (e.eContainer() instanceof AbstractElement) {
			return isMultipleCardinality((AbstractElement) e.eContainer());
		}
		return false;
	}
}
