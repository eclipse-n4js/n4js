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
package org.eclipse.n4js.tests.resource;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ImportedNamesTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	URI myClassOne;
	URI myClassTwo;
	URI myInterfaceFour;
	URI myRoleLikeInterface;
	URI myVariableTwo;

	URI resourceA;
	URI resourceB;
	URI resourceC;

	URI resourceXURI;
	URI resourceYURI;

	@Test
	public void testImportedNamesVarDeclGeneric() {
		ResourceSet rs = resourceSetProvider.get();
		resourceA = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/A.n4js"));
		resourceB = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/B.n4js"));
		resourceC = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/C.n4js"));

		rs.getResource(resourceA, true).getContents();
		rs.getResource(resourceB, true).getContents();
		rs.getResource(resourceC, true).getContents();

		EcoreUtil.resolveAll(rs);

		Set<String> expectedImportedNames = Set.of("org.eclipse.n4js.tests.resource.C",
				"org.eclipse.n4js.tests.resource.A",
				"org.eclipse.n4js.tests.resource.C.!.C",
				"org.eclipse.n4js.tests.resource.C.!.Z",
				"#.!.org.eclipse.n4js.tests.resource.C");
		N4JSResource resourceA2 = (N4JSResource) rs.getResource(resourceA, true);
		Set<String> actualImportedNames = toSet(map(getImportedNames(resourceA2), Object::toString));
		assertEquals("The list of imported names is wrong", expectedImportedNames, actualImportedNames);
	}

	@Test
	public void testImportedNamesFunctionReturnType() {
		ResourceSet rs = resourceSetProvider.get();
		myClassOne = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyClassOne.n4js"));
		myClassTwo = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyClassTwo.n4js"));
		myInterfaceFour = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyInterfaceFour.n4js"));
		myRoleLikeInterface = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyRoleLikeInterface.n4js"));
		myVariableTwo = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyVariableTwo.n4js"));

		rs.getResource(myClassOne, true).getContents();
		rs.getResource(myClassTwo, true).getContents();
		rs.getResource(myInterfaceFour, true).getContents();
		rs.getResource(myRoleLikeInterface, true).getContents();
		rs.getResource(myVariableTwo, true).getContents();

		EcoreUtil.resolveAll(rs);

		Set<String> expectedImportedNames = Set.of(
				"#.!.!POLY.Object",
				"#.!.Object",
				"#.!.org.eclipse.n4js.tests.resource.MyVariableTwo",
				"#.!.two",
				"#.!.void",
				"org.eclipse.n4js.tests.resource.MyClassOne",
				"org.eclipse.n4js.tests.resource.MyClassTwo.!.MyClassTwo",
				"org.eclipse.n4js.tests.resource.MyInterfaceFour.!.MyInterfaceFour",
				"org.eclipse.n4js.tests.resource.MyRoleLikeInterface.!.MyRoleLikeInterface",
				"org.eclipse.n4js.tests.resource.MyVariableTwo",
				"void");
		N4JSResource myClassOneResource = (N4JSResource) rs.getResource(myClassOne, true);
		Set<String> actualImportedNames = toSet(map(getImportedNames(myClassOneResource), Object::toString));
		assertEquals("The list of imported names is wrong", expectedImportedNames, actualImportedNames);
	}

	@Test
	public void testImportedNamesComposedTypes() {
		ResourceSet rs = resourceSetProvider.get();
		resourceXURI = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/X.n4js"));
		resourceYURI = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/Y.n4js"));

		rs.getResource(resourceXURI, true).getContents();
		rs.getResource(resourceYURI, true).getContents();
		EcoreUtil.resolveAll(rs);

		N4JSResource resourceX = (N4JSResource) rs.getResource(resourceYURI, true);

		Set<String> expectedImportedNames = Set.of("#.!.org.eclipse.n4js.tests.resource.X",
				"org.eclipse.n4js.tests.resource.X.!.J",
				"org.eclipse.n4js.tests.resource.X.!.I",
				"org.eclipse.n4js.tests.resource.X.!.X1",
				"org.eclipse.n4js.tests.resource.X.!.X2",
				"org.eclipse.n4js.tests.resource.Y",
				"org.eclipse.n4js.tests.resource.X");

		Set<String> actualImportedNames = toSet(map(getImportedNames(resourceX), Object::toString));
		assertEquals("The list of imported names is wrong", expectedImportedNames, actualImportedNames);
	}

	private Iterable<QualifiedName> getImportedNames(N4JSResource resource) {
		IResourceDescriptions allDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		IResourceDescription result = allDescriptions.getResourceDescription(resource.getURI());
		return result.getImportedNames();
	}
}
