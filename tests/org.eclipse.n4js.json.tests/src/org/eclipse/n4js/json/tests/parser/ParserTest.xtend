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
package org.eclipse.n4js.json.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSONInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.junit.Test
import static org.junit.Assert.*

/**
 * Tests for parsing JSON files.
 */
@RunWith(XtextRunner)
@InjectWith(JSONInjectorProvider)
class ParserTest {

	@Inject extension ParseHelper<JSONDocument>

	@Test def void testPlainValues() {
		'''{}'''.parseSuccessfully;
		'''[]'''.parseSuccessfully;
		'''42'''.parseSuccessfully;
		'''42.42'''.parseSuccessfully;
		'''42e+42'''.parseSuccessfully;
		'''42E+42'''.parseSuccessfully;
		'''42.42E-42'''.parseSuccessfully;
		'''"string"'''.parseSuccessfully;
		'''"null"'''.parseSuccessfully;
		'''"true"'''.parseSuccessfully;
		'''"false"'''.parseSuccessfully;
	}
	
	protected def JSONDocument parseSuccessfully(CharSequence json) {
		val doc = json.parse;
		assertTrue('''"«json»" ''' + doc.eResource.errors.join('\n')[line + ': ' + message], doc.eResource.errors.empty)
		return doc
	}
//
//
////	& 	('ModuleFilters'	'{' moduleFilters+=ModuleFilter+ '}')?
//
//	/**
//	 * also included
//	 */
//	@Test def void testCompleteExample() {
//		val project = '''
//			ProjectId: my.project.one
//			ProjectType: library
//			ProjectVersion: 0.0.1-SNAPSHOT
//			VendorId: org.eclipse.n4js
//			VendorName: "Eclipse N4JS Project"
//			Output: "output"
//			Libraries {
//				"lib"
//			}
//			Resources {
//				"resources"
//			}
//			Sources {
//				source {
//					"src",
//					"src2"
//				}
//				test {
//					"src-test"
//				}
//				external {
//					"external",
//					"external2"
//				}
//			}
//			ModuleFilters {
//				noValidate {
//					"**/path1/*.*",
//					"**/path2/A*.js"
//				}
//				noModuleWrap {
//					"**/*Wrapped.*" in "src",
//					"**/wrapped/*.js"
//				}
//			}
//			ProjectDependencies {
//				org.eclipse.n4js:my.project.one (0.0.1,0.0.2] compile,
//				org.eclipse.n4js:my.project.one 1.0 compile
//			}
//		'''.parse
//		val errors = project.eResource.errors
//		assertTrue(errors.toString, errors.empty)
//
//		assertEquals(3, project.sourceFragment.size)
//		assertEquals(SourceFragmentType.SOURCE, project.sourceFragment.head.sourceFragmentType)
//		assertEquals(#["src", "src2"], project.sourceFragment.head.paths)
//		assertEquals(SourceFragmentType.TEST, project.sourceFragment.get(1).sourceFragmentType)
//		assertEquals(#["src-test"], project.sourceFragment.get(1).paths)
//		assertEquals(SourceFragmentType.EXTERNAL, project.sourceFragment.last.sourceFragmentType)
//		assertEquals(#["external", "external2"], project.sourceFragment.last.paths)
//
//		assertEquals(2, project.moduleFilters.size)
//		assertEquals(ModuleFilterType.NO_VALIDATE, project.moduleFilters.head.moduleFilterType)
//		assertEquals(#["**/path1/*.*", "**/path2/A*.js"], project.moduleFilters.head.moduleSpecifiers.map[moduleSpecifierWithWildcard])
//		assertEquals(ModuleFilterType.NO_MODULE_WRAPPING, project.moduleFilters.get(1).moduleFilterType)
//		assertEquals(#["**/*Wrapped.*", "**/wrapped/*.js"], project.moduleFilters.get(1).moduleSpecifiers.map[moduleSpecifierWithWildcard])
//
//		assertEquals(project.allProjectDependencies.size, 2)
//		assertEquals("org.eclipse.n4js", project.allProjectDependencies.head.project.vendorId)
//		assertEquals("my.project.one", project.allProjectDependencies.head.project.projectId)
//
//		assertEquals(true, project.allProjectDependencies.head.versionConstraint.exclLowerBound)
//		assertEquals(0, project.allProjectDependencies.head.versionConstraint.lowerVersion.major)
//		assertEquals(0, project.allProjectDependencies.head.versionConstraint.lowerVersion.minor)
//		assertEquals(1, project.allProjectDependencies.head.versionConstraint.lowerVersion.micro)
//		assertEquals(false, project.allProjectDependencies.head.versionConstraint.exclUpperBound)
//		assertEquals(0, project.allProjectDependencies.head.versionConstraint.upperVersion.major)
//		assertEquals(0, project.allProjectDependencies.head.versionConstraint.upperVersion.minor)
//		assertEquals(2, project.allProjectDependencies.head.versionConstraint.upperVersion.micro)
//		assertEquals(ProjectDependencyScope.COMPILE, project.allProjectDependencies.head.scope)
//
//		assertEquals(null, project.allProjectDependencies.last.versionConstraint.upperVersion, null)
//		assertEquals(1, project.allProjectDependencies.last.versionConstraint.lowerVersion.major)
//		assertEquals(0, project.allProjectDependencies.last.versionConstraint.lowerVersion.minor)
//		assertEquals("if no number given 0 should be set by default", 0, project.allProjectDependencies.last.versionConstraint.lowerVersion.micro)
//		assertEquals(null, project.allProjectDependencies.last.versionConstraint.lowerVersion.qualifier)
//		assertEquals(ProjectDependencyScope.COMPILE, project.allProjectDependencies.last.scope)
//	}
//
//	@Test def void testProjectDependencyWithoutScope() {
//		val project = '''
//			ProjectId: my.project.one
//			ProjectType: library
//			ProjectVersion: 0.0.1-SNAPSHOT
//			VendorId: org.eclipse.n4js
//			VendorName: "Eclipse N4JS Project"
//			Output: "output"
//			Sources {
//				source {
//					"src"
//				}
//			}
//			ProjectDependencies {
//				org.eclipse:my.project.one (0.0.1,0.0.2]
//			}
//		'''.parse
//		val errors = project.eResource.errors
//		assertTrue(errors.toString, errors.empty)
//		// compile should be assigned by default
//		assertEquals(ProjectDependencyScope.COMPILE, project.allProjectDependencies.last.scope)
//	}
//
//	@Test def void testProjectDependencyWithoutVersion() {
//		val project = '''
//			ProjectId: my.project.one
//			ProjectType: library
//			ProjectVersion: 0.0.1-SNAPSHOT
//			VendorId: org.eclipse.n4js
//			VendorName: "Eclipse N4JS Project"
//			Output: "output"
//			Sources {
//				source {
//					"src"
//				}
//			}
//			ProjectDependencies {
//				org.eclipse:my.project.one
//			}
//		'''.parse
//		val errors = project.eResource.errors
//		assertTrue(errors.toString, errors.empty)
//		assertNull(project.allProjectDependencies.last.versionConstraint)
//	}
//
//	@Test def void testProjectDependencyWithoutVendor() {
//		val project = '''
//			ProjectId: my.project.one
//			ProjectType: library
//			ProjectVersion: 0.0.1-SNAPSHOT
//			VendorId: org.eclipse.n4js
//			VendorName: "Eclipse N4JS Project"
//			Output: "output"
//			Sources {
//				source {
//					"src"
//				}
//			}
//			ProjectDependencies {
//				my.project.one
//			}
//		'''.parse
//		val errors = project.eResource.errors
//		assertTrue(errors.toString, errors.empty)
//		// when no vendorId is given the vendorId of the current project should be used
//		assertEquals("org.eclipse.n4js", project.allProjectDependencies.last.project.vendorId)
//	}
//
//	@Test def void testProjectWithNoUpperBound() {
//		val project = '''
//			ProjectId: my.project.one
//			ProjectType: library
//			ProjectVersion: 0.0.1-SNAPSHOT
//			VendorId: org.eclipse.n4js
//			VendorName: "Eclipse N4JS Project"
//			Output: "output"
//			Sources {
//				source {
//					"src"
//				}
//			}
//			ProjectDependencies {
//				my.project.one 0.0.1,
//				my.project.one (0.0.1)
//			}
//		'''.parse
//		val errors = project.eResource.errors
//		assertTrue(errors.toString, errors.empty)
//		// when no vendorId is given the vendorId of the current project should be used
//		assertEquals("org.eclipse.n4js", project.allProjectDependencies.last.project.vendorId)
//
//		assertEquals(0, project.allProjectDependencies.last.versionConstraint.lowerVersion.major)
//		assertEquals(0, project.allProjectDependencies.last.versionConstraint.lowerVersion.minor)
//		assertEquals(1, project.allProjectDependencies.last.versionConstraint.lowerVersion.micro)
//		assertTrue(project.allProjectDependencies.last.versionConstraint.exclLowerBound)
//		assertEquals(null, project.allProjectDependencies.last.versionConstraint.upperVersion, null)
//
//		assertFalse(project.allProjectDependencies.head.versionConstraint.exclLowerBound)
//	}
//
//	@Test def void testOrderIndependence() {
//		val project = '''
//			Output: "output"
//			Sources {
//				source {
//					"src"
//				}
//			}
//			ProjectVersion: 0.0.1-SNAPSHOT
//			VendorName: "Eclipse N4JS Project"
//			VendorId: org.eclipse.n4js
//			ProjectDependencies {
//				my.project.one
//			}
//			ProjectId: my.project.one
//			ProjectType: library
//		'''.parse
//		val errors = project.eResource.errors
//		assertTrue(errors.toString, errors.empty)
//	}
}
