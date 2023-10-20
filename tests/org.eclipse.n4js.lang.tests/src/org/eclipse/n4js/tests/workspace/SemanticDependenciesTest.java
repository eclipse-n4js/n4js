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
package org.eclipse.n4js.tests.workspace;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.server.build.N4JSConfigSnapshotFactory;
import org.eclipse.n4js.packagejson.projectDescription.DependencyType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescriptionBuilder;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfig;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfig;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.n4js.workspace.utils.SemanticDependencySupplier;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.UriExtensions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Low level tests for {@link N4JSProjectConfig#getSemanticDependencies()} and the related tracking of definition
 * projects in {@link N4JSWorkspaceConfig}.
 * <p>
 * Uses custom mock-implementations to avoid a complicated test setup.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class SemanticDependenciesTest {

	@Inject
	private ProjectDiscoveryHelper projectDiscoveryHelper;
	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;
	@Inject
	private SemanticDependencySupplier semanticDependencySupplier;
	@Inject
	private N4JSConfigSnapshotFactory configSnapshotFactory;
	@Inject
	private UriExtensions uriExtensions;

	private MockWorkspaceConfig workspace;

	@Before
	public void createWorkspace() {
		this.workspace = new MockWorkspaceConfig(projectDiscoveryHelper, projectDescriptionLoader,
				semanticDependencySupplier, configSnapshotFactory, uriExtensions);
	}

	@After
	public void disposeWorkspace() {
		workspace.deregisterAllProjects();
		this.workspace = null;
	}

	/** Implicitly add dependency to a type definition project when explicit dependency to implementation is given. */
	@Test
	public void testAddImplicitDependencyToTypeDefinition() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		N4JSProjectConfig definition = definitionProject(implementation);
		N4JSProjectConfig client = project(new N4JSPackageName("client"), implementation);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies,
				List.of("client/node_modules/" + definition.getName(), "client/node_modules/impl"));
	}

	/**
	 * Corner case of several type definition projects for the same implementation should be handled gracefully (esp.
	 * consistently).
	 */
	@Test
	public void testAddImplicitDependencyToOneOfManyTypeDefinitions() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		definitionProject(new N4JSPackageName(N4JSGlobals.N4JSD_SCOPE, "someDefOfImpl"), implementation);
		definitionProject(new N4JSPackageName(N4JSGlobals.N4JSD_SCOPE, "anotherDefOfImpl"), implementation);
		N4JSProjectConfig client = project(new N4JSPackageName("client"), implementation);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies,
				List.of("client/node_modules/@n4jsd/anotherDefOfImpl", "client/node_modules/impl"));
	}

	/** There is a single implementation and definition project. The implementation project is listed first. */
	@Test
	public void testSingleTypeDefinitionDependencyOrder1() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		N4JSProjectConfig definition = definitionProject(implementation);
		N4JSProjectConfig client = project(new N4JSPackageName("client"), implementation, definition);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies,
				List.of("client/node_modules/@n4jsd/impl", "client/node_modules/impl"));
	}

	/** Similar to no. 1, but the definition project is listed first. */
	@Test
	public void testSingleTypeDefinitionDependencyOrder2() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		N4JSProjectConfig definition = definitionProject(implementation);
		N4JSProjectConfig client = project(new N4JSPackageName("client"), definition, implementation);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies,
				List.of("client/node_modules/@n4jsd/impl", "client/node_modules/impl"));
	}

	/** Similar to no. 1, but the definition project has an unconventional name, i.e. not in scope "@n4jsd". */
	@Test
	public void testSingleTypeDefinitionDependencyOrder3() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		N4JSProjectConfig definition = definitionProject(new N4JSPackageName("definitionOfImpl"), implementation);
		N4JSProjectConfig client = project(new N4JSPackageName("client"), implementation, definition);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies,
				List.of("client/node_modules/definitionOfImpl", "client/node_modules/impl"));
	}

	/** There is multiple distinct implementation and definition projects. */
	@Test
	public void testMultipleTypeDefinitionDependencyOrder1() {
		N4JSProjectConfig implementation1 = project(new N4JSPackageName("impl1"));
		N4JSProjectConfig definition1 = definitionProject(implementation1);
		N4JSProjectConfig implementation2 = project(new N4JSPackageName("impl2"));
		N4JSProjectConfig definition2 = definitionProject(implementation2);

		N4JSProjectConfig client = project(new N4JSPackageName("client"), definition1, implementation1, definition2,
				implementation2);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder("Definition projects are listed before implementation.",
				orderedDependencies, List.of("client/node_modules/@n4jsd/impl1", "client/node_modules/@n4jsd/impl2",
						"client/node_modules/impl1", "client/node_modules/impl2"));
	}

	/** There is a duplicate project ID among definition projects. */
	@Test
	public void testDuplicateProjectsDependencyOrder() {
		N4JSProjectConfig implementation1 = project(new N4JSPackageName("impl1"));
		N4JSProjectConfig definition1 = definitionProject(new N4JSPackageName("@n4jsd/impl"), implementation1);
		N4JSProjectConfig implementation2 = project(new N4JSPackageName("impl2"));
		N4JSProjectConfig definition2 = definitionProject(new N4JSPackageName("@n4jsd/impl"), implementation2);

		assertNull("should be null because it is shadowed by the project with same name that was registered earlier",
				definition2);

		N4JSProjectConfig client = project(new N4JSPackageName("client"), definition1.getName(),
				implementation1.getName(), "@n4jsd/impl", implementation2.getName());

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		assertOrder(
				"Duplicate type definition project IDs do not prevent the dependency order computation from terminating successfully.",
				orderedDependencies, List.of("client/node_modules/@n4jsd/impl", "client/node_modules/@n4jsd/impl",
						"client/node_modules/impl1", "client/node_modules/impl2"));
	}

	/** There is two type definition projects that both provide definitions for the same implementation project. */
	@Test
	public void testOneToManyTypeDefinitionsMapping1() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		N4JSProjectConfig def1 = definitionProject(new N4JSPackageName("def1"), implementation);
		N4JSProjectConfig def2 = definitionProject(new N4JSPackageName("def2"), implementation);

		N4JSProjectConfig client = project(new N4JSPackageName("client"), implementation, def1, def2);
		// notice order of 'def2', 'def1'
		N4JSProjectConfig clientReversed = project(new N4JSPackageName("clientReversed"), implementation, def2, def1);

		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();
		List<ProjectDependency> orderedDependenciesReversed = clientReversed.getSemanticDependencies();

		assertOrder(
				"Two type definitions project with same definesPackage will be listed in order of declaration before their implementation project.",
				orderedDependencies,
				List.of("client/node_modules/def1", "client/node_modules/def2", "client/node_modules/impl"));

		assertOrder(
				"Two type definitions project with same definesPackage will be listed in order of declaration before their implementation project.",
				orderedDependenciesReversed, List.of("clientReversed/node_modules/def2",
						"clientReversed/node_modules/def1", "clientReversed/node_modules/impl"));
	}

	/**
	 * There is a type definition dependency whose implementation project is not listed among the dependencies (orphan
	 * definition).
	 */
	@Test
	public void testOrphanedTypeDefinitions() {
		N4JSProjectConfig implementation = project(new N4JSPackageName("impl"));
		N4JSProjectConfig orphanDefinition = definitionProject(new N4JSPackageName("def"),
				new N4JSPackageName("non-existent"));
		N4JSProjectConfig regularDefinition = definitionProject(implementation);

		N4JSProjectConfig client1 = project(new N4JSPackageName("client1"), implementation, orphanDefinition,
				regularDefinition);
		N4JSProjectConfig client2 = project(new N4JSPackageName("client2"), implementation, regularDefinition,
				orphanDefinition);

		List<ProjectDependency> orderedDependencies1 = client1.getSemanticDependencies();
		List<ProjectDependency> orderedDependencies2 = client2.getSemanticDependencies();

		assertOrder("Orphan type definitions are also moved to the front.",
				orderedDependencies1,
				List.of("client1/node_modules/def", "client1/node_modules/@n4jsd/impl", "client1/node_modules/impl"));
		assertOrder("Orphan type definitions are also moved to the front.",
				orderedDependencies2,
				List.of("client2/node_modules/@n4jsd/impl", "client2/node_modules/def", "client2/node_modules/impl"));
	}

	/**
	 * There is a large number of implementation projects with and without type definitions and type definition projects
	 * with and without corresponding implementation project (orphan definitions).
	 *
	 * The initial list of dependencies is shuffled with a fixed seed.
	 */
	@Test
	public void testShuffledPermutations() {
		List<N4JSProjectConfig> pureImplProjects = new ArrayList<>();
		List<N4JSProjectConfig> implProjectsWithTypeDef = new ArrayList<>();
		List<N4JSProjectConfig> typeDefProjectWithImpl = new ArrayList<>();
		List<N4JSProjectConfig> orphanTypeDefProjects = new ArrayList<>();
		for (int i = 0; i <= 10; i++) {
			pureImplProjects.add(project(new N4JSPackageName("impl" + i)));
		}
		for (int i = 0; i <= 20; i++) {
			implProjectsWithTypeDef.add(project(new N4JSPackageName("impl-no-types-" + i)));
		}
		for (N4JSProjectConfig p : implProjectsWithTypeDef) {
			typeDefProjectWithImpl.add(definitionProject(p));
		}
		for (int i = 0; i <= 10; i++) {
			orphanTypeDefProjects.add(
					definitionProject(new N4JSPackageName("orphan-def" + i), new N4JSPackageName("non-existent-" + i)));
		}

		List<N4JSProjectConfig> dependencies = new ArrayList<>();
		dependencies.addAll(pureImplProjects);
		dependencies.addAll(implProjectsWithTypeDef);
		dependencies.addAll(typeDefProjectWithImpl);
		dependencies.addAll(orphanTypeDefProjects);
		// shuffle dependency order (with constant seed)
		Collections.shuffle(dependencies, new Random(821));

		N4JSProjectConfig client = project(new N4JSPackageName("client"),
				dependencies.toArray(new N4JSProjectConfig[0]));
		List<ProjectDependency> orderedDependencies = client.getSemanticDependencies();

		String orderRepresentation = join(" ", orderedDependencies);
		List<String> problems = checkTypeDefinitionsOccurBeforeImplementationProjects(orderedDependencies);

		assertEquals("Expected all type definitions to be listed before their implementation projects."
				+ orderRepresentation + ".", "", join("\n", problems));
	}

	/**
	 * Checks that the given list of dependencies always list type definition projects before their implementation
	 * project.
	 *
	 * Returns a list of problems or an empty list if the constraint was fulfilled.
	 */
	private List<String> checkTypeDefinitionsOccurBeforeImplementationProjects(
			Iterable<ProjectDependency> dependencies) {
		Set<N4JSProjectConfig> encounteredTypeDefs = new HashSet<>();
		Map<String, N4JSProjectConfig> encounteredImplProjectsById = new HashMap<>();

		List<String> problems = new ArrayList<>();

		for (String dependencyName : map(dependencies, d -> d.getPackageName())) {
			N4JSProjectConfig dependency = workspace.findProjectByName(dependencyName);
			if (dependency.getProjectDescription().getProjectType() == ProjectType.DEFINITION) {
				if (encounteredImplProjectsById.containsKey(dependency.getProjectDescription().getDefinesPackage())) {
					problems.add("Implementation project of type definition " + dependency.getName() +
							" was listed before its definition.");
				}
				encounteredTypeDefs.add(dependency);
			} else {
				encounteredImplProjectsById.put(dependency.getName(), dependency);
			}
		}

		return problems;
	}

	/**
	 * Asserts that the given list of {@code dependencies} are in the {@code expectedProjectNameOrder} in terms of their
	 * project IDs.
	 */
	private static void assertOrder(String message, Iterable<ProjectDependency> dependencies,
			Iterable<String> expectedProjectNameOrder) {
		String actual = join(" ", map(dependencies, d -> d.getPackageName()));
		String expectation = join(" ", expectedProjectNameOrder);

		assertEquals(message, expectation, actual);
	}

	/**
	 * Returns with a new project (of type 'library') with the given projectName.
	 */
	private N4JSProjectConfig project(N4JSPackageName projectName) {
		return project(projectName, new N4JSProjectConfig[0]);
	}

	/**
	 * Returns with a new project (of type 'library') with the given projectName and list of dependencies.
	 */
	private N4JSProjectConfig project(N4JSPackageName projectName, N4JSProjectConfig... dependencies) {
		List<N4JSProjectConfig> depList = Arrays.asList(dependencies);
		return project(projectName, toList(map(depList, d -> d.getName())).toArray(new String[0]));
	}

	/**
	 * Returns with a new project (of type 'library') with the given projectName and list of dependencies.
	 */
	private N4JSProjectConfig project(N4JSPackageName projectName, String... dependencies) {
		for (String depName : dependencies) {
			N4JSProjectConfig existingPrj = workspace.findProjectByName(depName);
			if (existingPrj != null) {
				ProjectDescription pd = existingPrj.getProjectDescription();

				String relPath = projectName.getRawName() + "/node_modules/";
				String newQName = relPath + depName;

				if (!Objects.equals(pd.getId(), newQName)) {
					ProjectDescription newPD = pd.change().setId(newQName).build();

					workspace.deregisterProject(depName);

					FileURI pId = workspace.getPathAsFileURI()
							.appendPath(relPath)
							.appendPath(depName);
					workspace.registerProject(pId, newPD);
				}
			}
		}
		FileURI prjLocation = workspace.getPathAsFileURI().appendPath(projectName.getRawName());
		ProjectDescription pd = newProjectDescription(prjLocation, projectName, null, dependencies);
		return workspace.registerProject(prjLocation, pd);
	}

	/**
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the
	 * projectName of {@code implementationProject}.
	 *
	 * The name of the type definition project is inferred from the {@code implementationProject} by appending the
	 * suffix {@code -n4jsd}.
	 */
	private N4JSProjectConfig definitionProject(N4JSProjectConfig implementationProject) {
		return definitionProject(new N4JSPackageName(N4JSGlobals.N4JSD_SCOPE, implementationProject.getName()),
				implementationProject);
	}

	/**
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the
	 * projectName of {@code implementationProject}.
	 */
	private N4JSProjectConfig definitionProject(N4JSPackageName projectName, N4JSProjectConfig implementationProject) {
		return definitionProject(projectName, implementationProject.getN4JSPackageName());
	}

	/**
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the
	 * {@code definesPackage}.
	 */
	private N4JSProjectConfig definitionProject(N4JSPackageName projectName, N4JSPackageName definesPackage) {
		FileURI prjLocation = workspace.getPathAsFileURI().appendPath(projectName.getRawName());
		ProjectDescription pd = newProjectDescription(prjLocation, projectName, definesPackage, new String[0]);
		return workspace.registerProject(prjLocation, pd);
	}

	private ProjectDescription newProjectDescription(FileURI location, N4JSPackageName projectName,
			N4JSPackageName definesPackage, String... dependencies) {

		ProjectType projectType = definesPackage != null ? ProjectType.DEFINITION : ProjectType.LIBRARY;
		ProjectDescriptionBuilder pdb = ProjectDescription.builder()
				.setLocation(location)
				.setPackageName(projectName.getRawName())
				.setProjectType(projectType)
				.setDefinesPackage(definesPackage == null ? null : definesPackage.getRawName());

		for (String depName : dependencies) {
			pdb.addDependency(new ProjectDependency(depName, DependencyType.RUNTIME, "", null));
		}
		return pdb.build();
	}

	static class MockWorkspaceConfig extends N4JSWorkspaceConfig {

		MockWorkspaceConfig(ProjectDiscoveryHelper projectDiscoveryHelper,
				ProjectDescriptionLoader projectDescriptionLoader,
				SemanticDependencySupplier semanticDependencySupplier,
				ConfigSnapshotFactory configSnapshotFactory, UriExtensions uriExtensions) {

			super(URI.createFileURI(File.separator + "mock-workspace"),
					projectDiscoveryHelper, projectDescriptionLoader, semanticDependencySupplier, configSnapshotFactory,
					uriExtensions);
		}

		@Override
		protected MockTypeDefinitionsProject createProjectConfig(FileURI path, ProjectDescription pd) {
			return new MockTypeDefinitionsProject(this, path, pd, projectDescriptionLoader, semanticDependencySupplier);
		}

		@Override
		public void deregisterAllProjects() {
			super.deregisterAllProjects();
		}

		public void deregisterProject(String name) {
			projectID2ProjectConfig.remove(name);
		}
	}

	static class MockTypeDefinitionsProject extends N4JSProjectConfig {

		MockTypeDefinitionsProject(
				MockWorkspaceConfig workspace, FileURI path,
				ProjectDescription pd, ProjectDescriptionLoader pdLoader,
				SemanticDependencySupplier semanticDependencySupplier) {

			super(workspace, path, pd, pdLoader, semanticDependencySupplier);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(getName() + " {");

			String definesPackage = getProjectDescription().getDefinesPackage();
			if (definesPackage != null) {
				builder.append("definesPackage=" + definesPackage);
			}
			Set<String> deps = getDependencies();
			if (!deps.isEmpty()) {
				builder.append("dependencies=" + deps.toString());
			}

			builder.append("}");
			return builder.toString();
		}
	}
}