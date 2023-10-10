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

import static com.google.common.collect.Sets.newTreeSet;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.build.XBuildContext;
import org.eclipse.n4js.xtext.ide.server.build.XClusteringStorageAwareResourceLoader.LoadResult;
import org.eclipse.n4js.xtext.ide.server.build.XIndexer;
import org.eclipse.n4js.xtext.ide.server.build.XIndexer.XIndexResult;
import org.eclipse.n4js.xtext.ide.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.xtext.ide.server.build.XStatefulIncrementalBuilder;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceBuilder;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * Tests handling of a cancellation during an incremental build.
 */

public class IncrementalBuilderCancellationTest extends AbstractIncrementalBuilderTest {

	private static final AtomicReference<URI> cancelOnResource = new AtomicReference<>(null);

	/**
	 * Iff greater zero, then cancel before building the n-th resource. Cancellation happens before the very first
	 * resource with a cancelCounter set to 1.
	 */
	private static final AtomicInteger cancelOnCountZero = new AtomicInteger(0);

	private static final class CancelingStatefulIncrementalBuilder extends XStatefulIncrementalBuilder {

		@Inject
		private OperationCanceledManager operationCanceledManager;

		@Override
		protected Delta buildClustered(LoadResult loadResult, XSource2GeneratedMapping newSource2GeneratedMapping,
				XIndexResult result) {

			var isCancelledByCounter = false;
			var isCancelledByResource = false;

			synchronized (cancelOnCountZero) {
				isCancelledByCounter = cancelOnCountZero.get() > 0 && cancelOnCountZero.decrementAndGet() == 0;
			}
			synchronized (cancelOnResource) {
				isCancelledByResource = cancelOnResource.get() != null && cancelOnResource.get() == loadResult.uri;
				if (isCancelledByResource) {
					cancelOnResource.set(null);
				}
			}

			if (isCancelledByResource || isCancelledByCounter) {
				operationCanceledManager.checkCanceled(new CancelIndicator() {
					@Override
					public boolean isCanceled() {
						return true;
					}
				});
			}

			return super.buildClustered(loadResult, newSource2GeneratedMapping, result);
		}
	}

	// Sort to get a predictable order of resources in the build
	private static class SortingIndexer extends XIndexer {
		@Override
		protected List<IResourceDescription.Delta> getDeltasForChangedResources(Iterable<URI> changedURIs,
				ResourceDescriptionsData oldIndex, XBuildContext context) {

			List<Delta> result = super.getDeltasForChangedResources(changedURIs, oldIndex, context);
			result.sort((d1, d2) -> d1.getUri().toString().compareTo(d2.getUri().toString()));
			return result;
		}
	}

	public static final class IncrementalBuilderCancellationTestModule extends AbstractGenericModule {

		public Class<? extends XStatefulIncrementalBuilder> bindXStatefulIncrementalBuilder() {
			return CancelingStatefulIncrementalBuilder.class;
		}

		public Class<? extends XIndexer> bindXIndexer() {
			return SortingIndexer.class;
		}
	}

	@Override
	protected Optional<Class<? extends Module>> getOverridingModule() {
		return Optional.of(IncrementalBuilderCancellationTestModule.class);
	}

