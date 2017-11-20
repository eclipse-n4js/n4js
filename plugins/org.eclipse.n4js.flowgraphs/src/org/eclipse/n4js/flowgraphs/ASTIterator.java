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
package org.eclipse.n4js.flowgraphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.factories.OrderedEContentProvider;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
final public class ASTIterator implements Iterator<ControlFlowElement> {
	ControlFlowElement cfe;
	ArrayList<EObject> nextElems = new ArrayList<>();
	ArrayList<ControlFlowElement> containerStack = new ArrayList<>();
	private int astPositionCounter = 0;

	public ASTIterator(EObject cfe) {
		nextElems.add(cfe);
	}

	@Override
	public boolean hasNext() {
		searchNextCFE();
		for (int i = 0; i < containerStack.size(); i++) {
			if (nextElems.get(i) != containerStack.get(containerStack.size() - 1 - i)) {
				return true;
			}
		}
		return containerStack.isEmpty() && !nextElems.isEmpty();
	}

	@Override
	public ControlFlowElement next() {
		return pop();
	}

	public ControlFlowElement container() {
		if (containerStack.isEmpty()) {
			return null;
		}
		return containerStack.get(containerStack.size() - 1);
	}

	public int pos() {
		return astPositionCounter++;
	}

	private void searchNextCFE() {
		int idx = 0;
		while (isClosingContainer(idx)) {
			idx++;
		}

		while (nextElems.size() > idx && !(nextElems.get(idx) instanceof ControlFlowElement)) {
			EObject firstElem = nextElems.remove(idx);
			List<EObject> children = OrderedEContentProvider.eContents(firstElem);
			nextElems.addAll(idx, children);

			while (isClosingContainer(idx)) {
				idx++;
			}
		}
	}

	private boolean isClosingContainer(int idx) {
		int cntSize = containerStack.size();
		return cntSize > idx && nextElems.get(idx) == containerStack.get(cntSize - 1 - idx);
	}

	private ControlFlowElement pop() {
		searchNextCFE();
		ControlFlowElement firstElem = (ControlFlowElement) nextElems.remove(0);
		if (!containerStack.isEmpty() && firstElem == containerStack.get(containerStack.size() - 1)) {
			containerStack.remove(containerStack.size() - 1);
			return pop();
		}

		List<EObject> children = OrderedEContentProvider.eContents(firstElem);
		boolean isContainer = FGUtils.isCFContainer(firstElem);

		if (isContainer) {
			containerStack.add(firstElem);
			nextElems.add(0, firstElem);
		}
		nextElems.addAll(0, children);
		return firstElem;
	}

}
