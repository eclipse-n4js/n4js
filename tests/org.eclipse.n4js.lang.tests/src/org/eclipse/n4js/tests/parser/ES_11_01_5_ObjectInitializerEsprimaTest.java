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

import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_01_5_ObjectInitializerEsprimaTest extends AbstractParserTest {

	@Test
	public void testEmpty_01() {
		Script script = parseESSuccessfully("x = {}");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		assertTrue(objectLiteral.getPropertyAssignments().isEmpty());
	}

	@Test
	public void testEmpty_02() {
		Script script = parseESSuccessfully("x = { }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		assertTrue(objectLiteral.getPropertyAssignments().isEmpty());
	}

	@Test
	public void testSimplePropertyNameValuePair_01() {
		Script script = parseESSuccessfully("x = { answer: 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("answer", answer.getName());
		assertNotNull(answer.getProperty());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testSimplePropertyNameValuePair_01_TrailingComma() {
		Script script = parseESSuccessfully("x = { answer: 42, }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("answer", answer.getName());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testSimplePropertyNameValuePair_01_TooManyTrailingCommas() {
		parseESWithError("x = { answer: 42,, }");
	}

	@Test
	public void testSimplePropertyNameValuePair_02() {
		Script script = parseESSuccessfully("x = { get: 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair get = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("get", get.getName());
		assertEquals(42, ((IntLiteral) get.getExpression()).toInt());
	}

	@Test
	public void testSimplePropertyNameValuePair_03() {
		Script script = parseESSuccessfully("x = { set: 43 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair set = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertNotNull(set.getProperty());
		assertEquals("set", set.getName());
		assertEquals(43, ((IntLiteral) set.getExpression()).toInt());
	}

	@Test
	public void testSimplePropertyNameValuePair_04() {
		Script script = parseESSuccessfully("x = { __proto__: 2 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair proto = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("__proto__", proto.getName());
		assertEquals(2, ((IntLiteral) proto.getExpression()).toInt());
	}

	@Test
	public void testNameIsKeyword_01() {
		Script script = parseESSuccessfully("x = { if: 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertNotNull(answer.getProperty());
		assertEquals("if", answer.getName());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testNameIsKeyword_02() {
		Script script = parseESSuccessfully("x = { true: 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertNotNull(answer.getProperty());
		assertEquals("true", answer.getName());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testNameIsKeyword_03() {
		Script script = parseESSuccessfully("x = { false: 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertNotNull(answer.getProperty());
		assertEquals("false", answer.getName());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testNameIsKeyword_04() {
		Script script = parseESSuccessfully("x = { null: 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertNotNull(answer.getProperty());
		assertEquals("null", answer.getName());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testNameIsString_01() {
		Script script = parseESSuccessfully("x = { \"answer\": 42 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair answer = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals(PropertyNameKind.STRING, answer.getDeclaredName().getKind());
		assertEquals("answer", answer.getName());
		assertEquals(42, ((IntLiteral) answer.getExpression()).toInt());
	}

	@Test
	public void testNameIsString_02() {
		Script script = parseESSuccessfully("x = { \"__proto__\": 2 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair proto = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals(PropertyNameKind.STRING, proto.getDeclaredName().getKind());
		assertEquals("__proto__", proto.getName());
		assertEquals(2, ((IntLiteral) proto.getExpression()).toInt());
	}

	@Test
	public void testTwoProperties() {
		Script script = parseESSuccessfully("x = { x: 1, x: 2 }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyNameValuePair first = (PropertyNameValuePair) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("x", first.getName());
		assertEquals(1, ((IntLiteral) first.getExpression()).toInt());
		PropertyNameValuePair last = (PropertyNameValuePair) last(objectLiteral.getPropertyAssignments());
		assertEquals("x", last.getName());
		assertEquals(2, ((IntLiteral) last.getExpression()).toInt());
	}

	@Test
	public void testGetMethod_01() {
		Script script = parseESSuccessfully("x = { get width() { return m_width } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("width", getter.getName());
		Block body = getter.getBody();
		ReturnStatement returnStatement = (ReturnStatement) body.getStatements().get(0);
		IdentifierRef returnValue = (IdentifierRef) returnStatement.getExpression();
		assertEquals("m_width", getText(returnValue));
	}

	@Test
	public void testGetMethod_02() {
		Script script = parseESSuccessfully("x = { get undef() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("undef", getter.getName());
		Block body = getter.getBody();
		assertTrue(body.getStatements().isEmpty());
	}

	@Test
	public void testGetMethodNameIsKeyword_01() {
		Script script = parseESSuccessfully("x = { get if() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("if", getter.getName());
		Block body = getter.getBody();
		assertEquals(true, body.getStatements().isEmpty());
	}

	@Test
	public void testGetMethodNameIsKeyword_02() {
		Script script = parseESSuccessfully("x = { get true() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("true", getter.getName());
		Block body = getter.getBody();
		assertEquals(true, body.getStatements().isEmpty());
	}

	@Test
	public void testGetMethodNameIsKeyword_03() {
		Script script = parseESSuccessfully("x = { get false() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("false", getter.getName());
		Block body = getter.getBody();
		assertEquals(true, body.getStatements().isEmpty());
	}

	@Test
	public void testGetMethodNameIsKeyword_04() {
		Script script = parseESSuccessfully("x = { get null() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("null", getter.getName());
		Block body = getter.getBody();
		assertEquals(true, body.getStatements().isEmpty());
	}

	@Test
	public void testGetMethodNameIsString_01() {
		Script script = parseESSuccessfully("x = { get \"undef\"() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("undef", getter.getName());
		Block body = getter.getBody();
		assertTrue(body.getStatements().isEmpty());
	}

	@Test
	public void testGetMethodNameIsIntLiteral_01() {
		Script script = parseESSuccessfully("x = { get 10() {} }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals(PropertyNameKind.NUMBER, getter.getDeclaredName().getKind());
		assertEquals("10", getter.getName());
		Block body = getter.getBody();
		assertTrue(body.getStatements().isEmpty());
	}

	@Test
	public void testSetMethod_01() {
		Script script = parseESSuccessfully("x = { set width(w) { m_width = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("width", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_width", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testSetMethodNameIsKeyword_01() {
		Script script = parseESSuccessfully("x = { set if(w) { m_if = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("if", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_if", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testSetMethodNameIsKeyword_02() {
		Script script = parseESSuccessfully("x = { set true(w) { m_true = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("true", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_true", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testSetMethodNameIsKeyword_03() {
		Script script = parseESSuccessfully("x = { set false(w) { m_false = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("false", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_false", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testSetMethodNameIsKeyword_04() {
		Script script = parseESSuccessfully("x = { set null(w) { m_null = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("null", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_null", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testSetMethodNameIsString() {
		Script script = parseESSuccessfully("x = { set \"null\"(w) { m_null = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("null", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_null", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testSetMethodNameIsIntValue() {
		Script script = parseESSuccessfully("x = { set 10(w) { m_null = w } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		PropertySetterDeclaration setter = (PropertySetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals(PropertyNameKind.NUMBER, setter.getDeclaredName().getKind());
		assertEquals("10", setter.getName());
		assertEquals("w", setter.getFpar().getName());
		Block body = setter.getBody();
		ExpressionStatement onlyStatement = (ExpressionStatement) body.getStatements().get(0);
		AssignmentExpression innerAssignment = (AssignmentExpression) onlyStatement.getExpression();
		assertEquals("m_null", getText(innerAssignment.getLhs()));
		assertEquals("w", getText(innerAssignment.getRhs()));
	}

	@Test
	public void testGetAndSetMethodSameName() {
		Script script = parseESSuccessfully(
				"x = { get width() { return m_width }, set width(width) { m_width = width; } }");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("x", getText(identifier));
		ObjectLiteral objectLiteral = (ObjectLiteral) assignment.getRhs();
		assertEquals(2, objectLiteral.getPropertyAssignments().size());
		PropertyGetterDeclaration getter = (PropertyGetterDeclaration) objectLiteral.getPropertyAssignments().get(0);
		assertEquals("width", getter.getName());
		PropertySetterDeclaration setter = (PropertySetterDeclaration) last(objectLiteral.getPropertyAssignments());
		assertEquals("width", setter.getName());
		assertEquals("width", setter.getFpar().getName());
	}

	@Test
	public void testSingleNameSyntax_positiveCases() {
		try {
			Script script = parseHelper.parse("x = {a,prop:b};");
			assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSingleNameSyntax_positiveCases_destructuring() {
		try {
			Script script = parseHelper.parse("""
					({a,prop:b}=null); // simple case
					({a,prop1:{b},prop2:[x,{c},y]}=null); // nesting cases
					for({a,prop1:{b},prop2:[x,{c},y]} in null){} // for..in loop
					for({a,prop1:{b},prop2:[x,{c},y]} of null){} // for..of loop
					""");
			assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSingleNameSyntax_negativeCases() {
		try {
			Script script = parseHelper.parse("x = {a=\"oof\"};");
			assertEquals(1, script.eResource().getErrors().size());
			assertEquals(
					"A default value is only allowed within a destructuring pattern.",
					script.eResource().getErrors().get(0).getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}