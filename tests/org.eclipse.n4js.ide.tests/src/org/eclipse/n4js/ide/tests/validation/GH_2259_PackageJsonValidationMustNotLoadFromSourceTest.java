/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.validation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ide.server.build.N4JSProjectBuilder;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.xtext.ide.server.build.IBuildRequestFactory;
import org.eclipse.n4js.xtext.ide.server.build.ProjectBuilder;
import org.eclipse.n4js.xtext.ide.server.build.XBuildResult;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * Test for bug GH-2259.
 * <p>
 * The problem was that a package.json validation caused resources in other projects to be loaded from source (in method
 * {@code N4JSProjectSetupJsonValidatorExtension#checkConsistentPolyfills(JSONDocument)}).
 */
@SuppressWarnings("javadoc")
public class GH_2259_PackageJsonValidationMustNotLoadFromSourceTest extends AbstractIdeTest {

	// The custom implementation of N4JSProjectBuilder (see below) will add messages to
	// this list while the initial build is running:
	private static final List<String> buildProgressMessages = Collections.synchronizedList(new ArrayList<>());

	@Test
	public void test() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"Project1", Map.of(
						"SomeModule1.n4jsd", """
								@@Global @@ProvidedByRuntime

								@Polyfill
								export external public class Array<T> extends Array<T> {
									public addedMethod();
								}
								""",
						PACKAGE_JSON, """
								{
									"name": "Project1",
									"version": "0.0.1",
									"n4js": {
										"projectType": "runtimeLibrary",
										"vendorId": "org.eclipse.n4js",
										"sources": {
											"source": [
												"src"
											]
										},
										"output": "src-gen"
									}
								}
								"""),
				"Project2", Map.of(
						"SomeModule2.n4jsd", """
								@@Global @@ProvidedByRuntime

								@Polyfill
								export external public class Array<T> extends Array<T> {
									public addedMethod();
								}
								""",
						PACKAGE_JSON, """
								{
									"name": "Project2",
									"version": "0.0.1",
									"n4js": {
										"projectType": "runtimeLibrary",
										"vendorId": "org.eclipse.n4js",
										"sources": {
											"source": [
												"src"
											]
										},
										"output": "src-gen"
									}
								}
								"""),
				"MainProject", Map.of(
						"Main.n4jsd", """
								export external public class MainClass {}
								""",
						CFG_DEPENDENCIES, """
								Project1
								Project2
								""",
						PACKAGE_JSON, """
								{
									"name": "MainProject",
									"version": "0.0.1",
									"n4js": {
										"projectType": "runtimeLibrary",
										"vendorId": "org.eclipse.n4js",
										"sources": {
											"source": [
												"src"
											]
										},
										"output": "src-gen",
										"requiredRuntimeLibraries": [
											"Project1",
											"Project2"
										]
									},
									"dependencies": {
										"Project1": "*",
										"Project2": "*"
									}
								}
								""")));

		buildProgressMessages.clear();

		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("MainProject/package.json", List.of(
						"(Error, [13:3 - 13:13], The libraries Project1, Project2 provide polyfills for the same element \"Array#addedMethod\" and cannot be used together.)",
						"(Error, [14:3 - 14:13], The libraries Project1, Project2 provide polyfills for the same element \"Array#addedMethod\" and cannot be used together.)")));

		assertEquals(8, buildProgressMessages.size());

		Iterable<String> msgsTail = IterableExtensions.drop(buildProgressMessages, 6);
		Iterable<String> msgsTrimmed = IterableExtensions.map(msgsTail, String::trim);
		String messagesOfBuildingMainProject = Strings.join("\n-----\n", msgsTrimmed);

		assertEquals("""
				BEFORE building project "yarn-test-project/packages/MainProject"
				Resource set of Project1 contains no resources
				Resource set of Project2 contains no resources
				-----
				AFTER building project "yarn-test-project/packages/MainProject"
				Resource set of Project1 contains a single resource loaded from description
				Resource set of Project2 contains a single resource loaded from description
				""".toString().trim(), messagesOfBuildingMainProject);
		// NOTE: before the bug fix, resources in Project1 and Project2 were loaded from source while
		// building project "MainProject", so we would see the string segment "loaded from source" instead
		// of "loaded from description".
	}

	@Override
	protected Optional<Class<? extends com.google.inject.Module>> getOverridingModule() {
		return Optional.of(GH_2259_TestModule.class);
	}

	public static final class GH_2259_TestModule extends AbstractGenericModule {

		public Class<? extends ProjectBuilder> bindProjectBuilder() {
			return GH_2259_TestProjectBuilder.class;
		}
	}

	public static final class GH_2259_TestProjectBuilder extends N4JSProjectBuilder {

		@Override
		public XBuildResult doInitialBuild(IBuildRequestFactory buildRequestFactory, List<Delta> externalDeltas) {
			String id = getProjectConfig().getName();
			ProjectBuilder pb1 = this.workspaceManager.getProjectBuilder("yarn-test-project/packages/Project1");
			ProjectBuilder pb2 = this.workspaceManager.getProjectBuilder("yarn-test-project/packages/Project2");

			buildProgressMessages.add("""
					BEFORE building project "%s"
					Resource set of Project1 %s
					Resource set of Project2 %s
					""".formatted(id,
					getResourceSetStatus(pb1.getResourceSet()),
					getResourceSetStatus(pb2.getResourceSet())));

			XBuildResult result = super.doInitialBuild(buildRequestFactory, externalDeltas);

			buildProgressMessages.add("""
					AFTER building project "%s"
					Resource set of Project1 %s
					Resource set of Project2 %s
					""".formatted(id,
					getResourceSetStatus(pb1.getResourceSet()),
					getResourceSetStatus(pb2.getResourceSet())));

			return result;
		}

		private String getResourceSetStatus(ResourceSet resSet) {
			EList<Resource> resources = resSet.getResources();

			if (resources.isEmpty()) {
				return "contains no resources";
			} else if (resources.size() == 1) {
				N4JSResource res = (N4JSResource) resources.get(0);
				String prefix = "contains a single resource";
				if (res.getScript() != null && !res.getScript().eIsProxy()) {
					return prefix + " loaded from source";
				} else if (res.isLoadedFromDescription()) {
					return prefix + " loaded from description";
				} else {
					return prefix + "";
				}
			} else {
				return "contains " + resources.size() + " resources";
			}
		}
	}
}
