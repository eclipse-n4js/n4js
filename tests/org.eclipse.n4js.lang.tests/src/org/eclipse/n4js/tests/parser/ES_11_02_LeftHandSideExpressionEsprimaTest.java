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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_02_LeftHandSideExpressionEsprimaTest extends AbstractParserTest {

	@Test
	public void testNewButton_01() {
		Script script = parseESSuccessfully("new Button");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		NewExpression newExpression = (NewExpression) statement.getExpression();
		IdentifierRef button = (IdentifierRef) newExpression.getCallee();
		assertEquals("Button", getText(button));
		assertFalse(newExpression.isWithArgs());
	}

	@Test
	public void testNewButton_02() {
		Script script = parseESSuccessfully("new Button()");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		NewExpression newExpression = (NewExpression) statement.getExpression();
		IdentifierRef button = (IdentifierRef) newExpression.getCallee();
		assertEquals("Button", getText(button));
		assertTrue(newExpression.isWithArgs());
	}

	@Test
	public void testNewNewFoo_01() {
		Script script = parseESSuccessfully("new new foo");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		NewExpression outerNewExpression = (NewExpression) statement.getExpression();
		NewExpression innerNewExpression = (NewExpression) outerNewExpression.getCallee();
		IdentifierRef foo = (IdentifierRef) innerNewExpression.getCallee();
		assertEquals("foo", getText(foo));
		assertFalse(innerNewExpression.isWithArgs());
		assertFalse(outerNewExpression.isWithArgs());
	}

	@Test
	public void testNewNewFoo_02() {
		Script script = parseESSuccessfully("new new foo()");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		NewExpression outerNewExpression = (NewExpression) statement.getExpression();
		NewExpression innerNewExpression = (NewExpression) outerNewExpression.getCallee();
		IdentifierRef foo = (IdentifierRef) innerNewExpression.getCallee();
		assertEquals("foo", getText(foo));
		assertTrue(innerNewExpression.isWithArgs());
		assertFalse(outerNewExpression.isWithArgs());
	}

	@Test
	public void testNewFooBar_01() {
		Script script = parseESSuccessfully("new foo().bar()");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedCallExpression call = (ParameterizedCallExpression) statement.getExpression();
		ParameterizedPropertyAccessExpression bar = (ParameterizedPropertyAccessExpression) call.getTarget();
		assertEquals("bar", getPropertyText(bar));
		NewExpression newExpression = (NewExpression) bar.getTarget();
		IdentifierRef foo = (IdentifierRef) newExpression.getCallee();
		assertEquals("foo", getText(foo));
		assertTrue(newExpression.isWithArgs());
	}

	@Test
	public void testNewIndexAccess() {
		Script script = parseESSuccessfully("new foo[bar]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		NewExpression newExpression = (NewExpression) statement.getExpression();
		IndexedAccessExpression indexAccess = (IndexedAccessExpression) newExpression.getCallee();
		IdentifierRef foo = (IdentifierRef) indexAccess.getTarget();
		assertEquals("foo", getText(foo));
		IdentifierRef arg = (IdentifierRef) indexAccess.getIndex();
		assertEquals("bar", getText(arg));
	}

	@Test
	public void testNewDotAccess_01() {
		Script script = parseESSuccessfully("new foo.bar()");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		NewExpression newExpression = (NewExpression) statement.getExpression();
		ParameterizedPropertyAccessExpression fooBar = (ParameterizedPropertyAccessExpression) newExpression
				.getCallee();
		IdentifierRef foo = (IdentifierRef) fooBar.getTarget();
		assertEquals("foo", getText(foo));
		assertEquals("bar", getPropertyText(fooBar));
		assertTrue(newExpression.isWithArgs());
	}

	@Test
	public void testNewDotAccess_02() {
		Script script = parseESSuccessfully("( new foo).bar()");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedCallExpression call = (ParameterizedCallExpression) statement.getExpression();
		ParameterizedPropertyAccessExpression bar = (ParameterizedPropertyAccessExpression) call.getTarget();
		ParenExpression paren = (ParenExpression) bar.getTarget();
		NewExpression newExpression = (NewExpression) paren.getExpression();
		IdentifierRef foo = (IdentifierRef) newExpression.getCallee();
		assertEquals("foo", getText(foo));
		assertEquals("bar", getPropertyText(bar));
	}

	@Test
	public void testCallExpression_01() {
		Script script = parseESSuccessfully("foo(bar, baz)");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedCallExpression foo = (ParameterizedCallExpression) statement.getExpression();
		assertEquals("foo", getText(foo.getTarget()));
		assertEquals(2, foo.getArguments().size());
		assertEquals("bar", getText(foo.getArguments().get(0).getExpression()));
		assertEquals("baz", getText(foo.getArguments().get(1).getExpression()));
	}

	@Test
	public void testCallExpression_02() {
		Script script = parseESSuccessfully("(    foo  )()");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedCallExpression call = (ParameterizedCallExpression) statement.getExpression();
		IdentifierRef foo = (IdentifierRef) ((ParenExpression) call.getTarget()).getExpression();
		assertEquals("foo", getText(foo));
		assertEquals(0, call.getArguments().size());
	}

	@Test
	public void testDotAccess_01() {
		Script script = parseESSuccessfully("universe.milkyway");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression milkyway = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("milkyway", getPropertyText(milkyway));
		assertEquals("universe", getText(milkyway.getTarget()));
	}

	@Test
	public void testDotAccess_02() {
		Script script = parseESSuccessfully("universe.milkyway.solarsystem");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression solarsystem = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("solarsystem", getPropertyText(solarsystem));
		ParameterizedPropertyAccessExpression milkyway = (ParameterizedPropertyAccessExpression) solarsystem
				.getTarget();
		assertEquals("milkyway", getPropertyText(milkyway));
		IdentifierRef universe = (IdentifierRef) milkyway.getTarget();
		assertEquals("universe", getText(universe));
	}

	@Test
	public void testDotAccess_03() {
		Script script = parseESSuccessfully("universe.milkyway.solarsystem.Earth");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression earth = (ParameterizedPropertyAccessExpression) statement.getExpression();
		assertEquals("Earth", getPropertyText(earth));
		ParameterizedPropertyAccessExpression solarsystem = (ParameterizedPropertyAccessExpression) earth.getTarget();
		assertEquals("solarsystem", getPropertyText(solarsystem));
		ParameterizedPropertyAccessExpression milkyway = (ParameterizedPropertyAccessExpression) solarsystem
				.getTarget();
		assertEquals("milkyway", getPropertyText(milkyway));
		IdentifierRef universe = (IdentifierRef) milkyway.getTarget();
		assertEquals("universe", getText(universe));
	}

	@Test
	public void testIndexAccess_01() {
		Script script = parseESSuccessfully("universe[galaxyName, otherUselessName]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IndexedAccessExpression universe = (IndexedAccessExpression) statement.getExpression();
		assertEquals("universe", getText(universe.getTarget()));
		CommaExpression comma = (CommaExpression) universe.getIndex();
		assertEquals(2, comma.getExprs().size());
	}

	@Test
	public void testIndexAccess_02() {
		Script script = parseESSuccessfully("universe[galaxyName]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IndexedAccessExpression universe = (IndexedAccessExpression) statement.getExpression();
		assertEquals("universe", getText(universe.getTarget()));
		IdentifierRef identifier = (IdentifierRef) universe.getIndex();
		assertEquals("galaxyName", getText(identifier));
	}

	@Test
	public void testDotAccess_04() {
		Script script = parseESSuccessfully("universe[42].galaxies");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression galaxies = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("galaxies", getPropertyText(galaxies));
		IndexedAccessExpression universeIndexed = (IndexedAccessExpression) galaxies.getTarget();
		IntLiteral number = (IntLiteral) universeIndexed.getIndex();
		assertEquals(42, number.toInt());
		IdentifierRef universe = (IdentifierRef) universeIndexed.getTarget();
		assertEquals("universe", getText(universe));
	}

	@Test
	public void testDotAccess_05() {
		Script script = parseESSuccessfully("universe(42).galaxies");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression galaxies = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("galaxies", getPropertyText(galaxies));
		ParameterizedCallExpression call = (ParameterizedCallExpression) galaxies.getTarget();
		IntLiteral number = (IntLiteral) call.getArguments().get(0).getExpression();
		assertEquals(42, number.toInt());
		IdentifierRef universe = (IdentifierRef) call.getTarget();
		assertEquals("universe", getText(universe));
	}

	@Test
	public void testDotAccess_06() {
		Script script = parseESSuccessfully("universe(42).galaxies(14, 3, 77).milkyway");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression milkyway = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("milkyway", getPropertyText(milkyway));
		ParameterizedCallExpression galaxiesCall = (ParameterizedCallExpression) milkyway.getTarget();
		List<Expression> galaxiesArgs = toList(map(galaxiesCall.getArguments(), arg -> arg.getExpression()));
		assertEquals(3, galaxiesArgs.size());
		assertEquals(14, ((IntLiteral) galaxiesArgs.get(0)).toInt());
		assertEquals(3, ((IntLiteral) galaxiesArgs.get(1)).toInt());
		assertEquals(77, ((IntLiteral) galaxiesArgs.get(2)).toInt());
		ParameterizedPropertyAccessExpression galaxies = (ParameterizedPropertyAccessExpression) galaxiesCall
				.getTarget();
		assertEquals("galaxies", getPropertyText(galaxies));
		ParameterizedCallExpression universeCall = (ParameterizedCallExpression) galaxies.getTarget();
		List<Expression> universeArgs = toList(map(universeCall.getArguments(), arg -> arg.getExpression()));
		assertEquals(1, universeArgs.size());
		assertEquals(42, ((IntLiteral) universeArgs.get(0)).toInt());
		IdentifierRef universe = (IdentifierRef) universeCall.getTarget();
		assertEquals("universe", getText(universe));
	}

	@Test
	public void testDotAccess_07() {
		Script script = parseESSuccessfully("earth.asia.Indonesia.prepareForElection(2014)");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedCallExpression call = (ParameterizedCallExpression) statement.getExpression();
		assertEquals(2014, ((IntLiteral) call.getArguments().get(0).getExpression()).toInt());
	}

	@Test
	public void testKeyword_01() {
		Script script = parseESSuccessfully("universe.if");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression keyword = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("universe", getText(keyword.getTarget()));
		assertEquals("if", getPropertyText(keyword));
	}

	@Test
	public void testKeyword_02() {
		Script script = parseESSuccessfully("universe.true");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression keyword = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("universe", getText(keyword.getTarget()));
		assertEquals("true", getPropertyText(keyword));
	}

	@Test
	public void testKeyword_03() {
		Script script = parseESSuccessfully("universe.false");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression keyword = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("universe", getText(keyword.getTarget()));
		assertEquals("false", getPropertyText(keyword));
	}

	@Test
	public void testKeyword_04() {
		Script script = parseESSuccessfully("universe.null");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		ParameterizedPropertyAccessExpression keyword = (ParameterizedPropertyAccessExpression) statement
				.getExpression();
		assertEquals("universe", getText(keyword.getTarget()));
		assertEquals("null", getPropertyText(keyword));
	}

}
