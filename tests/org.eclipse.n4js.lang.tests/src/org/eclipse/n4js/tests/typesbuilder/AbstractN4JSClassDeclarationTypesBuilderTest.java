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
package org.eclipse.n4js.tests.typesbuilder;

import static java.util.Collections.emptyMap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.tests.typesbuilder.utils.OrderedEmfFormatter;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractN4JSClassDeclarationTypesBuilderTest {
	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;
	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	protected void executeTest(String testFileName,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs,
			int expectedExportedElementsCount) {

		try {

			XtextResourceSet rs = resourceSetProvider.get();

			// load original resource
			Resource testResource = rs.createResource(URI.createURI("src/" + getPath() + "/" + testFileName));
			testResource.load(emptyMap());

			executeUnloadLoadFromDescriptionsAndCompleteLoadTest(0, 1, testResource,
					expectedTypeToNamePairs, expectedExportedElementsCount);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void executeUnloadLoadFromDescriptionsAndCompleteLoadTest(int index, int max, Resource testResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs,
			int expectedExportedElementsCount) throws Exception {

		assertTrue(testResource.getURI().trimFileExtension().lastSegment() + " should have no errors but: "
				+ testResource.getErrors().toString(), testResource.getErrors().isEmpty());
		int expectedTestContentCount = 1 + expectedTypeToNamePairs.size();// 1 AST + 2 type roots
		assertEquals("test content count", 2, testResource.getContents().size());

		TModule exportedScript = (TModule) IterableExtensions.last(testResource.getContents());
		assertEquals(expectedExportedElementsCount,
				exportedScript.getTypes().size() + exportedScript.getExportedVariables().size());

		assertExpectedTypes(testResource, expectedTypeToNamePairs);
		EcoreUtil.resolveAll(testResource);
		assertTrue(testResource.getErrors().toString(), testResource.getErrors().isEmpty());
		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(testResource);
		assertFalse("Test that the index has been filled", isEmpty(resourceDescriptions.getAllResourceDescriptions()));

		Iterable<IEObjectDescription> eoDescs = flatten(map(filter(resourceDescriptions.getAllResourceDescriptions(),
				rd -> Objects.equals(rd.getURI().fileExtension(), "n4js")), rd -> rd.getExportedObjects()));
		assertEquals("count of elements are marked as exported should equal to EResourceDescriptions",
				expectedTestContentCount, size(eoDescs));

		assertExampleJSStructure(1 + (3 * index) + ". step: complete load", testResource);

		assertExampleTypeStructure(1 + (3 * index) + ". step: complete load", testResource);

		IEObjectDescription syntaxEoDesc = head(eoDescs);
		XMIResourceImpl fromUserData = new XMIResourceImpl();
		fromUserData.load(new StringInputStream(
				syntaxEoDesc.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT), "UTF-8"), null);
		compareUserData(syntaxEoDesc, fromUserData);

		URI uri = testResource.getURI();
		ResourceSet resourceSet = testResource.getResourceSet();
		IResourceDescriptions updatedResourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(resourceSet);
		IResourceDescription updatedResourceDescription = updatedResourceDescriptions.getResourceDescription(uri);

		testResource.unload();
		resourceSet.getResources().remove(testResource);

		XtextResourceSet rs2 = resourceSetProvider.get();
		N4JSResource newN4jsResource = (N4JSResource) rs2.createResource(uri);

		newN4jsResource.loadFromDescription(updatedResourceDescription);

		assertExampleTypeStructure(2 + (3 * index) + ". step: load from resource", newN4jsResource);

		assertTrue(2 + (3 * index) + ". step: load from resource - AST is proxy",
				((BasicEList<? extends EObject>) newN4jsResource.getContents()).basicGet(0).eIsProxy());

		// triggers resolve of AST
		assertFalse(2 + (3 * index) + ". step: load from resource - AST no proxy after first access",
				newN4jsResource.getContents().get(0).eIsProxy());

		EObject astElement = ((SyntaxRelatedTElement) newN4jsResource.getContents().get(1)).getAstElement();
		assertFalse(2 + (3 * index) + ". step: load from resource - AST element no proxy when fetched from type",
				astElement.eIsProxy());

		// rerun unload-load process until index > max
		if (index <= max) {
			executeUnloadLoadFromDescriptionsAndCompleteLoadTest(index + 1, max, newN4jsResource,
					expectedTypeToNamePairs, expectedExportedElementsCount);
		}
	}

	private void compareUserData(IEObjectDescription syntaxEoDesc, XMIResourceImpl fromUserData) {
		assertEquals("Stored user data " + syntaxEoDesc, getExpectedTypesSerialization().toString(),
				OrderedEmfFormatter.objToStr(head(fromUserData.getContents())));
	}

	abstract CharSequence getExpectedTypesSerialization();

	void assertExpectedTypes(Resource testResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs) {
		EList<Type> types = ((TModule) last(testResource.getContents())).getTypes();
		EList<TVariable> variables = ((TModule) testResource.getContents()).getExportedVariables();
		List<IdentifiableElement> exported = new ArrayList<>();
		exported.addAll(types);
		exported.addAll(variables);
		assertEquals("expectedTypes count", expectedTypeToNamePairs.size(), exported.size());

		// val instanceCheck = [
		// int index, Pair<? extends Class<? extends EObject>,String> pair |
		//
		// IdentifiableElement element = exported.get(index);
		// Class<? extends IdentifiableElement> elementType = element.getClass();
		// boolean assignable = pair.key.isAssignableFrom(elementType);
		// if(!assignable) {
		// println(pair.key + " is not instanceof " + element);
		// } else {
		// return switch(element) {
		// Type: element.name == pair.value
		// TVariable: element.name == pair.value
		// }
		// }
		// assertTrue("Expecting "+pair.key+" with name "+pair.value+" at position "+index+" but was "+element,
		// assignable);
		//
		// ];

		for (int i = 0; i < expectedTypeToNamePairs.size(); i++) {
			Pair<? extends Class<? extends EObject>, String> pair = expectedTypeToNamePairs.get(i);
			// instanceCheck.apply(i, pair);

			IdentifiableElement element = exported.get(i);
			Class<? extends IdentifiableElement> elementType = element.getClass();
			boolean assignable = pair.getKey().isAssignableFrom(elementType);
			if (!assignable) {
				System.out.println(pair.getKey() + " is not instanceof " + element);
			} else {
				// switch(element) {
				// Type: element.name == pair.value
				// TVariable: element.name == pair.value
				// }
			}
			assertTrue(
					"Expecting " + pair.getKey() + " with name " + pair.getValue() + " at position " + i + " but was "
							+ element,
					assignable);

		}
	}

	abstract void assertExampleTypeStructure(String phase, Resource resource);

	abstract void assertExampleJSStructure(String phase, Resource resource);

	private String getPath() {
		return AbstractN4JSClassDeclarationTypesBuilderTest.class.getPackage().getName().replaceAll("\\.", "/");
	}
}
