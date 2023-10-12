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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ES_11_ExpressionParserTest extends Assert {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testPostfixExpression_01() throws Exception {
		Script program = parseHelper.parse("""
				var x = 1;
				x++;
				""");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

	@Test
	public void testPostfixExpression_02() throws Exception {
		Script program = parseHelper.parse("""
				var x = 1
				x++
				""");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

	@Test
	public void testPrefixExpression_01() throws Exception {
		Script program = parseHelper.parse("""
				var x = 1;
				++x;
				""");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

	@Test
	public void testPrefixExpression_02() throws Exception {
		Script program = parseHelper.parse("""
				var x = 1
				++x
				""");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

	@Test
	public void testPrefixExpression_03() throws Exception {
		Script program = parseHelper.parse("""
				var x = 1
				--x
				""");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

	@Test
	public void testPrefixExpression_04() throws Exception {
		Script program = parseHelper.parse("""
				var x = 1
				~x
				""");
		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());
	}

}
