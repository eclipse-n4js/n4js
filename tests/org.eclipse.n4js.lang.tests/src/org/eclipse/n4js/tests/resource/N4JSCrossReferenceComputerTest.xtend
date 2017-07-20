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
package org.eclipse.n4js.tests.resource

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.ArrayList
import org.apache.commons.lang3.tuple.ImmutablePair
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.resource.N4JSCrossReferenceComputer
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.validation.TypesKeywordProvider
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.util.IAcceptor
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N4JSCrossReferenceComputerTest {
	@Inject 
	private Provider<XtextResourceSet> resourceSetProvider
	@Inject
	private N4JSCrossReferenceComputer crossReferenceComputer;
	@Inject
	private TypesKeywordProvider typesKeywordProvider

	URI resourceA
	URI resourceB
	URI resourceC

	URI myClassOne
	URI myClassTwo
	URI myInterfaceFour
	URI myRoleLikeInterface
	URI myVariableTwo

	@Test
	def void testCrossRefVarDeclGeneric() {
		var rs = resourceSetProvider.get();
		resourceA = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/A.n4js"))
		resourceB = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/B.n4js"))
		resourceC = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/C.n4js"))

		rs.getResource(resourceA, true).contents
		rs.getResource(resourceB, true).contents
		rs.getResource(resourceC, true).contents

		EcoreUtil.resolveAll(rs)

		crossReferenceComputer.computeCrossRefs(rs.getResource(resourceA, true), new IAcceptor<ImmutablePair<EObject, EObject>> {
			override accept(ImmutablePair<EObject, EObject> pair) {
				val t = pair.right
				println("Found " + t)
			}
		});
	}

	@Test
	def void testCrossRefTypes() {
		var rs = resourceSetProvider.get();
		myClassOne = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyClassOne.n4js"))
		myClassTwo = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyClassTwo.n4js"))
		myInterfaceFour = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyInterfaceFour.n4js"))
		myRoleLikeInterface = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyRoleLikeInterface.n4js"))
		myVariableTwo = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyVariableTwo.n4js"))

		rs.getResource(myClassOne, true).contents
		rs.getResource(myClassTwo, true).contents
		rs.getResource(myInterfaceFour, true).contents
		rs.getResource(myRoleLikeInterface, true).contents
		rs.getResource(myVariableTwo, true).contents

		EcoreUtil.resolveAll(rs)

		val refs = new ArrayList<String>();
		
		crossReferenceComputer.computeCrossRefs(rs.getResource(myClassOne, true), new IAcceptor<ImmutablePair<EObject, EObject>> {
			override accept(ImmutablePair<EObject, EObject> pair) {
				val t = pair.right
				if (t instanceof IdentifiableElement) {
					refs.add(typesKeywordProvider.keyword(t) + " - " + t.name);
				} else {
					throw new Exception(t + " is not identifiable element");
				}
			}
		});

		val actualRefs = refs.toSet.join(",");
		val expectedRefs = "variable - two,method - myMethodFour,method - getElement,method - myMethodTwo,field - myAttributeTwo"
		assertEquals("The list of found cross references is wrong", expectedRefs, actualRefs)
	}
}
