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
package org.eclipse.n4js.tester.ui.resultsview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestElement;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;

/**
 * Internal data model for {@link TestResultsView}.
 * <p>
 * By only allowing to define parent/child relations via the constructor (note that {@link #addChild(ResultNode)} is
 * private) we enforce that trees are built top/down and are immutable. This simplifies caching of results in non-leaf
 * nodes and other things.
 * <p>
 * Rationale for creating a dedicated data model for the UI and not using the test model (i.e. TestTree, TestSuite, ...)
 * directly:
 * <ul>
 * <li>we may want to have a different hierarchy in the UI than in the test model.
 * <li>we need more tree traversal functionality than in the test model (i.e. {@link #getParent()}).
 * <li>we need to cache the aggregated status (sum of passed, failed, etc. tests) from an entire sub tree in each
 * non-leaf node.
 * <li>we need a fast node look-up (c.f. {@link #findById(ID)}).
 * </ul>
 */
/* package */class ResultNode {

	private final ResultNode parent;
	// list of child nodes; will be <code>null</code> in leaf nodes.
	private final List<ResultNode> children;
	// aggregated status of all children; will be <code>null</code> in leaf nodes.
	private final TestStatusCounter childrenStatus;
	// a map to quickly find a node by ID; will be <code>null</code> in all nodes except the root.
	private final Map<ID, ResultNode> nodeRegistry;

	private final TestElement element;

	private boolean running = false;

	public ResultNode(ResultNode parent, TestElement element) {
		this.parent = parent;
		this.element = element;

		this.children = isContainer() ? new ArrayList<>() : null;
		this.childrenStatus = isContainer() ? new TestStatusCounter() : null;
		this.nodeRegistry = isRoot() ? new HashMap<>() : null;

		// make sure root itself is registered in the nodeRegistry
		if (isRoot() && getId() != null)
			nodeRegistry.put(getId(), this);

		// add to parent (if any)
		if (parent != null)
			parent.addChild(this);
	}

	// ####################################################################################################
	// TYPES OF NODES

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isContainer() {
		return !isLeaf();
	}

	public boolean isLeaf() {
		return element instanceof TestCase;
	}

	// ####################################################################################################
	// PARENT/CHILD MANAGEMENT

	public ResultNode getParent() {
		return parent;
	}

	/** never returns <code>null</code>. */
	public ResultNode getRoot() {
		ResultNode n = this;
		while (n.parent != null)
			n = n.parent;
		return n;
	}

	public boolean isAncestorOf(ResultNode other) {
		while (other != null) {
			other = other.parent;
			if (this == other)
				return true;
		}
		return false;
	}

	public boolean hasChildren() {
		return isContainer() && !children.isEmpty();
	}

	public ResultNode[] getChildren() {
		return isContainer() ? children.toArray(new ResultNode[children.size()]) : new ResultNode[0];
	}

	private void addChild(ResultNode child) {
		if (isLeaf())
			throw new IllegalStateException("cannot add a child to a leaf node");
		children.add(child);
		final ID childId = child.getId();
		if (childId != null)
			getRoot().nodeRegistry.put(childId, child);
	}

	public ResultNode findById(ID id) {
		return id != null ? getRoot().nodeRegistry.get(id) : null;
	}

	// ####################################################################################################
	// GETTERS/SETTERS FOR SIMPLE PROPERTIES (either stored in ResultNode or taken from test model element)

	public boolean isRunning() {
		return running;
	}

	public void startRunning() {
		running = true;
	}

	public void stopRunning() {
		running = false;
	}

	public TestElement getElement() {
		return element;
	}

	public TestTree getTestTree() {
		if (element instanceof TestTree)
			return (TestTree) element;
		return null;
	}

	public TestSuite getTestSuite() {
		if (element instanceof TestSuite)
			return (TestSuite) element;
		return null;
	}

	public TestCase getTestCase() {
		if (element instanceof TestCase)
			return (TestCase) element;
		return null;
	}

	public void updateResult(TestResult newResult) {
		final TestCase tc = getTestCase();
		if (tc != null) {
			tc.setResult(newResult);
			// perform necessary updates (also in ancestors) ...
			refreshCachedStatus();
		}
	}

	public ID getId() {
		if (element instanceof TestTree)
			return ((TestTree) element).getSessionId();
		if (element instanceof TestCase)
			return ((TestCase) element).getId();
		return null;
	}

	public TestStatus getStatus() {
		final TestCase tc = getTestCase();
		if (tc != null) {
			final TestResult tr = tc.getResult();
			if (tr != null) {
				return tr.getTestStatus();
			}
		}
		return null;
	}

	public long getElapsedTime() {
		final TestCase tc = getTestCase();
		if (tc != null) {
			final TestResult tr = tc.getResult();
			if (tr != null) {
				return tr.getElapsedTime();
			}
		}
		return 0;
	}

	// ####################################################################################################
	// MANAGEMENT OF PROPERTIES DERIVED FROM CHILDREN, GRAND CHILDREN, etc.

	public TestStatusCounter getChildrenStatus() {
		return childrenStatus;
	}

	protected void refreshCachedStatus() {
		// update myself
		if (isLeaf()) {
			// no cached status in this case; we directly read the status from the TestResult of the TestCase every
			// time #getStatus() is called -> so nothing to update here
		} else {
			// derive cached childrenStatus from children
			childrenStatus.clear();
			for (ResultNode child : children) {
				if (child.isLeaf()) {
					final TestStatus childStatus = child.getStatus();
					if (childStatus != null)
						childrenStatus.increment(childStatus);
				} else {
					childrenStatus.increment(child.childrenStatus);
				}
			}
		}

		// update ancestors
		if (parent != null)
			parent.refreshCachedStatus();
	}

	// note: caching of this property not required
	public int countTestCases() {
		return (int) stream().filter(node -> node.element instanceof TestCase).count();
	}

	public Stream<ResultNode> stream() {
		if (isContainer()) {
			return Stream.concat(Stream.of(this), children.stream().flatMap((child) -> {
				return child.stream();
			}));
		}
		return Stream.of(this);
	}

	/**
	 * Returns (maybe empty) list of all test cases that have failed or errors.
	 */
	public List<TestCase> getFailed() {
		List<TestCase> failed = new ArrayList<>();
		collectFailed(failed);
		return failed;
	}

	private void collectFailed(List<TestCase> failed) {
		if (getTestCase() != null) {
			TestResult result = getTestCase().getResult();
			if (result != null) {
				TestStatus status = result.getTestStatus();
				if (status != null && status.isFailedOrError()) {
					failed.add(getTestCase());
				}
			}
		} else if (!isLeaf()) {
			for (ResultNode node : getChildren()) {
				failed.addAll(node.getFailed());
			}
		}
	}
}
