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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.math.BigDecimal;
import java.util.Iterator;

import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_01_4_ArrayInitializerEsprimaTest extends AbstractParserTest {

	@Test
	public void testArrayInitializer_01() {
		Script script = parseESSuccessfully("x = []");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testArrayInitializer_02() {
		Script script = parseESSuccessfully("x = [ ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testArrayInitializer_03() {
		Script script = parseESSuccessfully("x = [ 42 ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		ArrayElement singleElement = array.getElements().get(0);
		assertEquals(new BigDecimal(42), ((IntLiteral) singleElement.getExpression()).getValue());
	}

	@Test
	public void testArrayInitializer_04() {
		Script script = parseESSuccessfully("x = [ 42, ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(1, array.getElements().size());
		ArrayElement firstElement = array.getElements().get(0);
		assertEquals(42, ((IntLiteral) firstElement.getExpression()).toInt());
	}

	@Test
	public void testArrayInitializer_05() {
		Script script = parseESSuccessfully("x = [ ,, 42 ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(3, array.getElements().size());
		ArrayElement emptyFirstElement = array.getElements().get(0);
		assertNull(emptyFirstElement.getExpression());
		ArrayElement emptySecondElement = array.getElements().get(1);
		assertNull(emptySecondElement.getExpression());
		ArrayElement lastElement = last(array.getElements());
		assertEquals(42, ((IntLiteral) lastElement.getExpression()).toInt());
	}

	@Test
	public void testArrayInitializer_06() {
		Script script = parseESSuccessfully("x = [ 1, 2, 3, ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(3, array.getElements().size());
		Iterator<ArrayElement> elements = array.getElements().iterator();
		assertEquals(1, ((IntLiteral) elements.next().getExpression()).toInt());
		assertEquals(2, ((IntLiteral) elements.next().getExpression()).toInt());
		assertEquals(3, ((IntLiteral) elements.next().getExpression()).toInt());
	}

	@Test
	public void testArrayInitializer_07() {
		Script script = parseESSuccessfully("x = [ 1, 2,, 3, ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(4, array.getElements().size());
		Iterator<ArrayElement> elements = array.getElements().iterator();
		assertEquals(1, ((IntLiteral) elements.next().getExpression()).toInt());
		assertEquals(2, ((IntLiteral) elements.next().getExpression()).toInt());
		assertNull(elements.next().getExpression());
		assertEquals(3, ((IntLiteral) elements.next().getExpression()).toInt());
	}

	@Test
	public void testArrayInitializer_08() {
		Script script = parseESSuccessfully("x = [ 1, 2,, 3,, ]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(5, array.getElements().size());
		Iterator<ArrayElement> elements = array.getElements().iterator();
		assertEquals(1, ((IntLiteral) elements.next().getExpression()).toInt());
		assertEquals(2, ((IntLiteral) elements.next().getExpression()).toInt());
		assertNull(elements.next().getExpression());
		assertEquals(3, ((IntLiteral) elements.next().getExpression()).toInt());
		assertNull(elements.next().getExpression());
	}
}
