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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Objects;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ES_07_08_LiteralsTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testLiterals() throws Exception {
		Script program = parseHelper.parse("""
				var string = "Hello";
				var num = 1;
				var bool = true;
				var nullL = null;
				var regexp = /someregex/;
				var division = 12 / 5;
				""");

		assertTrue(program.eResource().getErrors().toString(), program.eResource().getErrors().isEmpty());

		Iterator<VariableDeclaration> varDecls = filter(program.eAllContents(),
				VariableDeclaration.class);
		assertType(StringLiteral.class,
				findFirst(varDecls, vd -> Objects.equals(vd.getName(), "string")).getExpression());
		assertType(NumericLiteral.class,
				findFirst(varDecls, vd -> Objects.equals(vd.getName(), "num")).getExpression());
		assertType(BooleanLiteral.class,
				findFirst(varDecls, vd -> Objects.equals(vd.getName(), "bool")).getExpression());
		assertType(NullLiteral.class, findFirst(varDecls, vd -> Objects.equals(vd.getName(), "nullL")).getExpression());
		assertType(RegularExpressionLiteral.class,
				findFirst(varDecls, vd -> Objects.equals(vd.getName(), "regexp")).getExpression());
		assertType(MultiplicativeExpression.class,
				findFirst(varDecls, vd -> Objects.equals(vd.getName(), "division")).getExpression());
	}

	public void assertType(Class<?> type, Expression expression) {
		assertNotNull("Expected type " + type.getName() + ", was null", expression);
		assertTrue("Expected type " + type.getName() + ", was " + expression.eClass().getName(),
				type.isInstance(expression));
	}

}
