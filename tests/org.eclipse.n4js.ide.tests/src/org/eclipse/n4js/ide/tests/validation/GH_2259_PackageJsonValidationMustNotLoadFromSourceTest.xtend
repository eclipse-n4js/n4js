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
package org.eclipse.n4js.ide.tests.validation

import com.google.common.base.Optional
import java.io.IOException
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.ide.server.build.N4JSProjectBuilder
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.xtext.ide.server.build.IBuildRequestFactory
import org.eclipse.n4js.xtext.ide.server.build.ProjectBuilder
import org.eclipse.n4js.xtext.ide.server.build.XBuildResult
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.service.AbstractGenericModule
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test for bug GH-2259.
 * <p>
 * The problem was that a package.json validation caused resources in other projects to be loaded from source
 * (in method {@code N4JSProjectSetupJsonValidatorExtension#checkConsistentPolyfills(JSONDocument)}).
 */
class GH_2259_PackageJsonValidationMustNotLoadFromSourceTest extends AbstractIdeTest {

	// The custom implementation of N4JSProjectBuilder (see below) will add messages to
	// this list while the initial build is running:
	private static final List<String> buildProgressMessages = Collections.synchronizedList(newArrayList);

	@Test
	def void test() throws IOException {
		testWorkspaceManager.createTestOnDisk(
			"Project1" -> #[
				"SomeModule1.n4jsd" -> '''
					@@Global @@ProvidedByRuntime

					@Polyfill
					export external public class Array<T> extends Array<T> {
						public addedMethod();
					}
				''',
				PACKAGE_JSON -> '''
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
				'''
			],
			"Project2" -> #[
				"SomeModule2.n4jsd" -> '''
					@@Global @@ProvidedByRuntime

					@Polyfill
					export external public class Array<T> extends Array<T> {
						public addedMethod();
					}
				''',
				PACKAGE_JSON -> '''
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
				'''
			],
			"MainProject" -> #[
				"Main.n4jsd" -> '''
					export external public class MainClass {}
				''',
				CFG_DEPENDENCIES -> '''
					Project1
					Project2
				''',
				PACKAGE_JSON -> '''
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
				'''
			]
		);

		buildProgressMessages.clear();

		startAndWaitForLspServer();
		assertIssues(
			"MainProject/package.json" -> #[
				'(Error, [13:3 - 13:13], The libraries Project1, Project2 provide polyfills for the same element "#.Array#addedMethod" and cannot be used together.)',
				'(Error, [14:3 - 14:13], The libraries Project1, Project2 provide polyfills for the same element "#.Array#addedMethod" and cannot be used together.)'
			]
		);

		assertEquals(8, buildProgressMessages.size);

		val messagesOfBuildingMainProject = buildProgressMessages.drop(6).map[trim].join("\n-----\n");

		assertEquals('''
			BEFORE building project "yarn-test-project/packages/MainProject"
			Resource set of Project1 contains no resources
			Resource set of Project2 contains no resources
			-----
			AFTER building project "yarn-test-project/packages/MainProject"
			Resource set of Project1 contains a single resource loaded from description
			Resource set of Project2 contains a single resource loaded from description
		'''.toString.trim, messagesOfBuildingMainProject);
		// NOTE: before the bug fix, resources in Project1 and Project2 were loaded from source while
		// building project "MainProject", so we would see the string segment "loaded from source" instead
		// of "loaded from description".
	}

	override protected getOverridingModule() {
		return Optional.of(GH_2259_TestModule);
	}

	public static final class GH_2259_TestModule extends AbstractGenericModule {

		def Class<? extends ProjectBuilder> bindProjectBuilder() {
			return GH_2259_TestProjectBuilder;
		}
	}

	public static final class GH_2259_TestProjectBuilder extends N4JSProjectBuilder {

		override XBuildResult doInitialBuild(IBuildRequestFactory buildRequestFactory, List<Delta> externalDeltas) {
			val id = getProjectConfig().getName();
			val pb1 = this.workspaceManager.getProjectBuilder("yarn-test-project/packages/Project1");
			val pb2 = this.workspaceManager.getProjectBuilder("yarn-test-project/packages/Project2");

			buildProgressMessages += '''
				BEFORE building project "«id»"
				Resource set of Project1 «getResourceSetStatus(pb1.resourceSet)»
				Resource set of Project2 «getResourceSetStatus(pb2.resourceSet)»
			''';

			val result = super.doInitialBuild(buildRequestFactory, externalDeltas);

			buildProgressMessages += '''
				AFTER building project "«id»"
				Resource set of Project1 «getResourceSetStatus(pb1.resourceSet)»
				Resource set of Project2 «getResourceSetStatus(pb2.resourceSet)»
			''';

			return result;
		}

		def private String getResourceSetStatus(ResourceSet resSet) {
			val resources = resSet.resources;
			return if (resources.empty) {
				"contains no resources"
			} else if(resources.size === 1) {
				val res = resources.head as N4JSResource;
				"contains a single resource" + (
					if (res.getScript() !== null && !res.getScript().eIsProxy()) {
						" loaded from source"
					} else if (res.isLoadedFromDescription()) {
						" loaded from description"
					} else {
						""
					}
				)
			} else {
				"contains " + resources.size + " resources"
			}
		}
	}
}
