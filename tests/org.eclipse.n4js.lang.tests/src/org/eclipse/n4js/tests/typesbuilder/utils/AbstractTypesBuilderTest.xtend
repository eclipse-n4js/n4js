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
package org.eclipse.n4js.tests.typesbuilder.utils

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.tests.typesbuilder.extensions.N4JSResourceExtensions
import org.eclipse.n4js.tests.typesbuilder.extensions.ResourceAssertionsExtensions
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesAssertionsExtensions
import org.eclipse.n4js.tests.typesbuilder.extensions.UserDataAssertions
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public abstract class AbstractTypesBuilderTest {

	static val char dot = '.'
	static val char slash = '/'

	@Inject
	private extension TypesAssertionsExtensions;

	@Inject
	protected extension ResourceAssertionsExtensions;

	@Inject
	protected extension N4JSResourceExtensions;

	@Inject
	private extension UserDataAssertions;

	@Inject protected Provider<XtextResourceSet> resourceSetProvider;

	def abstract protected boolean enableUserDataCompare();

	def protected executeTest(String testFileName,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs,
			int expectedTypesCount, int expectedTypesOnIndexCount) {
		var rs = resourceSetProvider.get();

		// load original resource
		val testResource = rs.createResource(rs.URIConverter.normalize(URI.createURI("src/" + path + "/" + testFileName)))
		testResource.load(emptyMap)

		executeUnloadLoadFromDescriptionsAndCompleteLoadTest(0, 1, testResource,
			expectedExportedTypeToNamePairs, expectedTypesCount, expectedTypesOnIndexCount)
	}


	def protected getPath() {
		this.class.package.name.replace(dot, slash)
	}

	def protected void executeUnloadLoadFromDescriptionsAndCompleteLoadTest(int index, int max, Resource testResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs,
			int expectedTypesCount, int expectedTypesOnIndexCount) {

		val updatedResourceDescription = assertFullyLoadedResource(index, testResource, expectedTypesCount, expectedExportedTypeToNamePairs)
		
		val newN4jsResource = unloadResourceAndReloadFromDescription(index, testResource, updatedResourceDescription)
		
		resolveASTandAssert(index, newN4jsResource)
		
		rerunUnloadLoadProcess(index, max, newN4jsResource, expectedExportedTypeToNamePairs, expectedTypesCount, expectedTypesOnIndexCount)
	}

	def private assertFullyLoadedResource(int index, Resource testResource, int expectedTypesCount,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs) {

		var phase = 1 + (3 * index) + ". step: complete load"

		assertResourceHasNoErrors(phase, testResource)

		assertExpectedTypesCount(phase, testResource, expectedTypesCount)

		resolve(testResource)

		val resourceDescriptions = assertIndexHasBeenFilled(phase, testResource);
		

		val eoDescs = assertAllExportedElementAreOnIndex(phase, resourceDescriptions, expectedExportedTypeToNamePairs)
		
		assertExampleJSStructure(phase, testResource)

		assertExampleTypeStructure(phase, testResource)

		assertSerializedUserData(eoDescs, expectedTypesSerialization, enableUserDataCompare, testResource as N4JSResource)

		return getResourceDescription(testResource)
	}

	/**
	 * Unloads given resource and returns newly from description loaded resource.
	 */
	def private N4JSResource unloadResourceAndReloadFromDescription(int index, Resource testResource, IResourceDescription updatedResourceDescription) {
		val phase = 2 + (3 * index) + ". step: load from resource description"
		assertUserDataCreated(phase + ", before unload, by resource", testResource)
		unloadResource(testResource)
		assertUserDataCreated(phase + ", after unload, by descriptions", updatedResourceDescription)
		val newN4jsResource = reloadResourceFromDescription(testResource, updatedResourceDescription)
		
		assertExampleTypeStructure(phase, newN4jsResource)
		assertASTIsProxifed(phase, newN4jsResource)
		return newN4jsResource;
	}

	def private resolveASTandAssert(int index, N4JSResource newN4jsResource) {

		val phase = 3 + (3 * index) + ". step: resolve AST of proxified resource"

		resolveAST(phase, newN4jsResource)
		assertUserDataCreated(phase + ", after resolving AST", newN4jsResource)
		
		assertASTResolved(phase, newN4jsResource)
	}

	private def rerunUnloadLoadProcess(int index, int max, N4JSResource newN4jsResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs,
			int expectedExportedElementsCount, int expectedTypesOnIndexCount) {
		if(index <= max) {
			executeUnloadLoadFromDescriptionsAndCompleteLoadTest(index+1, max, newN4jsResource,
				expectedTypeToNamePairs, expectedExportedElementsCount, expectedTypesOnIndexCount)
		}
	}

	def abstract CharSequence getExpectedTypesSerialization();

	def abstract void assertExampleTypeStructure(String phase, Resource resource);

	def abstract void assertExampleJSStructure(String phase, Resource resource);

	/**
	 * Returns the common prefix (package) for qualified names. As the test files are usually contained in the same folder as the test class,
	 * the prefix is similar to the Java test's package name. Subclasses may override to adjust the prefix. The prefix must end with a dot if not empty.
	 */
	def getQualifiedNamePrefix() {
		this.class.package.name.replace(".", N4JSQualifiedNameConverter.DELIMITER) + N4JSQualifiedNameConverter.DELIMITER;
	}
}
