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
package org.eclipse.n4js.tests.parser

import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.n4JS.ReturnStatement
import org.junit.Test

class ES_11_01_5_ObjectInitializerEsprimaTest extends AbstractParserTest {

	@Test
	def void testEmpty_01() {
		val script = 'x = {}'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		assertTrue(objectLiteral.propertyAssignments.empty)
	}

	@Test
	def void testEmpty_02() {
		val script = 'x = { }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		assertTrue(objectLiteral.propertyAssignments.empty)
	}

	@Test
	def void testSimplePropertyNameValuePair_01() {
		val script = 'x = { answer: 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals("answer", answer.name)
		assertEquals(PropertyNameKind.IDENTIFIER, answer.declaredName.kind)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testSimplePropertyNameValuePair_01_TrailingComma() {
		val script = 'x = { answer: 42, }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals("answer", answer.name)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testSimplePropertyNameValuePair_01_TooManyTrailingCommas() {
		'x = { answer: 42,, }'.parseESWithError
	}

	@Test
	def void testSimplePropertyNameValuePair_02() {
		val script = 'x = { get: 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val get = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals("get", get.name)
		assertEquals(42, (get.expression as IntLiteral).toInt)
	}

	@Test
	def void testSimplePropertyNameValuePair_03() {
		val script = 'x = { set: 43 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val set = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.IDENTIFIER, set.declaredName.kind)
		assertEquals("set", set.name)
		assertEquals(43, (set.expression as IntLiteral).toInt)
	}

	@Test
	def void testSimplePropertyNameValuePair_04() {
		val script = 'x = { __proto__: 2 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val proto = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals("__proto__", proto.name)
		assertEquals(2, (proto.expression as IntLiteral).toInt)
	}

	@Test
	def void testNameIsKeyword_01() {
		val script = 'x = { if: 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.IDENTIFIER, answer.declaredName.kind)
		assertEquals("if", answer.name)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testNameIsKeyword_02() {
		val script = 'x = { true: 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.IDENTIFIER, answer.declaredName.kind)
		assertEquals("true", answer.name)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testNameIsKeyword_03() {
		val script = 'x = { false: 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.IDENTIFIER, answer.declaredName.kind)
		assertEquals("false", answer.name)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testNameIsKeyword_04() {
		val script = 'x = { null: 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.IDENTIFIER, answer.declaredName.kind)
		assertEquals("null", answer.name)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testNameIsString_01() {
		val script = 'x = { "answer": 42 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val answer = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.STRING, answer.declaredName.kind)
		assertEquals("answer", answer.name)
		assertEquals(42, (answer.expression as IntLiteral).toInt)
	}

	@Test
	def void testNameIsString_02() {
		val script = 'x = { "__proto__": 2 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val proto = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals(PropertyNameKind.STRING, proto.declaredName.kind)
		assertEquals("__proto__", proto.name)
		assertEquals(2, (proto.expression as IntLiteral).toInt)
	}

	@Test
	def void testTwoProperties() {
		val script = 'x = { x: 1, x: 2 }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val first = objectLiteral.propertyAssignments.head as PropertyNameValuePair
		assertEquals("x", first.name)
		assertEquals(1, (first.expression as IntLiteral).toInt)
		val last = objectLiteral.propertyAssignments.last as PropertyNameValuePair
		assertEquals("x", last.name)
		assertEquals(2, (last.expression as IntLiteral).toInt)
	}

	@Test
	def void testGetMethod_01() {
		val script = 'x = { get width() { return m_width } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("width", getter.name)
		val body = getter.body
		val returnStatement = body.statements.head as ReturnStatement
		val returnValue = returnStatement.expression as IdentifierRef
		assertEquals("m_width", returnValue.text)
	}

	@Test
	def void testGetMethod_02() {
		val script = 'x = { get undef() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("undef", getter.name)
		val body = getter.body
		assertTrue(body.statements.empty)
	}

	@Test
	def void testGetMethodNameIsKeyword_01() {
		val script = 'x = { get if() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("if", getter.name)
		val body = getter.body
		assertEquals(true, body.statements.empty)
	}

	@Test
	def void testGetMethodNameIsKeyword_02() {
		val script = 'x = { get true() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("true", getter.name)
		val body = getter.body
		assertEquals(true, body.statements.empty)
	}

	@Test
	def void testGetMethodNameIsKeyword_03() {
		val script = 'x = { get false() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("false", getter.name)
		val body = getter.body
		assertEquals(true, body.statements.empty)
	}

	@Test
	def void testGetMethodNameIsKeyword_04() {
		val script = 'x = { get null() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("null", getter.name)
		val body = getter.body
		assertEquals(true, body.statements.empty)
	}

	@Test
	def void testGetMethodNameIsString_01() {
		val script = 'x = { get "undef"() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("undef", getter.name)
		val body = getter.body
		assertTrue(body.statements.empty)
	}

	@Test
	def void testGetMethodNameIsIntLiteral_01() {
		val script = 'x = { get 10() {} }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals(PropertyNameKind.NUMBER, getter.declaredName.kind)
		assertEquals("10", getter.name)
		val body = getter.body
		assertTrue(body.statements.empty)
	}

	@Test
	def void testSetMethod_01() {
		val script = 'x = { set width(w) { m_width = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals("width", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_width", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testSetMethodNameIsKeyword_01() {
		val script = 'x = { set if(w) { m_if = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals("if", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_if", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testSetMethodNameIsKeyword_02() {
		val script = 'x = { set true(w) { m_true = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals("true", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_true", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testSetMethodNameIsKeyword_03() {
		val script = 'x = { set false(w) { m_false = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals("false", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_false", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testSetMethodNameIsKeyword_04() {
		val script = 'x = { set null(w) { m_null = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals("null", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_null", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testSetMethodNameIsString() {
		val script = 'x = { set "null"(w) { m_null = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals("null", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_null", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testSetMethodNameIsIntValue() {
		val script = 'x = { set 10(w) { m_null = w } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		val setter = objectLiteral.propertyAssignments.head as PropertySetterDeclaration
		assertEquals(PropertyNameKind.NUMBER, setter.declaredName.kind)
		assertEquals("10", setter.name)
		assertEquals("w", setter.fpar.name)
		val body = setter.body
		val onlyStatement = body.statements.head as ExpressionStatement
		val innerAssignment = onlyStatement.expression as AssignmentExpression
		assertEquals("m_null", (innerAssignment.lhs as IdentifierRef).text)
		assertEquals("w", (innerAssignment.rhs as IdentifierRef).text)
	}

	@Test
	def void testGetAndSetMethodSameName() {
		val script = 'x = { get width() { return m_width }, set width(width) { m_width = width; } }'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val assignment = statement.expression as AssignmentExpression
		val identifier = assignment.lhs as IdentifierRef
		assertEquals('x', identifier.text)
		val objectLiteral = assignment.rhs as ObjectLiteral
		assertEquals(2, objectLiteral.propertyAssignments.size)
		val getter = objectLiteral.propertyAssignments.head as PropertyGetterDeclaration
		assertEquals("width", getter.name)
		val setter = objectLiteral.propertyAssignments.last as PropertySetterDeclaration
		assertEquals("width", setter.name)
		assertEquals("width", setter.fpar.name)
	}

	@Test
	def void testSingleNameSyntax_positiveCases() {
		val script = 'x = {a,prop:b};'.parse
		assertTrue(script.eResource.errors.toString,script.eResource.errors.empty);
	}

	@Test
	def void testSingleNameSyntax_positiveCases_destructuring() {
		val script = '''
			({a,prop:b}=null); // simple case
			({a,prop1:{b},prop2:[x,{c},y]}=null); // nesting cases
			for({a,prop1:{b},prop2:[x,{c},y]} in null){} // for..in loop
			for({a,prop1:{b},prop2:[x,{c},y]} of null){} // for..of loop
		'''.parse
		assertTrue(script.eResource.errors.toString,script.eResource.errors.empty);
	}

	@Test
	def void testSingleNameSyntax_negativeCases() {
		val script = 'x = {a="oof"};'.parse
		assertEquals(1,script.eResource.errors.size)
		assertEquals(
				"A default value is only allowed within a destructuring pattern.",
				script.eResource.errors.get(0).message)
	}
}
