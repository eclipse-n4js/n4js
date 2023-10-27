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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.validation.JavaScriptVariant.n4js;
import static org.eclipse.n4js.validation.JavaScriptVariant.strict;
import static org.eclipse.n4js.validation.JavaScriptVariant.unrestricted;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for operator test (6.1.10- 6.1.18)
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_15_RelationalExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	@Test
	public void testType() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			for (String op : List.of("<", "<=", ">", ">=", "instanceof", "in")) {
				assertOperatorType(mode, "boolean", "n1 " + op + " n2");
				assertOperatorType(mode, "boolean", "s1 " + op + " s2");
			}
		}
	}

	@Test
	public void testExpectedType_LessEtc() {
		for (String op : List.of("<", "<=", ">", ">=")) {
			assertBinaryOperatorExpectedType(unrestricted, "any", "any", "n1 " + op + " n2");
			assertBinaryOperatorExpectedType(strict, "any", "any", "n1 " + op + " n2");
			assertBinaryOperatorExpectedType(n4js, "number", "number", "n1 " + op + " n2");
			assertBinaryOperatorExpectedType(n4js, "string", "string", "s1 " + op + " s2");
			assertBinaryOperatorExpectedType(n4js, "boolean", "boolean", "f1 " + op + " f2");
			assertBinaryOperatorExpectedType(n4js, "boolean", "union{boolean,number,string}",
					"undefined " + op + " f2");
			assertBinaryOperatorExpectedType(n4js, "boolean", "union{boolean,number,string}", "null " + op + " f2");
			assertBinaryOperatorExpectedType(n4js, "boolean", "union{boolean,number,string}", "a " + op + " f2");
		}
	}

	@Test
	public void testExpectedType_InstanceOf() {
		assertBinaryOperatorExpectedType(unrestricted, "any",
				"union{Function,type{Object},type{N4Enum}}", "n1 instanceof n2");
		assertBinaryOperatorExpectedType(strict, "any",
				"union{Function,type{Object},type{N4Enum}}", "n1 instanceof n2");
		assertBinaryOperatorExpectedType(n4js, "any",
				"union{Function,type{Object},type{N4Enum}}", "n1 instanceof n2");
	}

	@Test
	public void testExpectedType_In() {
		assertBinaryOperatorExpectedType(unrestricted, "any", "Object", "n1 in n2");
		assertBinaryOperatorExpectedType(strict, "any", "Object", "n1 in n2");
		assertBinaryOperatorExpectedType(n4js, "union{number,string,symbol}", "Object", "n1 in n2");
	}
}
