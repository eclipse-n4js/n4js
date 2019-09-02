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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.N4JSTestHelper
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static org.junit.Assert.*

/*
 * Tests for judgment subtype, see n4js.xsemantics for judgment, axiom and rules.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JudgmentSubtypeTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	@Inject
	extension ValidationTestHelper

	@Inject
	extension N4JSTestHelper

	@Test
	def void testSubtypeSametype() {

		// class A{}
		val script = '''
			class A{}
			var a1: A;
			var a2: A;
		'''.parseAndValidateSuccessfully()
//		val classDecl = script.scriptElements.head as N4ClassDeclaration
//		val AType = classDecl.definedType

		// subtypeType: A <: A
//		val result = ts.subtypeType(newRuleEnvironment(script), AType, AType)
//		assertSubtype(result, true)

		val A1 = script.scriptElements.get(1).variableStatementDeclaredType
		val A2 = script.scriptElements.get(2).variableStatementDeclaredType

		// subtypeTypeRef: A <: A
		val r2 = ts.subtype(newRuleEnvironment(script), A1, A2)
		assertSubtype(r2, true)
	}

	@Test
	def void testSubtypeUnknownType_01() {
		val script = '''
			var x: Unknown1
			var y: Unknown2
		'''.parse
		val lastTwoVars = script.scriptElements.reverseView.subList(0, 2)
		val firstType = lastTwoVars.head.variableStatementDeclaredType
		val secondType = lastTwoVars.last.variableStatementDeclaredType

		val r2 = ts.subtype(newRuleEnvironment(script), firstType, secondType)
		assertSubtype(r2, true)
	}

	@Test
	def void testSubtypeUnknownType_02() {
		val script = '''
			var x: String
			var y: Unknown
		'''.parse
		val lastTwoVars = script.scriptElements.reverseView.subList(0, 2)
		val firstType = lastTwoVars.head.variableStatementDeclaredType
		val secondType = lastTwoVars.last.variableStatementDeclaredType

		val r2 = ts.subtype(newRuleEnvironment(script), firstType, secondType)
		assertSubtype(r2, true)
	}

	@Test
	def void testSubtypeUnknownType_03() {
		val script = '''
			var x: Unknown
			var y: String
		'''.parse
		val lastTwoVars = script.scriptElements.reverseView.subList(0, 2)
		val firstType = lastTwoVars.head.variableStatementDeclaredType
		val secondType = lastTwoVars.last.variableStatementDeclaredType

		val r2 = ts.subtype(newRuleEnvironment(script), firstType, secondType)
		assertSubtype(r2, true)
	}


	@Test
	def void testSubtypeUnknownType_04() {
		val script = '''
			var s: String
		'''.parseAndValidateSuccessfully
		val given = script.scriptElements.head.variableStatementDeclaredType
		val unknown = TypeRefsFactory.eINSTANCE.createUnknownTypeRef

		val r1 = ts.subtype(newRuleEnvironment(script), given, unknown)
		assertSubtype(r1, true)

		val r2 = ts.subtype(newRuleEnvironment(script), unknown, given)
		assertSubtype(r2, true)

		val r3 = ts.subtype(newRuleEnvironment(script), unknown, TypeRefsFactory.eINSTANCE.createUnknownTypeRef)
		assertSubtype(r3, true)
	}

	@Test
	def void testSubtypeSubclass() {

		// class A{}
		val script = '''
			class A{}
			class B extends A{}
			class C extends B{}
			var a: A;
			var b: B;
			var c: C;
			var x: any;
		'''.parseAndValidateSuccessfully()

		val G = newRuleEnvironment(script);

//		val AType = (script.scriptElements.get(0) as N4ClassDeclaration).definedType
//		val BType = (script.scriptElements.get(1) as N4ClassDeclaration).definedType
//		val CType = (script.scriptElements.get(2) as N4ClassDeclaration).definedType

//		// subtypeType: B <: A
//		var result = ts.subtypeType(G, BType, AType)
//		assertSubtype(result, true)
//
//		// subtypeType: A <: B
//		result = ts.subtypeType(G, AType, BType)
//		assertSubtype(result, false)
//
//		// subtypeType: C <: A
//		result = ts.subtypeType(G, CType, AType)
//		assertSubtype(result, true)

		val A = script.scriptElements.get(3).variableStatementDeclaredType
		val B = script.scriptElements.get(4).variableStatementDeclaredType
		val C = script.scriptElements.get(5).variableStatementDeclaredType
		val any = script.scriptElements.get(6).variableStatementDeclaredType

		assertNotNull(any)

		// subtypeTypeRef: B <: A
		var result = ts.subtype(G, B, A)
		assertSubtype(result, true)

		// subtypeTypeRef: C <: A
		result = ts.subtype(G, C, A)
		assertSubtype(result, true)

		// subtypeTypeRef: A <: any
		result = ts.subtype(G, A, any)
		assertSubtype(result, true)

	}

	@Test
	def void testFunctionDecl() {
		// class A{}
		val script = '''
			class A{}
			function foo(): A { return new A(); }
			class B extends A {}
			function bar(): A { return new B(); }
			function baz(): B { return new A(); }
		'''.parse()

		val issues = script.validate();
//		println(issues)
		assertEquals(1, issues.size) // TODO improve issue checking
	}

	@Test
	def void testSubtypeFunctionRefs() {
		// class A{}
		val script = '''
			var f1: {function()};
			var f2: {function()};
		'''.parseAndValidateSuccessfully()

		val f1 = script.scriptElements.get(0).variableStatementDeclaredType
		val f2 = script.scriptElements.get(0).variableStatementDeclaredType

		val G = newRuleEnvironment(script);
		var result = ts.subtype(G, f1, f2);
		assertSubtype(result, true)
	}

	@Test
	def void testSubtypeConstructorTypeRefWithWildcard() {
		val script = '''
			class C {}
			let ctor: constructor{? extends C};
		'''.parseAndValidateSuccessfully()

		val ctorTypeRefFromAST = script.eAllContents.filter(TypeTypeRef).head;
		assertTrue(ctorTypeRefFromAST.isConstructorRef);

		val G = newRuleEnvironment(script);
		val open = TypeUtils.copy(ctorTypeRefFromAST);
		val closed1 = ts.substTypeVariablesWithCapture(G, open) as TypeTypeRef;
		val closed2 = ts.substTypeVariablesWithCapture(G, open) as TypeTypeRef;
		val reopened = ts.reopenExistentialTypes(G, closed1) as TypeTypeRef;

		val openCpy = TypeUtils.copy(open);
		val closed1Cpy = TypeUtils.copy(closed1);

		// ensure our tests data (i.e. type references open, closed1, etc.) has exactly the properties we want to test:
		assertNotSame(closed1, open);
		assertNotSame(closed2, open);
		assertNotSame(openCpy, open);
		assertNotSame(closed1Cpy, closed1);
		assertNotSame(reopened, closed1);
		assertTrue(open.typeArg instanceof Wildcard);
		assertFalse((closed1.typeArg as ExistentialTypeRef).reopened);
		assertFalse((closed1Cpy.typeArg as ExistentialTypeRef).reopened);
		assertFalse((closed2.typeArg as ExistentialTypeRef).reopened);
		assertTrue((reopened.typeArg as ExistentialTypeRef).reopened);
		assertNotEquals((closed1.typeArg as ExistentialTypeRef).id, (closed2.typeArg as ExistentialTypeRef).id);
		assertEquals((closed1Cpy.typeArg as ExistentialTypeRef).id, (closed1.typeArg as ExistentialTypeRef).id);

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
