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
package org.eclipse.n4js.dirtystate;

import com.google.inject.Inject
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex
import org.eclipse.n4js.resource.N4JSResourceDescriptionManager
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.ui.building.ResourceDescriptionWithoutModuleUserData
import org.eclipse.xtext.builder.impl.BuildData
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData
import org.junit.Test

import static org.junit.Assert.*

/**
 * For more builder corner cases, see {@link ReproduceInvalidIndexPluginTest}.
 */
// converted from IncrementalBuilderCornerCasesPluginTest
public class IncrementalBuilderCornerCasesIdeTest extends ConvertedIdeTest {

	@Inject
	private ConcurrentIndex concurrentIndex;

	@Inject
	private IResourceDescription.Manager resourceDescriptionsManager;

	/**
	 * This tests the bug fix of IDEBUG-347.
	 * <p>
	 * To make this test fail, comment out creation of {@link ResourceDescriptionWithoutModuleUserData}s in
	 * method {@code N4ClusteringBuilderState#queueAffectedResources()}, including the subsequent invocation
	 * of {@link BuildData#requestRebuild() requestRebuild()}.
	 */
	@Test
	def void testMissingReloadBug() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''

				// class XYZ {}

				function foo() {}

				export public class A {}

			''',
			"B" -> '''

				import { A } from "A"
				import { C } from "C"


				var arrCC : Array<A>;

				var t2 : C = new C();

				t2.m(arrCC);

			''',
			"C" -> '''

				import { A } from "A"


				export public class C {
					m(param : Array<A>) {}
				}

			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", '''

			class XYZ {}

			function foo() {}

			export public class A {}

		''');

		// wait for the incremental build to be completed (but do not trigger a full build!)
		joinServerRequests();

		// next line would fail if you comment out creation of ResourceDescriptionWithoutModuleUserData
		// in N4ClusteringBuilderState#queueAffectedResources()
		assertIssuesInModules("B" -> #[]);
		assertNoIssues();

		assertAllDescriptionsHaveModuleData();
	}


	/**
	 * Tests the bug fix of IDEBUG-398.
	 * <p>
	 * The problem was that method <code>isAffected()</code> in class <code>N4JSResourceDescriptionManager</code> did not
	 * take into account project dependencies, so if file A imported FQN N and file B exports N, that method always returned
	 * <code>true</code>, even if B lies in a project that is not among the direct dependencies of A. This could lead to
	 * cyclic dependencies between resource that caused other problems in the incremental builder.
	 */
	@Test
	def void testEqualQualifiedNameInNonDependantProject() {
		// note: we do not add a dependency between the two projects!
		testWorkspaceManager.createTestOnDisk(
			"First" -> #[
				"C" -> '''
					export public class C {}
				''',
				"M" -> '''
					import { C } from "C"
					var c : C;
				'''
			],
			"Second" -> #[
				"C" -> '''
					export public class C {}
				''',
				"M" -> '''
					import { C } from "C"
					var c : C;
				'''
			]
		);
		startAndWaitForLspServer(); // note: do not validate Xtext index (want do my custom checks first!)
		assertNoIssues();

		// assert that a change to file C in first project would affect
		// file M in first project but *not* file M in second project

		val c1_rd = findFileInXtextIndex("First", "C.n4js")
		val m1_rd = findFileInXtextIndex("First", "M.n4js")
		val m2_rd = findFileInXtextIndex("Second", "M.n4js")
		assertNotNull("cannot find file in Xtext index", c1_rd)
		assertNotNull("cannot find file in Xtext index", m1_rd)
		assertNotNull("cannot find file in Xtext index", m2_rd)

		val delta = new DefaultResourceDescriptionDelta(c1_rd,null);
		val deltas = Collections.singletonList(delta as Delta);

		assertTrue(resourceDescriptionsManager instanceof N4JSResourceDescriptionManager)
		val rdm = resourceDescriptionsManager as N4JSResourceDescriptionManager;
		val xtextIndex = new ResourceDescriptionsData(concurrentIndex.entries.flatMap[value.allResourceDescriptions]);

		assertTrue("file M.n4js in first project should be affected by change to file C.n4js in first project", rdm.isAffected(deltas, m1_rd, xtextIndex))
		assertFalse("file M.n4js in second project should *not* be affected by change to file C.n4js in first project", rdm.isAffected(deltas, m2_rd, xtextIndex))

// note: the following assertion is obsolete after the transition to LSP and converting this test to an IDE test
// (method #assertXtextIndexIsValid() made sure that the index does not contain any instances of class
// ResourceDescriptionWithoutModuleUserData, because those instances were intended only as an internal
// work-around inside the Eclipse builder and were expected to never leak outside the builder; the LSP
// builder does not need such a work-around)
//		assertXtextIndexIsValid // now do the general Xtext index validity checking
		assertAllDescriptionsHaveModuleData
	}

	private def IResourceDescription findFileInXtextIndex(String projectName, String fileNameIncludingExtension) {
		return concurrentIndex.getProjectIndex(projectName).allResourceDescriptions.findFirst[rd|
			val uri = rd?.URI;
			return uri !== null
				&& uri.toString.endsWith("/" + fileNameIncludingExtension);
		];
	}

	/** Returns with the decoded format of the first segment of the URI as the name of the containing project.*/
	private def getProjectName(URI uri) {
		URI.decode(uri.segment(1));
	}
}
