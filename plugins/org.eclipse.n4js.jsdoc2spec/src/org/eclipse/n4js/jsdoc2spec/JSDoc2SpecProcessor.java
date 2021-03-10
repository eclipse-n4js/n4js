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
package org.eclipse.n4js.jsdoc2spec;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.utils.ContainerTypesHelper;

import com.google.inject.Inject;

/**
 * Main controller scanning project for JSDoc comments, reading existing doc files if necessary, and merging results to
 * new doc file contents. Needs to be injected.
 *
 * <p>
 * Jira: IDE-1356, IDE-2336
 */
abstract public class JSDoc2SpecProcessor {

	@Inject
	N4JSDReader n4jsdReader;

	/**
	 * Helper instance, e.g. to access type members.
	 */
	@Inject
	protected ContainerTypesHelper containerTypesHelper;

	/**
	 * Contains all issues discovered during the doc generation.
	 */
	protected final IJSDoc2SpecIssueAcceptor issueAcceptor = new JSDoc2SpecAcceptor();

	/**
	 * This method sets the root directory.
	 */
	abstract public void setRootDir(File newRootDir);

	/**
	 * Computes doc file updates.
	 *
	 * @throws InterruptedException
	 *             thrown when user cancels the operation
	 *
	 * @returns map with changed files as keys and new content.
	 */
	abstract public Collection<SpecFile> computeUpdates(Collection<SpecInfo> specInfos, SubMonitorMsg monitor)
			throws IOException, InterruptedException;

	/**
	 * Full processing including reading of doc files, scanning all projects, and eventually updating doc files.
	 * Usually, these steps are done separately by client, e.g., by wizard; this method is basically for tests.
	 *
	 * @throws InterruptedException
	 *             thrown when user cancels the operation
	 */
	public Collection<SpecFile> convert(File docRoot, Collection<N4JSProjectConfigSnapshot> projects,
			Function<N4JSProjectConfigSnapshot, ResourceSet> resSetProvider, SubMonitorMsg monitor)
			throws IOException, InterruptedException {

		setRootDir(docRoot);
		SubMonitorMsg sub = monitor.convert(4 * 100);
		Collection<SpecInfo> specInfos = readN4JSDs(projects, resSetProvider, sub.newChild(200));
		Collection<SpecFile> specChangeSet = computeUpdates(specInfos, sub.newChild(100));
		return specChangeSet;
	}

	/**
	 * Returns all warnings with locations.
	 */
	public String getWarnings() {
		return issueAcceptor.warnings();
	}

	/**
	 * Clears all warnings.
	 */
	public void resetIssues() {
		issueAcceptor.reset();
	}

	/**
	 * @throws InterruptedException
	 *             thrown when user cancels the operation
	 * @see N4JSDReader#readN4JSDs(Collection, Function, SubMonitorMsg)
	 */
	public Collection<SpecInfo> readN4JSDs(Collection<N4JSProjectConfigSnapshot> projects,
			Function<N4JSProjectConfigSnapshot, ResourceSet> resSetProvider, SubMonitorMsg monitor)
			throws InterruptedException {

		SubMonitorMsg sub = monitor.convert(100 * (projects.size() + 2));
		n4jsdReader.issueAcceptor = this.issueAcceptor;
		return n4jsdReader.readN4JSDs(projects, resSetProvider, sub);
	}
}
