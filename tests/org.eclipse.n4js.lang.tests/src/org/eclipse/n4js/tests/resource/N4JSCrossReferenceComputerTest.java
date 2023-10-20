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

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.resource.N4JSCrossReferenceComputer;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.utils.TypesKeywordProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.IAcceptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4JSCrossReferenceComputerTest {
	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	private N4JSCrossReferenceComputer crossReferenceComputer;
	@Inject
	private TypesKeywordProvider typesKeywordProvider;

	URI resourceA;
	URI resourceB;
	URI resourceC;

	URI myClassOne;
	URI myClassTwo;
	URI myInterfaceFour;
	URI myRoleLikeInterface;
	URI myVariableTwo;

	URI resourceX;
	URI resourceY;

	@Test
	public void testCrossRefVarDeclGeneric() {
		ResourceSet rs = resourceSetProvider.get();
		resourceA = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/A.n4js"));
		resourceB = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/B.n4js"));
		resourceC = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/C.n4js"));

		rs.getResource(resourceA, true).getContents();
		rs.getResource(resourceB, true).getContents();
		rs.getResource(resourceC, true).getContents();

		EcoreUtil.resolveAll(rs);

		final HashSet<EObject> refs = new LinkedHashSet<>();

		crossReferenceComputer.computeCrossRefs(rs.getResource(resourceA, true), new IAcceptor<EObject>() {
			@Override
			public void accept(EObject t) {
				refs.add(t);
			}
		});

		String actualRefs = Strings.join(",", ref -> toStringRep(ref), refs);
		String expectedRefs = "class - C,class - Z";

		assertEquals("The list of found cross references is wrong", expectedRefs, actualRefs);
	}

	@Test
	public void testCrossRefTypes() {
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

		HashSet<EObject> refs = new LinkedHashSet<>();

		crossReferenceComputer.computeCrossRefs(rs.getResource(myClassOne, true), new IAcceptor<EObject>() {
			@Override
			public void accept(EObject t) {
				refs.add(t);
			}
		});

		String actualRefs = Strings.join(",", ref -> toStringRep(ref), refs);
		String expectedRefs = "variable - two,method - myMethodFour,method - getElement,method - myMethodTwo,field - myAttributeTwo";

		assertEquals("The list of found cross references is wrong", expectedRefs, actualRefs);
	}

	private String toStringRep(EObject eobj) {
		if (eobj instanceof IdentifiableElement) {
			return typesKeywordProvider.keyword(eobj) + " - " + ((IdentifiableElement) eobj).getName();
		} else {
			throw new RuntimeException(eobj + " is not an IdentifiableElement!");
		}
	}

	@Test
	public void testCrossRefComposedMember() {
		ResourceSet rs = resourceSetProvider.get();
		resourceX = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/X.n4js"));
		resourceY = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/Y.n4js"));

		rs.getResource(resourceX, true).getContents();
		rs.getResource(resourceY, true).getContents();

		EcoreUtil.resolveAll(rs);

		HashSet<EObject> refs = new LinkedHashSet<>();

		crossReferenceComputer.computeCrossRefs(rs.getResource(resourceY, true), new IAcceptor<EObject>() {
			@Override
			public void accept(EObject t) {
				refs.add(t);
			}
		});

		String actualRefs = Strings.join(",", ref -> toStringRep(ref), refs);
		String expectedRefs = "class - X1,class - X2,field - foo,field - foo";

		assertEquals("The list of found cross references is wrong", expectedRefs, actualRefs);
	}
}
