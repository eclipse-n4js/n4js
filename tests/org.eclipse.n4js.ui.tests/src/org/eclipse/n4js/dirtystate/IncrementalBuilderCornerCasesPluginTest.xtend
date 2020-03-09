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
import java.io.IOException
import java.util.Collections
import java.util.Objects
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.resource.N4JSResourceDescriptionManager
import org.eclipse.n4js.resource.UserDataMapper
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.building.ResourceDescriptionWithoutModuleUserData
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil
import org.eclipse.xtext.util.StringInputStream
import org.junit.Test

import static org.junit.Assert.*
import org.eclipse.xtext.builder.impl.BuildData

/**
 * For more builder corner cases, see {@link ReproduceInvalidIndexPluginTest}.
 */
public class IncrementalBuilderCornerCasesPluginTest extends AbstractBuilderParticipantTest {

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
		val project = createJSProject("Test");
		val src2 = configureProjectWithXtext(project);
		val projectDescriptionFile2 = project.project.getFile(N4JSGlobals.PACKAGE_JSON);
		assertMarkers("manifest of project should have no errors", projectDescriptionFile2, 0)

		val fileA = createTestFile(src2, "A", '''

			// class XYZ {}

			function foo() {}

			export public class A {}

		''')
		val fileB = createTestFile(src2, "B", '''

			import { A } from "A"
			import { C } from "C"


			var arrCC : Array<A>;

			var t2 : C = new C();

			t2.m(arrCC);

		''')
		val fileC = createTestFile(src2, "C", '''

			import { A } from "A"


			export public class C {
				m(param : Array<A>) {}
			}

		''')

		IResourcesSetupUtil.fullBuild();

		assertMarkers("file should have no errors", fileA, 0);
		assertMarkers("file should have no errors", fileB, 0);
		assertMarkers("file should have no errors", fileC, 0);

		fileA.setContents(new StringInputStream('''

			class XYZ {}

			function foo() {}

			export public class A {}

		'''), true, true, new NullProgressMonitor)

		// wait for the incremental build to be completed (but do not trigger a full build!)
		waitForIncrementalBuild();

		// next line would fail if you comment out creation of ResourceDescriptionWithoutModuleUserData
		// in N4ClusteringBuilderState#queueAffectedResources()
		assertMarkers("file should still have no errors", fileB, 0);

		assertAllDescriptionsHaveModuleData();
	}

	def private void assertAllDescriptionsHaveModuleData() throws IOException {
		var descriptions = xtextIndex.allResourceDescriptions
		for (description : descriptions) {
			if (N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(description.URI.fileExtension)) {
				var moduleDescription = description.getExportedObjectsByType(TypesPackage.Literals.TMODULE).head
				assertNotNull(description.URI.toString, moduleDescription)
				var moduleAsString = UserDataMapper.
					getDeserializedModuleFromDescriptionAsString(moduleDescription, description.URI)
				assertNotNull(description.URI.toString(), moduleAsString)
			}
		}
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
		val firstProjectUnderTest = createJSProject("First")
		val secondProjectUnderTest = createJSProject("Second")
		val src = configureProjectWithXtext(firstProjectUnderTest)
		val src2 = configureProjectWithXtext(secondProjectUnderTest)
		val projectDescriptionFile1 = firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) of first project should have no errors", projectDescriptionFile1, 0)
		val projectDescriptionFile2 = secondProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) of second project should have no errors", projectDescriptionFile2, 0)

		waitForAutoBuild

		// note: we do not add a dependency between the two projects!
		val c1 = createTestFile(src, "C", '''
			export public class C {}
		''')
		val m1 = createTestFile(src, "M", '''
			import { C } from "C"
			var c : C;
		''')
		val c2 = createTestFile(src2, "C", '''
			export public class C {}
		''')
		val m2 = createTestFile(src2, "M", '''
			import { C } from "C"
			var c : C;
		''')

		waitForAutoBuild(false) // note: do not validate Xtext index (want do my custom checks first!)

		assertMarkers("file should have no errors", c1, 0)
		assertMarkers("file should have no errors", m1, 0)
		assertMarkers("file should have no errors", c2, 0)
		assertMarkers("file should have no errors", m2, 0)

		val xtextIndex = getXtextIndex

		// assert that a change to file C in first project would affect
		// file M in first project but *not* file M in second project

		val c1_rd = findFileInXtextIndex(xtextIndex, firstProjectUnderTest, "C.n4js")
		val m1_rd = findFileInXtextIndex(xtextIndex, firstProjectUnderTest, "M.n4js")
		val m2_rd = findFileInXtextIndex(xtextIndex, secondProjectUnderTest, "M.n4js")
		assertNotNull("cannot find file in Xtext index", c1_rd)
		assertNotNull("cannot find file in Xtext index", m1_rd)
		assertNotNull("cannot find file in Xtext index", m2_rd)

		val delta = new DefaultResourceDescriptionDelta(c1_rd,null);
		val deltas = Collections.singletonList(delta as Delta);

		assertTrue(resourceDescriptionsManager instanceof N4JSResourceDescriptionManager)
		val rdm = resourceDescriptionsManager as N4JSResourceDescriptionManager;

		assertTrue("file M.n4js in first project should be affected by change to file C.n4js in first project", rdm.isAffected(deltas, m1_rd, xtextIndex))
		assertFalse("file M.n4js in second project should *not* be affected by change to file C.n4js in first project", rdm.isAffected(deltas, m2_rd, xtextIndex))

		assertXtextIndexIsValid // now do the general Xtext index validity checking
		assertAllDescriptionsHaveModuleData
	}

	private def IResourceDescription findFileInXtextIndex(IResourceDescriptions xtextIndex, IProject project, String fileNameIncludingExtension) {
		xtextIndex.allResourceDescriptions.findFirst[rd|
			val uri = rd?.URI;
			return uri !== null
				&& uri.toString.endsWith("/" + fileNameIncludingExtension)
				&& (project === null || (
					uri.isPlatformResource
					&& uri.segmentCount >= 2
					&& Objects.equals(project.name, uri.projectName)
					)
				);
		]
	}

	/** Returns with the decoded format of the first segment of the URI as the name of the containing project.*/
	private def getProjectName(URI uri) {
		URI.decode(uri.segment(1));
	}
}
