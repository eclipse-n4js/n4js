/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import com.google.common.base.Optional
import java.util.List
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceBuilder
import org.eclipse.xtext.service.AbstractGenericModule
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests handling of a cancellation during an incremental build.
 */
class GeneratingOutputFileDoesNotTriggerBuildTest extends AbstractIncrementalBuilderTest {

	private static final AtomicInteger incrementalBuildWasTriggered = new AtomicInteger();

	// report when incremental builds are triggered
	private static class ReportingWorkspaceBuilder extends XWorkspaceBuilder {

		override createIncrementalBuildTask(List<URI> dirtyFiles, List<URI> deletedFiles) {
			incrementalBuildWasTriggered.incrementAndGet();
			return super.createIncrementalBuildTask(dirtyFiles, deletedFiles);
		}
	}

	public static final class GeneratingOutputFileDoesNotTriggerBuildTestModule extends AbstractGenericModule {

		def Class<? extends XWorkspaceBuilder> bindXWorkspaceBuilder() {
			return ReportingWorkspaceBuilder;
		}
	}

	override protected getOverridingModule() {
		return Optional.of(GeneratingOutputFileDoesNotTriggerBuildTestModule);
	}

	@Test
	def void testGeneratingOutputFileDoesNotTriggerBuild() {
		testWorkspaceManager.createTestProjectOnDisk(
			"SomeModule" -> '''
				console.log('hello');
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// make sure injection is set up properly
		incrementalBuildWasTriggered.set(0);
		changeNonOpenedFile("SomeModule", "'hello'" -> "'bye bye'");
		joinServerRequests();
		assertEquals(1, incrementalBuildWasTriggered.get());

		incrementalBuildWasTriggered.set(0);

		// simulate that the client reports that the output file has been changed
		// (as happens when the builder (re-)generates the output file)
		val outputFileURI = getOutputFile("SomeModule").toFileURI;
		sendDidChangeWatchedFiles(outputFileURI);
		joinServerRequests(); // we don't expect the builder to do anything at this point; but if it incorrectly does something we want to wait for it to finish

		assertEquals("did not expect any builds to be triggered, but one or more builds were triggered", 0, incrementalBuildWasTriggered.get());
	}
}
