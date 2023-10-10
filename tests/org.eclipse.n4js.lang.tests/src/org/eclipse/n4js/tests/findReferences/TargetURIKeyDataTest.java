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
package org.eclipse.n4js.tests.findReferences;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.tooling.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.tooling.findReferences.TargetURIKey;
import org.eclipse.n4js.tooling.findReferences.TargetURIKey.Data;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TargetURIKeyDataTest extends Assert implements IQualifiedNameProvider {

	@Inject
	TargetURIKey key;
	@Inject
	Provider<TargetURIs> targetURISetProvider;
	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	@Before
	public void setUp() {
		key.setQualifiedNameProvider(this);
	}

	@Test
	public void testCreateFromURIs() {
		List<URI> uris = List.of(
				URI.createURI("platform:/resource/myProject/src/A.js#A"),
				URI.createURI("platform:/resource/myProject/src/A.js#B"),
				URI.createURI("platform:/resource/myProject/src/B.js#A"),
				URI.createURI("platform:/resource/myProject/src/B.js#B"),
				URI.createURI("platform:/resource/myProject/src/B.js#C"),
				URI.createURI("platform:/resource/myProject/src/B.js#C") // intentional dup
		);
		TargetURIs set = targetURISetProvider.get();
		set.addAllURIs(uris);
		assertEquals(5, set.size());
		assertEquals(2, set.getTargetResourceURIs().size());
		assertEquals(2, set.getEObjectURIs(uris.get(0).trimFragment()).size());
		assertEquals(3, set.getEObjectURIs(uris.get(2).trimFragment()).size());

		Data data = key.getData(set, new SimpleResourceAccess(resourceSetProvider.get()));
		assertTrue(data.getTypesOrModulesToFind().isEmpty());
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EOBJECT));
		assertTrue(data.isMatchingConcreteSyntax("dummy"));
	}

	@Test
	public void createAddEObject_01() {
		EClass c = EcoreFactory.eINSTANCE.createEClass();
		c.setName("MyEClass");
		Data data = key.getData(targetURISetProvider.get(), new SimpleResourceAccess(resourceSetProvider.get()));
		data.add(c);
		assertFalse(data.isMatchingConcreteSyntax("Unexpected"));
		assertTrue(data.isMatchingConcreteSyntax("MyEClass"));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EOBJECT));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASSIFIER));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASS));
		assertFalse(data.isEReferenceTypeApplicable(EcorePackage.Literals.EATTRIBUTE));
	}

	@Test
	public void createAddEObject_02() {
		EClass c = EcoreFactory.eINSTANCE.createEClass();
		c.setName("MyEClass");
		EAttribute a = EcoreFactory.eINSTANCE.createEAttribute();
		a.setName("MyEAttribute");
		c.getEStructuralFeatures().add(a);
		Data data = key.getData(targetURISetProvider.get(), new SimpleResourceAccess(resourceSetProvider.get()));
		data.add(c);
		data.add(a);
		assertFalse(data.isMatchingConcreteSyntax("Unexpected"));
		assertTrue(data.isMatchingConcreteSyntax("MyEClass"));
		assertTrue(data.isMatchingConcreteSyntax("MyEAttribute"));
		assertTrue(data.isMatchingConcreteSyntax("MyEClass.MyEAttribute"));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EOBJECT));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASSIFIER));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.ECLASS));
		assertTrue(data.isEReferenceTypeApplicable(EcorePackage.Literals.EATTRIBUTE));
		assertFalse(data.isEReferenceTypeApplicable(EcorePackage.Literals.EREFERENCE));
	}

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof ENamedElement) {
			EObject container = obj.eContainer();
			if (container instanceof ENamedElement) {
				return getFullyQualifiedName(container).append(((ENamedElement) obj).getName());
			} else {
				return QualifiedName.create(((ENamedElement) obj).getName());
			}
		} else
			throw new IllegalArgumentException(obj.toString());
	}

	@Override
	public QualifiedName apply(EObject input) {
		return getFullyQualifiedName(input);
	}

}
