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
package org.eclipse.n4js.hlc.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.n4js.hlc.base.BuildType;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

/**
 * Downloads, installs, compiles and runs 'express'.
 */
public class TargetPlatformReuseTest extends AbstractN4jscTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("GH-963-reuse-tp", Predicates.alwaysTrue());
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/**
	 * Test for checking the npm support in the headless case by downloading third party package, importing it and
	 * running it with Common JS.
	 */
	@Test
	public void testReuseTargetPlatformLocation() throws ExitCodeException {
		final String wsRoot = workspace.getAbsolutePath().toString();

		final String[] args = {
				"--installMissingDependencies",
				"--projectlocations", wsRoot,
				"--buildType", BuildType.allprojects.toString()
		};

		// obtain reference to the node_modules folder in use
		final File nodeModulesFolder = new File(wsRoot + "/A" + "/node_modules");

		// first call, initially installs dependencies into target platform location
		new N4jscBase().doMain(args);

		// compute footprint (wrt. modification timestamps) of node_modules folder
		final SortedMap<String, Long> moduleFootprintBefore = computeFolderModificationFootprint(nodeModulesFolder);

		// second call, should reuse target platform location of first call
		new N4jscBase().doMain(args);

		// compute second footprint of node_modules folder for comparison
		final SortedMap<String, Long> moduleFootprintAfter = computeFolderModificationFootprint(nodeModulesFolder);
		final List<String> modifiedPackages = computeModifiedFiles(moduleFootprintBefore, moduleFootprintAfter);

		Assert.assertEquals("Packages should not be re-installed on target platform location reuse.",
				"[]", modifiedPackages.toString());
	}

	/**
	 * Computes a modification timestamp footprint for the given {@code folder}.
	 */
	private SortedMap<String, Long> computeFolderModificationFootprint(File folder) {
		final SortedMap<String, Long> modificationFootprint = new TreeMap<>();

		for (File file : folder.listFiles()) {
			modificationFootprint.put(file.getName(), file.lastModified());
		}

		return modificationFootprint;
	}

	/**
	 * Given two sorted maps of modification footprint maps (cf. {@link #computeFolderModificationFootprint(File)}),
	 * this method returns the list of file names that were modified between the two footprints.
	 *
	 * More specifically, this includes files that were removed or added or whose {@link File#lastModified()} timestamps
	 * differ.
	 */
	private List<String> computeModifiedFiles(SortedMap<String, Long> beforeFootprint,
			SortedMap<String, Long> afterFootprint) {

		List<String> modifiedFiles = new ArrayList<>();

		// check for modified modules
		for (Entry<String, Long> beforeEntry : beforeFootprint.entrySet()) {
			final long afterTimestamp = afterFootprint.getOrDefault(beforeEntry.getKey(), 0L);
			if (afterTimestamp != beforeEntry.getValue()) {
				modifiedFiles.add(beforeEntry.getKey());
			}
		}

		// check for newly added modules
		for (Entry<String, Long> afterEntry : afterFootprint.entrySet()) {
			if (!beforeFootprint.containsKey(afterEntry.getKey())) {
				modifiedFiles.add(afterEntry.getKey());
			}
		}

		// sort list alphabetically for test robustness
		modifiedFiles.sort(Comparator.comparing(s -> s));

		return modifiedFiles;
	}

}
