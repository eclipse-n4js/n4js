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
package org.eclipse.n4js.tests.workspace

import com.google.inject.Inject
import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List
import java.util.Random
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.ide.server.build.N4JSConfigSnapshotFactory
import org.eclipse.n4js.projectDescription.DependencyType
import org.eclipse.n4js.projectDescription.ProjectDependency
import org.eclipse.n4js.projectDescription.ProjectDescription
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.utils.ProjectDescriptionLoader
import org.eclipse.n4js.utils.ProjectDiscoveryHelper
import org.eclipse.n4js.workspace.N4JSProjectConfig
import org.eclipse.n4js.workspace.N4JSWorkspaceConfig
import org.eclipse.n4js.workspace.locations.FileURI
import org.eclipse.n4js.workspace.utils.N4JSProjectName
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.util.UriExtensions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Low level tests for {@link N4JSProjectConfig#computeSemanticDependencies()} and
 * the related tracking of definition projects in {@link N4JSWorkspaceConfig}.
 * <p>
 * Uses custom mock-implementations to avoid a complicated test setup.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class SemanticDependenciesTest {

	@Inject private ProjectDiscoveryHelper projectDiscoveryHelper;
	@Inject private ProjectDescriptionLoader projectDescriptionLoader;
	@Inject private N4JSConfigSnapshotFactory configSnapshotFactory;
	@Inject private UriExtensions uriExtensions;

	private MockWorkspaceConfig workspace;

	@Before
	public def void createWorkspace() {
		this.workspace = new MockWorkspaceConfig(projectDiscoveryHelper, projectDescriptionLoader, configSnapshotFactory, uriExtensions);
	}

	@After
	public def void disposeWorkspace() {
		this.workspace = null;
	}

	/** Implicitly add dependency to a type definition project when explicit dependency to implementation is given. */
	@Test
	public def void testAddImplicitDependencyToTypeDefinition() {
		val implementation = project(new N4JSProjectName("impl"));
		val definition = implementation.definitionProject;
		val client = project(new N4JSProjectName("client"), #[implementation]);

		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #[definition.name, "impl"]);
	}

	/** Corner case of several type definition projects for the same implementation should be handled gracefully (esp. consistently). */
	@Test
	public def void testAddImplicitDependencyToOneOfManyTypeDefinitions() {
		val implementation = project(new N4JSProjectName("impl"));
		definitionProject(new N4JSProjectName(N4JSGlobals.N4JSD_SCOPE, "someDefOfImpl"), implementation);
		definitionProject(new N4JSProjectName(N4JSGlobals.N4JSD_SCOPE, "anotherDefOfImpl"), implementation);
		val client = project(new N4JSProjectName("client"), #[implementation]);

		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["@n4jsd/anotherDefOfImpl", "impl"]);
	}

	/** There is a single implementation and definition project. The implementation project is listed first. */
	@Test
	public def void testSingleTypeDefinitionDependencyOrder1() {
		val implementation = project(new N4JSProjectName("impl"));
		val definition = implementation.definitionProject;
		val client = project(new N4JSProjectName("client"), #[implementation, definition]);
		
		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["@n4jsd/impl", "impl"]);
	}
	
	/** Similar to no. 1, but the definition project is listed first. */
	@Test
	public def void testSingleTypeDefinitionDependencyOrder2() {
		val implementation = project(new N4JSProjectName("impl"));
		val definition = implementation.definitionProject;
		val client = project(new N4JSProjectName("client"), #[definition, implementation]);
		
		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["@n4jsd/impl", "impl"]);
	}
	
	/** Similar to no. 1, but the definition project has an unconventional name, i.e. not in scope "@n4jsd". */
	@Test
	public def void testSingleTypeDefinitionDependencyOrder3() {
		val implementation = project(new N4JSProjectName("impl"));
		val definition = definitionProject(new N4JSProjectName("definitionOfImpl"), implementation);
		val client = project(new N4JSProjectName("client"), #[implementation, definition]);
		
		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["definitionOfImpl", "impl"]);
	}
	
	/** There is multiple distinct implementation and definition projects. */
	@Test
	public def void testMultipleTypeDefinitionDependencyOrder1() {
		val implementation1 = project(new N4JSProjectName("impl1"));
		val definition1 = implementation1.definitionProject;
		val implementation2 = project(new N4JSProjectName("impl2"));
		val definition2 = implementation2.definitionProject;
		
		val client = project(new N4JSProjectName("client"), #[definition1, implementation1, definition2, implementation2]);
		
		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Definition projects are listed before implementation.", 
			orderedDependencies, #["@n4jsd/impl1", "@n4jsd/impl2", "impl1", "impl2"]);
	}
	
	/** There is a duplicate project ID among definition projects. */
	@Test
	public def void testDuplicateProjectsDependencyOrder() {
		val implementation1 = project(new N4JSProjectName("impl1"));
		val definition1 = definitionProject(new N4JSProjectName("@n4jsd/impl"), implementation1);
		val implementation2 = project(new N4JSProjectName("impl2"));
		val definition2 = definitionProject(new N4JSProjectName("@n4jsd/impl"), implementation2);
		
		assertNull("should be null because it is shadowed by the project with same name that was registered earlier", definition2);
		
		val client = project(new N4JSProjectName("client"), #[definition1.name, implementation1.name, "@n4jsd/impl", implementation2.name]);
		
		val orderedDependencies = client.computeSemanticDependencies();
		assertOrder("Duplicate type definition project IDs do not prevent the dependency order computation from terminating successfully.",
			orderedDependencies, #["@n4jsd/impl", "@n4jsd/impl", "impl1", "impl2"]);
	}
	
	/** There is two type definition projects that both provide definitions for the same implementation project. */
	@Test
	public def void testOneToManyTypeDefinitionsMapping1() {
		val implementation = project(new N4JSProjectName("impl"));
		val def1 = definitionProject(new N4JSProjectName("def1"), implementation);
		val def2 = definitionProject(new N4JSProjectName("def2"), implementation);
		
		val client = project(new N4JSProjectName("client"), #[implementation, def1, def2]);
		// notice order of 'def2', 'def1'
		val clientReversed = project(new N4JSProjectName("clientReversed"), #[implementation, def2, def1]);
		
		val orderedDependencies = client.computeSemanticDependencies();
		val orderedDependenciesReversed = clientReversed.computeSemanticDependencies();

		assertOrder("Two type definitions project with same definesPackage will be listed in order of declaration before their implementation project.",
			orderedDependencies, #["def1", "def2", "impl"] );
			
		assertOrder("Two type definitions project with same definesPackage will be listed in order of declaration before their implementation project.",
			orderedDependenciesReversed, #["def2", "def1", "impl"] );
	}
	
	/** There is a type definition dependency whose implementation project is not listed among the dependencies (orphan definition). */
	@Test
	public def void testOrphanedTypeDefinitions() {
		val implementation = project(new N4JSProjectName("impl"));
		val orphanDefinition = definitionProject(new N4JSProjectName("def"), new N4JSProjectName("non-existent"));
		val regularDefinition = implementation.definitionProject;
		
		val client1 = project(new N4JSProjectName("client1"), #[implementation, orphanDefinition, regularDefinition]);
		val client2 = project(new N4JSProjectName("client2"), #[implementation, regularDefinition, orphanDefinition]);
		
		val orderedDependencies1 = client1.computeSemanticDependencies();
		val orderedDependencies2 = client2.computeSemanticDependencies();
		
		assertOrder("Orphan type definitions are also moved to the front.", 
			orderedDependencies1, #["def", "@n4jsd/impl", "impl"]);
		assertOrder("Orphan type definitions are also moved to the front.", 
			orderedDependencies2, #["@n4jsd/impl", "def", "impl"]);
	}
	
	/**
	 * There is a large number of implementation projects with and without type definitions and type definition
	 * projects with and without corresponding implementation project (orphan definitions).
	 * 
	 * The initial list of dependencies is shuffled with a fixed seed.
	 */
	@Test
	public def void testShuffledPermutations() {
		val pureImplProjects = (0..10).map[num | project(new N4JSProjectName("impl" + num))].toList;
		val implProjectsWithTypeDef = (0..20).map[num | project(new N4JSProjectName("impl-no-types-" + num))].toList;
		val typeDefProjectWithImpl = implProjectsWithTypeDef.map[p | p.definitionProject].toList;
		val orphanTypeDefProjects = (0..10).map[num | definitionProject(new N4JSProjectName("orphan-def" + num), new N4JSProjectName("non-existent-" + num))].toList;
		
		val dependencies = newArrayList();
		dependencies.addAll(pureImplProjects + implProjectsWithTypeDef + typeDefProjectWithImpl + orphanTypeDefProjects);
		// shuffle dependency order (with constant seed)
		Collections.shuffle(dependencies, new Random(821));
				
		val client = project(new N4JSProjectName("client"), dependencies);
		val orderedDependencies = client.computeSemanticDependencies();
		
		val orderRepresentation = orderedDependencies.join(" ");
		val problems = checkTypeDefinitionsOccurBeforeImplementationProjects(orderedDependencies);
		
		assertEquals("Expected all type definitions to be listed before their implementation projects." 
			+ orderRepresentation + ".", "", problems.join("\n"));
	}

	/**
	 * Checks that the given list of dependencies always list type definition projects before their implementation project.
	 * 
	 * Returns a list of problems or an empty list if the constraint was fulfilled.
	 */	
	private def List<String> checkTypeDefinitionsOccurBeforeImplementationProjects(Iterable<ProjectDependency> dependencies) {
		val encounteredTypeDefs = newHashSet;
		val encounteredImplProjectsById = newHashMap;
		
		val List<String> problems = new ArrayList();
		
		for (dependencyName : dependencies.map[projectName]) {
			val dependency = workspace.findProjectByName(dependencyName);
			if (dependency.projectDescription.projectType == ProjectType.DEFINITION) {
				if (encounteredImplProjectsById.containsKey(dependency.projectDescription.definesPackage)) {
					problems.add("Implementation project of type definition " + dependency.name + 
						" was listed before its definition.");
				}
				encounteredTypeDefs.add(dependency);
			} else {
				encounteredImplProjectsById.put(dependency.name, dependency);
			}
		}
		
		return problems;
	}
	
	/**
	 * Asserts that the given list of {@code dependencies} are in 
	 * the {@code expectedProjectNameOrder} in terms of their project IDs.
	 */
	private static def void assertOrder(String message, Iterable<ProjectDependency> dependencies, Iterable<String> expectedProjectNameOrder) {
		val actual = dependencies.map[d | d.projectName].join(" ");
		val expectation = expectedProjectNameOrder.join(" ");
		
		assertEquals(message, expectation, actual);
	}
	
	/**
	 * Returns with a new project (of type 'library') with the given projectName.
	 */
	private def N4JSProjectConfig project(N4JSProjectName projectName) {
		return project(projectName, Collections.<String>emptyList);
	}

	/**
	 * Returns with a new project (of type 'library') with the given projectName and list of dependencies.
	 */
	private def N4JSProjectConfig project(N4JSProjectName projectName, N4JSProjectConfig... dependencies) {
		return project(projectName, dependencies.map[name]);
	}
	
	/**
	 * Returns with a new project (of type 'library') with the given projectName and list of dependencies.
	 */
	private def N4JSProjectConfig project(N4JSProjectName projectName, String... dependencies) {
		val pd = newProjectDescription(projectName, null, dependencies);
		return workspace.registerProject(workspace.pathAsFileURI.appendPath(projectName.getRawName), pd);
	}
	
	/**
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * projectName of {@code implementationProject}.
	 * 
	 * The name of the type definition project is inferred from the {@code implementationProject} by appending the suffix {@code -n4jsd}. 
	 */
	private def N4JSProjectConfig definitionProject(N4JSProjectConfig implementationProject) {
		return definitionProject(new N4JSProjectName(N4JSGlobals.N4JSD_SCOPE, implementationProject.name), implementationProject);
	}
	
	/** 
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * projectName of {@code implementationProject}. 
	 */
	private def N4JSProjectConfig definitionProject(N4JSProjectName projectName, N4JSProjectConfig implementationProject) {
		return definitionProject(projectName, implementationProject.n4JSProjectName);
	}
	
	/** 
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * {@code definesPackage}. 
	 */
	private def N4JSProjectConfig definitionProject(N4JSProjectName projectName, N4JSProjectName definesPackage) {
		val pd = newProjectDescription(projectName, definesPackage, Collections.<String>emptyList());
		return workspace.registerProject(workspace.pathAsFileURI.appendPath(projectName.getRawName), pd);
	}

	private static def ProjectDescription newProjectDescription(N4JSProjectName projectName,
		N4JSProjectName definesPackage, String... dependencies) {

		val projectType = if (definesPackage !== null) {
			ProjectType.DEFINITION
		} else {
			ProjectType.LIBRARY
		};
		val pdb = ProjectDescription.builder()
			.setProjectName(projectName.rawName)
			.setProjectType(projectType)
			.setDefinesPackage(definesPackage?.rawName);
		for (depName : dependencies) {
			pdb.addProjectDependency(new ProjectDependency(depName, DependencyType.RUNTIME, "", null));
		}
		return pdb.build();
	}
}

class MockWorkspaceConfig extends N4JSWorkspaceConfig {

	new(ProjectDiscoveryHelper projectDiscoveryHelper, ProjectDescriptionLoader projectDescriptionLoader,
		ConfigSnapshotFactory configSnapshotFactory, UriExtensions uriExtensions) {

		super(URI.createFileURI(File.separator + "mock-workspace"),
			projectDiscoveryHelper, projectDescriptionLoader, configSnapshotFactory, uriExtensions);
	}

	override protected MockTypeDefinitionsProject createProjectConfig(FileURI path, ProjectDescription pd) {
		return new MockTypeDefinitionsProject(this, path, pd, projectDescriptionLoader);
	}
}

class MockTypeDefinitionsProject extends N4JSProjectConfig {

	new(MockWorkspaceConfig workspace, FileURI path, ProjectDescription pd, ProjectDescriptionLoader pdLoader) {
		super(workspace, path, pd, pdLoader);
	}
	
	public override String toString() {
		val StringBuilder builder = new StringBuilder();
		builder.append(getName() + " {");

		val definesPackage = getProjectDescription().getDefinesPackage();
		if (definesPackage !== null) {
			builder.append("definesPackage=" + definesPackage);
		}
		val deps = getDependencies();
		if (!deps.isEmpty()) {
			builder.append("dependencies=" + deps.toString());
		}

		builder.append("}");
		return builder.toString();
	}
}
