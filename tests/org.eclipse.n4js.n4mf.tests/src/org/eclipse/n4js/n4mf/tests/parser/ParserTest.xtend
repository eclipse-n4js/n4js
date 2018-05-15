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
package org.eclipse.n4js.n4mf.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.N4MFInjectorProvider
import org.eclipse.n4js.n4mf.ProjectDependencyScope
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.SourceFragmentType
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests for parsing N4MF files having the expected entries in AST afterwards.
 *
 * Test methods with suffix "example" are taken from the N4JS spec, chapter "components".
 */
@RunWith(XtextRunner)
@InjectWith(N4MFInjectorProvider)
class ParserTest {

	@Inject extension ParseHelper<ProjectDescription>

	@Test def void testProjectId() {
		val project = '''
			ProjectId: MyProjectID
			ProjectType: library
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorId: MyVendorID
			VendorName: "My Vendor Name"
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)

		assertEquals(project.projectId, "MyProjectID")
		assertEquals(project.vendorId, "MyVendorID")
		assertEquals(project.vendorName, "My Vendor Name")
		assertEquals(project.projectType, ProjectType.LIBRARY)
		val projectVersion = project.projectVersion
		assertEquals(projectVersion.major, 0)
		assertEquals(projectVersion.minor, 0)
		assertEquals(projectVersion.micro, 1)
		assertEquals(projectVersion.qualifier, "SNAPSHOT")
	}


//	& 	('ModuleFilters'	'{' moduleFilters+=ModuleFilter+ '}')?

	/**
	 * also included
	 */
	@Test def void testCompleteExample() {
		val project = '''
			ProjectId: my.project.one
			ProjectType: library
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorId: org.eclipse.n4js
			VendorName: "Eclipse N4JS Project"
			Output: "output"
			Libraries {
				"lib"
			}
			Resources {
				"resources"
			}
			Sources {
				source {
					"src",
					"src2"
				}
				test {
					"src-test"
				}
				external {
					"external",
					"external2"
				}
			}
			ModuleFilters {
				noValidate {
					"**/path1/*.*",
					"**/path2/A*.js"
				}
				noModuleWrap {
					"**/*Wrapped.*" in "src",
					"**/wrapped/*.js"
				}
			}
			ProjectDependencies {
				org.eclipse.n4js:my.project.one (0.0.1,0.0.2] compile,
				org.eclipse.n4js:my.project.one 1.0 compile
			}
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)

