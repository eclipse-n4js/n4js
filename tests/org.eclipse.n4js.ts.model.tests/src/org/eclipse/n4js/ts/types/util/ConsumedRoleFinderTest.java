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
package org.eclipse.n4js.ts.types.util;

import static org.eclipse.n4js.ts.types.util.TypesTestUtils.clazz;
import static org.eclipse.n4js.ts.types.util.TypesTestUtils.ext;
import static org.eclipse.n4js.ts.types.util.TypesTestUtils.impl;
import static org.eclipse.n4js.ts.types.util.TypesTestUtils.interf;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 */
@RunWith(XtextRunner.class)
public class ConsumedRoleFinderTest {

	@Test
	public void testSimple() {
		TInterface R0 = interf("R0");
		TInterface R1 = interf("R1");
		TClass A = impl(clazz("A"), R0);
		TClass B = clazz("B");
		TInterface R2 = ext(interf("R2"), R0);

		assertFind("R0", ConsumptionUtils.findPathToInterface(A, R0));
		assertFind("R0", ConsumptionUtils.findPathToInterface(R2, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(A, R1));
		assertFind("", ConsumptionUtils.findPathToInterface(B, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(R0, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(R2, R1));
	}

	@Test
	public void testIndirect() {
		TInterface R0 = interf("R0");
		TInterface R1 = ext(interf("R1"), R0);
		TInterface R2 = interf("R2");
		TInterface R3 = ext(interf("R3"), R1, R2);
		TInterface R4 = interf("R4");
		TInterface R5 = ext(interf("R5"), R4);
		TInterface R6 = ext(interf("R6"), R5, R3);

		TClass A = impl(clazz("A"), R1);
		TClass B = impl(clazz("B"), R3);
		TClass C = impl(clazz("C"), R5);
		TClass D = impl(clazz("D"), R6);
		assertFind("R1,R0", ConsumptionUtils.findPathToInterface(A, R0));
		assertFind("R3,R1,R0", ConsumptionUtils.findPathToInterface(B, R0));
		assertFind("", ConsumptionUtils.findPathToInterface(C, R0));
		assertFind("R6,R3,R1,R0", ConsumptionUtils.findPathToInterface(D, R0));
	}

	@Test
	public void testRecursion() {
		TInterface R0 = interf("R0");
		TInterface R1 = ext(interf("R1"), R0);
		ext(R0, R1);
		TClass A = impl(clazz("A"), R1);
		assertFind("R1,R0", ConsumptionUtils.findPathToInterface(A, R0));
		assertFind("R1", ConsumptionUtils.findPathToInterface(A, R1));
	}

	void assertFind(String expectedResultAsString, List<TClassifier> classifiers) {
		assertEquals(expectedResultAsString, Strings.join(",", map(classifiers, clf -> clf.getName())));
	}

}
