/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.packagejson

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSONGlobals
import org.eclipse.n4js.json.JSONParseHelper
import org.eclipse.n4js.packagejson.PackageJsonHelper
import org.eclipse.n4js.projectDescription.ModuleFilterType
import org.eclipse.n4js.projectDescription.ProjectDescription
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.semver.Semver.TagVersionRequirement
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement
import org.eclipse.n4js.utils.languages.N4LanguageUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.packagejson.PackageJsonProperties.*
import static org.junit.Assert.*

/**
 * Testing the conversion from {@link JSONDocument} to {@link ProjectDescription}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class PackageJsonHelperTest {

	private static final String DEFAULT_PROJECT_ID = "TestProject";

	@Inject
	private PackageJsonHelper packageJsonHelper;

	@Inject
	protected extension JSONParseHelper;


	@Test
	def void testDependencies() {
		val pd = '''
			{
				"dependencies": {
					"express": ">=1.2.3",
					"lodash": "~2.3.4"
				},
				"devDependencies": {
					"eslint": "^3.4.5",
					"emptyVersionRequirement": "",
					"latestVersionRequirement": "latest"
				}
			}
		'''.parseAndConvert

		// currently normal and "dev" dependencies are not distinguished
		assertEquals(5, pd.projectDependencies.size);
		val dep0 = pd.projectDependencies.get(0);
		val dep1 = pd.projectDependencies.get(1);
		val dep2 = pd.projectDependencies.get(2);
		val dep3 = pd.projectDependencies.get(3);
		val dep4 = pd.projectDependencies.get(4);

		assertEquals("express", dep0.projectName);
		assertEquals(">=1.2.3", dep0.versionRequirement.toString);
		assertEquals("lodash", dep1.projectName);
		assertEquals("~2.3.4", dep1.versionRequirement.toString);
		assertEquals("eslint", dep2.projectName);
		assertEquals("^3.4.5", dep2.versionRequirement.toString);

		// a dependency with an empty version requirement:
		assertEquals("emptyVersionRequirement", dep3.projectName);
		assertTrue("empty string as version requirement should be parsed to an empty VersionRangeSetRequirement",
			dep3.versionRequirement instanceof VersionRangeSetRequirement
			&& (dep3.versionRequirement as VersionRangeSetRequirement).ranges.empty);
		// a dependency with version requirement "latest":
		assertEquals("latestVersionRequirement", dep4.projectName);
		assertTrue("'latest' as version requirement should be parsed to a TagVersionRequirement",
			dep4.versionRequirement instanceof TagVersionRequirement);
		assertEquals("latest", (dep4.versionRequirement as TagVersionRequirement).tagName);
	}

	@Test
	def void testDependenciesInvalid() {
		val pd = '''
			{
				"dependencies": {
					"invalidVersionRequirement": 42,
					"": "0.0.1"
				}
			}
		'''.parseAndConvert

		// make sure no dependency was added for the invalid entry with empty key:
		assertNull(pd.projectDependencies.filter[projectName.nullOrEmpty].head);

		assertEquals(1, pd.projectDependencies.size);
		val dep0 = pd.projectDependencies.get(0);

		// a dependency with an invalid version requirement is not ignored entirely,
		// just the version requirement is omitted:
		assertEquals("invalidVersionRequirement", dep0.projectName);
		assertEquals(null, dep0.versionRequirement);
	}

	@Test
	def void testModuleFilters() {
		val pd = '''
			{
				"n4js": {
					"moduleFilters": {
						"noValidate": [
							"abc*",
							{
								"sourceContainer": "src",
								"module": "def*"
							}
						]
					}
				}
			}
		'''.parseAndConvert

		assertEquals(1, pd.moduleFilters.size);
		val mf0 = pd.moduleFilters.get(0);

		assertEquals(ModuleFilterType.NO_VALIDATE, mf0.moduleFilterType);
		assertEquals(2, mf0.moduleSpecifiers.size);
		assertEquals(null, mf0.moduleSpecifiers.get(0).sourcePath);
		assertEquals("abc*", mf0.moduleSpecifiers.get(0).moduleSpecifierWithWildcard);
		assertEquals("src", mf0.moduleSpecifiers.get(1).sourcePath);
		assertEquals("def*", mf0.moduleSpecifiers.get(1).moduleSpecifierWithWildcard);
	}

	@Test
	def void testSourceContainers() {
		val pd = '''
			{
				"n4js": {
					"sources": {
						"external": [
							"src-ext1",
							"src-ext2"
						],
						"source": [
							"src1",
							"src2"
						],
						"test": [
							"src-test1",
							"src-test2"
						],
						"invalid": [
							"src-invalid"
						],
						"test": []
					}
				}
			}
		'''.parseAndConvert

		assertEquals(3, pd.sourceContainers.size);
		val sc0 = pd.sourceContainers.get(0);
		val sc1 = pd.sourceContainers.get(1);
		val sc2 = pd.sourceContainers.get(2);

		assertEquals(SourceContainerType.EXTERNAL, sc0.sourceContainerType);
		assertEquals(#[ "src-ext1", "src-ext2" ], sc0.paths);
		assertEquals(SourceContainerType.SOURCE, sc1.sourceContainerType);
		assertEquals(#[ "src1", "src2" ], sc1.paths);
		assertEquals(SourceContainerType.TEST, sc2.sourceContainerType);
		assertEquals(#[ "src-test1", "src-test2" ], sc2.paths);
	}

	@Test
	def void testSourceContainersInvalid() {
		val jsonSource = '''
			{
				"n4js": {
					"sources": {
						"invalid": [
							"src-invalid"
						],
						"test": []
					}
				}
			}
		''';
		val pdNoDefaults = jsonSource.parseAndConvert(false, null);
		val pdWithDefaults = jsonSource.parseAndConvert;

		// all source container specifications are invalid, so they should all be ignored:
		assertTrue(pdNoDefaults.sourceContainers.empty);
		// if default values are applied, the default should come into effect:
		assertCorrectDefaultSourceContainers(pdWithDefaults);
	}

	@Test
	def void testDefaultValues_empty() {
		val pd = '''
			{}
		'''.parseAndConvert

		assertCorrectDefaults(pd, true);

		assertFalse(pd.hasNestedNodeModulesFolder);
		assertFalse(pd.hasN4JSNature);
	}

	@Test
	def void testDefaultValues_n4jsSection() {
		val pd = '''
			{ "n4js": {} }
		'''.parseAndConvert

		assertCorrectDefaults(pd, true);

		assertFalse(pd.hasNestedNodeModulesFolder);
		assertTrue(pd.hasN4JSNature);
	}

	@Test
	def void testDefaultValues_defaultProjectTypeExplicitlySet() {
		val defaultProjectType = "plainjs";
		val pd = '''
			{ "n4js": { "projectType": "«defaultProjectType»" } }
		'''.parseAndConvert

		assertCorrectDefaults(pd, true);

		assertFalse(pd.hasNestedNodeModulesFolder);
		assertTrue(pd.hasN4JSNature);
	}

	@Test
	def void testDefaultValues_nonValidationProject() {
		val pd = '''
			{ "n4js": { "projectType": "library" } }
		'''.parseAndConvert

		assertCorrectDefaults(pd, false);

		assertFalse(pd.hasNestedNodeModulesFolder);
		assertTrue(pd.hasN4JSNature);
	}


	def private assertCorrectDefaults(ProjectDescription pd, boolean hasDefaultProjectType) {
		assertEquals(DEFAULT_PROJECT_ID, pd.projectName);
		assertEquals(VERSION.defaultValue, pd.projectVersion.toString);
		assertEquals(VENDOR_ID.defaultValue, pd.vendorId);
		assertEquals(null, pd.vendorName);
		if(hasDefaultProjectType) {
			assertEquals(ProjectType.PLAINJS, pd.projectType);
		} else {
			assertNotEquals(ProjectType.PLAINJS, pd.projectType);
		}
		assertEquals(MAIN_MODULE.defaultValue, pd.mainModule);
		assertEquals(null, pd.extendedRuntimeEnvironment);
		assertEquals(#[], pd.providedRuntimeLibraries);
		assertEquals(#[], pd.requiredRuntimeLibraries);
		assertEquals(#[], pd.projectDependencies);
		assertEquals(null, pd.implementationId);
		assertEquals(#[], pd.implementedProjects);
		assertEquals(#[], pd.initModules);
		assertEquals(null, pd.execModule);
		assertEquals(OUTPUT.defaultValue, pd.outputPath);
		assertEquals(#[], pd.moduleFilters);
		assertEquals(#[], pd.testedProjects);
		assertCorrectDefaultSourceContainers(pd);
	}
	def private assertCorrectDefaultSourceContainers(ProjectDescription pd) {
		// expect a single source container of type "source" with path "."
		assertEquals(1, pd.sourceContainers.size);
		assertEquals(#["."], pd.sourceContainers.head.paths);
		assertEquals(SourceContainerType.SOURCE, pd.sourceContainers.head.sourceContainerType);
	}


	def private ProjectDescription parseAndConvert(CharSequence jsonSource) {
		return parseAndConvert(jsonSource, true, DEFAULT_PROJECT_ID);
	}
	def private ProjectDescription parseAndConvert(CharSequence jsonSource, boolean applyDefaultValues, String defaultProjectName) {
		val jsonParseHelper = N4LanguageUtils.getServiceForContext(JSONGlobals.FILE_EXTENSION, JSONParseHelper).get();
		val jsonDocument = jsonParseHelper.parseSuccessfully(jsonSource);
		val pd = packageJsonHelper.convertToProjectDescription(jsonDocument, applyDefaultValues, defaultProjectName);
		return pd;
	}
}
