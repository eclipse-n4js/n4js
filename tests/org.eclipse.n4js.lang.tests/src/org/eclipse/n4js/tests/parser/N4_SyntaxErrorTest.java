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

import static org.eclipse.n4js.validation.IssueCodes.AST_INVALID_FOR_AWAIT;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.linking.impl.XtextLinkingDiagnostic;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.impl.InvariantChecker;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;

public class N4_SyntaxErrorTest extends AbstractParserTest {

	@Test
	public void testSyntaxErrorInClassBody() throws Exception {
		Script script = parseHelper.parse("""
					public class C {
						c: C = syntax error
					}
				""");

		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		N4ClassDeclaration n4Class = (N4ClassDeclaration) script.getScriptElements().get(0);
		N4FieldDeclaration n4Field = (N4FieldDeclaration) n4Class.getOwnedMembers().get(0);
		assertEquals("c", n4Field.getName());
	}

	@Test
	public void testSyntaxErrorInRegExLiteral_01() throws Exception {
		Script script = parseHelper.parse("""
					{ /* empty block followed by unclosed regex*/ } /

				""");

		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		ExpressionStatement stmt = (ExpressionStatement) last(script.getScriptElements());
		RegularExpressionLiteral regEx = (RegularExpressionLiteral) stmt.getExpression();
		assertEquals("/", regEx.getValue());
		Diagnostic diagnostic = script.eResource().getErrors().get(0);
		assertEquals("Invalid regular expression literal", diagnostic.getMessage());
	}

	@Test
	public void testSyntaxErrorInRegExLiteral_02() throws Exception {
		Script script = parseHelper.parse("""
					{ /* empty block followed by unclosed regex*/ } /=

				""");

		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		ExpressionStatement stmt = (ExpressionStatement) last(script.getScriptElements());
		RegularExpressionLiteral regEx = (RegularExpressionLiteral) stmt.getExpression();
		assertEquals("/=", regEx.getValue());
		Diagnostic diagnostic = script.eResource().getErrors().get(0);
		assertEquals("Invalid regular expression literal", diagnostic.getMessage());
	}

	@Test
	public void testBrowserTests_MissingParentheses() throws Exception {
		Script script = parseHelper.parse("""
					YUI.add("browser-tests", function(Y) {

				    var suite = new Y.Test.Suite("YUI: Core Browser");

				    suite.add(new Y.Test.Case({
				        test_attach_after: function() {
				            var Assert = Y.Assert;
				            YUI.add("after-test", function(Y) {
				                Y.afterTest = true;
				                Assert.isObject(Y.Node, "Node not loaded before this module");
				            }, "1.0.0", {
				                after: [ "node" ]
				            });

				            YUI().use("after-test", function(Y2) {
				                Assert.isTrue(Y2.afterTest, "after-test module was not loaded");
				            });
				        },
				        test_rollup_false: function() {
				            var Assert = Y.Assert;
				            YUI().use("dd", function(Y) {
				                Assert.isUndefined(Y.Env._attached.dd, "DD Alias Module was attached");
				            });
				            YUI().use("node", function(Y) {
				                Assert.isUndefined(Y.Env._attached.node, "Node Alias Module was attached");
				            });
				        }
				    });

				    Y.Test.Runner.add(suite);

				});
				""");

		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Diagnostic diagnostic = script.eResource().getErrors().get(0);
		assertEquals("missing ')' at ';'", diagnostic.getMessage());
	}

