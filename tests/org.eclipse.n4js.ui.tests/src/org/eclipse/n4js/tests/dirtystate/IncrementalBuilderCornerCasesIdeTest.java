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
package org.eclipse.n4js.tests.dirtystate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.resource.N4JSResourceDescriptionManager;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * For more builder corner cases, see {@link ReproduceInvalidIndexIdeTest}.
 */
// converted from IncrementalBuilderCornerCasesPluginTest
public class IncrementalBuilderCornerCasesIdeTest extends ConvertedIdeTest {

	@Inject
	private IResourceDescription.Manager resourceDescriptionsManager;

	/**
	 * This tests the bug fix of IDEBUG-347.
	 * <p>
	 * To make this test fail, comment out creation of {@code ResourceDescriptionWithoutModuleUserData}s in method
	 * {@code N4ClusteringBuilderState#queueAffectedResources()}, including the subsequent invocation of
	 * {@code BuildData#requestRebuild() requestRebuild()}.
	 */
	@Test
	public void testMissingReloadBug() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("A", """

						// class XYZ {}

						function foo() {}

						export public class A {}

						"""),
				Pair.of("B", """

						import { A } from "A"
						import { C } from "C"


						var arrCC : Array<A>;

						var t2 : C = new C();

						t2.m(arrCC);

						"""),
				Pair.of("C", """

						import { A } from "A"


						export public class C {
							m(param : Array<A>) {}
						}

						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", """

				class XYZ {}

				function foo() {}

				export public class A {}

				""");

		// wait for the incremental build to be completed (but do not trigger a full build!)
		joinServerRequests();

		// next line would fail if you comment out creation of ResourceDescriptionWithoutModuleUserData
		// in N4ClusteringBuilderState#queueAffectedResources()
		assertIssuesInModules(Pair.of("B", Collections.emptyList()));
		assertNoIssues();

		assertAllDescriptionsHaveModuleData();
	}

	/**
	 * Tests the bug fix of IDEBUG-398.
	 * <p>
	 * The problem was that method <code>isAffected()</code> in class <code>N4JSResourceDescriptionManager</code> did
	 * not take into account project dependencies, so if file A imported FQN N and file B exports N, that method always
	 * returned <code>true</code>, even if B lies in a project that is not among the direct dependencies of A. This
	 * could lead to cyclic dependencies between resource that caused other problems in the incremental builder.
	 */
	@Test
	public void testEqualQualifiedNameInNonDependantProject() throws IOException {
		// note: we do not add a dependency between the two projects!
		testWorkspaceManager.createTestOnDisk(
				Pair.of("First", List.of(
						Pair.of("C", """
									export public class C {}
								"""),
						Pair.of("M", """
									import { C } from "C"
									var c : C;
								"""))),
				Pair.of("Second", List.of(
						Pair.of("C", """
									export public class C {}
								"""),
						Pair.of("M", """
									import { C } from "C"
									var c : C;
								"""))));
		startAndWaitForLspServer(); // note: do not validate Xtext index (want do my custom checks first!)
		assertNoIssues();

		// assert that a change to file C in first project would affect
		// file M in first project but *not* file M in second project

		IResourceDescription c1_rd = findFileInXtextIndex("First", "C.n4js");
		IResourceDescription m1_rd = findFileInXtextIndex("First", "M.n4js");
		IResourceDescription m2_rd = findFileInXtextIndex("Second", "M.n4js");
		assertNotNull("cannot find file in Xtext index", c1_rd);
		assertNotNull("cannot find file in Xtext index", m1_rd);
		assertNotNull("cannot find file in Xtext index", m2_rd);

		DefaultResourceDescriptionDelta delta = new DefaultResourceDescriptionDelta(c1_rd, null);
		List<Delta> deltas = Collections.singletonList((Delta) delta);

		assertTrue(resourceDescriptionsManager instanceof N4JSResourceDescriptionManager);
		N4JSResourceDescriptionManager rdm = (N4JSResourceDescriptionManager) resourceDescriptionsManager;
		ResourceDescriptionsData xtextIndex = new ResourceDescriptionsData(IterableExtensions
				.flatMap(concurrentIndex.entries(), entry -> entry.getValue().getAllResourceDescriptions()));
		WorkspaceConfigSnapshot workspaceConfig = concurrentIndex.getWorkspaceConfigSnapshot();

		assertTrue("file M.n4js in first project should be affected by change to file C.n4js in first project",
				rdm.isAffected(deltas, m1_rd, xtextIndex, workspaceConfig));
		assertFalse("file M.n4js in second project should *not* be affected by change to file C.n4js in first project",
				rdm.isAffected(deltas, m2_rd, xtextIndex, workspaceConfig));

		// note: the following assertion is obsolete after the transition to LSP and converting this test to an IDE test
		// (method #assertXtextIndexIsValid() made sure that the index does not contain any instances of class
		// ResourceDescriptionWithoutModuleUserData, because those instances were intended only as an internal
		// work-around inside the Eclipse builder and were expected to never leak outside the builder; the LSP
		// builder does not need such a work-around)
		// assertXtextIndexIsValid // now do the general Xtext index validity checking
		assertAllDescriptionsHaveModuleData();
	}

	private IResourceDescription findFileInXtextIndex(String projectName, String fileNameIncludingExtension) {
		ResourceDescriptionsData projectIndex = concurrentIndex
				.getProjectIndex("yarn-test-project/packages/" + projectName);

		for (IResourceDescription rd : projectIndex.getAllResourceDescriptions()) {
			if (rd != null && rd.getURI() != null
					&& rd.getURI().toString().endsWith("/" + fileNameIncludingExtension)) {
				return rd;
			}
		}
		return null;
	}

}
