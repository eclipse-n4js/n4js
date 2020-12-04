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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructure;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructureFactory;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.guards.InstanceofGuard;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * This analyzer detects and holds information about all instanceof guards at a given source location. It differentiates
 * between instanceof guards that always, never or may hold.
 */
public class InstanceofGuardAnalyser extends GraphVisitorInternal {
	Map<ControlFlowElement, InstanceofBranchWalker> elementsToBranch = new HashMap<>();
	Multimap<IdentifierRef, InstanceofGuard> guardsOnIRef = HashMultimap.create();

	/** Constructor */
	public InstanceofGuardAnalyser() {
		super(TraverseDirection.Forward);
	}

	/** @return all {@code instanceof} guards that <b>always</b> hold at the given element */
	public Collection<InstanceofGuard> getAlwaysHoldingGuards(ControlFlowElement cfe) {
		return getGuards(cfe, GuardAssertion.AlwaysHolds);
	}

	/** @return all {@code instanceof} guards that <b>never</b> hold at the given element */
	public Collection<InstanceofGuard> getDefinitiveGuards(ControlFlowElement cfe) {
		return getGuards(cfe, GuardAssertion.AlwaysHolds, GuardAssertion.NeverHolds);
	}

	/** @return all {@code instanceof} guards that have the given {@link GuardAssertion} */
	protected Collection<InstanceofGuard> getGuards(ControlFlowElement cfe, GuardAssertion... guardAssertions) {
		if (cfe instanceof IdentifierRef) {
			Collection<InstanceofGuard> assertedGuards = new ArrayList<>();
			Set<GuardAssertion> gaSet = Sets.newHashSet(guardAssertions);
			for (InstanceofGuard guard : guardsOnIRef.get((IdentifierRef) cfe)) {
				if (gaSet.contains(guard.asserts)) {
					assertedGuards.add(guard);
				}
			}

			return assertedGuards;
		}
		return Collections.emptyList();
	}

	@Override
	protected void initializeContainerInternal(ControlFlowElement curContainer) {
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

			Iterator<BranchWalkerInternal> bwiIter = branchWalkers.iterator();
			if (!bwiIter.hasNext()) {
				return joinedIBW;
			}

			InstanceofBranchWalker ibw = (InstanceofBranchWalker) bwiIter.next();
			joinedIBW.guards.putAll(ibw.guards);

			while (bwiIter.hasNext()) {
				ibw = (InstanceofBranchWalker) bwiIter.next();
				if (ibw.isDeadCodeBranch()) {
					continue;
				}

				joinedIBW.guards.keySet().retainAll(ibw.guards.keySet());
				for (Symbol symbol : joinedIBW.guards.keySet()) {
					joinedIBW.guards.get(symbol).retainAll(ibw.guards.get(symbol));
				}
			}

			return joinedIBW;
		}
	}

	class InstanceofBranchWalker extends BranchWalkerInternal {
		Multimap<Symbol, InstanceofGuard> guards = HashMultimap.create();

		@Override
		protected BranchWalkerInternal fork() {
			InstanceofBranchWalker ibw = new InstanceofBranchWalker();
			ibw.guards.putAll(guards);
			return ibw;
		}

		@Override
		protected void visit(Node node) {
			ControlFlowElement cfe = node.getControlFlowElement();
			elementsToBranch.put(cfe, this);

			Collection<EffectInfo> writeEInfos = EffectInfo.findAll(node.effectInfos, EffectType.Write);
			if (!writeEInfos.isEmpty()) {
				// The symbol was written, hence the guard is invalid and is removed.
				for (EffectInfo ei : writeEInfos) {
					guards.removeAll(ei.symbol);
					guardsOnIRef.removeAll(ei.location);
				}
			}

			if (cfe instanceof IdentifierRef) {
				// Type guards only work on IdentifierRefs
				IdentifierRef iRef = (IdentifierRef) cfe;
				Symbol cfeSymbol = getSymbolFactory().create(cfe);
				if (cfeSymbol != null && cfeSymbol.definingContainers.size() < 2) {
					Collection<InstanceofGuard> guardsOnCfe = guards.get(cfeSymbol);
					guardsOnIRef.replaceValues(iRef, guardsOnCfe);
				}
			}
		}

		@Override
		protected void visit(Node lastVisitNode, Node end, ControlFlowEdge edge) {
			GuardStructure guardStructure = GuardStructureFactory.create(getSymbolFactory(), edge);

			if (guardStructure != null) {
				for (Guard guard : guardStructure.allGuards()) {
					if (guard.type == GuardType.InstanceOf) {
						InstanceofGuard instanceofGuard = (InstanceofGuard) guard;

						switch (guard.asserts) {
						case AlwaysHolds:
							guards.put(guard.getSymbol(), instanceofGuard);
							break;
						case NeverHolds:
							guards.put(guard.getSymbol(), instanceofGuard);
							break;
						case MayHolds:
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
