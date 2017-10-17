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

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.Sets;

/**
 * Connects two {@link ControlFlowElement}s from {@link #start} to {@link #end}. The flow type is defined in
 * {@link #cfTypes}. It is possible, that there are multiple edges connecting the same nodes (called parallel edges).
 * <p/>
 * <b>Attention:</b> Two edges are equal and have equal hashes, iff their {@link #start} and {@link #end} nodes, and
 * their {@link #cfTypes}s are the same.
 */
public class FlowEdge implements Comparable<FlowEdge> {
	/** Source node of the edge */
	final public ControlFlowElement start;
	/** Target node of the edge */
	final public ControlFlowElement end;
	/** Flow types that connect {@link #start} and {@link #end} */
	final public SortedSet<ControlFlowType> cfTypes;

	/** Constructor */
	public FlowEdge(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
		this.start = start;
		this.end = end;
		this.cfTypes = Collections.unmodifiableSortedSet(Sets.newTreeSet(cfTypes));
	}

	@Override
	public int compareTo(FlowEdge fe) {
		int compareVal = 0;
		compareVal = (compareVal != 0) ? compareVal : this.start.hashCode() - fe.start.hashCode();
		compareVal = (compareVal != 0) ? compareVal : this.end.hashCode() - fe.end.hashCode();
		compareVal = (compareVal != 0) ? compareVal : this.cfTypes.size() - fe.cfTypes.size();

		Iterator<ControlFlowType> myCFTs = this.cfTypes.iterator();
		Iterator<ControlFlowType> otherCFTs = fe.cfTypes.iterator();
		while (compareVal == 0 && myCFTs.hasNext()) {
			ControlFlowType myCFT = myCFTs.next();
			ControlFlowType otherCFT = otherCFTs.next();
			compareVal = myCFT.hashCode() - otherCFT.hashCode();
		}

		return compareVal;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FlowEdge))
			return false;

		FlowEdge fe = (FlowEdge) o;
		boolean equals = true;
		equals &= this.start == fe.start;
		equals &= this.end == fe.end;
		equals &= this.cfTypes.equals(fe.cfTypes);
		return equals;
	}

	@Override
	public int hashCode() {
		long hashCode = start.hashCode() + end.hashCode();
		for (ControlFlowType cft : cfTypes) {
			hashCode += cft.hashCode();
		}
		return (int) (hashCode % Integer.MAX_VALUE);
	}

	@Override
	public String toString() {
		String toString = "";
		toString += FGUtils.getSourceText(start);
		toString += " -";
		boolean firstCFT = true;
		for (Iterator<ControlFlowType> cftIt = cfTypes.iterator(); cftIt.hasNext();) {
			ControlFlowType cft = cftIt.next();
			if (cft != ControlFlowType.Successor) {
				if (!firstCFT) {
					toString += "|";
				}
				firstCFT = false;
				toString += cft.name();
			}
		}
		toString += "-> ";
		toString += FGUtils.getSourceText(end);
		return toString;
	}
}
