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
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.ide.xtext.server.build.XBuildContext
import org.eclipse.n4js.ide.xtext.server.build.XClusteringStorageAwareResourceLoader.LoadResult
import org.eclipse.n4js.ide.xtext.server.build.XIndexer
import org.eclipse.n4js.ide.xtext.server.build.XIndexer.XIndexResult
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping
import org.eclipse.n4js.ide.xtext.server.build.XStatefulIncrementalBuilder
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData
import org.eclipse.xtext.service.AbstractGenericModule
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.util.Strings
import org.junit.Test

import static org.junit.Assert.*
import java.util.concurrent.atomic.AtomicReference
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager

/**
 * Tests handling of a cancellation during an incremental build.
 */
class IncrementalBuilderCancellationTest extends AbstractIncrementalBuilderTest {

	private static final AtomicReference<URI> cancelOnResource = new AtomicReference(null);
	private static final AtomicReference<URI> cancelAfterResource = new AtomicReference(null);
	
	/**
	 * Iff greater zero, then cancel before building the n-th resource. Cancellation happens before the very first 
	 * resource with a cancelCounter set to 1.
	 */
	private static final AtomicInteger cancelOnCountZero = new AtomicInteger(0);

	private static final class CancelingStatefulIncrementalBuilder extends XStatefulIncrementalBuilder {

		@Inject
		private OperationCanceledManager operationCanceledManager;
		
		override protected buildClustered(LoadResult loadResult, XSource2GeneratedMapping newSource2GeneratedMapping, XIndexResult result) {
			var isCancelledByCounter = false;
			var isCancelledByResource = false;

			synchronized(cancelOnCountZero) {
				isCancelledByCounter = cancelOnCountZero.get() > 0 && cancelOnCountZero.decrementAndGet() == 0;
			}
			synchronized(cancelOnResource) {
				isCancelledByResource = cancelOnResource.get() !== null && cancelOnResource.get() == loadResult.uri;
			}
			synchronized(cancelAfterResource) {
				if (cancelAfterResource.get() !== null && cancelAfterResource.get() == loadResult.uri) {
					cancelOnCountZero.set(1);
				}
			}

			if (isCancelledByResource || isCancelledByCounter) {
				operationCanceledManager.checkCanceled([true]);
			}
			
			super.buildClustered(loadResult, newSource2GeneratedMapping, result);
		}
	}
	
	// Sort to get a predictable order of resources in the build
	private static class SortingIndexer extends XIndexer {
		
		override protected getDeltasForChangedResources(Iterable<URI> changedURIs, ResourceDescriptionsData oldIndex, XBuildContext context) {
			val result = super.getDeltasForChangedResources(changedURIs, oldIndex, context)
			return result.sortBy[ it.uri.toString ]
		}
		
	}

	public static final class IncrementalBuilderCancellationTestModule extends AbstractGenericModule {

		def Class<? extends XStatefulIncrementalBuilder> bindXStatefulIncrementalBuilder() {
			return CancelingStatefulIncrementalBuilder;
		}
		
		def Class<? extends XIndexer> bindXIndexer() {
			return SortingIndexer;
		}
	}

	override protected getOverridingModule() {
		return Optional.of(IncrementalBuilderCancellationTestModule);
	}

	@Test
	def void testRecoverFromCancelledBuild() {
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

		cancelOnCountZero.set(4);
		saveOpenedFile("Main");
		joinServerRequests();
		assertEquals(0, cancelOnCountZero.get());

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

		assertNoIssues();
	}
	
	@Test
	def void testCancelBuildAndPerformSubsequentChange() {
		val (String)=>String sourceOfImportingFile = ['''
			import {Cls} from "Main";
			new Cls().meth«it»();
		'''];
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> sourceOfImportingFile.apply("A"),
			"B" -> sourceOfImportingFile.apply("B"),
			"C" -> sourceOfImportingFile.apply("C"),
			"D" -> sourceOfImportingFile.apply("D"),
			"Main" -> '''
				export public class Cls {
					public methA() {}
				}
			'''
		);
		startAndWaitForLspServer();
		var issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals(3, issues.size);
		assertEquals('''
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methB'.)
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methC'.)
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methD'.)'''.toString,
			issues.sort.join(Strings.newLine))

		// cancel before A is validated - no error changed
		cancelOnCountZero.set(1);
		changeNonOpenedFile("Main", "methA(" -> "methB(");
		joinServerRequests();
		assertEquals(0, cancelOnCountZero.get());
		issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals(3, issues.size);
		assertEquals('''
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methB'.)
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methC'.)
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methD'.)'''.toString,
			issues.sort.join(Strings.newLine))
			
