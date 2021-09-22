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
package org.eclipse.n4js.tests.findReferences

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.ENamedElement
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.tooling.findReferences.SimpleResourceAccess
import org.eclipse.n4js.tooling.findReferences.TargetURIKey
import org.eclipse.xtext.findReferences.TargetURIs
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TargetURIKeyDataTest extends Assert implements IQualifiedNameProvider {

	@Inject TargetURIKey key
	@Inject Provider<TargetURIs> targetURISetProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider

	@Before
	def void setUp() {
		key.qualifiedNameProvider = this
	}

	@Test
	def void testCreateFromURIs() {
		val uris = #[
			URI.createURI('platform:/resource/myProject/src/A.js#A'),
			URI.createURI('platform:/resource/myProject/src/A.js#B'),
			URI.createURI('platform:/resource/myProject/src/B.js#A'),
			URI.createURI('platform:/resource/myProject/src/B.js#B'),
			URI.createURI('platform:/resource/myProject/src/B.js#C'),
			URI.createURI('platform:/resource/myProject/src/B.js#C') // intentional dup
		]
		val set = targetURISetProvider.get
		set.addAllURIs(uris);
		assertEquals(5, set.size)
		assertEquals(2, set.targetResourceURIs.size)
		assertEquals(2, set.getEObjectURIs(uris.get(0).trimFragment).size)
		assertEquals(3, set.getEObjectURIs(uris.get(2).trimFragment).size)

		val data = key.getData(set, new SimpleResourceAccess(resourceSetProvider.get))
		assertTrue(data.typesOrModulesToFind.empty)
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EOBJECT))
		assertTrue(data.isMatchingConcreteSyntax('dummy'))
	}

	@Test
	def void createAddEObject_01() {
		val c = EcoreFactory.eINSTANCE.createEClass => [ name = "MyEClass" ]
		val data = key.getData(targetURISetProvider.get, new SimpleResourceAccess(resourceSetProvider.get))
		data.add(c)
		assertFalse(data.isMatchingConcreteSyntax('Unexpected'))
		assertTrue(data.isMatchingConcreteSyntax('MyEClass'))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EOBJECT))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASSIFIER))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASS))
		assertFalse(data.isEReferenceTypeApplicable(EcorePackage.Literals.EATTRIBUTE))
	}

	@Test
	def void createAddEObject_02() {
		val c = EcoreFactory.eINSTANCE.createEClass => [ name = "MyEClass" ]
		val a = EcoreFactory.eINSTANCE.createEAttribute => [ name = "MyEAttribute"; c.EStructuralFeatures += it ]
		val data = key.getData(targetURISetProvider.get, new SimpleResourceAccess(resourceSetProvider.get))
		data.add(c)
		data.add(a)
		assertFalse(data.isMatchingConcreteSyntax('Unexpected'))
		assertTrue(data.isMatchingConcreteSyntax('MyEClass'))
		assertTrue(data.isMatchingConcreteSyntax('MyEAttribute'))
		assertTrue(data.isMatchingConcreteSyntax('MyEClass.MyEAttribute'))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EOBJECT))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASSIFIER))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASS))
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EATTRIBUTE))
		assertFalse(data.isEReferenceTypeApplicable(EcorePackage.Literals.EREFERENCE))
	}

	override getFullyQualifiedName(EObject obj) {
		if (obj instanceof ENamedElement) {
			val container = obj.eContainer
			if (container instanceof ENamedElement) {
				return container.fullyQualifiedName.append(obj.name)
			} else {
				return QualifiedName.create(obj.name)
			}
		} else
			throw new IllegalArgumentException(obj.toString)
	}

	override apply(EObject input) {
		return input.fullyQualifiedName
	}

}
