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
package org.eclipse.n4js.tests.typesbuilder.utils;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.typesbuilder.extensions.N4JSResourceExtensions;
import org.eclipse.n4js.tests.typesbuilder.extensions.ResourceAssertionsExtensions;
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesAssertionsExtensions;
import org.eclipse.n4js.tests.typesbuilder.extensions.UserDataAssertions;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractTypesBuilderTest {

	static char dot = '.';
	static char slash = '/';

	@Inject
	private TypesAssertionsExtensions typesX;

	@Inject
	protected ResourceAssertionsExtensions resX;

	@Inject
	protected N4JSResourceExtensions n4resX;

	@Inject
	private UserDataAssertions userX;

	@Inject
	protected Provider<XtextResourceSet> resourceSetProvider;

	abstract protected boolean enableUserDataCompare();

	protected void executeTest(String testFileName,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs,
			int expectedTypesCount, int expectedTypesOnIndexCount) {

		XtextResourceSet rs = resourceSetProvider.get();

		URI uri = URI.createURI("src/" + getPath() + "/" + testFileName);
		// load original resource
		Resource testResource = rs.createResource(rs.getURIConverter().normalize(uri));
		try {
			testResource.load(Collections.emptyMap());

			executeUnloadLoadFromDescriptionsAndCompleteLoadTest(0, 1, testResource,
					expectedExportedTypeToNamePairs, expectedTypesCount, expectedTypesOnIndexCount);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	protected String getPath() {
		return this.getClass().getPackage().getName().replace(dot, slash);
	}

	protected void executeUnloadLoadFromDescriptionsAndCompleteLoadTest(int index, int max, Resource testResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs,
			int expectedTypesCount, int expectedTypesOnIndexCount) {

		IResourceDescription updatedResourceDescription = assertFullyLoadedResource(index, testResource,
				expectedTypesCount, expectedExportedTypeToNamePairs);

		N4JSResource newN4jsResource = unloadResourceAndReloadFromDescription(index, testResource,
				updatedResourceDescription);

		resolveASTandAssert(index, newN4jsResource);

		rerunUnloadLoadProcess(index, max, newN4jsResource, expectedExportedTypeToNamePairs, expectedTypesCount,
				expectedTypesOnIndexCount);
	}

	private IResourceDescription assertFullyLoadedResource(int index, Resource testResource, int expectedTypesCount,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs) {

		var phase = 1 + (3 * index) + ". step: complete load";

		resX.assertResourceHasNoErrors(phase, testResource);

		typesX.assertExpectedTypesCount(phase, testResource, expectedTypesCount);

		n4resX.resolve(testResource);

		IResourceDescriptions resourceDescriptions = typesX.assertIndexHasBeenFilled(phase, testResource);

		Iterable<IEObjectDescription> eoDescs = typesX.assertAllExportedElementAreOnIndex(phase, resourceDescriptions,
				expectedExportedTypeToNamePairs);

		assertExampleJSStructure(phase, testResource);

		assertExampleTypeStructure(phase, testResource);

		userX.assertSerializedUserData(eoDescs, getExpectedTypesSerialization(), enableUserDataCompare(),
				(N4JSResource) testResource);

		return n4resX.getResourceDescription(testResource);
	}

	/**
	 * Unloads given resource and returns newly from description loaded resource.
	 */
	private N4JSResource unloadResourceAndReloadFromDescription(int index, Resource testResource,
			IResourceDescription updatedResourceDescription) {
		String phase = 2 + (3 * index) + ". step: load from resource description";
		typesX.assertUserDataCreated(phase + ", before unload, by resource", testResource);
		n4resX.unloadResource(testResource);
		typesX.assertUserDataCreated(phase + ", after unload, by descriptions", updatedResourceDescription);
		N4JSResource newN4jsResource = n4resX.reloadResourceFromDescription(testResource, updatedResourceDescription);

		assertExampleTypeStructure(phase, newN4jsResource);
		resX.assertASTIsProxifed(phase, newN4jsResource);
		return newN4jsResource;
	}

	private void resolveASTandAssert(int index, N4JSResource newN4jsResource) {

		String phase = 3 + (3 * index) + ". step: resolve AST of proxified resource";

		resX.resolveAST(phase, newN4jsResource);
		typesX.assertUserDataCreated(phase + ", after resolving AST", newN4jsResource);

		resX.assertASTResolved(phase, newN4jsResource);
	}

	private void rerunUnloadLoadProcess(int index, int max, N4JSResource newN4jsResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs,
			int expectedExportedElementsCount, int expectedTypesOnIndexCount) {

		if (index <= max) {
			executeUnloadLoadFromDescriptionsAndCompleteLoadTest(index + 1, max, newN4jsResource,
					expectedTypeToNamePairs, expectedExportedElementsCount, expectedTypesOnIndexCount);
		}
	}

	abstract public CharSequence getExpectedTypesSerialization();

	abstract public void assertExampleTypeStructure(String phase, Resource resource);

	abstract public void assertExampleJSStructure(String phase, Resource resource);

	/**
	 * Returns the common prefix (package) for qualified names. As the test files are usually contained in the same
	 * folder as the test class, the prefix is similar to the Java test's package name. Subclasses may override to
	 * adjust the prefix. The prefix must end with a dot if not empty.
	 */
	public String getQualifiedNamePrefix() {
		return this.getClass().getPackage().getName().replace(".", N4JSQualifiedNameConverter.DELIMITER)
				+ N4JSQualifiedNameConverter.DELIMITER;
	}
}
