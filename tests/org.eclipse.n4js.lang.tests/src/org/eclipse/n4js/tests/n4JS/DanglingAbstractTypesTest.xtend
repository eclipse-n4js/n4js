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
package org.eclipse.n4js.tests.n4JS

import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TypesPackage
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

/**
 * Checks that all abstract types in the xcore files have at least one concrete subtype.
 */
@RunWith(Parameterized)
class DanglingAbstractTypesTest {
	/**
	 * Returns test data.
	 */
	@Parameters(name = "{0}")
	def static Collection<Object[]> data() throws Exception {
		val allClasses = (
			TypeRefsPackage.eINSTANCE.EClassifiers + TypesPackage.eINSTANCE.EClassifiers + N4JSPackage.eINSTANCE.EClassifiers
		).filter(EClass).toList
		return allClasses.filter[isAbstract || isInterface].map [ c |
			val Object[] arr = #[c.name, c, allClasses]
			return arr
		].toList
	}

	protected val String name
	val EClass clazz
	val List<EClass> allClasses
	@FinalFieldsConstructor
	new() {}

	@Test
	def void hasConcreteSubtype() {
		Assert.assertTrue(allClasses.exists[
			if (isAbstract || isInterface) {
				return false
			}
			return clazz.isSuperTypeOf(it)
		])
	}
}