	@Test
	public void testRecoverFromCancelledBuild() {
		String sourceOfImportingFile = """
					import {Cls} from "Main";
					new Cls().meth();
				""";
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A01", sourceOfImportingFile,
				"A02", sourceOfImportingFile,
				"A03", sourceOfImportingFile,
				"A04", sourceOfImportingFile,
				"Main", """
							export public class Cls {
								public meth() {}
							}
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main");
		changeOpenedFile("Main", Pair.of("meth(", "methx("));
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
		Collection<Diagnostic> issues = getIssues().values();
		assertEquals(2, issues.size());
		for (Diagnostic d : issues) {
			String msg = getStringLSP4J().toStringShort(d);
			assertEquals("(Error, [1:11 - 1:15], Couldn't resolve reference to IdentifiableElement 'meth'.)", msg);
		}

		changeOpenedFile("Main", Pair.of("methx(", "meth("));
		saveOpenedFile("Main");
		joinServerRequests();

		assertNoIssues();
	}

	@Test
	public void testCancelBuildAndPerformSubsequentChange() {
		Function<String, String> sourceOfImportingFile = new Function<>() {
			@Override
			public String apply(String t) {
				return """
						import {Cls} from "Main";
						new Cls().meth%s();
						""".formatted(t);
			}
		};

		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", sourceOfImportingFile.apply("A"),
				"B", sourceOfImportingFile.apply("B"),
				"C", sourceOfImportingFile.apply("C"),
				"D", sourceOfImportingFile.apply("D"),
				"Main", """
							export public class Cls {
								public methA() {}
							}
						"""));
		startAndWaitForLspServer();

		Set<String> issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals(3, issues.size());
		assertEquals("""
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methB'.)
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methC'.)
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methD'.)""",
				org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues));

		// cancel before A is validated - no error changed
		cancelOnCountZero.set(1);
		changeNonOpenedFile("Main", Pair.of("methA(", "methB("));
		joinServerRequests();
		assertEquals(0, cancelOnCountZero.get());
		issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals(3, issues.size());
		assertEquals("""
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methB'.)
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methC'.)
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methD'.)""",
				org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues));

		changeNonOpenedFile("D", Pair.of("methD(", "methB("));
		joinServerRequests();
		issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals("""
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methA'.)
				(Error, [1:10 - 1:15], Couldn't resolve reference to IdentifiableElement 'methC'.)""",
				org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues));
	}

	@Test
	public void testCancelBuildAndPerformSubsequentChange_02() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							export public let a = {
								a1 : { a2 : '' }
							};
						""",
				"B", """
							import {a} from "A";
							export public let b = a.a1;
						""",
				"C", """
							import {b} from "B";
							export public let c = b.a3; // intentionally broken
						""",
				"D", """
							import {c} from "C";
							export public let d = c;
						"""));
		startAndWaitForLspServer();

		Set<String> issues = newTreeSet(
				map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals(org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues), 1, issues.size());

		assertEquals("(Error, [1:25 - 1:27], Couldn't resolve reference to IdentifiableElement 'a3'.)",
				org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues));

		// Rename a2 to a3 to fix the reference in C but cancel, after C was processed
		cancelOnCountZero.set(4);
		changeNonOpenedFile("A", Pair.of("a1 : { a2 : '' }", "a0 : {}, a1 : { a3 : '' }"));
		joinServerRequests();
		assertEquals(0, cancelOnCountZero.get());
		issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals(org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues), 0, issues.size());

		// Perform another change in A which will break C again
		changeNonOpenedFile("A", Pair.of("a0 : {}, a1 : { a3 : '' }", "some : 'literal', a1 : { a2 : 1 }"));
		joinServerRequests();
		issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals("(Error, [1:25 - 1:27], Couldn't resolve reference to IdentifiableElement 'a3'.)",
				org.eclipse.n4js.utils.Strings.join(Strings.newLine(), issues));
	}

	/**
	 * This test case tests a very specific edge case: A resource is (1) created, (2) a cancellation happens, and (3)
	 * the resource is removed again before the next incremental build iteration. What should not happen is that the
	 * build crashes during this course of actions. A crash could happen since the delta that is created at (1) will be
	 * merged with the delta at (3). This merge will result in a delta from old==null to new==null, which is an invalid
	 * delta.
	 */
	@Test
	public void testDeletionDuringCancelledBuild() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectClientA", Map.of(
						"MainClientA", """
									import {Cls} from "MainLibTEMP";
									new Cls().meth();
									export public class ClsA {
										public methA() {}
									}
								""",
						CFG_DEPENDENCIES, """
									ProjectLib
								"""),
				"ProjectClientB", Map.of(
						"MainClientB", """
									import {ClsA} from "MainClientA";
									new ClsA().methA();
								""",
						CFG_DEPENDENCIES, """
									ProjectClientA
								"""),
				"ProjectLib", Map.of(
						"MainLib", """
									export public class Cls {
										public meth() {}
									}
								""")));

		FileURI uriMainClient = getFileURIFromModuleName("MainClientA");
		startAndWaitForLspServer();
		assertIssues2(Map.of(
				"MainClientA", List.of(
						"(Error, [0:19 - 0:32], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:5 - 1:8], Couldn't resolve reference to IdentifiableElement 'Cls'.)")));

		cancelOnResource.set(uriMainClient.toURI());
		createFileOnDiskWithoutNotification("MainLib", "MainLibTEMP.n4js", """
					export public class Cls {
						public meth() {}
					}
				""");
		// this change should fix the issue
		changeNonOpenedFile("MainLibTEMP",
				Pair.of("public meth() {}", """
							public meth() {}
							public methNew() {} // trigger rebuild of MainClient
						"""));
		joinServerRequests();
		assertNull("expected cancellation to have happened, but it did not happen", cancelOnResource.get());

		// due to cancellation the errors still remain
		assertIssues2(Map.of(
				"MainClientA", List.of(
						"(Error, [0:19 - 0:32], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:5 - 1:8], Couldn't resolve reference to IdentifiableElement 'Cls'.)")));

		FileURI uriMainLib_TEMP = getFileURIFromModuleName("MainLibTEMP");
		// perform two different changes
		// Change #1: introduce new errors that should show up
		changeFileOnDiskWithoutNotification("MainClientA",
				Pair.of("public methA() {}", "public methXXX() {} // introduce error"));
		// Change #2: delete file to cause the error to occur
		deleteNonOpenedFile(uriMainLib_TEMP);
		joinServerRequests();

		assertIssues2(Map.of(
				"MainClientA", List.of(
						"(Error, [0:19 - 0:32], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:5 - 1:8], Couldn't resolve reference to IdentifiableElement 'Cls'.)"),
				// This error is due to change #2
				"MainClientB", List.of(
						"(Error, [1:12 - 1:17], Couldn't resolve reference to IdentifiableElement 'methA'.)")));
	}

	/**
	 * Because of the cancellation during building "ProjectClient2" (which is being built last), the deltas produced by
	 * building "ProjectMain" and "ProjectClient1" will remain in {@link XWorkspaceBuilder#toBeConsideredDeltas}. This
	 * test makes sure that those deltas do not mess up the incremental build following the cancellation.
	 */
	@SuppressWarnings("javadoc")
	@Test
	public void testCancelWhileProcessingCrossProjectDependencies() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectMain", Map.of(
						"MainModule", """
									export public class Cls {
										public meth() {}
									}
								"""),
				"ProjectClient1", Map.of(
						"ClientModule1", """
									import {Cls} from "MainModule";
									new Cls().meth();
								""",
						CFG_DEPENDENCIES, """
									ProjectMain
								"""),
				"ProjectClient2", Map.of(
						"ClientModule2", """
									import {Cls} from "MainModule";
									new Cls().meth();
								""",
						CFG_DEPENDENCIES, """
									ProjectMain,
									ProjectClient1
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI clientModule2FileURI = getFileURIFromModuleName("ClientModule2");

		cancelOnResource.set(clientModule2FileURI.toURI());
		changeNonOpenedFile("MainModule", Pair.of("meth(", "methx("));
		joinServerRequests();
		assertNull("expected cancellation to have happened, but it did not happen", cancelOnResource.get());

		assertIssues2(Map.of(
				"ClientModule1", List.of(
						"(Error, [1:11 - 1:15], Couldn't resolve reference to IdentifiableElement 'meth'.)"),
				"ClientModule2", List.of(
				// the error in this module must not show up, due to the cancellation!
				)));

		changeNonOpenedFile("MainModule", Pair.of("methx(", "meth("));
		joinServerRequests();

		assertNoIssues();
	}
}
