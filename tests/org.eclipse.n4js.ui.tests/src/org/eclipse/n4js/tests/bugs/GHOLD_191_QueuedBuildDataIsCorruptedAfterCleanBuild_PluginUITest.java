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
package org.eclipse.n4js.tests.bugs;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Class for testing that the {@link QueuedBuildData} contains no queued resources after a full clean build.
 */
@SuppressWarnings("restriction")
public class GHOLD_191_QueuedBuildDataIsCorruptedAfterCleanBuild_PluginUITest extends AbstractIDEBUG_Test {

	private static final String PROJECT_NAME = "GHOLD-191";

	@Inject
	private QueuedBuildData queuedBuildData;

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File("probands/" + PROJECT_NAME + "/").getAbsolutePath()));
	}

	/**
	 * Asserts that the {@link QueuedBuildData} contains no queued resource after a full clean build.
	 */
	@Test
	public void testQueuedResourcesAfterFullCleanBuild() throws CoreException {
		cleanBuild();
		waitForAutoBuild();
		final Iterable<URI> allRemainingURIs = queuedBuildData.getAllRemainingURIs();
		assertTrue("Expected zero remaining queued resources after a full clean build. Was: "
				+ Iterables.toString(allRemainingURIs), Iterables.isEmpty(allRemainingURIs));
	}

}
