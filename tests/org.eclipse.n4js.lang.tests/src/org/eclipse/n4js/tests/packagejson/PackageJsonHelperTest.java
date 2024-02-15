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
package org.eclipse.n4js.tests.packagejson;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.json.JSONGlobals;
import org.eclipse.n4js.json.JSONParseHelper;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.packagejson.PackageJsonHelper;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilter;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescriptionBuilder;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.semver.Semver.TagVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Testing the conversion from {@link JSONDocument} to {@link ProjectDescription}.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class PackageJsonHelperTest {

	private static final String DEFAULT_PROJECT_ID = "TestProject";

	@Inject
	private PackageJsonHelper packageJsonHelper;

	@Inject
	protected JSONParseHelper parseHelper;

	@Test
	public void testDependencies() {
		ProjectDescription pd = parseAndConvert("""
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
				""");

		// currently normal and "dev" dependencies are not distinguished
		assertEquals(5, pd.getProjectDependencies().size());
		ProjectDependency dep0 = pd.getProjectDependencies().get(0);
		ProjectDependency dep1 = pd.getProjectDependencies().get(1);
		ProjectDependency dep2 = pd.getProjectDependencies().get(2);
		ProjectDependency dep3 = pd.getProjectDependencies().get(3);
		ProjectDependency dep4 = pd.getProjectDependencies().get(4);

		assertEquals("express", dep0.getPackageName());
		assertEquals(">=1.2.3", dep0.getVersionRequirement().toString());
		assertEquals("lodash", dep1.getPackageName());
		assertEquals("~2.3.4", dep1.getVersionRequirement().toString());
		assertEquals("eslint", dep2.getPackageName());
		assertEquals("^3.4.5", dep2.getVersionRequirement().toString());

		// a dependency with an empty version requirement:
		assertEquals("emptyVersionRequirement", dep3.getPackageName());
		assertTrue("empty string as version requirement should be parsed to an empty VersionRangeSetRequirement",
				dep3.getVersionRequirement() instanceof VersionRangeSetRequirement
						&& ((VersionRangeSetRequirement) dep3.getVersionRequirement()).getRanges().isEmpty());
		// a dependency with version requirement "latest":
		assertEquals("latestVersionRequirement", dep4.getPackageName());
		assertTrue("'latest' as version requirement should be parsed to a TagVersionRequirement",
				dep4.getVersionRequirement() instanceof TagVersionRequirement);
		assertEquals("latest", ((TagVersionRequirement) dep4.getVersionRequirement()).getTagName());
	}

	@Test
	public void testDependenciesInvalid() {
		ProjectDescription pd = parseAndConvert("""
				{
					"dependencies": {
						"invalidVersionRequirement": 42,
						"": "0.0.1"
					}
				}
				""");

		// make sure no dependency was added for the invalid entry with empty key:
		assertNull(findFirst(pd.getProjectDependencies(), _pd -> isNullOrEmpty(_pd.getPackageName())));

		assertEquals(1, pd.getProjectDependencies().size());
		ProjectDependency dep0 = pd.getProjectDependencies().get(0);

		// a dependency with an invalid version requirement is not ignored entirely,
		// just the version requirement is omitted:
		assertEquals("invalidVersionRequirement", dep0.getPackageName());
		assertEquals(null, dep0.getVersionRequirement());
	}

	@Test
	public void testModuleFilters() {
		ProjectDescription pd = parseAndConvert("""
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
				""");

		assertEquals(1, pd.getModuleFilters().size());
		ModuleFilter mf0 = pd.getModuleFilters().get(0);

		assertEquals(ModuleFilterType.NO_VALIDATE, mf0.getType());
		assertEquals(2, mf0.getSpecifiers().size());
		assertEquals(null, mf0.getSpecifiers().get(0).getSourcePath());
		assertEquals("abc*", mf0.getSpecifiers().get(0).getSpecifierWithWildcard());
		assertEquals("src", mf0.getSpecifiers().get(1).getSourcePath());
		assertEquals("def*", mf0.getSpecifiers().get(1).getSpecifierWithWildcard());
	}

	@Test
	public void testSourceContainers() {
		ProjectDescription pd = parseAndConvert("""
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
				""");

		assertEquals(3, pd.getSourceContainers().size());
		SourceContainerDescription sc0 = pd.getSourceContainers().get(0);
		SourceContainerDescription sc1 = pd.getSourceContainers().get(1);
		SourceContainerDescription sc2 = pd.getSourceContainers().get(2);

		assertEquals(SourceContainerType.EXTERNAL, sc0.getType());
		assertEquals(List.of("src-ext1", "src-ext2"), sc0.getPaths());
		assertEquals(SourceContainerType.SOURCE, sc1.getType());
		assertEquals(List.of("src1", "src2"), sc1.getPaths());
		assertEquals(SourceContainerType.TEST, sc2.getType());
		assertEquals(List.of("src-test1", "src-test2"), sc2.getPaths());
	}

	@Test
	public void testSourceContainersInvalid() {
		String jsonSource = """
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
				""";
		ProjectDescription pdNoDefaults = parseAndConvert(jsonSource, false, null);
		ProjectDescription pdWithDefaults = parseAndConvert(jsonSource);

		// all source container specifications are invalid, so they should all be ignored:
		assertTrue(pdNoDefaults.getSourceContainers().isEmpty());
		// if default values are applied, the default should come into effect:
		assertCorrectDefaultSourceContainers(pdWithDefaults);
	}

	@Test
	public void testDefaultValues_empty() {
		ProjectDescription pd = parseAndConvert("""
				{}
				""");

		assertCorrectJSDefaults(pd, true);

		assertFalse(pd.hasNestedNodeModulesFolder());
		assertFalse(pd.hasN4JSNature());
	}

	@Test
	public void testDefaultValues_n4jsSection() {
		ProjectDescription pd = parseAndConvert("""
				{ "n4js": {} }
				""");

		assertCorrectJSDefaults(pd, true);

		assertFalse(pd.hasNestedNodeModulesFolder());
		assertTrue(pd.hasN4JSNature());
	}

	@Test
	public void testDefaultValues_defaultProjectTypeExplicitlySet() {
		ProjectDescription pd = parseAndConvert("""
				{ "n4js": { "projectType": "«defaultProjectType»" } }
				""");

		assertCorrectJSDefaults(pd, true);

		assertFalse(pd.hasNestedNodeModulesFolder());
		assertTrue(pd.hasN4JSNature());
	}

	@Test
	public void testDefaultValues_nonValidationProject() {
		ProjectDescription pd = parseAndConvert("""
				{ "n4js": { "projectType": "library" } }
				""");

		assertCorrectN4JSDefaults(pd, false);

		assertFalse(pd.hasNestedNodeModulesFolder());
		assertTrue(pd.hasN4JSNature());
	}

	private void assertCorrectN4JSDefaults(ProjectDescription pd, boolean hasDefaultProjectType) {
		assertEquals(DEFAULT_PROJECT_ID, pd.getPackageName());
		assertEquals(VERSION.defaultValue, pd.getVersion().toString());
		assertEquals(VENDOR_ID.defaultValue, pd.getVendorId());
		assertEquals(null, pd.getVendorName());
		if (hasDefaultProjectType) {
			assertEquals(ProjectType.PLAINJS, pd.getProjectType());
		} else {
			assertNotEquals(ProjectType.PLAINJS, pd.getProjectType());
		}
		assertEquals(MAIN_MODULE.defaultValue, pd.getMainModule());
		assertEquals(null, pd.getExtendedRuntimeEnvironment());
		assertEquals(Collections.emptyList(), pd.getProvidedRuntimeLibraries());
		assertEquals(Collections.emptyList(), pd.getRequiredRuntimeLibraries());
		assertEquals(Collections.emptyList(), pd.getProjectDependencies());
		assertEquals(null, pd.getImplementationId());
		assertEquals(Collections.emptyList(), pd.getImplementedProjects());
		assertEquals(OUTPUT.defaultValue, pd.getOutputPath());
		assertEquals(Collections.emptyList(), pd.getModuleFilters());
		assertEquals(Collections.emptyList(), pd.getTestedProjects());
		assertCorrectDefaultSourceContainers(pd);
	}

	private void assertCorrectJSDefaults(ProjectDescription pd, boolean hasDefaultProjectType) {
		assertEquals(DEFAULT_PROJECT_ID, pd.getPackageName());
		assertEquals(null, pd.getVersion());
		assertEquals(null, pd.getVendorId());
		assertEquals(null, pd.getVendorName());
		if (hasDefaultProjectType) {
			assertEquals(ProjectType.PLAINJS, pd.getProjectType());
		} else {
			assertNotEquals(ProjectType.PLAINJS, pd.getProjectType());
		}
		assertEquals(MAIN_MODULE.defaultValue, pd.getMainModule());
		assertEquals(null, pd.getExtendedRuntimeEnvironment());
		assertEquals(Collections.emptyList(), pd.getProvidedRuntimeLibraries());
		assertEquals(Collections.emptyList(), pd.getRequiredRuntimeLibraries());
		assertEquals(Collections.emptyList(), pd.getProjectDependencies());
		assertEquals(null, pd.getImplementationId());
		assertEquals(Collections.emptyList(), pd.getImplementedProjects());
		assertEquals(OUTPUT.defaultValue, pd.getOutputPath());
		assertEquals(Collections.emptyList(), pd.getModuleFilters());
		assertEquals(Collections.emptyList(), pd.getTestedProjects());
		assertCorrectDefaultSourceContainers(pd);
	}

	private void assertCorrectDefaultSourceContainers(ProjectDescription pd) {
		// expect a single source container of type "source" with path "."
		assertEquals(1, pd.getSourceContainers().size());
		assertEquals(List.of("."), pd.getSourceContainers().get(0).getPaths());
		assertEquals(SourceContainerType.SOURCE, pd.getSourceContainers().get(0).getType());
	}

	private ProjectDescription parseAndConvert(CharSequence jsonSource) {
		return parseAndConvert(jsonSource, true, DEFAULT_PROJECT_ID);
	}

	private ProjectDescription parseAndConvert(CharSequence jsonSource, boolean applyDefaultValues,
			String defaultProjectName) {
		JSONParseHelper jsonParseHelper = N4LanguageUtils
				.getServiceForContext(JSONGlobals.FILE_EXTENSION, JSONParseHelper.class).get();
		JSONDocument jsonDocument;
		try {
			jsonDocument = jsonParseHelper.parseSuccessfully(jsonSource);
			ProjectDescriptionBuilder preInitPD = packageJsonHelper.convertToProjectDescription(jsonDocument);
			packageJsonHelper.adjustAndApplyDefaults(jsonDocument, preInitPD, applyDefaultValues, defaultProjectName);
			ProjectDescription pd = preInitPD.build();

			return pd;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}
}
