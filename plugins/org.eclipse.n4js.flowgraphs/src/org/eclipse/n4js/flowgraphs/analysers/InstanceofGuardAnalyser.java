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
package org.eclipse.n4js.flowgraphs.analysers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructure;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructureFactory;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.guards.InstanceofGuard;
import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * This analyzer detects and holds information about all instanceof guards at a given source location. It differentiates
 * between instanceof guards that always, never or may hold.
 */
public class InstanceofGuardAnalyser extends GraphVisitorInternal {
	Map<ControlFlowElement, InstanceofBranchWalker> elementsToBranch = new HashMap<>();

	/** Constructor */
	public InstanceofGuardAnalyser() {
		super(TraverseDirection.Forward);
	}

	/** @return all RHS expressions of {@code instanceof} guards that <b>always</b> hold at the given element */
	public Collection<InstanceofGuard> getAlwaysHoldingTypes(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);
		InstanceofBranchWalker ibw = elementsToBranch.get(cfe);
		if (ibw != null) {
			return ibw.alwaysHoldGards;
		}
		return null;
	}

	/** @return all RHS expressions of {@code instanceof} guards that <b>never</b> hold at the given element */
	public Collection<InstanceofGuard> getNeverHoldingTypes(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);
		InstanceofBranchWalker ibw = elementsToBranch.get(cfe);
		if (ibw != null) {
			return ibw.neverHoldGards;
		}
		return null;
	}

	/** @return all RHS expressions of {@code instanceof} guards that <b>may</b> hold at the given element */
	public Collection<InstanceofGuard> getMayHoldingTypes(ControlFlowElement cfe) {
		cfe = CFEMapper.map(cfe);
		InstanceofBranchWalker ibw = elementsToBranch.get(cfe);
		if (ibw != null) {
			return ibw.mayHoldGards;
		}
		return null;
	}

	@Override
	protected void initializeModeInternal(TraverseDirection curDirection, ControlFlowElement curContainer) {
		requestActivation(new InstanceofGraphExplorer());
	}

	class InstanceofGraphExplorer extends GraphExplorerInternal {
		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new InstanceofBranchWalker();
		}

		@Override
		protected BranchWalkerInternal joinBranchWalkers(List<BranchWalkerInternal> branchWalkers) {
			InstanceofBranchWalker joinedIBW = new InstanceofBranchWalker();
			Set<InstanceofGuard> alwaysHoldUnion = new HashSet<>();

			Iterator<BranchWalkerInternal> bwiIter = branchWalkers.iterator();
			if (!bwiIter.hasNext()) {
				return joinedIBW;
			}

			InstanceofBranchWalker ibw = (InstanceofBranchWalker) bwiIter.next();
			alwaysHoldUnion.addAll(ibw.alwaysHoldGards);
			joinedIBW.alwaysHoldGards.addAll(ibw.alwaysHoldGards);
			joinedIBW.neverHoldGards.addAll(ibw.neverHoldGards);
			joinedIBW.mayHoldGards.addAll(ibw.mayHoldGards);

			while (bwiIter.hasNext()) {
				ibw = (InstanceofBranchWalker) bwiIter.next();
				if (ibw.isDeadCodeBranch()) {
					continue;
				}

				alwaysHoldUnion.addAll(ibw.alwaysHoldGards);
				joinedIBW.alwaysHoldGards.retainAll(ibw.alwaysHoldGards);
				joinedIBW.neverHoldGards.retainAll(ibw.neverHoldGards);
				joinedIBW.mayHoldGards.addAll(ibw.mayHoldGards);
			}

			alwaysHoldUnion.removeAll(joinedIBW.alwaysHoldGards);
			joinedIBW.mayHoldGards.addAll(alwaysHoldUnion);
			return joinedIBW;
		}
	}

	class InstanceofBranchWalker extends BranchWalkerInternal {
		Set<InstanceofGuard> alwaysHoldGards = new HashSet<>();
		Set<InstanceofGuard> neverHoldGards = new HashSet<>();
		Set<InstanceofGuard> mayHoldGards = new HashSet<>();

		@Override
		protected BranchWalkerInternal fork() {
			InstanceofBranchWalker ibw = new InstanceofBranchWalker();
			ibw.alwaysHoldGards.addAll(alwaysHoldGards);
			ibw.neverHoldGards.addAll(neverHoldGards);
			ibw.mayHoldGards.addAll(mayHoldGards);
			return ibw;
		}

		@Override
		protected void visit(Node node) {
			elementsToBranch.put(node.getControlFlowElement(), this);
		}

		@Override
		protected void visit(Node lastVisitNode, Node end, ControlFlowEdge edge) {
			GuardStructure guardStructure = GuardStructureFactory.create(edge);

			if (guardStructure != null) {
				for (Guard guard : guardStructure.allGuards()) {
					if (guard.type == GuardType.InstanceOf) {
						InstanceofGuard instanceofGuard = (InstanceofGuard) guard;

						switch (guard.asserts) {
						case AlwaysHolds:
							alwaysHoldGards.add(instanceofGuard);
							break;
						case NeverHolds:
							neverHoldGards.add(instanceofGuard);
							break;
						case MayHolds:
							mayHoldGards.add(instanceofGuard);
							break;

						default:
							break;
						}
					}
				}
			}
		}

	}
}
