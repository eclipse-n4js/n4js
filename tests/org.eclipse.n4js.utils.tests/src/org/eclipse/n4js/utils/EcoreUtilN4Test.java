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
package org.eclipse.n4js.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.n4js.utils.EcoreUtilN4.IResourceLocatorWithCreateSupport;
import org.junit.Test;

import com.google.common.base.Predicate;

/**
 */
@SuppressWarnings("javadoc")
public class EcoreUtilN4Test {

	/***/
	@Test
	public void testCreateResourceInCorrectResourceSet() {
		ResourceSetImpl resSetNone = new ResourceSetImpl();
		ResourceSetImpl resSetUpper = new ResourceSetImpl();
		ResourceSetImpl resSetLower = new ResourceSetImpl();

		Resource.Factory.Registry resourceFactoryRegistry = new ResourceFactoryRegistryImpl();
		resSetNone.setResourceFactoryRegistry(resourceFactoryRegistry);
		resSetUpper.setResourceFactoryRegistry(resourceFactoryRegistry);
		resSetLower.setResourceFactoryRegistry(resourceFactoryRegistry);
		resourceFactoryRegistry.getExtensionToFactoryMap().put("test", new Resource.Factory() {
			@Override
			public Resource createResource(URI uri) {
				return new ResourceImpl(uri) {
					@Override
					public void load(Map<?, ?> options) throws IOException {
						isLoaded = true;
					}
				};
			}
		});

		class TestResourceLocator extends ResourceLocator implements IResourceLocatorWithCreateSupport {
			public TestResourceLocator(ResourceSetImpl resourceSet) {
				super(resourceSet);
			}

			private ResourceSet getTargetResourceSet(URI uri) {
				boolean upperCase = Character.isUpperCase(uri.lastSegment().charAt(0));
				return upperCase ? resSetUpper : resSetLower;
			}

			@Override
			public Resource getResource(URI uri, boolean loadOnDemand) {
				ResourceSet target = getTargetResourceSet(uri);
				if (target == null || target == resourceSet) {
					return basicGetResource(uri, loadOnDemand);
				}
				return target.getResource(uri, loadOnDemand);
			}

			@Override
			public Resource createResource(URI uri) {
				ResourceSet target = getTargetResourceSet(uri);
				if (target == null || target == resourceSet) {
					return demandCreateResource(uri);
				}
				return target.createResource(uri);
			}
		}
		@SuppressWarnings("unused")
		TestResourceLocator l1 = new TestResourceLocator(resSetNone);
		@SuppressWarnings("unused")
		TestResourceLocator l2 = new TestResourceLocator(resSetUpper);
		@SuppressWarnings("unused")
		TestResourceLocator l3 = new TestResourceLocator(resSetLower);

		// not the actual test, just to make sure our set up works properly:
		Resource resUpper = resSetNone.getResource(URI.createFileURI("Upper.test"), true);
		Resource resLower = resSetNone.getResource(URI.createFileURI("lower.test"), true);
		assertSame(resSetUpper, resUpper.getResourceSet());
		assertSame(resSetLower, resLower.getResourceSet());
		assertTrue(resUpper.isLoaded());
		assertTrue(resLower.isLoaded());

		// now for the actual test:
		Resource resUpper2 = EcoreUtilN4.createResourceInCorrectResourceSet(resSetNone,
				URI.createFileURI("Upper2.test"));
		assertSame(resSetUpper, resUpper2.getResourceSet());
		assertFalse(resUpper2.isLoaded());
	}

	@Test
	public void testGetAllContentsFiltered() {

		EPackage root = epack("root",
				ecl("A"), ecl("B"), edt("C"), //
				epack("notfiltered", ecl("Sub1A"), edt("Sub1C")), //
				ecl("D"), //
				epack("filteredSub", ecl("Sub2A"), ecl("Sub2B"), edt("Sub2C")),
				ecl("E"), edt("F"));
		Iterator<EObject> iter = EcoreUtilN4.getAllContentsFiltered(root, new Predicate<EObject>() {

			@Override
			public boolean apply(EObject input) {
				return !((ENamedElement) input).getName().startsWith("filtered");
			}
		});
		assertEqualsByNames("A,B,C,D,E,F,notfiltered,Sub1A,Sub1C", iter);
	}

	@Test
	public void testGetAllContentsFilteredIgnoreRootPredicate() {

		EPackage root = epack("filteredRoot",
				ecl("A"), ecl("B"), edt("C"), //
				epack("notfiltered", ecl("Sub1A"), edt("Sub1C")), //
				ecl("D"), //
				epack("filtered", ecl("Sub2A"), ecl("Sub2B"), edt("Sub2C")),
				ecl("E"), edt("F"));
		Iterator<EObject> iter = EcoreUtilN4.getAllContentsFiltered(root, new Predicate<EObject>() {

			@Override
			public boolean apply(EObject input) {
				return !((ENamedElement) input).getName().startsWith("filtered");
			}
		});
		assertEqualsByNames("A,B,C,D,E,F,notfiltered,Sub1A,Sub1C", iter);
	}

	@Test
	public void testGetAllContentsFilteredNoMatch() {

		EPackage root = epack("root",
				epack("filteredSub1", ecl("Sub1A"), edt("Sub1C")), //
				epack("filteredSub2", ecl("Sub2A"), ecl("Sub2B"), edt("Sub2C")));
		Iterator<EObject> iter = EcoreUtilN4.getAllContentsFiltered(root, new Predicate<EObject>() {

			@Override
			public boolean apply(EObject input) {
				return !((ENamedElement) input).getName().startsWith("filtered");
			}
		});
		assertEqualsByNames("", iter);
	}

	@Test
	public void testGetAllContentsFilteredEmpty() {

		EPackage root = epack("root");
		Iterator<EObject> iter = EcoreUtilN4.getAllContentsFiltered(root, new Predicate<EObject>() {

			@Override
			public boolean apply(EObject input) {
				return !((ENamedElement) input).getName().startsWith("filtered");
			}
		});
		assertEqualsByNames("", iter);
	}

	private void assertEqualsByNames(String string, Iterator<EObject> iter) {
		StringBuilder strb = new StringBuilder();
		while (iter.hasNext()) {
			strb.append(((ENamedElement) iter.next()).getName());
			if (iter.hasNext())
				strb.append(",");
		}
		assertEquals(string, strb.toString());
	}

	private EClass ecl(String n) {
		EClass c = EcoreFactory.eINSTANCE.createEClass();
		c.setName(n);
		return c;
	}

	private EDataType edt(String n) {
		EDataType d = EcoreFactory.eINSTANCE.createEDataType();
		d.setName(n);
		return d;
	}

	private EPackage epack(String n, EObject... contents) {
		EPackage p = EcoreFactory.eINSTANCE.createEPackage();
		p.setName(n);

		for (EObject eobj : contents) {
			if (eobj instanceof EPackage) {
				p.getESubpackages().add((EPackage) eobj);
			} else {
				p.getEClassifiers().add((EClassifier) eobj);
			}
		}

		return p;
	}

}
