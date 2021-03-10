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
package org.eclipse.n4js.tests.typedefinitions

import com.google.common.collect.ImmutableList
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashSet
import java.util.List
import java.util.Random
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.internal.TypeDefinitionsAwareDependenciesSupplier
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfig
import org.eclipse.n4js.projectDescription.DependencyType
import org.eclipse.n4js.projectDescription.ProjectDependency
import org.eclipse.n4js.projectDescription.ProjectDescription
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.projectModel.names.N4JSProjectName
import org.eclipse.n4js.utils.URIUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit test of {@link TypeDefinitionsAwareDependenciesSupplier}. 
 * 
 * Uses a custom mock-implementation {@link MockTypeDefinitionsProject} of {@link IN4JSProject} to avoid a complicated test setup.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TypeDefinitionsAwareDependenciesSupplierTest extends Assert {
	
	/** There is a single implementation and definition project. The implementation project is listed first. */
	@Test
	public def void testSingleTypeDefinitionDependencyOrder1() {
		val implementation = project(new N4JSProjectName("impl"));
		val definition = implementation.definitionProject;
		val clientDependencies = #[implementation, definition];
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["impl-n4jsd", "impl"]);
	}
	
	/** There is a single implementation and definition project. The definition project is listed first. */
	@Test
	public def void testSingleTypeDefinitionDependencyOrder2() {
		val implementation = project(new N4JSProjectName("impl"));
		val definition = implementation.definitionProject;
		val clientDependencies = #[definition, implementation];
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["impl-n4jsd", "impl"]);
	}
	
	/** There is multiple distinct implementation and definition projects. */
	@Test
	public def void testMultipleTypeDefinitionDependencyOrder1() {
		val implementation1 = project(new N4JSProjectName("impl1"));
		val definition1 = implementation1.definitionProject;
		val implementation2 = project(new N4JSProjectName("impl2"));
		val definition2 = implementation2.definitionProject;
		
		val clientDependencies = #[definition1, implementation1, definition2, implementation2];
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		assertOrder("Definition projects are listed before implementation.", 
			orderedDependencies, #["impl1-n4jsd", "impl1", "impl2-n4jsd", "impl2"]);
	}
	
	/** There is a duplicate project ID among definition projects. */
	@Test
	public def void testDuplicateProjectsDependencyOrder() {
		val implementation1 = project(new N4JSProjectName("impl1"));
		val definition1 = definitionProject(new N4JSProjectName("impl-n4jsd"), implementation1);
		val implementation2 = project(new N4JSProjectName("impl2"));
		val definition2 = definitionProject(new N4JSProjectName("impl-n4jsd"), implementation2);
		
		val clientDependencies = #[definition1, implementation1, definition2, implementation2];
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		assertOrder("Duplicate type definition project IDs do not prevent the dependency order computation 
			from termination successfully.", orderedDependencies, 
			#["impl-n4jsd", "impl1", "impl-n4jsd", "impl2"]);
	}
	
	/** There is two type definition projects that both provide definitions for the same implementation project. */
	@Test
	public def void testOneToManyTypeDefinitionsMapping1() {
		val implementation = project(new N4JSProjectName("impl"));
		val def1 = definitionProject(new N4JSProjectName("def1"), implementation);
		val def2 = definitionProject(new N4JSProjectName("def2"), implementation);
		
		val clientDependencies = #[implementation, def1, def2];
		// notice order of 'def2', 'def1'
		val clientDependenciesReversed = #[implementation, def2, def1];
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		val orderedDependenciesReversed = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependenciesReversed);

		assertOrder("Two type definitions project with same definesPackage will be listed in order of 
				declaration before their implementation project.",
			orderedDependencies, #["def1", "def2", "impl"] );
			
		assertOrder("Two type definitions project with same definesPackage will be listed in order of 
				declaration before their implementation project.",
			orderedDependenciesReversed, #["def2", "def1", "impl"] );
	}
	
	/** There is a type definition dependency whose implementation project is not listed among the dependencies (orphan definition). */
	@Test
	public def void testOrphanedTypeDefinitions() {
		val implementation = project(new N4JSProjectName("impl"));
		val orphanDefinition = definitionProject(new N4JSProjectName("def"), new N4JSProjectName("non-existent"));
		val regularDefinition = implementation.definitionProject;
		
		val clientDependencies = #[implementation, orphanDefinition, regularDefinition];
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		
		assertOrder("Orphan type definitions are included at the end of the dependency list.", 
			orderedDependencies, #["impl-n4jsd", "impl", "def"]);
	}
	
	/**
	 * There is a large number of implementation projects with and without type definitions and type definition
	 * projects with and without corresponding implementation project (orphan definitions).
	 * 
	 * The initial list of dependencies is shuffled with a fixed seed.
	 */
	@Test
	public def void testShuffledPermutations() {
		val pureImplProjects = (0..10).map[num | project(new N4JSProjectName("impl" + num))];
		val implProjectsWithTypeDef = (0..20).map[num | project(new N4JSProjectName("impl-no-types-" + num))];
		val typeDefProjectWithImpl = implProjectsWithTypeDef.map[p | p.definitionProject];
		val orphanTypeDefProjects = (0..10).map[num | definitionProject(new N4JSProjectName("orphan-def" + num), new N4JSProjectName("non-existent-" + num))];
		
		val dependencies = newArrayList();
		dependencies.addAll(pureImplProjects + implProjectsWithTypeDef + typeDefProjectWithImpl + orphanTypeDefProjects);
		// shuffle dependency order (with constant seed)
		Collections.shuffle(dependencies, new Random(821));
				
		val clientDependencies = dependencies;
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(clientPath, clientDependencies);
		
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
	private static def List<String> checkTypeDefinitionsOccurBeforeImplementationProjects(Iterable<N4JSProjectConfig> dependencies) {
		val encounteredTypeDefs = newHashSet;
		val encounteredImplProjectsById = newHashMap;
		
		val List<String> problems = new ArrayList();
		
		for (dependency : dependencies) {
			if (dependency.type == ProjectType.DEFINITION) {
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
	private static def void assertOrder(String message, Iterable<N4JSProjectConfig> dependencies, Iterable<String> expectedProjectNameOrder) {
		val actual = dependencies.map[d | d.name].join(" ");
		val expectation = expectedProjectNameOrder.join(" ");
		
		assertEquals(message, expectation, actual);
	}
	
	/**
	 * Returns with a new project (of type 'library') with the given projectName and list of dependencies.
	 */
	private static def N4JSProjectConfig project(N4JSProjectName projectName, N4JSProjectConfig... dependencies) {
		return new MockTypeDefinitionsProject(projectName, dependencies);
	}
	
	/**
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * projectName of {@code implementationProject}.
	 * 
	 * The name of the type definition project is inferred from the {@code implementationProject} by appending the suffix {@code -n4jsd}. 
	 */
	private static def N4JSProjectConfig definitionProject(N4JSProjectConfig implementationProject) {
		return definitionProject(new N4JSProjectName(implementationProject.name + "-n4jsd"), implementationProject);
	}
	
	/** 
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * projectName of {@code implementationProject}. 
	 */
	private static def N4JSProjectConfig definitionProject(N4JSProjectName projectName, N4JSProjectConfig implementationProject) {
		return definitionProject(projectName, implementationProject.n4JSProjectName);
	}
	
	/** 
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * {@code definesPackage}. 
	 */
	private static def N4JSProjectConfig definitionProject(N4JSProjectName projectName, N4JSProjectName definesPackage) {
		return new MockTypeDefinitionsProject(projectName, definesPackage);
	}
	
	private static def FileURI clientPath() {
		new FileURI(URIUtils.toFileUri("/client.n4js"));
	}
}

/**
 * Mostly non-functional mock-implementation of {@link IN4JSProject}.
 *
 * Only methods directly overridden by this implementation can be used. Invoking other methods may result in
 * undefined behavior (or NPEs etc.).
 */
class MockTypeDefinitionsProject extends N4JSProjectConfig {
	private final N4JSProjectName definesPackage;
	private final ImmutableList<? extends N4JSProjectConfig> dependencies;

	new(N4JSProjectName projectName) {
		this(projectName, null as N4JSProjectName);
	}

	new(N4JSProjectName projectName, N4JSProjectConfig... dependencies) {
		this(projectName, null, dependencies);
	}
	
	new(N4JSProjectName projectName, N4JSProjectName definesPackage, N4JSProjectConfig... dependencies) {
		super(
			new N4JSWorkspaceConfig(null, null, null, null, null),
			newLocation(projectName),
			newProjectDescription(projectName, definesPackage, dependencies),
			null);
		this.definesPackage = definesPackage;
		this.dependencies = ImmutableList.copyOf(dependencies);
	}
	
	override getType() {
		if (this.definesPackage === null) {
			return ProjectType.LIBRARY;
		} else {
			return ProjectType.DEFINITION;
		}
	}

	override getDependencies() {
		return new LinkedHashSet(dependencies.map[name]);
	}

	public override String toString() {
		val StringBuilder builder = new StringBuilder();
		builder.append(getName() + " {");

		if (definesPackage !== null) {
			builder.append("definesPackage=" + this.definesPackage);
		}
		if (!dependencies.isEmpty()) {
			builder.append("dependencies=" + this.dependencies.toString());
		}

		builder.append("}");
		return builder.toString();
	}
	
	// counter that is used in synthesizing new project location URIs
	private static int containerCounter = 0;
	
	/**
	 * Returns a new unique location for a test project with the given project Id.
	 * 
	 * The returned location is guaranteed to be distinct from all other locations returned
	 * by this method (passing the same project ID twice, will yield two distinct locations). 
	 */
	private static def FileURI newLocation(N4JSProjectName projectName) {
		return new FileURI(URIUtils.toFileUri("/container" + containerCounter++ + "/" + projectName))
	}

	private static def ProjectDescription newProjectDescription(N4JSProjectName projectName,
		N4JSProjectName definesPackage, N4JSProjectConfig... dependencies) {

		val pdb = ProjectDescription.builder()
			.setProjectName(projectName.rawName)
			.setDefinesPackage(definesPackage.rawName);
		for (projectConfig : dependencies) {
			pdb.addProjectDependency(new ProjectDependency(projectConfig.name, DependencyType.RUNTIME, "", null));
		}
		return pdb.build();
	}
}
