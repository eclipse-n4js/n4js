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

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static org.junit.Assert.*

/**
 * N4JS Spec Test: 4.15 This Type
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
@Log
class AT_802_ThisTypeAnnotationTest extends AbstractTypesystemTest {

	@Test
	def void testMethodWithoutDeclaredThisType() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class A {
				f(): this {
					return null;
				}
			}
			var a: A = new A();
			var x = a.f();
		''')

		val fundef = EcoreUtil2.getAllContentsOfType(script, N4MethodDeclaration).head;

		val tmethF = fundef.definedType as TMethod
		assertNull("No @This at f() but declaredThisType != null", tmethF.declaredThisType )

		val tfun = fundef.infer as FunctionTypeExprOrRef
		assertNull("No @This at f() but on inferred type saw declaredThisType != null", tfun.declaredThisType )

		assertNoValidationErrors(script);
	}


	@Test
	def void testFunctionWithDeclaredThisType() {
		val script = createScript(JavaScriptVariant.n4js, '''
			class A {
				public a: number;
			}
			@This(A)
			function f () { this.a += 1 };
			@This(~A with {s: string;})
			function f1 () { this.a += 1; this.s = "hallo"; }
		''')

		val functionDeclarations = EcoreUtil2.getAllContentsOfType(script, FunctionDeclaration);
		val fundecl = functionDeclarations.get(0)

		val tfun = fundecl.definedType as TFunction
		tfun.assertDeclaredThisType("A")

		val fte = fundecl.infer as FunctionTypeExprOrRef
		fte.assertDeclaredThisType("A");

		val fundecl1 = functionDeclarations.get(1)
		val tfun1 = fundecl1.definedType as TFunction
		tfun1.assertDeclaredThisType("~A with { s: string }")

		val fte1 = fundecl1.infer as FunctionTypeExprOrRef
		fte1.assertDeclaredThisType("~A with { s: string }");
		assertNoValidationErrors(script);
	}

	@Test
	def void test_X(){
		val script = createScript(JavaScriptVariant.n4js,'''
		class A {}
		class B{ dumpMe: {@This(B) function():void}; }
		var genericdumper: { @This(union{A,B}) function():void };
		var b: B;
		b.dumpMe = genericdumper;
		''')
		var assignmentExpr = ((script.scriptElements.get(4) as ExpressionStatement).expression as AssignmentExpression)
		var bmethodRef = assignmentExpr.lhs as ParameterizedPropertyAccessExpression
		var gmethodRef = assignmentExpr.rhs as IdentifierRef

		var infTypeB = bmethodRef.infer
		var infTypeGen = gmethodRef.infer

		assertSubtype( newRuleEnvironment(script), infTypeGen, infTypeB, true );

		assertNoValidationErrors(script);
	}


	@Test
	def void test_01() {
		val script = createScript(JavaScriptVariant.n4js,'''
			class X { x: X; }
			@This(X) function f() {
				this.x // X.x
			}
		''')
		val fdecl = script.scriptElements.get(1) as FunctionDeclaration
		assertDeclaredThisType( fdecl.infer as FunctionTypeExprOrRef,"X" )
		assertNoValidationErrors(script);
	}

	@Test
//	@Ignore("see IDE-496")
	def void test_02() {
		val script = createScript(JavaScriptVariant.n4js,'''
			class X { x: X; }
			var x: X
			@This(X) function f() {
				var nested = function() {
					this.x // global var x
				}
			}
		''')
		assertDeclaredThisType((script.scriptElements.get(2) as FunctionDeclaration).definedType as TFunction,"X")
		// @Ignore("see IDE-496"): assertNoValidationErrors(script);
	}

	@Test
	def void test_03() {
		val script = createScript(JavaScriptVariant.n4js,'''
			class X { x: X; }
			var x: X
			function f() {
				var nested = @This(X) function() {
					this.x // member X.x
				}
			}
		''')

		val fe = EcoreUtil2.eAllOfType(script,FunctionExpression).head

		assertDeclaredThisType(fe.definedType as TFunction,"X")

		assertNoValidationErrors(script);
	}

	@Test
	def void test_04() {
		val script = createScript(JavaScriptVariant.n4js,'''
			class Y extends X { y: Y; }
			class X {
				@This(Y)
				m(): any {
					this.y
					return null;
				}
			}
		''')

		val methDeclM = (script.scriptElements.get(1) as N4ClassDeclaration).ownedMethods.get(0);
		assertDeclaredThisType( methDeclM.definedType as TMethod, "Y")
		assertNoValidationErrors(script);
	}

	@Test
	def void test_05() {
		val script = createScript(JavaScriptVariant.n4js,'''
			function f() {
				var nested = @This(~Object with{a: any;}) function() {
					this.a // member record.a
				}
			}
		''')

		val fe = EcoreUtil2.eAllOfType(script,FunctionExpression).head

		assertDeclaredThisType(fe.definedType as TFunction,"~Object with { a: any }")

		assertNoValidationErrors(script);
	}

	@Test
	def void testAnnotatedFunctionExpressionAsParameter(){
		val script = createScript(JavaScriptVariant.n4js, '''
			console.log(
			@This(any)
			function <S extends Object, T, P> len(s: S, t: T, p: P, a, n: number=undefined, ...vas: string) : number {
				return (s+t+p+a+n+vas).length
			}
			);
		''')

		val argToLog = ((script.scriptElements.get(0) as ExpressionStatement).expression as ParameterizedCallExpression).arguments.map[expression]

		assertEquals("Only one arg.",1,argToLog.size)

		val fe = (argToLog.get(0) as FunctionExpression)
		val inf = fe.infer;
		assertEquals("{@This(any) function<S extends Object,T,P>(S,T,P,any,number=…,...string):number}",inf.typeRefAsString)

		inf.assertDeclaredThisType("any")

		script.assertNoValidationErrors
	}

	def dispatch void assertDeclaredThisType(TFunction tf, String expectedDeclType) {
		assertEquals(expectedDeclType, tf.declaredThisType?.typeRefAsString)
	}
	def dispatch void assertDeclaredThisType(FunctionTypeExprOrRef tf, String expectedDeclType) {
		assertEquals(expectedDeclType, tf.declaredThisType?.typeRefAsString)
	}

	/*
	 * Tests that the declared this type of a given element equals the expected name,
	 */
	def void assertDeclaredThisTypeNameOnInferredType(String expectedTypeName, TypableElement elementToBeTypeInferred) {

		val result = elementToBeTypeInferred.infer()

		assertNotNull("No type inferred", result)
		assertEquals("Wrong type inferred", expectedTypeName, result.typeRefAsString)
	}

	def TypeRef infer(TypableElement elementToBeTypeInferred) {
		val G = newRuleEnvironment(elementToBeTypeInferred);
		val result = ts.type(G, elementToBeTypeInferred);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		return result;
	}
}
