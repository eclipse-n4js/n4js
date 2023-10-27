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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.WildcardCaptureTestHelper;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.tests.issues.IssueUtils;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for judgment subtype, see n4js.xsemantics for judgment, axiom and rules.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class JudgmentSubtypeTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

	@Inject
	private WildcardCaptureTestHelper wildcardCaptureTestHelper;

	@Test
	public void testSubtypeSametype() {

		// class A{}
		Script script = n4TestHelper.parseAndValidateSuccessfully("""
				class A{}
				var a1: A;
				var a2: A;
				""");
		// val classDecl = script.getScriptElements().head ;
		// val AType = classDecl.getDefinedType();

		// subtypeType: A <: A
		// val result = ts.subtypeType(newRuleEnvironment(script), AType, AType);
		// assertSubtype(result, true);

		TypeRef A1 = variableStatementDeclaredType(script.getScriptElements().get(1));
		TypeRef A2 = variableStatementDeclaredType(script.getScriptElements().get(2));

		// subtypeTypeRef: A <: A
		Result r2 = ts.subtype(newRuleEnvironment(script), A1, A2);
		assertSubtype(r2, true);
	}

	@Test
	public void testSubtypeUnknownType_01() throws Exception {
		Script script = parseHelper.parse("""
				var x: Unknown1
				var y: Unknown2
				""");
		assertEquals(List.of(
				"ERROR:Couldn't resolve reference to Type 'Unknown1'. (__synthetic0.n4js line : 1 column : 8)",
				"ERROR:Couldn't resolve reference to Type 'Unknown2'. (__synthetic0.n4js line : 2 column : 8)"),
				toList(map(valTestHelper.validate(script), issue -> IssueUtils.toString(issue))));

		List<ScriptElement> lastTwoVars = ListExtensions.reverseView(script.getScriptElements()).subList(0, 2);
		TypeRef firstType = variableStatementDeclaredType(head(lastTwoVars));
		TypeRef secondType = variableStatementDeclaredType(last(lastTwoVars));

		Result r2 = ts.subtype(newRuleEnvironment(script), firstType, secondType);
		assertSubtype(r2, true);
	}

	@Test
	public void testSubtypeUnknownType_02() throws Exception {
		Script script = parseHelper.parse("""
				var x: String
				var y: Unknown
				""");
		assertEquals(List.of(
				"ERROR:Couldn't resolve reference to Type 'Unknown'. (__synthetic0.n4js line : 2 column : 8)"),
				toList(map(valTestHelper.validate(script), issue -> IssueUtils.toString(issue))));

		List<ScriptElement> lastTwoVars = ListExtensions.reverseView(script.getScriptElements()).subList(0, 2);
		TypeRef firstType = variableStatementDeclaredType(head(lastTwoVars));
		TypeRef secondType = variableStatementDeclaredType(last(lastTwoVars));

		Result r2 = ts.subtype(newRuleEnvironment(script), firstType, secondType);
		assertSubtype(r2, true);
	}

	@Test
	public void testSubtypeUnknownType_03() throws Exception {
		Script script = parseHelper.parse("""
				var x: Unknown
				var y: String
				""");
		assertEquals(List.of(
				"ERROR:Couldn't resolve reference to Type 'Unknown'. (__synthetic0.n4js line : 1 column : 8)"),
				toList(map(valTestHelper.validate(script), issue -> IssueUtils.toString(issue))));

		List<ScriptElement> lastTwoVars = ListExtensions.reverseView(script.getScriptElements()).subList(0, 2);
		TypeRef firstType = variableStatementDeclaredType(head(lastTwoVars));
		TypeRef secondType = variableStatementDeclaredType(last(lastTwoVars));

		Result r2 = ts.subtype(newRuleEnvironment(script), firstType, secondType);
		assertSubtype(r2, true);
	}

	@Test
	public void testSubtypeUnknownType_04() {
		Script script = n4TestHelper.parseAndValidateSuccessfully("""
				var s: String
				""");
		TypeRef given = variableStatementDeclaredType(script.getScriptElements().get(0));
		UnknownTypeRef unknown = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();

		Result r1 = ts.subtype(newRuleEnvironment(script), given, unknown);
		assertSubtype(r1, true);

		Result r2 = ts.subtype(newRuleEnvironment(script), unknown, given);
		assertSubtype(r2, true);

		Result r3 = ts.subtype(newRuleEnvironment(script), unknown, TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
		assertSubtype(r3, true);
	}

	@Test
	public void testSubtypeSubclass() {

		// class A{}
		Script script = n4TestHelper.parseAndValidateSuccessfully("""
				class A{}
				class B extends A{}
				class C extends B{}
				var a: A;
				var b: B;
				var c: C;
				var x: any;
				""");

		RuleEnvironment G = newRuleEnvironment(script);

		// val AType = ((N4ClassDeclaration)script.getScriptElements().get(0) ).getDefinedType();
		// val BType = ((N4ClassDeclaration)script.getScriptElements().get(1) ).getDefinedType();
		// val CType = ((N4ClassDeclaration)script.getScriptElements().get(2) ).getDefinedType();

		// // subtypeType: B <: A
		// TypeRef result = ts.subtypeType(G, BType, AType);
		// assertSubtype(result, true);
		//
		// // subtypeType: A <: B
		// result = ts.subtypeType(G, AType, BType)
		// assertSubtype(result, false);
		//
		// // subtypeType: C <: A
		// result = ts.subtypeType(G, CType, AType)
		// assertSubtype(result, true);

		TypeRef A = variableStatementDeclaredType(script.getScriptElements().get(3));
		TypeRef B = variableStatementDeclaredType(script.getScriptElements().get(4));
		TypeRef C = variableStatementDeclaredType(script.getScriptElements().get(5));
		TypeRef any = variableStatementDeclaredType(script.getScriptElements().get(6));

		assertNotNull(any);

		// subtypeTypeRef: B <: A
		Result result = ts.subtype(G, B, A);
		assertSubtype(result, true);

		// subtypeTypeRef: C <: A
		result = ts.subtype(G, C, A);
		assertSubtype(result, true);

		// subtypeTypeRef: A <: any
		result = ts.subtype(G, A, any);
		assertSubtype(result, true);

	}

	@Test
	public void testFunctionDecl() throws Exception {
		// class A{}
		Script script = parseHelper.parse("""
				class A{}
				function foo(): A { return new A(); }
				class B extends A {}
				function bar(): A { return new B(); }
				function baz(): B { return new A(); }
				""");

		List<Issue> issues = valTestHelper.validate(script);
		// println(issues)
		assertEquals(1, issues.size());// TODO improve issue checking;
	}

	@Test
	public void testSubtypeFunctionRefs() {
		// class A{}
		Script script = n4TestHelper.parseAndValidateSuccessfully("""
				var f1: {function()};
				var f2: {function()};
				""");

		TypeRef f1 = variableStatementDeclaredType(script.getScriptElements().get(0));
		TypeRef f2 = variableStatementDeclaredType(script.getScriptElements().get(0));

		RuleEnvironment G = newRuleEnvironment(script);
		Result result = ts.subtype(G, f1, f2);
		assertSubtype(result, true);
	}

	@Test
	public void testSubtypeConstructorTypeRefWithWildcard() {
		Script script = n4TestHelper.parseAndValidateSuccessfully("""
				class C {}
				let ctor: constructor{? extends C};
				""");

		TypeTypeRef ctorTypeRefFromAST = head(filter(script.eAllContents(), TypeTypeRef.class));
		assertTrue(ctorTypeRefFromAST.isConstructorRef());

		RuleEnvironment G = newRuleEnvironment(script);
		TypeTypeRef open = TypeUtils.copy(ctorTypeRefFromAST);
		TypeTypeRef closed1 = (TypeTypeRef) ts.substTypeVariablesWithFullCapture(G, open);
		TypeTypeRef closed2 = (TypeTypeRef) ts.substTypeVariablesWithFullCapture(G, open);
		TypeTypeRef reopened = (TypeTypeRef) wildcardCaptureTestHelper.reopenExistentialTypes(closed1);

		TypeTypeRef openCpy = TypeUtils.copy(open);
		TypeTypeRef closed1Cpy = TypeUtils.copy(closed1);

		// ensure our tests data (i.e. type references open, closed1, etc.) has exactly the properties we want to test:
		assertNotSame(closed1, open);
		assertNotSame(closed2, open);
		assertNotSame(openCpy, open);
		assertNotSame(closed1Cpy, closed1);
		assertNotSame(reopened, closed1);
		assertTrue(open.getTypeArg() instanceof Wildcard);
		assertFalse(((ExistentialTypeRef) closed1.getTypeArg()).isReopened());
		assertFalse(((ExistentialTypeRef) closed1Cpy.getTypeArg()).isReopened());
		assertFalse(((ExistentialTypeRef) closed2.getTypeArg()).isReopened());
		assertTrue(((ExistentialTypeRef) reopened.getTypeArg()).isReopened());
		assertNotEquals(((ExistentialTypeRef) closed1.getTypeArg()).getId(),
				((ExistentialTypeRef) closed2.getTypeArg()).getId());
		assertEquals(((ExistentialTypeRef) closed1Cpy.getTypeArg()).getId(),
				((ExistentialTypeRef) closed1.getTypeArg()).getId());

		// now the actual tests:

		assertTrue(ts.subtypeSucceeded(G, openCpy, open));
		assertTrue(ts.subtypeSucceeded(G, closed1, open));
		assertTrue(ts.subtypeSucceeded(G, closed2, open));
		assertTrue(ts.subtypeSucceeded(G, reopened, open));

		assertFalse(ts.subtypeSucceeded(G, open, closed1));
		assertTrue(ts.subtypeSucceeded(G, closed1Cpy, closed1));
		assertFalse(ts.subtypeSucceeded(G, closed2, closed1));
		assertFalse(ts.subtypeSucceeded(G, reopened, closed1));

		assertTrue(ts.subtypeSucceeded(G, openCpy, reopened));
		assertTrue(ts.subtypeSucceeded(G, closed1, reopened));
		assertTrue(ts.subtypeSucceeded(G, closed2, reopened));
		assertTrue(ts.subtypeSucceeded(G, reopened, reopened));
	}
}
