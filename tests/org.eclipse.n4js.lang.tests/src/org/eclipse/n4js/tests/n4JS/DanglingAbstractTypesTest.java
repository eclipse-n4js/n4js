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
package org.eclipse.n4js.tests.n4JS;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Checks that all abstract types in the xcore files have at least one concrete subtype.
 */
@RunWith(Parameterized.class)
public class DanglingAbstractTypesTest {
	/**
	 * Returns test data.
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> data() throws Exception {
		List<EClassifier> classifiers = new ArrayList<>();
		classifiers.addAll(TypeRefsPackage.eINSTANCE.getEClassifiers());
		classifiers.addAll(TypesPackage.eINSTANCE.getEClassifiers());
		classifiers.addAll(N4JSPackage.eINSTANCE.getEClassifiers());

		List<EClass> allClasses = toList(filter(classifiers, EClass.class));
		Iterable<EClass> allNonConcreteClasses = filter(allClasses, ec -> ec.isAbstract() || ec.isInterface());

		return toList(map(allNonConcreteClasses, c -> new Object[] { c.getName(), c, allClasses }));
	}

	protected String name;
	EClass clazz;
	List<EClass> allClasses;

	@FinalFieldsConstructor
	public DanglingAbstractTypesTest(final String name, final EClass clazz, final List<EClass> allClasses) {
		super();
		this.name = name;
		this.clazz = clazz;
		this.allClasses = allClasses;
	}

	@Test
	public void hasConcreteSubtype() {
		Assert.assertTrue(exists(allClasses, c -> {
			if (c.isAbstract() || c.isInterface()) {
				return false;
			}
			return clazz.isSuperTypeOf(c);
		}));
	}
}
