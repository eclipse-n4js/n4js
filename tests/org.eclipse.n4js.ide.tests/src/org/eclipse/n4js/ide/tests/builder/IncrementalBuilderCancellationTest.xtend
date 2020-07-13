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
import com.google.inject.Inject
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.n4js.ide.xtext.server.build.XClusteringStorageAwareResourceLoader.LoadResult
import org.eclipse.n4js.ide.xtext.server.build.XIndexer.XIndexResult
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping
import org.eclipse.n4js.ide.xtext.server.build.XStatefulIncrementalBuilder
import org.eclipse.xtext.service.AbstractGenericModule
import org.eclipse.xtext.service.OperationCanceledManager
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests handling of a cancellation during an incremental build.
 */
class IncrementalBuilderCancellationTest extends AbstractIncrementalBuilderTest {

	private static final AtomicInteger cancelCounter = new AtomicInteger(0);

	private static final class CancelingStatefulIncrementalBuilder extends XStatefulIncrementalBuilder {

		@Inject
		private OperationCanceledManager operationCanceledManager;

		override protected buildClustured(LoadResult loadResult, XSource2GeneratedMapping newSource2GeneratedMapping, XIndexResult result) {
			var isCancelRequested = false;
			synchronized(cancelCounter) {
				isCancelRequested = cancelCounter.get() > 0 && cancelCounter.decrementAndGet() == 0;
			}
			if (isCancelRequested) {
				operationCanceledManager.checkCanceled([true]);
			}
			super.buildClustured(loadResult, newSource2GeneratedMapping, result);
		}
	}

	public static final class IncrementalBuilderCancellationTestModule extends AbstractGenericModule {

		def Class<? extends XStatefulIncrementalBuilder> bindXStatefulIncrementalBuilder() {
			return CancelingStatefulIncrementalBuilder;
		}
	}

	override protected getOverridingModule() {
		return Optional.of(IncrementalBuilderCancellationTestModule);
	}

	@Test
	def void testTEMP() {
		val sourceOfImportingFile = '''
			import {Cls} from "Main";
			new Cls().meth();
		''';
		testWorkspaceManager.createTestProjectOnDisk(
			"A01" -> sourceOfImportingFile,
			"A02" -> sourceOfImportingFile,
			"A03" -> sourceOfImportingFile,
			"A04" -> sourceOfImportingFile,
			"Main" -> '''
				export public class Cls {
					public meth() {}
				}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main");
		changeOpenedFile("Main", "meth(" -> "methx(");
		joinServerRequests();
		assertNoIssues();

		cancelCounter.set(4);
		saveOpenedFile("Main");
		joinServerRequests();
		assertEquals(0, cancelCounter.get());

		// With a cancelCounter of 4 we expect a cancellation to occur in the fourth file being built.
		// Since "Main" is built first and counted as well, we expect the cancellation to occur in the
		// third "A0n" file being built. Since the file in which the cancellation occurs does not report
		// any issues (cancellation happens before that), we expect the error to show up in two "A0n" files:
		val issues = getIssues().values();
		assertEquals(2, issues.size);
		issues.map[getStringLSP4J().toStringShort(it)].forEach[
			assertEquals("(Error, [1:10 - 1:14], Couldn't resolve reference to IdentifiableElement 'meth'.)", it);
		];

		changeOpenedFile("Main", "methx(" -> "meth(");
		saveOpenedFile("Main");
		joinServerRequests();

		assertNoIssues(); // note: before the bug fix, the above two errors were not removed
	}
}
