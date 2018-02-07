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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link FastFlowVisitor} is used to traverse the source code in execution order. The main idea is the following:
 * <ul>
 * <li/>The analysis starts from an activation location (AL).
 * <li/>From AL in forward or backward execution direction, locations of interest (LIs) are searched.
 * <li/>If an LI is found, the corresponding AL can be retrieved.
 * <li/>It is guaranteed, that there exists a control flow from AL to LI.
 * <li/>Note: Dead code control flows have to be deactivated manually.
 * </ul>
 *
 * <p>
 * It is optimized for time performance and simple use. Hence, only one method has to be implemented:
 * {@link #visitNext(FastFlowBranch, ControlFlowElement)}. This method serves two purposes: (1) to activate a branch
 * using {@link ActivationLocation}s, and (2) to find locations of interest that succeed these
 * {@link ActivationLocation}s.
 */
abstract public class FastFlowVisitor extends GraphVisitor {
	final InitialFastFlowBranch iffb = new InitialFastFlowBranch();
	private FFExplorer ffExplorer;

	/**
	 * {@link ActivationLocation}s mark a location in the source. The method {@link #getKey()} is used to (1) prevent
	 * that the same location is activate multiple times, and (2) to find the {@link ActivationLocation} when an
	 * location of interest was found.
	 */
	abstract static protected class ActivationLocation {
		private ControlFlowElement activationLocation;

		/** @return a key for an activation location. There can be only one {@link ActivationLocation} for each key */
		abstract public Object getKey();

		/** @return the {@link ControlFlowElement} of the activation location */
		public ControlFlowElement getLocation() {
			return activationLocation;
		}
	}

	/** {@link FastFlowBranch} provides methods for a location while traversing the source code. */
	static protected interface FastFlowBranch {
		/** Creates a new {@link ActivationLocation} */
		public void activate(ActivationLocation activationLocation);

		/** @return the {@link ActivationLocation} for the given key, or null */
		public ActivationLocation getActivationLocation(Object key);

		/** @return all {@link ActivationLocation}s that were created on this branch or its predecessors */
		public Collection<ActivationLocation> getAllActivationLocations();
	}

	class InitialFastFlowBranch implements FastFlowBranch {
		ActivationLocation actLoc;

		@Override
		public void activate(ActivationLocation activationLocation) {
			this.actLoc = activationLocation;
			ffExplorer = new FFExplorer();
			requestActivation(ffExplorer);
		}

		@Override
		public ActivationLocation getActivationLocation(Object key) {
			return null;
		}

		@Override
		public Collection<ActivationLocation> getAllActivationLocations() {
			return Collections.emptyList();
		}
	}

	/**
	 * Visits the next {@link ControlFlowElement}.
	 * <p>
	 * Note: Two succeeding calls to this method do not imply that their cfe values succeed each other.
	 */
	abstract protected void visitNext(FastFlowBranch currentBranch, ControlFlowElement cfe);

	/** @return all {@link ActivationLocation}s that this Visitor found */
	public Collection<ActivationLocation> getAllActivationLocations() {
		Collection<ActivationLocation> actLocs = new LinkedList<>();
		for (GraphExplorerInternal expl : getActivatedExplorers()) {
			FFBranch lastFFB = (FFBranch) expl.allBranches.getLast();
			Collection<ActivationLocation> branchValues = lastFFB.actLocs.values();
			actLocs.addAll(branchValues);
		}
		return actLocs;
	}

	/** Constructor */
	public FastFlowVisitor() {
		super(TraverseDirection.Backward);
	}

	@Override
	final protected void visit(ControlFlowElement cfe) {
		if (this.ffExplorer == null) {
			visitNext(iffb, cfe);
		}
	}

	@Override
	protected void terminateMode(TraverseDirection curMode, ControlFlowElement curContainer) {
		this.ffExplorer = null;
	}

	private class FFExplorer extends GraphExplorer {
		private boolean skipFirstVisit = true;

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new FFBranch();
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			FFBranch joinedWalker = new FFBranch();
			for (BranchWalker bW : branchWalkers) {
				FFBranch cvbw = (FFBranch) bW;
				joinedWalker.actLocs.putAll(cvbw.actLocs);
			}
			return joinedWalker;
		}
	}

	private class FFBranch extends BranchWalker implements FastFlowBranch {
		final Map<Object, ActivationLocation> actLocs = new HashMap<>();

		@Override
		protected FFBranch forkPath() {
			FFBranch newBranch = new FFBranch();
			newBranch.actLocs.putAll(actLocs);
			return newBranch;
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			if (ffExplorer.skipFirstVisit) {
				ffExplorer.skipFirstVisit = false;
				actLocs.put(iffb.actLoc.getKey(), iffb.actLoc);
				return;
			}
			visitNext(this, cfe);
		}

		@Override
		public void activate(ActivationLocation activationLocation) {
			actLocs.put(activationLocation.getKey(), activationLocation);
		}

		@Override
		public ActivationLocation getActivationLocation(Object key) {
			return actLocs.get(key);
		}

		@Override
		public Collection<ActivationLocation> getAllActivationLocations() {
			return actLocs.values();
		}
	}

}
