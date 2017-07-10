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
package org.eclipse.n4js.ts.types.util

import org.eclipse.n4js.ts.types.TClassifier
import java.util.List
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.ts.types.util.TypesTestUtils.*
import org.eclipse.n4js.ts.types.util.ConsumptionUtils

/**
 */
@RunWith(XtextRunner)
public class ConsumedRoleFinderTest {

	@Test
	def void testSimple() {
		var R0 = interf("R0");
		var R1 = interf("R1");
		val A = clazz("A").impl(R0);
		val B = clazz("B");
		var R2 = interf("R2").ext(R0);

		assertFind("R0", ConsumptionUtils.findPathToInterface(A, R0));
		assertFind("R0", ConsumptionUtils.findPathToInterface(R2, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(A, R1));
		assertFind("", ConsumptionUtils.findPathToInterface(B, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(R0, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(R2, R1));
	}

	@Test
	def void testIndirect() {
		var R0 = interf("R0")
		var R1 = interf("R1").ext(R0);
		var R2 = interf("R2")
		var R3 = interf("R3").ext(R1, R2)
		var R4 = interf("R4")
		var R5 = interf("R5").ext(R4);
		var R6 = interf("R6").ext(R5,R3)

		var A = clazz("A").impl(R1);
		var B = clazz("B").impl(R3);
		var C = clazz("C").impl(R5);
		var D = clazz("D").impl(R6);
		assertFind("R1,R0", ConsumptionUtils.findPathToInterface(A, R0));
		assertFind("R3,R1,R0", ConsumptionUtils.findPathToInterface(B, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(C, R0));
		assertFind("R6,R3,R1,R0", ConsumptionUtils.findPathToInterface(D, R0));
	}

	@Test
	def void testRecursion() {
		var R0 = interf("R0")
		var R1 = interf("R1").ext(R0);
		R0.ext(R1);
		var A = clazz("A").impl(R1);
		assertFind("R1,R0", ConsumptionUtils.findPathToInterface(A, R0));
		assertFind("R1", ConsumptionUtils.findPathToInterface(A, R1));
	}


	def assertFind(String expectedResultAsString, List<TClassifier> classifiers) {
		assertEquals(expectedResultAsString, classifiers.map[name].join(","));
	}

}
