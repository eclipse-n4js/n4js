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
import org.eclipse.n4js.resource.N4JSResourceDescriptionManager
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import java.util.Collections
import java.util.Objects
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta
import org.eclipse.xtext.util.StringInputStream
import org.junit.Test

/**
 */
public class IncrementalBuilderCornerCasesPluginTest extends AbstractBuilderParticipantTest {

	@Inject
	private IResourceDescription.Manager resourceDescriptionsManager;

	/**
	 * This tests the bug fix of IDEBUG-347.
	 * <p>
	 * To reproduce the bug and to make the following test fail, comment out the following method
	 * <code>#queueAffectedResources()</code> in class <code>N4JSGenerateImmediatelyBuilderState</code>.
	 * However, the test is probably quite fragile and maybe the bug cannot be reproduced after future
	 * other changes to the incremental builder.
	 * <p>
	 */
	@Test
	def void testMissingReloadBug() {

		// first project
		// (contains only empty files and does not have any dependencies to/from main project,
		// but is required to reproduce bug)

		val firstProjectUnderTest = createJSProject("XBugAuxiliary")
		val src1 = configureProjectWithXtext(firstProjectUnderTest)
		val manifest1 = firstProjectUnderTest.project.getFile("manifest.n4mf")
		assertMarkers("manifest of first project should have no errors", manifest1, 0)

		createTestFile(src1, "A", "")
		createTestFile(src1, "B", "")
		createTestFile(src1, "C", "")
		createTestFile(src1, "D", "")
		createTestFile(src1, "E", "")
		createTestFile(src1, "F", "")
		createTestFile(src1, "G", "")

		waitForAutoBuild

		// second project
		// (main project; note that we need a nested source folder)

		val secondProjectUnderTest = createJSProject("XBugMain","src/n4js","src-gen",null)
		val src2 = configureProjectWithXtext(secondProjectUnderTest,"src/n4js")
		val manifest2 = secondProjectUnderTest.project.getFile("manifest.n4mf")
		assertMarkers("manifest of second project should have no errors", manifest2, 0)

		val someClazz = createTestFile(src2, "SomeClazz", '''

			//class XYZ {}

			function foo() {}

			export public class SomeClazz {}

		''')
		val xt = createTestFile(src2, "XT", '''

			import { SomeClazz } from "SomeClazz"
			import { T2other } from "XT2"


			var arrCC : Array<SomeClazz>;

			var t2 : T2other = new T2other();

			t2.m(arrCC);

		''')
		val xt2 = createTestFile(src2, "XT2", '''

			import { SomeClazz } from "SomeClazz"


			export public class T2other {
				m(param : Array<SomeClazz>) {}
			}

		''')

		waitForAutoBuild

		assertMarkers("file should have no errors", someClazz, 0);
		assertMarkers("file should have no errors", xt, 0);
		assertMarkers("file should have no errors", xt2, 0);

		someClazz.setContents(new StringInputStream('''

			class XYZ {}

			function foo() {}

			export public class SomeClazz {}

		'''), true, true, new NullProgressMonitor)

		waitForAutoBuild

		// next line would fail if you comment out method
		// org.eclipse.n4js.ui.building.N4JSGenerateImmediatelyBuilderState#queueAffectedResources()
		assertMarkers("file should still have no errors", xt, 0);
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
		val manifest1 = firstProjectUnderTest.project.getFile("manifest.n4mf")
		assertMarkers("manifest of first project should have no errors", manifest1, 0)
		val manifest2 = secondProjectUnderTest.project.getFile("manifest.n4mf")
		assertMarkers("manifest of second project should have no errors", manifest2, 0)

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
		assertMarkers("file should have no errors but one unused variable warning", m1, 1)
		assertMarkers("file should have no errors", c2, 0)
		assertMarkers("file should have no errors but one unused variable warning", m2, 1)

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
