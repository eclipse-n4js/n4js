/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ASTIterator;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

/**
 *
 */
public class ReentrantASTIterator implements ASTIteratorInfo {
	final private Set<ControlFlowElement> cfContainers;
	final private Map<ControlFlowElement, ComplexNode> cnMap;
	final private ASTIterator astIt;

	ReentrantASTIterator(Set<ControlFlowElement> cfContainers, Map<ControlFlowElement, ComplexNode> cnMap,
			Script script) {

		this.cfContainers = cfContainers;
		this.cnMap = cnMap;
		this.astIt = new ASTIterator(script);
	}

	/** Creates {@link ComplexNode}s for every {@link ControlFlowElement}. */
	@Override
	public void visitUtil(ControlFlowElement terminateNode) {

		while (astIt.hasNext()) {
			ControlFlowElement cfe = astIt.next();
			ControlFlowElement mappedCFE = CFEMapper.map(cfe);
			if (cfe == mappedCFE) {
				if (mappedCFE != null && !cnMap.containsKey(mappedCFE)) {
					ComplexNode cn = CFEFactoryDispatcher.build(this, mappedCFE);
					if (cn != null) {
						cfContainers.add(cn.getControlFlowContainer());
						cnMap.put(mappedCFE, cn);
					}
				}

				if (cfe == terminateNode || cfe == mappedCFE) {
					// break;
				}
			}
		}
	}

	@Override
	public ControlFlowElement container() {
		return astIt.container();
	}

	@Override
	public int pos() {
		return astIt.pos();
	}

}
