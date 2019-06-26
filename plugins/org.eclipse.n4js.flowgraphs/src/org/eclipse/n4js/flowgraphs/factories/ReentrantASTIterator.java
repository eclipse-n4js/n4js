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

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ASTIterator;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;

/**
 * The {@link ReentrantASTIterator} is implemented to number all nodes in order of their location in the AST. This works
 * as follows. The elements of the AST are traversed in order and for each element, a {@link ComplexNode} is created.
 * For every {@link DelegatingNode} of a {@link ComplexNode}, this {@link ReentrantASTIterator} is called again
 * (re-entered). This is done to await the traversal of the delegated AST element. After this delegated AST element is
 * processed, its AST position is known. Now, its succeeding node can be created with the correct AST position.
 */
public class ReentrantASTIterator {
	static final String ASSERTION_MSG_AST_ORDER = "DelegatingNode or AST order erroneous";

	final private SymbolFactory symbolFactory;
	final private Set<ControlFlowElement> cfContainers;
	final private Map<ControlFlowElement, ComplexNode> cnMap;
	final private ASTIterator astIt;
	final private String scriptName;

	private int astPositionCounter = 0;

	/** Constructor */
	ReentrantASTIterator(SymbolFactory symbolFactory, Set<ControlFlowElement> cfContainers,
			Map<ControlFlowElement, ComplexNode> cnMap, Script script) {

		this.symbolFactory = symbolFactory;
		this.cfContainers = cfContainers;
		this.cnMap = cnMap;
		this.astIt = new ASTIterator(script);
		this.scriptName = script.eResource().getURI().toString();
	}

	/** Visits all {@link ControlFlowElement} in the {@link Script} */
	public void visitAll() {
		visitUtil(null);
	}

	/** Creates {@link ComplexNode}s for every {@link ControlFlowElement}. */
	public void visitUtil(ControlFlowElement termNode) {
		termNode = CFEMapper.map(termNode);
		while (astIt.hasNext()) {
			ControlFlowElement cfe = astIt.next();
			ControlFlowElement mappedCFE = CFEMapper.map(cfe);
			if (cfe == mappedCFE) {
				if (mappedCFE != null && !cnMap.containsKey(mappedCFE)) {
					ComplexNode cn = CFEFactoryDispatcher.build(this, mappedCFE);
					if (cn != null) {
						checkState(astPositionCounter - 1 == cn.getExit().astPosition, ASSERTION_MSG_AST_ORDER);

						cfContainers.add(cn.getControlFlowContainer());
						cnMap.put(mappedCFE, cn);

						try (Measurement m = N4JSDataCollectors.dcAugmentEffectInfo
								.getMeasurement("AugmentEffectInfo_" + scriptName);) {

							CFEEffectInfos.set(symbolFactory, cnMap, cn, cfe);
						}
					}
				}
				if (termNode == cfe || (termNode == mappedCFE && termNode != null)) {
					return;
				}
			}
		}

		checkState(termNode == null, ASSERTION_MSG_AST_ORDER);
	}

	/** @return the current container */
	public ControlFlowElement container() {
		return astIt.container();
	}

	/** @return the current position */
	public int pos() {
		return astPositionCounter++;
	}

}
