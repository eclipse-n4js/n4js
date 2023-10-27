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
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * N4JS Spec Test: 4.15 This Type
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
@Log
public class AT_802_ThisTypeAnnotationTest extends AbstractTypesystemTest {

	@Test
	public void testMethodWithoutDeclaredThisType() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A {
					f(): this {
						return null;
					}
				}
				var a: A = new A();
				var x = a.f();
				""");

		N4MethodDeclaration fundef = EcoreUtil2.getAllContentsOfType(script, N4MethodDeclaration.class).get(0);

		TMethod tmethF = (TMethod) fundef.getDefinedType();
		assertNull("No @This at f() but declaredThisType != null", tmethF.getDeclaredThisType());

		FunctionTypeExprOrRef tfun = (FunctionTypeExprOrRef) infer(fundef);
		assertNull("No @This at f() but on inferred type saw declaredThisType != null", tfun.getDeclaredThisType());

		assertNoValidationErrors(script);
	}

	@Test
	public void testFunctionWithDeclaredThisType() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A {
					public a: number;
				}
				@This(A)
				function f () { this.a += 1 };
				@This(~A with {s: string;})
				function f1 () { this.a += 1; this.s = "hallo"; }
				""");

		List<FunctionDeclaration> functionDeclarations = EcoreUtil2.getAllContentsOfType(script,
				FunctionDeclaration.class);
		FunctionDeclaration fundecl = functionDeclarations.get(0);

		TFunction tfun = (TFunction) fundecl.getDefinedType();
		assertDeclaredThisType(tfun, "A");

		FunctionTypeExprOrRef fte = (FunctionTypeExprOrRef) infer(fundecl);
		assertDeclaredThisType(fte, "A");

		FunctionDeclaration fundecl1 = functionDeclarations.get(1);
		TFunction tfun1 = (TFunction) fundecl1.getDefinedType();
		assertDeclaredThisType(tfun1, "~A with { s: string }");

		FunctionTypeExprOrRef fte1 = (FunctionTypeExprOrRef) infer(fundecl1);
		assertDeclaredThisType(fte1, "~A with { s: string }");
		assertNoValidationErrors(script);
	}

	@Test
	public void test_X() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class A {}
				class B{ dumpMe: {@This(B) function():void}; }
				var genericdumper: { @This(union{A,B}) function():void };
				var b: B;
				b.dumpMe = genericdumper;
					""");
		var assignmentExpr = ((AssignmentExpression) ((ExpressionStatement) script.getScriptElements().get(4))
				.getExpression());
		ParameterizedPropertyAccessExpression bmethodRef = (ParameterizedPropertyAccessExpression) assignmentExpr
				.getLhs();
		IdentifierRef gmethodRef = (IdentifierRef) assignmentExpr.getRhs();

		TypeRef infTypeB = infer(bmethodRef);
		TypeRef infTypeGen = infer(gmethodRef);

		assertSubtype(newRuleEnvironment(script), infTypeGen, infTypeB, true);

		assertNoValidationErrors(script);
	}

	@Test
	public void test_01() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class X { x: X; }
				@This(X) function f() {
					this.x // X.x
				}
				""");
		FunctionDeclaration fdecl = (FunctionDeclaration) script.getScriptElements().get(1);
		assertDeclaredThisType((FunctionTypeExprOrRef) infer(fdecl), "X");
		assertNoValidationErrors(script);
	}

	@Test
	public void test_02() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class X { x: X; }
				var x: X
				@This(X) function f() {
					var nested = function() {
						this.x // global var x
					}
				}
				""");
		assertDeclaredThisType((TFunction) ((FunctionDeclaration) script.getScriptElements().get(2)).getDefinedType(),
				"X");
	}

	@Test
	public void test_03() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class X { x: X; }
				var x: X
				function f() {
					var nested = @This(X) function() {
						this.x // member X.x
					}
				}
				""");

		FunctionExpression fe = EcoreUtil2.eAllOfType(script, FunctionExpression.class).get(0);

		assertDeclaredThisType((TFunction) fe.getDefinedType(), "X");

		assertNoValidationErrors(script);
	}

	@Test
	public void test_04() {
		Script script = createScript(JavaScriptVariant.n4js, """
				class Y extends X { y: Y; }
				class X {
					@This(Y)
					m(): any {
						this.y
						return null;
					}
				}
				""");

		N4MethodDeclaration methDeclM = ((N4ClassDeclaration) script.getScriptElements().get(1)).getOwnedMethods()
				.get(0);
		assertDeclaredThisType((TMethod) methDeclM.getDefinedType(), "Y");
		assertNoValidationErrors(script);
	}

	@Test
	public void test_05() {
		Script script = createScript(JavaScriptVariant.n4js, """
				function f() {
					var nested = @This(~Object with{a: any;}) function() {
						this.a // member record.a
					}
				}
				""");

		FunctionExpression fe = EcoreUtil2.eAllOfType(script, FunctionExpression.class).get(0);

		assertDeclaredThisType((TFunction) fe.getDefinedType(), "~Object with { a: any }");

		assertNoValidationErrors(script);
	}

	@Test
	public void testAnnotatedFunctionExpressionAsParameter() {
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						console.log(
						@This(any)
						function <S extends Object, T, P> len(s: S, t: T, p: P, a, n: number=undefined, ...vas: string) : number {
							return (s+t+p+a+n+vas).length
						}
						);
						""");

		List<Expression> argToLog = toList(
				map(((ParameterizedCallExpression) ((ExpressionStatement) script.getScriptElements().get(0))
						.getExpression()).getArguments(), arg -> arg.getExpression()));

		assertEquals("Only one arg.", 1, argToLog.size());

		FunctionExpression fe = (FunctionExpression) argToLog.get(0);
		TypeRef inf = infer(fe);
		assertEquals("{@This(any) function<S extends Object,T,P>(S,T,P,any,number=…,...string):number}",
				inf.getTypeRefAsString());

		assertDeclaredThisType(inf, "any");

		assertNoValidationErrors(script);
	}

	void assertDeclaredThisType(EObject tf, String expectedDeclType) {
		if (tf instanceof TFunction) {
			assertDeclaredThisType((TFunction) tf, expectedDeclType);
		}
		if (tf instanceof FunctionTypeExprOrRef) {
			assertDeclaredThisType((FunctionTypeExprOrRef) tf, expectedDeclType);
		}
	}

	void assertDeclaredThisType(TFunction tf, String expectedDeclType) {
		TypeRef dtt = tf.getDeclaredThisType();
		assertEquals(expectedDeclType, dtt == null ? null : dtt.getTypeRefAsString());
	}

	void assertDeclaredThisType(FunctionTypeExprOrRef tf, String expectedDeclType) {
		TypeRef dtt = tf.getDeclaredThisType();
		assertEquals(expectedDeclType, dtt == null ? null : dtt.getTypeRefAsString());
	}

	/*
	 * Tests that the declared this type of a given element equals the expected name,
	 */
	public void assertDeclaredThisTypeNameOnInferredType(String expectedTypeName,
			TypableElement elementToBeTypeInferred) {

		TypeRef result = infer(elementToBeTypeInferred);

		assertNotNull("No type inferred", result);
		assertEquals("Wrong type inferred", expectedTypeName, result.getTypeRefAsString());
	}

	TypeRef infer(TypableElement elementToBeTypeInferred) {
		RuleEnvironment G = newRuleEnvironment(elementToBeTypeInferred);
		TypeRef result = ts.type(G, elementToBeTypeInferred);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		return result;
	}
}
