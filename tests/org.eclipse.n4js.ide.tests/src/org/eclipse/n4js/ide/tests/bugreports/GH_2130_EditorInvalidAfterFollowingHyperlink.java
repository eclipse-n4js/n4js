/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.bugreports;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.xtext.server.ResourceTaskManagerIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.inject.Inject;

/**
 * Real-world use case that lead to this bug:
 * <p>
 * When CMD-clicking on an identifier, VS Code sends a sequence of 'didOpen', 'didClose', and 'didOpen' notifications
 * for the same file within a couple 100 microseconds (the first pair of open/close is for a special tool tip showing
 * the target file's source code within the original editor). Since after a 'didClose' we give running operations a
 * chance to end gracefully (we just set the isCanceled flag), the second 'didOpen' may occur before the old resource
 * task context was actually removed from the resource task context.
 *
 * @see ResourceTaskManagerIdeTest#testCreateDisposeRecreate()
 */

public class GH_2130_EditorInvalidAfterFollowingHyperlink extends AbstractIdeTest {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Test
	public void testFastOpenCloseOpenSequence() {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("TestModule", " console.log('Hello!');"));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI fileURI = getFileURIFromModuleName("TestModule");
		CountDownLatch lengthyOperationMayFinish = new CountDownLatch(1);

		openFileNoWait(fileURI);
		resourceTaskManager.runInExistingContextVoid(fileURI.toURI(), "lengthy operation", (rtc, ci) -> {
			// this simulates a long-running operation that does not react to cancellation and
			// runs longer than a subsequently received didClose and didOpen for the same file
			Uninterruptibles.awaitUninterruptibly(lengthyOperationMayFinish, 10, TimeUnit.SECONDS);
		});
		closeFileNoWait(fileURI);

		openFileNoWait(fileURI);

		// this worked even before the bug fix:
		assertTrue(resourceTaskManager.hasContext(fileURI.toURI()));

		// now let's allow the lengthy operation to finish:
		lengthyOperationMayFinish.countDown();
		// wait for all updates to happen
		joinServerRequests();

		// before fix: returned 'false'
		assertTrue(resourceTaskManager.hasContext(fileURI.toURI()));
		// before fix: threw exception "no existing context found for given URI"
		resourceTaskManager.runInExistingContextVoid(fileURI.toURI(), "noop", (rtc, ci) -> {
			// don't actually do anything here; we just want to make sure the ResourceTaskContext exists
		});
	}
}
