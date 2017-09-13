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
package org.eclipse.n4js.runner.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.binaries.BinaryCommandFactory;
import org.eclipse.n4js.binaries.nodejs.NodeBinariesConstants;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.utils.Version;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests node and npm version used in tests to be the correct version. Basically for simplifying error resolution in
 * case the wrong node version is picked up by a test.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class NodeVersionTest {

	@Inject
	NodeJsBinary nodeJsBinary;

	@Inject
	NpmBinary npmBinary;

	@Inject
	private BinaryCommandFactory commandFactory;

	/**
	 * Simply checks if the npm version found by default matches the required version. Prints out path if this fails.
	 */
	@Test
	public void testNPMVersion() {

		final ProcessResult result = commandFactory.checkBinaryVersionCommand(npmBinary).execute();
		final Version currentVersion = Version.createFromString(result.getStdOut().trim());

		assertTrue("Version of node in " + npmBinary.getBinaryAbsolutePath() + ": " + currentVersion
				+ ",  need at least " + NodeBinariesConstants.NPM_MIN_VERSION,
				currentVersion.compareTo(NodeBinariesConstants.NPM_MIN_VERSION) >= 0);
	}

	/**
	 * Simply checks if the node version found by default matches the required version. Prints out path if this fails.
	 */
	@Test
	public void testNodeJsVersion() {

		final ProcessResult result = commandFactory.checkBinaryVersionCommand(nodeJsBinary).execute();

		final Version currentVersion = Version.createFromString(result.getStdOut().trim());

		assertTrue("Version of node in " + nodeJsBinary.getBinaryAbsolutePath() + ": " + currentVersion
				+ ",  need at least " + NodeBinariesConstants.NODE_MIN_VERSION,
				currentVersion.compareTo(NodeBinariesConstants.NODE_MIN_VERSION) >= 0);
	}

}