		assertEquals(3, project.sourceFragment.size)
		assertEquals(SourceFragmentType.SOURCE, project.sourceFragment.head.sourceFragmentType)
		assertEquals(#["src", "src2"], project.sourceFragment.head.paths)
		assertEquals(SourceFragmentType.TEST, project.sourceFragment.get(1).sourceFragmentType)
		assertEquals(#["src-test"], project.sourceFragment.get(1).paths)
		assertEquals(SourceFragmentType.EXTERNAL, project.sourceFragment.last.sourceFragmentType)
		assertEquals(#["external", "external2"], project.sourceFragment.last.paths)

		assertEquals(2, project.moduleFilters.size)
		assertEquals(ModuleFilterType.NO_VALIDATE, project.moduleFilters.head.moduleFilterType)
		assertEquals(#["**/path1/*.*", "**/path2/A*.js"], project.moduleFilters.head.moduleSpecifiers.map[moduleSpecifierWithWildcard])
		assertEquals(ModuleFilterType.NO_MODULE_WRAPPING, project.moduleFilters.get(1).moduleFilterType)
		assertEquals(#["**/*Wrapped.*", "**/wrapped/*.js"], project.moduleFilters.get(1).moduleSpecifiers.map[moduleSpecifierWithWildcard])

		assertEquals(project.projectDependencies.size, 2)
		assertEquals("org.eclipse.n4js", project.projectDependencies.head.project.vendorId)
		assertEquals("my.project.one", project.projectDependencies.head.project.projectId)

		assertEquals(true, project.projectDependencies.head.versionConstraint.exclLowerBound)
		assertEquals(0, project.projectDependencies.head.versionConstraint.lowerVersion.major)
		assertEquals(0, project.projectDependencies.head.versionConstraint.lowerVersion.minor)
		assertEquals(1, project.projectDependencies.head.versionConstraint.lowerVersion.micro)
		assertEquals(false, project.projectDependencies.head.versionConstraint.exclUpperBound)
		assertEquals(0, project.projectDependencies.head.versionConstraint.upperVersion.major)
		assertEquals(0, project.projectDependencies.head.versionConstraint.upperVersion.minor)
		assertEquals(2, project.projectDependencies.head.versionConstraint.upperVersion.micro)
		assertEquals(ProjectDependencyScope.COMPILE, project.projectDependencies.head.scope)

		assertEquals(null, project.projectDependencies.last.versionConstraint.upperVersion, null)
		assertEquals(1, project.projectDependencies.last.versionConstraint.lowerVersion.major)
		assertEquals(0, project.projectDependencies.last.versionConstraint.lowerVersion.minor)
		assertEquals("if no number given 0 should be set by default", 0, project.projectDependencies.last.versionConstraint.lowerVersion.micro)
		assertEquals(null, project.projectDependencies.last.versionConstraint.lowerVersion.qualifier)
		assertEquals(ProjectDependencyScope.COMPILE, project.projectDependencies.last.scope)
	}

	@Test def void testProjectDependencyWithoutScope() {
		val project = '''
			ProjectId: my.project.one
			ProjectType: library
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorId: org.eclipse.n4js
			VendorName: "Eclipse N4JS Project"
			Output: "output"
			Sources {
				source {
					"src"
				}
			}
			ProjectDependencies {
				org.eclipse:my.project.one (0.0.1,0.0.2]
			}
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)
		// compile should be assigned by default
		assertEquals(ProjectDependencyScope.COMPILE, project.projectDependencies.last.scope)
	}

	@Test def void testProjectDependencyWithoutVersion() {
		val project = '''
			ProjectId: my.project.one
			ProjectType: library
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorId: org.eclipse.n4js
			VendorName: "Eclipse N4JS Project"
			Output: "output"
			Sources {
				source {
					"src"
				}
			}
			ProjectDependencies {
				org.eclipse:my.project.one
			}
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)
		assertNull(project.projectDependencies.last.versionConstraint)
	}

	@Test def void testProjectDependencyWithoutVendor() {
		val project = '''
			ProjectId: my.project.one
			ProjectType: library
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorId: org.eclipse.n4js
			VendorName: "Eclipse N4JS Project"
			Output: "output"
			Sources {
				source {
					"src"
				}
			}
			ProjectDependencies {
				my.project.one
			}
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)
		// when no vendorId is given the vendorId of the current project should be used
		assertEquals("org.eclipse.n4js", project.projectDependencies.last.project.vendorId)
	}

	@Test def void testProjectWithNoUpperBound() {
		val project = '''
			ProjectId: my.project.one
			ProjectType: library
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorId: org.eclipse.n4js
			VendorName: "Eclipse N4JS Project"
			Output: "output"
			Sources {
				source {
					"src"
				}
			}
			ProjectDependencies {
				my.project.one 0.0.1,
				my.project.one (0.0.1)
			}
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)
		// when no vendorId is given the vendorId of the current project should be used
		assertEquals("org.eclipse.n4js", project.projectDependencies.last.project.vendorId)

		assertEquals(0, project.projectDependencies.last.versionConstraint.lowerVersion.major)
		assertEquals(0, project.projectDependencies.last.versionConstraint.lowerVersion.minor)
		assertEquals(1, project.projectDependencies.last.versionConstraint.lowerVersion.micro)
		assertTrue(project.projectDependencies.last.versionConstraint.exclLowerBound)
		assertEquals(null, project.projectDependencies.last.versionConstraint.upperVersion, null)

		assertFalse(project.projectDependencies.head.versionConstraint.exclLowerBound)
	}

	@Test def void testOrderIndependence() {
		val project = '''
			Output: "output"
			Sources {
				source {
					"src"
				}
			}
			ProjectVersion: 0.0.1-SNAPSHOT
			VendorName: "Eclipse N4JS Project"
			VendorId: org.eclipse.n4js
			ProjectDependencies {
				my.project.one
			}
			ProjectId: my.project.one
			ProjectType: library
		'''.parse
		val errors = project.eResource.errors
		assertTrue(errors.toString, errors.empty)
	}
}
