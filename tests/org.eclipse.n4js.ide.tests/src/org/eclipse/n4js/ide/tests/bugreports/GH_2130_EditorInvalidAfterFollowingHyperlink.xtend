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
package org.eclipse.n4js.ide.tests.bugreports

import com.google.common.util.concurrent.Uninterruptibles
import com.google.inject.Inject
import java.util.concurrent.CountDownLatch
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager
import org.junit.Test

import static org.junit.Assert.*

/**
 * When CMD-clicking on an identifier, VS Code sends a sequence of `didOpen`, `didClose`, and `didOpen` notifications for
 * the same file within a couple 100 microseconds (the first pair of open/close is for a special tool tip showing the target
 * file's source code within the original editor). Since after a `didClose` we give running operations a chance to end
 * gracefully (we just set the isCanceled flag), the second `didOpen` may occur before the old resource task context was
 * actually removed from the registry.
 */
class GH_2130_EditorInvalidAfterFollowingHyperlink extends AbstractIdeTest {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Test
	def void testFastOpenCloseOpenSequence() {
		testWorkspaceManager.createTestProjectOnDisk(
			"TestModule" -> '''
				console.log("Hello!");
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val fileURI = getFileURIFromModuleName("TestModule");
		val lengthyOperationMayFinish = new CountDownLatch(1);

		openFileNoWait(fileURI);
		resourceTaskManager.runInExistingContextVoid(fileURI.toURI, "lengthy operation", [ rtc, ci |
			// this simulates a long-running operation that does not react to cancellation and
			// runs longer than a subsequently received didClose and didOpen for the same file
			Uninterruptibles.awaitUninterruptibly(lengthyOperationMayFinish);
		]);
		closeFileNoWait(fileURI);

		openFileNoWait(fileURI);

		// this worked even before the bug fix:
		assertTrue(resourceTaskManager.isOpen(fileURI.toURI));

		// now let's allow the lengthy operation to finish:
		lengthyOperationMayFinish.countDown();
		// wait for all updates to happen
		joinServerRequests();

		// before fix: returned 'false'
		assertTrue(resourceTaskManager.isOpen(fileURI.toURI));
		// before fix: threw exception "no existing context found for given URI"
		resourceTaskManager.runInExistingContextVoid(fileURI.toURI, "noop", [
			// don't actually do anything here; we just want to make sure the ResourceTaskContext exists
		]);
	}
}
