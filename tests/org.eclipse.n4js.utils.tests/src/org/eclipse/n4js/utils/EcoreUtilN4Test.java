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

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Test;

import com.google.common.base.Predicate;

/**
 */
@SuppressWarnings("javadoc")
public class EcoreUtilN4Test {

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
