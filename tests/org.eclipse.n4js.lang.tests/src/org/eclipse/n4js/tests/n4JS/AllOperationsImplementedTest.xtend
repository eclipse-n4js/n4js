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
import org.eclipse.n4js.ts.types.TypesPackage
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Collection
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import java.lang.reflect.Parameter

/**
 * Creates instances of all concrete EClasses in the given EPackage and
 * invokes all declared EOperations on them. Should never see an
 * UnsupportedOperationException.
 */
@RunWith(Parameterized)
class AllOperationsImplementedTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name = "{0}")
	def static Collection<Object[]> data() throws Exception {
		val allConcreteClasses = (
			TypeRefsPackage.eINSTANCE.EClassifiers + TypesPackage.eINSTANCE.EClassifiers + N4JSPackage.eINSTANCE.EClassifiers
		).filter(EClass).filter[!abstract && !interface]
		return allConcreteClasses.map [ c |
			val allMethods = c.EAllOperations.map[ op |
				c.instanceClass.getMethod(op.name, op.EParameters.map[it.EType.instanceClass])
			].toSet
			return allMethods.map[ op |
				val Object[] arr = #['''«c.name».«op.name»'''.toString, c, op]
				return arr
			]
		].flatten.toList
	}

	protected val String name
	val EClass clazz
	val Method method
	@FinalFieldsConstructor
	new() {}

	@Test
	def void invoke() {
		val instance = EcoreUtil.create(clazz)
		val Object[] array = method.parameters.map[ toDefaultValue ]
		try {
			method.invoke(instance, array)
		} catch(InvocationTargetException e) {
			switch (e.cause) {
				NullPointerException,
				IllegalArgumentException,
				IllegalStateException: { /* ignore */}
				// duplication on purpose to make it more obvious what we try to figure out here
				UnsupportedOperationException: throw e.cause
				default: throw e.cause
			}
		}
	}

	private def Object toDefaultValue(Parameter param) {
		return switch type: param.type {
			case type == byte: 0 as byte
			case type == short: 0 as short
			case type == char: 0 as char
			case type == int: 0
			case type == long: 0L
			case type == float: 0.0F
			case type == double: 0.0D
			case type == boolean: false
			default: null
		}
	}

}