		changeNonOpenedFile("D", "methD(" -> "methB(");
		joinServerRequests();
		issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals('''
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methA'.)
			(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methC'.)'''.toString,
			issues.sort.join(Strings.newLine))
	}
	
	@Test
	def void testCancelBuildAndPerformSubsequentChange_02() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				export public let a = {
					a1 : { a2 : '' }
				};
			''',
			"B" -> '''
				import {a} from "A";
				export public let b = a.a1;
			''',
			"C" -> '''
				import {b} from "B";
				export public let c = b.a3; // intentionally broken
			''',
			"D" -> '''
				import {c} from "C";
				export public let d = c;
			'''
		);
		startAndWaitForLspServer();
		var issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals(issues.sort.join(Strings.newLine), 1, issues.size);
		
		assertEquals("(Error, [1:24 - 1:26], Couldn't resolve reference to IdentifiableElement 'a3'.)",
			issues.sort.join(Strings.newLine))

		// Rename a2 to a3 to fix the reference in C but cancel, after C was processed
		cancelOnCountZero.set(4);
		changeNonOpenedFile("A", "a1 : { a2 : '' }" -> "a0 : {}, a1 : { a3 : '' }");
		joinServerRequests();
		assertEquals(0, cancelOnCountZero.get());
		issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals(issues.sort.join(Strings.newLine), 0, issues.size);
			
		// Perform another change in A which will break C again
		changeNonOpenedFile("A", "a0 : {}, a1 : { a3 : '' }" -> "some : 'literal', a1 : { a2 : 1 }");
		joinServerRequests();
		issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals("(Error, [1:24 - 1:26], Couldn't resolve reference to IdentifiableElement 'a3'.)",
			issues.sort.join(Strings.newLine))
	}

	/**
	 * This test case tests a very specific edge case: A resource is 
	 * (1) created,
	 * (2) a cancellation happens, and
	 * (3) the resource is removed again before the next incremental build iteration.
	 * What should not happen is that the build crashes during this course of actions.
	 * A crash could happen since the delta that is created at (1) will be merged with the delta at (3).
	 * This merge will result in a delta from old==null to new==null, which is an invalid delta.
	 */
	@Test
	def void testDeletionDuringCancelledBuild() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProjectClientA" -> #[
				"MainClientA" -> '''
					import {Cls} from "MainLibTEMP";
					new Cls().meth();
					export public class ClsA {
						public methA() {}
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					ProjectLib
				'''
			],
			"ProjectClientB" -> #[
				"MainClientB" -> '''
					import {ClsA} from "MainClientA";
					new ClsA().methA();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					ProjectClientA
				'''
			],
			"ProjectLib" -> #[
				"MainLib" -> '''
					export public class Cls {
						public meth() {}
					}
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime
				'''
			]
		);
		
		
		val uriMainClient = getFileURIFromModuleName("MainClientA");
		startAndWaitForLspServer();
		assertIssues(
			"MainClientA" -> #[
				"(Error, [0:18 - 0:31], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:4 - 1:7], Couldn't resolve reference to IdentifiableElement 'Cls'.)"]);
		
		cancelOnResource.set(uriMainClient.toURI());
		createFileOnDiskWithoutNotification("MainLib", "MainLibTEMP.n4js", '''
				export public class Cls {
					public meth() {}
				}
			''');
		// this change should fix the issue
		changeNonOpenedFile("MainLibTEMP", "public meth() {}" -> '''
				public meth() {}
				public methNew() {} // trigger rebuild of MainClient
			''');
		joinServerRequests();
		
		// due to cancellation the errors still remain
		assertIssues(
			"MainClientA" -> #[
				"(Error, [0:18 - 0:31], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:4 - 1:7], Couldn't resolve reference to IdentifiableElement 'Cls'.)"]);
		
		
		val uriMainLib_TEMP = getFileURIFromModuleName("MainLibTEMP");
		cancelOnResource.set(null);
		// perform two different changes
		// Change #1: introduce new errors that should show up
		changeFileOnDiskWithoutNotification("MainClientA", "public methA() {}" -> "public methXXX() {} // introduce error");
		// Change #2: delete file to cause the error to occur
		deleteNonOpenedFile(uriMainLib_TEMP);
		joinServerRequests();
		
		
		assertIssues(
			"MainClientA" -> #[
				"(Error, [0:18 - 0:31], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:4 - 1:7], Couldn't resolve reference to IdentifiableElement 'Cls'.)"],
			// This error is due to change #2
			"MainClientB" -> #[
				"(Error, [1:11 - 1:16], Couldn't resolve reference to IdentifiableElement 'methA'.)"]);
	}
}
