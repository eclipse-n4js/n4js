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
import java.util.List
import java.util.Random
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.internal.N4JSProject
import org.eclipse.n4js.internal.TypeDefinitionsAwareDependenciesSupplier
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSProject
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
		val implementation = project("impl");
		val definition = implementation.definitionProject;
		val client = project("client", #[implementation, definition]);
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["impl-n4jsd", "impl"]);
	}
	
	/** There is a single implementation and definition project. The definition project is listed first. */
	@Test
	public def void testSingleTypeDefinitionDependencyOrder2() {
		val implementation = project("impl");
		val definition = implementation.definitionProject;
		val client = project("client", #[definition, implementation]);
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		assertOrder("Definition project is listed before implementation.", orderedDependencies, #["impl-n4jsd", "impl"]);
	}
	
	/** There is multiple distinct implementation and definition projects. */
	@Test
	public def void testMultipleTypeDefinitionDependencyOrder1() {
		val implementation1 = project("impl1");
		val definition1 = implementation1.definitionProject;
		val implementation2 = project("impl2");
		val definition2 = implementation2.definitionProject;
		
		val client = project("client", #[definition1, implementation1, definition2, implementation2]);
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		assertOrder("Definition projects are listed before implementation.", 
			orderedDependencies, #["impl1-n4jsd", "impl1", "impl2-n4jsd", "impl2"]);
	}
	
	/** There is a duplicate project ID among definition projects. */
	@Test
	public def void testDuplicateProjectsDependencyOrder() {
		val implementation1 = project("impl1");
		val definition1 = definitionProject("impl-n4jsd", implementation1);
		val implementation2 = project("impl2");
		val definition2 = definitionProject("impl-n4jsd", implementation2);
		
		val client = project("client", #[definition1, implementation1, definition2, implementation2]);
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		assertOrder("Duplicate type definition project IDs do not prevent the dependency order computation 
			from termination successfully.", orderedDependencies, 
			#["impl-n4jsd", "impl1", "impl-n4jsd", "impl2"]);
	}
	
	/** There is two type definition projects that both provide definitions for the same implementation project. */
	@Test
	public def void testOneToManyTypeDefinitionsMapping1() {
		val implementation = project("impl");
		val def1 = definitionProject("def1", implementation);
		val def2 = definitionProject("def2", implementation);
		
		val client = project("client", #[implementation, def1, def2]);		
		// notice order of 'def2', 'def1'
		val clientReversed = project("client", #[implementation, def2, def1]);
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		val orderedDependenciesReversed = TypeDefinitionsAwareDependenciesSupplier.get(clientReversed);

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
		val implementation = project("impl");
		val orphanDefinition = definitionProject("def", "non-existent");
		val regularDefinition = implementation.definitionProject;
		
		val client = project("client", #[implementation, orphanDefinition, regularDefinition]);
		
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		
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
		val pureImplProjects = (0..10).map[num | project("impl" + num)];
		val implProjectsWithTypeDef = (0..20).map[num | project("impl-no-types-" + num)];
		val typeDefProjectWithImpl = implProjectsWithTypeDef.map[p | p.definitionProject];
		val orphanTypeDefProjects = (0..10).map[num | definitionProject("orphan-def" + num, "non-existent-" + num)];
		
		val dependencies = newArrayList();
		dependencies.addAll(pureImplProjects + implProjectsWithTypeDef + typeDefProjectWithImpl + orphanTypeDefProjects);
		// shuffle dependency order (with constant seed)
		Collections.shuffle(dependencies, new Random(821));
				
		val client = project("client", dependencies);
		val orderedDependencies = TypeDefinitionsAwareDependenciesSupplier.get(client);
		
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
	private static def List<String> checkTypeDefinitionsOccurBeforeImplementationProjects(Iterable<IN4JSProject> dependencies) {
		val encounteredTypeDefs = newHashSet;
		val encounteredImplProjectsById = newHashMap;
		
		val List<String> problems = new ArrayList();
		
		for (dependency : dependencies) {
			if (dependency.projectType == ProjectType.DEFINITION) {
				if (encounteredImplProjectsById.containsKey(dependency.definesPackageName)) {
					problems.add("Implementation project of type definition " + dependency.projectId + 
						" was listed before its definition.");
				}
				encounteredTypeDefs.add(dependency);
			} else {
				encounteredImplProjectsById.put(dependency.projectId, dependency);
			}
		}
		
		return problems;
	}
	
	/**
	 * Asserts that the given list of {@code dependencies} are in 
	 * the {@code expectedProjectIdOrder} in terms of their project IDs.
	 */
	private static def void assertOrder(String message, Iterable<IN4JSProject> dependencies, Iterable<String> expectedProjectIdOrder) {
		val actual = dependencies.map[d | d.projectId].join(" ");
		val expectation = expectedProjectIdOrder.join(" ");
		
		assertEquals(message, expectation, actual);
	}
	
	/**
	 * Returns with a new project (of type 'library') with the given projectId and list of dependencies.
	 */
	private static def IN4JSProject project(String projectId, IN4JSProject... dependencies) {
		return new MockTypeDefinitionsProject(projectId, dependencies);
	}
	
	/**
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * projectId of {@code implementationProject}.
	 * 
	 * The name of the type definition project is inferred from the {@code implementationProject} by appending the suffix {@code -n4jsd}. 
	 */
	private static def IN4JSProject definitionProject(IN4JSProject implementationProject) {
		return definitionProject(implementationProject.projectId + "-n4jsd", implementationProject);
	}
	
	/** 
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * projectId of {@code implementationProject}. 
	 */
	private static def IN4JSProject definitionProject(String projectId, IN4JSProject implementationProject) {
		return definitionProject(projectId, implementationProject.projectId);
	}
	
	/** 
	 * Returns with a new definition project (of type 'definition') whose "definesPackage" property is set to the 
	 * {@code definesPackage}. 
	 */
	private static def IN4JSProject definitionProject(String projectId, String definesPackage) {
		return new MockTypeDefinitionsProject(projectId, definesPackage);
	}
}

/**
 * Mostly non-functional mock-implementation of {@link IN4JSProject}.
 *
 * Only methods directly overridden by this implementation can be used. Invoking other methods may result in
 * undefined behavior (or NPEs etc.).
 */
class MockTypeDefinitionsProject extends N4JSProject {
	private final String definesPackage;
	private final ImmutableList<? extends IN4JSProject> dependencies;

	new(String projectId) {
		super(newLocation(projectId), false, null);
		this.definesPackage = null;
		this.dependencies = ImmutableList.of();
	}

	new(String projectId, String definesPackage) {
		super(newLocation(projectId), false, null);
		this.definesPackage = definesPackage;
		this.dependencies = ImmutableList.of();
	}

	new(String projectId, IN4JSProject... dependencies) {
		super(newLocation(projectId), false, null);
		this.definesPackage = null;
		this.dependencies = ImmutableList.copyOf(dependencies);
	}
	
	override getProjectType() {
		if (this.definesPackage === null) {
			return ProjectType.LIBRARY;
		} else {
			return ProjectType.DEFINITION;
		}
	}

	public override ImmutableList<? extends IN4JSProject> getDependencies() {
		return this.dependencies;
	}

	public override String getDefinesPackageName() {
		return this.definesPackage;
	}

	public override String toString() {
		val StringBuilder builder = new StringBuilder();
		builder.append(getProjectId() + " {");

		if (definesPackage !== null) {
			builder.append("definesPackage=" + this.definesPackage);
		}
		if (!dependencies.isEmpty()) {
			builder.append("dependencies=" + this.dependencies.toString());
		}

		builder.append("}");
		return builder.toString();
	}
	
	/** Override to catch some mock-cases more gracefully. */
	override protected getModel() {
		throw new UnsupportedOperationException(MockTypeDefinitionsProject.simpleName + " is not backed by an N4JSModel.");
	}
	
	// counter that is used in synthesizing new project location URIs
	private static int containerCounter = 0;
	
	/**
	 * Returns a new unique location for a test project with the given project Id.
	 * 
	 * The returned location is guaranteed to be distinct from all other locations returned
	 * by this method (passing the same project ID twice, will yield two distinct locations). 
	 */
	private static def URI newLocation(String projectId) {
		return URI.createFileURI("container" + containerCounter++ + "/" + projectId
		)
	}
}
