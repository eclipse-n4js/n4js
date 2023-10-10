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
package org.eclipse.n4js.ide.tests.builder;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.n4js.ide.server.N4JSStatefulIncrementalBuilder;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.xtext.ide.server.QueuedExecutorService;
import org.eclipse.n4js.xtext.ide.server.build.IBuildRequestFactory.OnPostCreateListener;
import org.eclipse.n4js.xtext.ide.server.build.XClusteringStorageAwareResourceLoader.LoadResult;
import org.eclipse.n4js.xtext.ide.server.build.XIndexer.XIndexResult;
import org.eclipse.n4js.xtext.ide.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.xtext.ide.server.build.XStatefulIncrementalBuilder;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceBuilder;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * Asserts that
 * <ol>
 * <li>the initial build cannot be canceled, and
 * <li>file change events received during the initial build do not get lost and are properly processed after the initial
 * build.
 * </ol>
 */

public class InitialBuildNotCancelableTest extends AbstractIdeTest {

	private static final CountDownLatch didBuildModuleOther = new CountDownLatch(1);
	private static final CountDownLatch didPerformCancellation = new CountDownLatch(1);
	private static final CountDownLatch didCompleteInitialBuild = new CountDownLatch(1);
	private static final CountDownLatch didCheckResultOfInitialBuild = new CountDownLatch(1);

	private static final AtomicBoolean initialBuildCompletedNormally = new AtomicBoolean(false);

	private static final class TestWorkspaceBuilder extends XWorkspaceBuilder {
		@Override
		protected void onBuildDone(boolean wasInitialBuild, boolean wasCanceled,
				OnPostCreateListener postCreateListener, Optional<Throwable> throwable) {
			super.onBuildDone(wasInitialBuild, wasCanceled, postCreateListener, throwable);
			if (wasInitialBuild) {
				// normally = no cancellation, no exceptions
				initialBuildCompletedNormally.set(!wasCanceled && !throwable.isPresent());
				didCompleteInitialBuild.countDown();
				Uninterruptibles.awaitUninterruptibly(didCheckResultOfInitialBuild);
			}
		}
	}

	private static final class TestStatefulIncrementalBuilder extends N4JSStatefulIncrementalBuilder {

		@Inject
		private OperationCanceledManager operationCanceledManager;

		@Override
		protected IResourceDescription.Delta buildClustered(LoadResult loadResult,
				XSource2GeneratedMapping newSource2GeneratedMapping, XIndexResult result) {

			Delta retval = super.buildClustered(loadResult, newSource2GeneratedMapping, result);
			String fileBeingBuilt = loadResult.uri.lastSegment();
			if ("Other.n4js".equals(fileBeingBuilt)) {
				didBuildModuleOther.countDown();
				Uninterruptibles.awaitUninterruptibly(didPerformCancellation);
				operationCanceledManager.checkCanceled(getRequest().getCancelIndicator());
			}
			return retval;
		}
	}

	private static final class TestQueuedExecutorService extends QueuedExecutorService {

		@Override
		public synchronized void cancelAll() {
			super.cancelAll();
			didPerformCancellation.countDown();
		}

		@Override
		public synchronized void cancelAll(Object queueId) {
			super.cancelAll(queueId);
			didPerformCancellation.countDown();
		}
	}

	public static final class InitialBuildNotCancelableTestModule extends AbstractGenericModule {

		public Class<? extends XWorkspaceBuilder> bindXWorkspaceBuilder() {
			return TestWorkspaceBuilder.class;
		}

		public Class<? extends XStatefulIncrementalBuilder> bindXStatefulIncrementalBuilder() {
			return TestStatefulIncrementalBuilder.class;
		}

		public Class<? extends QueuedExecutorService> bindQueuedExecutorService() {
			return TestQueuedExecutorService.class;
		}
	}

	@Override
	protected Optional<Class<? extends Module>> getOverridingModule() {
		return Optional.of(InitialBuildNotCancelableTestModule.class);
	}

	@Test(timeout = 10000)
	public void testInitialBuildNotCancelable() throws InterruptedException {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject", Map.of(
						"Other", """
									export public class Other {
										public mX() {}
									}
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Other} from "Other";
									new Other().m();
								""",
						CFG_DEPENDENCIES, """
									OtherProject
								""")));
		startLspServerWithoutWaiting(); // will trigger initial build

		didBuildModuleOther.await();
		// at this point, file Other.n4js has been built completely but initial build is still in progress

		changeNonOpenedFile("Other", Pair.of("mX", "m")); // fix the error
		didPerformCancellation.await(); // continue initial build

		didCompleteInitialBuild.await();
		assertTrue("expected initial build to complete normally (no cancellation, no exceptions)",
				initialBuildCompletedNormally.get());
		// at this point, the initial build has completed

		// assert successful completion of initial build by ensuring the already fixed error has still shown up:
		assertIssues2(Pair.of("Main", List.of(
				"(Error, [1:13 - 1:14], Couldn't resolve reference to IdentifiableElement 'm'.)")));
		didCheckResultOfInitialBuild.countDown();

		joinServerRequests(); // wait for incremental build to complete
		// assert that the incremental build that was delayed by the initial build successfully removed the error
		assertNoIssues();
	}
}