	@Test
	public void testIDEBUG_5_01() throws Exception {
		Script script = parseHelper.parse("""
					var A<?
				""");

		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testIDEBUG_5_02() throws Exception {
		Script script = parseHelper.parse("""
					var A<
				""");
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		XtextResource res = (XtextResource) script.eResource();
		res.update(6, 0, "?");
		Script newScript = (Script) res.getContents().get(0);
		assertNotSame(script, newScript);
		assertFalse(newScript.eResource().getErrors().toString(), newScript.eResource().getErrors().isEmpty());
	}

	@Test
	public void testIDEBUG_8_01() throws Exception {
		Script script = parseHelper.parse("""
					var A<? extends
				""");
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testIDEBUG_8_02() throws Exception {
		Script script = parseHelper.parse("""
					var A<? super
				""");
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testIDEBUG_7_01() throws Exception {
		Script script = parseHelper.parse("""
					class C {}
					class D<A,> extends C {}
				""");
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(script.eResource());
	}

	@Test
	public void testIDEBUG_29_01() throws Exception {
		Script script = parseHelper.parse("""
					class A {
						method() {
							new class {
								var x: string;
							};
						}
					}
				""");
		List<Diagnostic> errorList = script.eResource().getErrors();
		assertEquals(errorList.toString(), 1, errorList.size());
		Diagnostic firstError = errorList.get(0);
		assertTrue(firstError.getMessage(), firstError.getMessage().contains("no viable alternative at input 'x'"));
	}

	@Test
	public void testIDEBUG_207_01() throws Exception {
		Script script = parseHelper.parse("""
					class A {
						method() {
							new class {
								var;
								x: string;
							};
						}
					}
				""");
		List<Diagnostic> errorList = script.eResource().getErrors();
		assertEquals(errorList.toString(), 0, errorList.size());
	}

	@Test
	public void testIDEBUG_29_02() throws Exception {
		Script script = parseHelper.parse("""
					new class {
						var x: string;
					};
				""");
		List<Diagnostic> errorList = script.eResource().getErrors();
		assertEquals(errorList.toString(), 1, errorList.size());
		Diagnostic firstError = errorList.get(0);
		assertTrue(firstError.getMessage(), firstError.getMessage().contains("no viable alternative at input 'x'"));
	}

	@Test
	public void testRecoveredIndexAccessExpression() throws Exception {
		Script script = parseHelper.parse("""
					var testArray = ["A""];
					testArray[0];
				""");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		ExpressionStatement stmt = (ExpressionStatement) last(script.getScriptElements());
		Expression expr = stmt.getExpression();
		assertTrue(String.valueOf(expr), expr instanceof IndexedAccessExpression);
		Diagnostic error = script.eResource().getErrors().get(0);
		assertEquals("no viable alternative at input '\"];'", error.getMessage());
	}

	@Test
	public void testIDE_1172_01() throws Exception {
		Script script = parseHelper.parse("""
				var s};
				""");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Diagnostic error = script.eResource().getErrors().get(0);
		assertEquals("missing EOF at '}'", error.getMessage());
		assertEquals("var s}".length() - 1, ((org.eclipse.xtext.diagnostics.Diagnostic) error).getOffset());
		assertTrue(IterableExtensions.last(script.getScriptElements()) instanceof VariableStatement);
	}

	@Test
	public void testIDE_1172_02() throws Exception {
		// var v = a + b, c + d;
		// this is tricky but valid according to the n4js java type annotation syntax:
		// the user obviously sees two binary operations where the parser sees this:
		// var v = a + b /* binary op*/,
		// <TypeRef type:"c" dynamic:"+"> <name:"d">
		// so basically two variable declarations in the same variable statement:
		// variable v with an inferred type and an initializer expression
		// variable d with the given dynamic type c
		//
		// since colon type annotation syntax is used now, this is no longer valid:

		Script script = parseHelper.parse("""
					var v = a + b,
					        c + d;
				""");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
	}

	@Test
	public void testIDE_1172_02a() throws Exception {
		// same as. testIDE_1172_02 but with colon type annotation syntax, making it less tricky

		Script script = parseHelper.parse("""
					var v = a + b,  d: c+;
				""");
		assertEquals(script.eResource().getErrors().toString(), 0, script.eResource().getErrors().size());
	}

	@Test
	public void testIDE_1172_03() throws Exception {
		Script script = parseHelper.parse("""
					var v = a + b,
					        class A { a = new A(); };
				""");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Diagnostic error = script.eResource().getErrors().get(0);
		assertEquals("no viable alternative at input 'class'", error.getMessage());
	}

	@Test
	public void testIDEBUG_384_01() throws Exception {
		Script script = parseHelper.parse("`${}\\");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Diagnostic error = script.eResource().getErrors().get(0);
		assertEquals("Template literal is not properly closed by a backtick.", error.getMessage());
	}

	@Test
	public void testIDEBUG_384_02() throws Exception {
		Script script = parseHelper.parse("\"\\");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Diagnostic error = script.eResource().getErrors().get(0);
		assertEquals("String literal is not properly closed by a double-quote.", error.getMessage());
	}

	@Test
	public void testIDEBUG_384_03() throws Exception {
		Script script = parseHelper.parse("'\\");
		assertEquals(script.eResource().getErrors().toString(), 1, script.eResource().getErrors().size());
		Diagnostic error = script.eResource().getErrors().get(0);
		assertEquals("String literal is not properly closed by a quote.", error.getMessage());
	}

	@Test
	public void testIDEBUG_281_01() throws Exception {
		String scriptAsText = """
					var class;
					var 1a = null;
				""";
		Script script = parseHelper.parse(scriptAsText);
		int insertHere = scriptAsText.indexOf("1");
		N4JSResource resource = (N4JSResource) script.eResource();

		List<Diagnostic> errors = resource.getErrors();
		assertEquals(Strings.join("\n", errors), 4, errors.size());
		resource.update(insertHere, 0, "a");
		assertEquals(2, errors.size());
		resource.update(insertHere, 0, "a");
		assertEquals(2, errors.size());
	}

	@Test
	public void testRegExWithLookAhead() {
		parseESSuccessfully("""
					var v = {
					    a: (b = "".match(/^a/)),
					    c: null
					};
				""");
	}

	@Test
	public void testIDEBUG_352_01() {
		parseESSuccessfully("""
					async function asyncParty(): number {
					    return 4;
					}

					async function doSomeStuff() {
					    var hello = "hi"
					    await asyncParty();
					    hello.charAt(0)

					    await asyncParty()
					    hello.charAt(0)

					    await asyncParty()
					}
				""");
	}

	@Test
	public void testIDEBUG_352_01_old() {
		parseESSuccessfully("""
					async function  asyncParty(): number {
					    return 4;
					}

					async function doSomeStuff() {
					    var hello = "hi"
					    await asyncParty();
					    hello.charAt(0)

					    await asyncParty()
					    hello.charAt(0)

					    await asyncParty()
					}
				""");
	}

	@Test
	public void testIDEBUG_352_02() {
		Script script = parseESWithError("""
					a >>>= b =
				""");
		ExpressionStatement stmt = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression expr = (AssignmentExpression) stmt.getExpression();
		assertEquals(AssignmentOperator.USHR_ASSIGN, expr.getOp());
	}

	@Test
	public void testIDEBUG_594_01() {
		Script script = parseESWithError("""
					function f(x: {function():type?}) {}
				""");
		FunctionDeclaration f = (FunctionDeclaration) script.getScriptElements().get(0);
		FormalParameter param = f.getFpars().get(0);
		assertEquals("x", param.getName());
		assertTrue(param.getDeclaredTypeRefInAST() instanceof FunctionTypeExpression);
		FunctionTypeExpression paramType = (FunctionTypeExpression) param.getDeclaredTypeRefInAST();
		assertTrue(paramType.getReturnTypeRef() instanceof TypeTypeRef);
		// returnType.getTypeArg is now interpreted as. wildcard, but check other AST elements:
	}

	@Test
	public void testIDEBUG_594_02() {
		Script script = parseESWithError("""
					function f(x: {function():constructor?}) {}
				""");
		FunctionDeclaration f = (FunctionDeclaration) script.getScriptElements().get(0);
		FormalParameter param = f.getFpars().get(0);
		assertEquals("x", param.getName());
		assertTrue(param.getDeclaredTypeRefInAST() instanceof FunctionTypeExpression);
		FunctionTypeExpression paramType = (FunctionTypeExpression) param.getDeclaredTypeRefInAST();
		assertTrue(paramType.getReturnTypeRef() instanceof TypeTypeRef);
		// returnType.getTypeArg is now interpreted as. wildcard, but check other AST elements:
	}

	@Test
	public void testNodeModelInvariants_01() {
		Script script = parseESWithError("""
					var ~Object with {
					var s;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
	}

	@Test
	public void testNodeModelInvariant_02() {
		Script script = parseESWithError("""
					var ~Object with {a: string; b: string;
					var s: string;
					s = r.a;
					s = r.b;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
	}

	@Test
	public void testNodeModelInvariant_03() {
		Script script = parseESWithError("""
					var ~Object with {a: string; b: string;}
					var s: string;
					s = r.a;
					s = r.b;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
	}

	@Test
	public void testNodeModelInvariant_03b() {
		Script script = parseESWithError("""
				var ~Object with {b;}
				var s;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
		assertEquals(4, ((XtextSyntaxDiagnostic) script.eResource().getErrors().get(0)).getOffset());
	}

	@Test
	public void testNodeModelInvariant_04() {
		Script script = parseESWithError("""
					export var ~Object with {a: string; b: string;}
					var s: string;
					s = r.a;
					s = r.b;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
	}

	@Test
	public void testNodeModelInvariant_05() {
		Script script = parseESWithError("""
					var ~Object with {}
					var s;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
	}

	@Test
	public void testNodeModelInvariant_06() {
		Script script = parseESWithError("""
					class A {}
					class B extends A {}
					class C extends B {}
					class D {}

					class G<T> {}
					class H<T,S> {}

					interface R {}
					interface I {}
					enum E{ LITERAL } // cannot be empty


					var ~Object with {a: A;b: B;
					var rd: ~A with{B b;}+;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
	}

	@Test
	public void testNodeModelInvariant_07() {
		Script script = parseESWithError("""
					class A {}
					class B extends A {}
					class C extends B {}
					class D {}

					class G<T> {}
					class H<T,S> {}

					interface R {}
					interface I {}
					enum E{ LITERAL } // cannot be empty


					var ~Object with {a: A;b: B;}
					var rd: ~A with{B b;}+;
				""");
		IParseResult parseResult = ((XtextResource) script.eResource()).getParseResult();
		ICompositeNode node = parseResult.getRootNode();
		assertSame(node.getRootNode(), node);
		new InvariantChecker().checkInvariant(node);
	}

	@Test
	public void testForAwaitOf01() {
		Script script = parseESWithError("""
					for await (let i = 0; i < 100; i++) {}
				""");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		assertEquals(AST_INVALID_FOR_AWAIT.name(), ((XtextLinkingDiagnostic) errors.get(0)).getCode());
	}

	@Test
	public void testForAwaitOf02() {
		Script script = parseESWithError("""
					for await (let p in []) {}
				""");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		assertEquals(AST_INVALID_FOR_AWAIT.name(), ((XtextLinkingDiagnostic) errors.get(0)).getCode());
	}

	@Test
	public void testForAwaitOf03() {
		parseESSuccessfully("""
					for await (let p of []) {}
				""");
	}
}
